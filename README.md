# contas-pagar
API Spring Boot JWT

# Para obter Token
	POST api/sign-up Content-Type: application/json
    {
     username = cliente
     password = 123
    }

# Infos do endpoint csv para ser importado
    POST api/contas-pagar/v1/conta/import-pendentes Content-Type: application/octet-stream
    arquivo: src/main/resources/import-csv/import-pendentes.csv

# Para realizar Build Maven
	mvn clean package -f pom.xml

# Para realizar Deploy Docker
	sudo docker-compose up --build

# Swagger
	http://localhost:8080/api/swagger-ui.html

# Docker
	https://hub.docker.com/r/mespindula/contas-pagar
