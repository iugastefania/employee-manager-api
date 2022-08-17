cd terraform/
terraform apply -target=aws_db_instance.default --auto-approve
terraform apply -target=aws_ecr_repository.foo --auto-approve

cd ..
aws ecr get-login-password --region eu-west-3 | docker login --username AWS --password-stdin 803534243049.dkr.ecr.eu-west-3.amazonaws.com
export DB_HOST=`aws rds --region eu-west-3 describe-db-instances --query "DBInstances[0].Endpoint.Address" | tr -d '"'`
mvn clean install

docker build . -t employee2 --build-arg db_host=`aws rds --region eu-west-3 describe-db-instances --query "DBInstances[0].Endpoint.Address" | tr -d '"'`
docker tag employee2:latest 803534243049.dkr.ecr.eu-west-3.amazonaws.com/employee2:latest
docker push 803534243049.dkr.ecr.eu-west-3.amazonaws.com/employee2:latest

cd terraform/
terraform apply --auto-approve

sleep 10s

v=`aws ecs list-tasks --cluster "default" --output text --query taskArns[0]`
if  [ $v != "None" ]; then
    aws ecs stop-task --task $v > stop2.txt
fi
aws ecs run-task --cluster "default" --task-definition task2 > run.txt