# springboot-cicd
Springboot Project


###Usefull-command:
#### langkah nyambungin ke postgre
```docker pull postgres```

```docker run --name postgres -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=db_name -d postgres```

```docker ps -a```

Buat env postgres di server
```nano postgres.env```

Buat network
```docker network create network_name```
contoh: ```docker network create network1```

```docker network ls```


```docker network connect network1 container_id_postgres```
Contoh : ```docker network connect network1 65290a9878b7```

Cek container, seharusnya ada postgres
```docker network inspect network1```

CICD flow ubah script run
```docker run -d --name springboot-cicd -p 443:8080 -p 80:8080 --env-file postgres.env --network network1 doofensmith/springboot-cicd:latest```
