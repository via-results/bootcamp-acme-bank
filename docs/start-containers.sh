#!/bin/bash

set -e

echo "🚀 Limpando e construindo todos os módulos Maven..."
mvn clean install -DskipTests

echo "🚀 FInalizando todos os serviços..."
docker compose down

echo "🐳 Subindo todos os serviços com Docker Compose..."
docker compose up -d --build
