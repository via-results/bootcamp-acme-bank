aws dynamodb create-table \
    --table-name client \
    --attribute-definitions \
        AttributeName=id,AttributeType=N \
        AttributeName=email,AttributeType=S \
        AttributeName=document,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --global-secondary-indexes '[
        {
            "IndexName": "email-index",
            "KeySchema": [
                {"AttributeName":"email","KeyType":"HASH"}
            ],
            "Projection": {"ProjectionType":"ALL"},
            "ProvisionedThroughput": {
                "ReadCapacityUnits": 5,
                "WriteCapacityUnits": 5
            }
        },
        {
            "IndexName": "document-index",
            "KeySchema": [
                {"AttributeName":"document","KeyType":"HASH"}
            ],
            "Projection": {"ProjectionType":"ALL"},
            "ProvisionedThroughput": {
                "ReadCapacityUnits": 5,
                "WriteCapacityUnits": 5
            }
        }
    ]' \
    --endpoint-url http://localhost:4566 --region us-east-1
