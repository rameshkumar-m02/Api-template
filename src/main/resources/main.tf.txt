provider "aws" {
  region = var.aws_region
  access_key  = var.aws_access_key
  secret_key  = var.aws_secret_key
}

resource "aws_iam_role" "jenkins_role" {
  name = "jenkins_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "jenkins_attachment" {
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
  role       = aws_iam_role.jenkins_role.name
}

resource "aws_iam_instance_profile" "jenkins_profile" {
  name = "jenkins_profiles"
  role = aws_iam_role.jenkins_role.name
}

resource "aws_security_group" "jenkins_security_group" {
  name        = "jenkins_security_group"
  description = "Security group for Jenkins server"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"  # Allow all inbound traffic
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"  # Allow all outbound traffic
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_instance" "jenkins_server" {
  ami           = "ami-06aa3f7caf3a30282"
  instance_type = var.aws_jenkins_server_type

  iam_instance_profile = aws_iam_instance_profile.jenkins_profile.name

  vpc_security_group_ids = [aws_security_group.jenkins_security_group.id]

  tags = {
    Name = "jenkins_instance"
  }
} 
