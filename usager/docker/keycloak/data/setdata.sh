#!/bin/bash
KEYCLOAK_URL=http://localhost:8180/

until $(curl --output /dev/null --silent --head --fail $KEYCLOAK_URL); do
    printf '.'
    sleep 5
done

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
genie_sherbrooke_id=$(/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Genie" -i)
gestion_sherbrooke_id=$(/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Gestion" -i)
droit_sherbrooke_id=$(/opt/keycloak/bin/kcadm.sh create groups/$sherbrooke_group_id/children -r usager -s name="Faculte de Droit" -i)

laval_group_id=$(/opt/keycloak/bin/kcadm.sh create groups -r usager -s name="Universite De Laval" -i)
genie_laval_id=$(/opt/keycloak/bin/kcadm.sh create groups/$laval_group_id/children -r usager -s name="Faculte de Genie" -i)
medecine_laval_id=$(/opt/keycloak/bin/kcadm.sh create groups/$laval_group_id/children -r usager -s name="Faculte de Medecine" -i)



#UNIVERSITE DE SHERBROOKE
master0102_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=master0102)
master0102_id=$(echo "$master0102_info" | grep -Po '"id"\s*:\s*"\K[^"]+')
/opt/keycloak/bin/kcadm.sh update users/$master0102_id/groups/$sherbrooke_group_id -r usager

#FACULTE DE GENIE
fac_genie_sherbrooke=("pald2501" "aubj1202" "aubo1502" "barr1306" "amae2901" "bele0801" "bele1103" "bild2707" "boie0601" "bils2704" "boof2101" "bour0703" "brel0901" "cake0801" "canb1801" "cany2101")
# Loop through each user
for username in "${fac_genie_sherbrooke[@]}"; do
    user_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=$username)
    user_id=$(echo $user_info | grep -Po '"id" *: *"[^"]*"' | cut -d':' -f2 | tr -d ' ",')
    /opt/keycloak/bin/kcadm.sh update users/$user_id/groups/$genie_sherbrooke_id -r usager
done

#FACULTE DE DROIT
fac_droit_sherbrooke=("cars1804" "houy2303" "jace1402" "jans2001" "keib3201" "keif1201" "kilv1201" "labc0301" "labg0902" "laby1302" "laft1301" "lals1003" "lamg0502" "lanj2131" "lant1401" )
# Loop through each user
for username in "${fac_droit_sherbrooke[@]}"; do
    user_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=$username)
    user_id=$(echo $user_info | grep -Po '"id" *: *"[^"]*"' | cut -d':' -f2 | tr -d ' ",')
    /opt/keycloak/bin/kcadm.sh update users/$user_id/groups/$droit_sherbrooke_id -r usager
done


#FACULTE DE GESTION
fac_gestion_sherbrooke=("maif1401" "lavd2311" "lavm1927" "lavm2134" "lebg2708" "gell3101" "gerz0501" "guea0902" "hasa3302" "durp2003" "farg2101" "carv0701" "caua1101" "chab1704" "clof1603" "cotr3901" "dufd2802" )
# Loop through each user
for username in "${fac_gestion_sherbrooke[@]}"; do
    user_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=$username)
    user_id=$(echo $user_info | grep -Po '"id" *: *"[^"]*"' | cut -d':' -f2 | tr -d ' ",')
    /opt/keycloak/bin/kcadm.sh update users/$user_id/groups/$gestion_sherbrooke_id -r usager
done

#UNIVERSITE DE LAVAL
laval_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=laval)
laval_id=$(echo "$laval_info" | grep -Po '"id"\s*:\s*"\K[^"]+')
/opt/keycloak/bin/kcadm.sh update users/$laval_id/groups/$laval_group_id -r usager

#FACULTE DE GENIE
fac_genie_laval=("beab1802" "turv5324" "pagm1302" "lede2401" "rerm1001" "robw1901" "ronk2602" "roua0701" "sehk2201" )
for username in "${fac_genie_laval[@]}"; do
    user_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=$username)
    user_id=$(echo $user_info | grep -Po '"id" *: *"[^"]*"' | cut -d':' -f2 | tr -d ' ",')
    /opt/keycloak/bin/kcadm.sh update users/$user_id/groups/$genie_laval_id -r usager
done

#FACULTE DE MEDECINE
fac_medecine_laval=("gaud1401" "sevm1802" "sinn1901" "sowa0801" "stao0901" "stds2101" "thip0901" "trew1501" "tria1001" "trus1706" )
for username in "${fac_medecine_laval[@]}"; do
    user_info=$(/opt/keycloak/bin/kcadm.sh get users -r usager -q username=$username)
    user_id=$(echo $user_info | grep -Po '"id" *: *"[^"]*"' | cut -d':' -f2 | tr -d ' ",')
    /opt/keycloak/bin/kcadm.sh update users/$user_id/groups/$medecine_laval_id -r usager
done

echo -e -n "\r"
echo "server running ...."
