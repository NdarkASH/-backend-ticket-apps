#!/bin/bash

echo "Testing Keycloak Integration..."

# Test 1: Get token from Keycloak directly
echo "1. Getting token from Keycloak..."
TOKEN_RESPONSE=$(curl -s -X POST http://localhost:9090/realms/tickets/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=tickets-client" \
  -d "client_secret=YOUR_CLIENT_SECRET" \
  -d "username=organizer" \
  -d "password=password")

echo "Token Response: $TOKEN_RESPONSE"

# Extract token
TOKEN=$(echo "$TOKEN_RESPONSE" | jq -r '.access_token')

# Test 2: Use token with your API
echo -e "\n2. Testing API with token..."
curl -X GET http://localhost:8080/api/v1/events \
  -H "Authorization: Bearer $TOKEN"

# Test 3: Test your login endpoint
echo -e "\n3. Testing login endpoint..."
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"organizer","password":"password"}'