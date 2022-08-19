resource "aws_db_instance" "default" {
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8.0.28"
  instance_class       = "db.t3.micro"
  identifier           = "employee3"
  username             = "admin"
  password             = "admin123"
  skip_final_snapshot  = true
  publicly_accessible  = true
  db_name              = "employee"
  vpc_security_group_ids = ["sg-0c92917a4049144ef"]
}