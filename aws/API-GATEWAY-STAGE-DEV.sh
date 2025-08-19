#!/bin/bash
set -e

# Variables
LS_URL="http://localhost:4566"
REGION="us-east-1"
BACKEND_CLIENTS_URL="http://host.docker.internal:8091/api/v1/clients"
BACKEND_ACCOUNTS_URL="http://host.docker.internal:8090/api/v1/accounts"
BACKEND_TRANSACTIONS_URL="http://host.docker.internal:8092/api/v1/transactions"

#####################################
# 1. Create REST API
#####################################
API_ID=$(aws --endpoint-url=$LS_URL apigateway create-rest-api \
  --name clients-api \
  --region $REGION \
  --query 'id' --output text)

echo "API created: $API_ID"

#####################################
# 2. Create root resource
#####################################
ROOT_ID=$(aws --endpoint-url=$LS_URL apigateway get-resources \
  --rest-api-id $API_ID \
  --region $REGION \
  --query 'items[0].id' --output text)

#####################################
# 3. Create resource/clients
#####################################
CLIENTS_RES_ID=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID \
  --region $REGION \
  --parent-id $ROOT_ID \
  --path-part "clients" \
  --query 'id' --output text)

echo "Resource /clients created: $CLIENTS_RES_ID"

#####################################
# 3. Create resource /accounts
#####################################
ACCOUNTS_RES_ID=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID \
  --region $REGION \
  --parent-id $ROOT_ID \
  --path-part "accounts" \
  --query 'id' --output text)

echo "Resource /accounts created: $ACCOUNTS_RES_ID"

#####################################
# 3. Create resource /transactions
#####################################
TRANSACTIONS_RES_ID=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID \
  --region $REGION \
  --parent-id $ROOT_ID \
  --path-part "transactions" \
  --query 'id' --output text)

echo "Resource /transactions created: $TRANSACTIONS_RES_ID"

#####################################
# Methods  /clients
#####################################

# GET /clients
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method GET \
  --authorization-type NONE

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method GET \
  --type HTTP_PROXY --integration-http-method GET \
  --uri "$BACKEND_CLIENTS_URL"

# POST /clients
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method POST \
  --authorization-type NONE

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method POST \
  --type HTTP_PROXY --integration-http-method POST \
  --uri "$BACKEND_CLIENTS_URL"

# DELETE /clients
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method DELETE \
  --authorization-type NONE

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENTS_RES_ID --http-method DELETE \
  --type HTTP_PROXY --integration-http-method DELETE \
  --uri "$BACKEND_CLIENTS_URL"

#####################################
# 4. Subresource /clients/{id}
#####################################
CLIENT_ID_RES=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID --region $REGION \
  --parent-id $CLIENTS_RES_ID --path-part "{id}" \
  --query 'id' --output text)

# GET /clients/{id}
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENT_ID_RES --http-method GET \
  --authorization-type NONE \
  --request-parameters "method.request.path.id=true"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENT_ID_RES --http-method GET \
  --type HTTP_PROXY --integration-http-method GET \
  --uri "$BACKEND_CLIENTS_URL/{id}" \
  --request-parameters "integration.request.path.id=method.request.path.id"

# DELETE /clients/{id}
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENT_ID_RES --http-method DELETE \
  --authorization-type NONE \
  --request-parameters "method.request.path.id=true"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $CLIENT_ID_RES --http-method DELETE \
  --type HTTP_PROXY --integration-http-method DELETE \
  --uri "$BACKEND_CLIENTS_URL/{id}" \
  --request-parameters "integration.request.path.id=method.request.path.id"

#####################################
# 5. Subresource /clients/email/{email}
#####################################

EMAIL_RES=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID --region $REGION \
  --parent-id $CLIENTS_RES_ID --path-part "email" \
  --query 'id' --output text)

EMAIL_PARAM_RES=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID --region $REGION \
  --parent-id $EMAIL_RES --path-part "{email}" \
  --query 'id' --output text)

aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $EMAIL_PARAM_RES --http-method GET \
  --authorization-type NONE \
  --request-parameters "method.request.path.email=true"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $EMAIL_PARAM_RES --http-method GET \
  --type HTTP_PROXY --integration-http-method GET \
  --uri "$BACKEND_CLIENTS_URL/email/{email}" \
  --request-parameters "integration.request.path.email=method.request.path.email"

#####################################
# 6. Subresource /clients/document/{document}
#####################################

DOC_RES=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID --region $REGION \
  --parent-id $CLIENTS_RES_ID --path-part "document" \
  --query 'id' --output text)

DOC_PARAM_RES=$(aws --endpoint-url=$LS_URL apigateway create-resource \
  --rest-api-id $API_ID --region $REGION \
  --parent-id $DOC_RES --path-part "{document}" \
  --query 'id' --output text)

aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $DOC_PARAM_RES --http-method GET \
  --authorization-type NONE \
  --request-parameters "method.request.path.document=true"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID --region $REGION \
  --resource-id $DOC_PARAM_RES --http-method GET \
  --type HTTP_PROXY --integration-http-method GET \
  --uri "$BACKEND_CLIENTS_URL/document/{document}" \
  --request-parameters "integration.request.path.document=method.request.path.document"


#####################################
# Methods /accounts
#####################################

# POST /accounts
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method POST \
  --authorization-type "NONE"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method POST \
  --type HTTP_PROXY \
  --integration-http-method POST \
  --uri $BACKEND_ACCOUNTS_URL \
  --passthrough-behavior WHEN_NO_MATCH

  echo "Resource /accounts created with ID: $ACCOUNTS_RES_ID"

# GET /accounts/number
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method GET \
  --authorization-type "NONE"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $ACCOUNTS_RES_ID \
  --http-method GET \
  --type HTTP_PROXY \
  --integration-http-method GET \
  --uri $BACKEND_ACCOUNTS_URL/check-balance

echo "Method GET /accounts/check-balance configured"

#####################################
# Methods  /transactions
#####################################

# Method POST /transactions
aws --endpoint-url=$LS_URL apigateway put-method \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $TRANSACTIONS_RES_ID \
  --http-method POST \
  --authorization-type "NONE"

aws --endpoint-url=$LS_URL apigateway put-integration \
  --rest-api-id $API_ID \
  --region $REGION \
  --resource-id $TRANSACTIONS_RES_ID \
  --http-method POST \
  --type HTTP_PROXY \
  --integration-http-method POST \
  --uri $BACKEND_TRANSACTIONS_URL

echo "Method POST /transactions configured"

#####################################
# 7. Deploy
#####################################
aws --endpoint-url=$LS_URL apigateway create-deployment \
  --rest-api-id $API_ID --region $REGION --stage-name dev

echo "API Gateway configured successfully!"
echo "Base URL: $LS_URL/restapis/$API_ID/dev/_user_request_/clients"
