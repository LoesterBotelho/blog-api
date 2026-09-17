```
docker volume create mariadb_data
```

```
docker run -d --name meu_mariadb -p 3306:3306 -e MARIADB_ROOT_PASSWORD=root -v mariadb_data:/var/lib/mysql --restart unless-stopped mariadb:11.8
```

```
docker exec -it meu_mariadb mariadb -u root -proot -e "CREATE DATABASE IF NOT EXISTS blogappdb;"
```

```
docker exec -it meu_mariadb mariadb -u root -proot
```

```
#spring.datasource.url=jdbc:mysql://localhost/blogappdb?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo&SameSite=None

spring.datasource.url=jdbc:mariadb://localhost:3306/blogappdb?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root
```