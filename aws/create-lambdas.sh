
# Create lamnda

aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name dlqProcessor \
  --region us-east-1 \
  --runtime java17 \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --handler org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest \
  --zip-file fileb://target/ms-processor-1.0-SNAPSHOT.jar


# Associeted lambda to sqs
aws --endpoint-url=http://localhost:4566 lambda create-event-source-mapping \
  --function-name dlqProcessor \
  --region us-east-1 \
  --batch-size 1 \
  --event-source-arn arn:aws:sqs:us-east-1:000000000000:transactions-accounts-dlq