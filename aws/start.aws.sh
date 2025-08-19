#!/bin/bash
set -e

echo "==== Criando recursos no LocalStack ===="

# DynamoDB table
aws dynamodb create-table \
    --table-name client \
    --attribute-definitions AttributeName=id,AttributeType=N \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=3 \
    --endpoint-url http://localhost:4566 --region us-east-1

# SNS Topic
aws sns create-topic \
    --name transactions \
    --endpoint-url=http://localhost:4566 --region us-east-1

# SQS queues
aws sqs create-queue \
    --queue-name transactions-accounts \
    --endpoint-url=http://localhost:4566 --region us-east-1

aws sqs create-queue \
    --queue-name transactions-accounts-dlq \
    --endpoint-url=http://localhost:4566 --region us-east-1

# Subscribe SQS to SNS
aws sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:000000000000:transactions \
    --protocol sqs \
    --notification-endpoint arn:aws:sqs:us-east-1:000000000000:transactions-accounts \
    --endpoint-url=http://localhost:4566 --region us-east-1

# Set DLQ policy
aws sqs set-queue-attributes \
    --queue-url http://localhost:4566/000000000000/transactions-accounts \
    --attributes '{"RedrivePolicy":"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:transactions-accounts-dlq\",\"maxReceiveCount\":\"3\"}"}' \
    --endpoint-url=http://localhost:4566 --region us-east-1


## create cloudwatch logs
aws --endpoint-url=http://localhost:4566 logs create-log-group --log-group-name ms-transactions-logs --region us-east-1
aws --endpoint-url=http://localhost:4566 logs create-log-stream --log-group-name ms-transactions-logs --log-stream-name transactions-stream --region us-east-1

echo "==== Recursos criados com sucesso ===="
