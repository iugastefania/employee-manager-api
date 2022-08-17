resource "aws_instance" "web" {
  ami           = "ami-0f51be0cf2e87b06c"
  instance_type = "t2.micro"
  key_name = "emp"
  vpc_security_group_ids = ["sg-02c8a7439bd6661ae"]
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name
}
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = aws_iam_role.ec2_role.name
}