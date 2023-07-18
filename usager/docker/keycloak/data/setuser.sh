#!/bin/bash

KEYCLOAK_HOST=http://localhost:8180
REALM_NAME=usager
KEYCLOAK_ADMIN_USER=admin
KEYCLOAK_ADMIN_PASSWORD=admin

# Get admin token
ADMIN_TOKEN=$(curl -s -X POST "$KEYCLOAK_HOST/auth/realms/master/protocol/openid-connect/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=$KEYCLOAK_ADMIN_USER" \
 -d "password=$KEYCLOAK_ADMIN_PASSWORD" \
 -d 'grant_type=password' \
 -d 'client_id=admin-cli' | jq -r '.access_token')

# Create or update user
function manage_user {
  USER_JSON_FILE=$1
  USER_ID=$(jq -r '.id' $USER_JSON_FILE)

  if [[ $(curl -s -o /dev/null -w "%{http_code}" -X GET "$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users/$USER_ID" \
   -H "Authorization: Bearer $ADMIN_TOKEN") == "200" ]]; then
    # User exists, update user
    curl -s -X PUT "$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users/$USER_ID" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -d @$USER_JSON_FILE
  else
    # User does not exist, create user
    curl -s -X POST "$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -d @$USER_JSON_FILE
  fi
}

manage_user /var/tmp/users.json
