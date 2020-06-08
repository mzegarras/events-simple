

### Events

Notificar la creación de uno o muchos wallet y enviarla mediante un webhook.
![Alt text](images/webhook.png?raw=true "WebHook")

Revisar mongo 
![Alt text](images/mongo.png?raw=true "Mongo")


## Pasos
* docker-compose up
* [Mongo - root/pwd](http://localhost:8081)
* [Rabbit - root/pwd](http://localhost:8082)
* Crea tu webhook en https://pipedream.com/
* [Instala siege opcional](https://github.com/JoeDog/siege)
* Inicia el proyecto
```
mvn spring-boot:run
```
* 1 petición
```
POST http://localhost:8080/wallets
Content-Type: application/json

{
  "wallet": {
    "type" : "PREPAID",
    "currency": "PEN",
    "description": "Wallet Prepago",
    "customer": {
      "id": 12345
    }
  }
}
```
* 200 peticiones
```
siege -r 2 -c 100 -d 1  -v -H "X-Api-Force-Sync: false" --content-type 'application/json' "http://localhost:8080/wallets POST {
  \"wallet\": {
   \"customer\": {
     \"id\": 12345
   },
   \"type\" : \"PREPAID\",
   \"currency\": \"PEN\",
   \"description\": \"Wallet Prepago\"
  }
}"
```