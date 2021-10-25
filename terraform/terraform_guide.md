서버에 환경 설정을 우선으로 해야한다.  
`export AWS_ACCESS_KEY_ID=myAccessKey;export AWS_SECRET_ACCESS_KEY=mySecretKey;export AWS_DEFAULT_REGION="ap-northeast-2"`

테라폼 세팅 초기화  
`terraform init`

테라폼 세팅 적용  
`terraform plan -out 테라폼플랜.tfplan`

변수도 함께 지정하려면 이렇게 한다.  
`terraform plan -var 변수명="값" -var 변수명="값" -out 테라폼플랜.tfplan`

프로비저닝 시작  
`terraform apply 테라폼플랜.tfplan`

인스턴스 삭제  
`terraform destroy`

ssh 접근에 필요한 키 생성  
`ssh-keygen -t rsa -b 4096 -N "" -f 키이름`

비밀키를 복사한 후에 권한 변경  
`cp ./키이름 ~/키이름;sudo chmod 700 ~/키이름`

이제 ssh 접속 가능  
`이후 ssh -i ~/키이름 ec2-user@생성한ip`

유저 데이터를 설정하면 만들자 마자 유저데이터에 입력된 내용을 수행 가능하다.
