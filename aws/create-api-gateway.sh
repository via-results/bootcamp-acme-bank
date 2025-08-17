#!/bin/bash
set -e

# ------------------------------
# 1. Criar API REST
# ------------------------------
API_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-rest-api \
  --name gtw-acme-bank \
  --region us-east-1 \
  --query "id" \
  --output text)

echo "API created with ID: $API_ID"

# Root resource "/"
PARENT_ID=$(aws --endpoint-url=http://localhost:4566 apigateway get-resources \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --query "items[0].id" \
  --output text)

# ------------------------------
# 2. Transactions Resource
# ------------------------------
TRANSACTIONS_RES_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --parent-id $PARENT_ID \
  --path-part transactions \
  --query "id" \
  --output text)

echo "Resource /transactions created with ID: $TRANSACTIONS_RES_ID"

# Método POST /transactions
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $TRANSACTIONS_RES_ID \
  --http-method POST \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $TRANSACTIONS_RES_ID \
  --http-method POST \
  --type HTTP_PROXY \
  --integration-http-method POST \
  --uri "http://host.docker.internal:8092/api/v1/transactions"

echo "Method POST /transactions configured"

# ------------------------------
# 3. Accounts Resource
# ------------------------------
ACCOUNTS_RES_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --parent-id $PARENT_ID \
  --path-part accounts \
  --query "id" \
  --output text)

echo "Resource /accounts created with ID: $ACCOUNTS_RES_ID"

# Método GET /accounts/check-balance
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method GET \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method GET \
  --type HTTP_PROXY \
  --integration-http-method GET \
  --uri "http://host.docker.internal:8090/api/v1/accounts/check-balance"

echo "Method GET /accounts/check-balance configured"

# Método POST /accounts
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method POST \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method POST \
  --type HTTP_PROXY \
  --integration-http-method POST \
  --uri "http://host.docker.internal:8090/api/v1/accounts" \
  --passthrough-behavior WHEN_NO_MATCH

echo "Method POST /accounts configured"

# ------------------------------
# 4. Clients Resource
# ------------------------------
CLIENTS_RES_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --parent-id $PARENT_ID \
  --path-part clients \
  --query "id" \
  --output text)

echo "Resource /clients created with ID: $CLIENTS_RES_ID"

# (a) GET /clients
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENTS_RES_ID \
  --http-method GET \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENTS_RES_ID \
  --http-method GET \
  --type HTTP_PROXY \
  --integration-http-method GET \
  --uri "http://host.docker.internal:8091/api/v1/clients"

echo "Method GET /clients configured"

# (b) POST /clients
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
  --region us-east-1 \
  --resource-id $CLIENTS_RES_ID \
  --http-method POST \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
  --region us-east-1 \
  --resource-id $CLIENTS_RES_ID \
  --http-method POST \
  --type HTTP_PROXY \
  --integration-http-method POST \
  --uri "http://host.docker.internal:8091/api/v1/clients" \
  --passthrough-behavior WHEN_NO_MATCH

echo "Method POST /clients configured"

# (c) /clients/{id}
CLIENT_BY_ID_RES_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --parent-id $CLIENTS_RES_ID \
  --path-part "{id}" \
  --query "id" \
  --output text)

echo "Resource /clients/{id} created with ID: $CLIENT_BY_ID_RES_ID"

# DELETE /clients/{id}
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENT_BY_ID_RES_ID \
  --http-method DELETE \
   --request-parameters "method.request.path.id=true" \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENT_BY_ID_RES_ID \
  --http-method DELETE \
  --type HTTP_PROXY \
  --integration-http-method DELETE \
  --request-parameters "integration.request.path.id=method.request.path.id" \
  --uri "http://host.docker.internal:8091/api/v1/clients/{id}"

echo "Method DELETE /clients/{id} configured"

# GET /clients/{id}
aws --endpoint-url=http://localhost:4566 apigateway put-method \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENT_BY_ID_RES_ID \
  --http-method GET \
  --authorization-type "NONE"

aws --endpoint-url=http://localhost:4566 apigateway put-integration \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --resource-id $CLIENT_BY_ID_RES_ID \
  --http-method GET \
  --type HTTP_PROXY \
  --integration-http-method GET \
   --request-parameters "integration.request.path.id=method.request.path.id" \
  --uri "http://host.docker.internal:8091/api/v1/clients/{id}"

echo "Method GET /clients/{id} configured"

# ------------------------------
# 5. Deploy da API
# ------------------------------
aws --endpoint-url=http://localhost:4566 apigateway create-deployment \
  --rest-api-id $API_ID \
   --region us-east-1 \
  --stage-name dev

echo "API enabled on: http://localhost:4566/restapis/$API_ID/dev/_user_request_/"

## API enabled on: http://localhost:4566/restapis/inlpqws3dm/dev/_user_request_/
