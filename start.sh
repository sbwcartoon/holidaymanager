#!/bin/bash

set -e

if command -v docker compose &> /dev/null; then
  COMPOSE_CMD="docker compose"
elif command -v docker-compose &> /dev/null; then
  COMPOSE_CMD="docker-compose"
else
  echo "docker compose or docker-compose not installed"
  exit 1
fi

echo "Using: $COMPOSE_CMD"

### execute
$COMPOSE_CMD -f docker-compose.yml up -d --build
