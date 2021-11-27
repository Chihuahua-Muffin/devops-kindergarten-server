#!/bin/bash

# 개인 키 생성 방법
:<<'END'
keyName=$1$2_key
keyLocation=~/${keyName}

ssh-keygen -t rsa -b 4096 -N "" -f ${keyName}
cp ./${keyName} ~/${keyName};sudo chmod 700 ~/${keyName}

keyVal=`cat ~/${keyName}`
END

terraform init
terraform plan -var userName=$1 -var userId=$2 -out student.tfplan

terraform apply student.tfplan

piip=`cat terraform.tfstate | grep "private_ip\""`
puip=`cat terraform.tfstate | grep "public_ip\""`

#curl -d "privateIp=${piip}&publicIp=${puip}" -X POST http://localhost:8080/api/register/ip
curl -X PUT -H "Content-Type: application/json; charset=utf-8" -d '{"userId":'"$2"', "publicIp":'"$puip"'}' http://localhost:8080/api/user/{$2}/ip

rm terraform.tfstate
rm terraform.tfstate.backup
rm student.tfplan

# 개인 키 사용하는 경우
# curl -d "privateIp=${piip}&publicIp=${puip}&keyVal=${keyVal}" -X POST http://localhost:8080/api/register/ip
