#!/bin/bash

# Install jq if not already installed
if ! command -v jq &> /dev/null
then
    echo "jq could not be found"
    echo "Installing jq..."
    sudo apt-get update
    sudo apt-get install -y jq

fi

timeout 300 /bin/bash -c 'until curl -sI -o /dev/null -w "%{http_code}\n" localhost:8180; do sleep 5; done;'

echo "beginning of finalisation ...."
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8180/ --realm master --user admin --password admin
/opt/keycloak/bin/kcadm.sh create realms -s realm=usager -s enabled=true -o
/opt/keycloak/bin/kcadm.sh create clients -r usager -f /var/tmp/frontend.json
/opt/keycloak/bin/kcadm.sh create clients -r usager -f /var/tmp/backend.json
/opt/keycloak/bin/kcadm.sh create partialImport -r usager -s ifResourceExists=OVERWRITE -f /var/tmp/users.json

sherbrooke_group_id=$(/opt/keycloak/bin/kcadm.sh create groups -r usager -s name="Universite De Sherbrooke" -i)
/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Genie"
/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Gestion"
/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Droit"

laval_group_id=$(/opt/keycloak/bin/kcadm.sh create groups -r usager -s name="Universite De Laval" -i)
/opt/keycloak/bin/kcadm.sh create groups/$laval_group_id/children -r usager -s name="Faculte de Genie"
/opt/keycloak/bin/kcadm.sh create groups/$laval_group_id/children -r usager -s name="Faculte de Medecine"

bour0703_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=bour0703)
bour0703_id=$(echo $bour0703_info | grep -Po '"id":.*?[^\\]",' | cut -d':' -f2 | tr -d ' ",')

echo "bour0703's ID: $bour0703_id"

master0102_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=master0102)
master0102_id=$(echo $master0102_info | grep -Po '"id":.*?[^\\]",' | cut -d':' -f2 | tr -d ' ",')

echo "master0102's ID: $master0102_id"


echo -e -n "\r"
echo "server running ...."
