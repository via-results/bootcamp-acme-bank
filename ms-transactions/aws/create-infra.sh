## create topic
aws --endpoint-url=http://localhost:4566 sns create-topic --name transactions --region us-east-1

#create queue
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name transactions-accounts --region us-east-1

# create DLQ
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name transactions-accounts-dlq --region us-east-1

#association sqs on sns
aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn arn:aws:sns:us-east-1:000000000000:transactions \
  --protocol sqs \
  --notification-endpoint arn:aws:sqs:us-east-1:000000000000:transactions-accounts --region east-1

#association sqs on dlq
aws --endpoint-url=http://localhost:4566 sqs set-queue-attributes \
  --queue-url http://localhost:4566/000000000000/transactions-account \
  --attributes '{"RedrivePolicy":"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:transactions-accounts-dlq\",\"maxReceiveCount\":\"3\"}"}'


## create cloudwatch logs

aws --endpoint-url=http://localhost:4566 logs create-log-group --log-group-name ms-transactions-logs

aws --endpoint-url=http://localhost:4566 logs create-log-stream --log-group-name ms-transactions-logs --log-stream-name transactions-stream


