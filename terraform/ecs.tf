resource "aws_ecs_task_definition" "service" {
  family = "task3"
  requires_compatibilities = ["EC2"]

  container_definitions = jsonencode([
    {
      name      = "employee_container2"
      image     = aws_ecr_repository.foo.repository_url
      memory    = 500
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
    }
  ])

}