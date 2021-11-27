variable "userName" {
  type = string
  default = "dUser"
}

variable "userId" {
    type = string
    default = "dId"
}

# variable "getKey" {
#     type = string
# }

resource "aws_key_pair" "key" {
    key_name = "${var.userId}-key"
    public_key = file("./ec2Key.pub")
}

resource "aws_instance" "web" {
    # name = "${var.userName}-${var.userId}-ec2"
    // amazon-linux2의 ami
    ami = "ami-0e4a9ad2eb120e054"
    instance_type = "t2.micro"
    // 보안 그룹 이름
    security_groups = ["launch-wizard-3"]
    key_name = "${var.userId}-key"
    tags = {
        Name = "${var.userName}-${var.userId}-ec2"
    }
}

resource "null_resource" "start" {
    // aws_instance가 생성된 이후에 실행되어야 하므로 실행 순서를 설정한다.
    depends_on = [aws_instance.web]

    connection {
        type = "ssh"
        user = "ec2-user"
        private_key = file("./ec2Key")
        host = aws_instance.web.public_ip
        // 연결이 안 될 때를 위해 설정
        timeout = "5m"
    }

    provisioner "remote-exec" {
        // 나중에는 프로비저닝 하는 방향으로 진행
        inline = [
            "until [ -f /var/lib/cloud/instance/boot-finished ]; do sleep 1; done",
            "sudo yum update -y",
            "sudo yum install git -y",
            "curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash",
            ". ~/.nvm/nvm.sh",
            "nvm install node",
            "git clone https://github.com/Chihuahua-Muffin/devops-kindergarten-terminal-server.git",
            "cd devops-kindergarten-terminal-server",
            "npm install",
            "nohup node index > applog.log &",
        ]
    }
}
