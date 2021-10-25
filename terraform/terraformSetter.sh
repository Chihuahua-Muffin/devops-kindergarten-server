#!/bin/bash

# aws_env_var setter
:<<'END'
echo -e "AWS_ACCESS_KEY_ID : c "
read AWS_ACCESS_KEY_ID
echo -e "AWS_SECRET_ACCESS_KEY : c"
read AWS_SECRET_ACCESS_KEY

export AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} >> ~/.bashrc
export AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} >> ~/.bashrc
export AWS_DEFAULT_REGION="ap-northeast-2" >> ~/.bashrc
source ~/.bashrc

echo $AWS_ACCESS_KEY_ID
echo $AWS_SECRET_ACCESS_KEY
echo $AWS_DEFAULT_REGION
END

# terraform setter
sudo yum update
sudo yum install -y yum-utils
sudo yum-config-manager --add-repo https://rpm.releases.hashicorp.com/AmazonLinux/hashicorp.repo
sudo yum -y install terraform

# 학생 공통 키
ssh-keygen -t rsa -b 4096 -N "" -f ec2Key
cp ./ec2Key ~/ec2Key
sudo chmod 700 ~/ec2Key
