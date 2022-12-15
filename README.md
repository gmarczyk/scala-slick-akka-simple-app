# Scala backend simple app


### Launch via docker

To launch the backend, follow these steps:

1. Clone the project
   ```sh
   git clone git@github.com:gmarczyk/scala-slick-akka-simple-app.git
   ```
2. Cd to repository folder ./Marczyk_Scala_BI and execute
   ```sh
   docker-compose build
   docker-compose up
   ```

PostgreSQL server with database named marczyk_bi should start, 
along with the application available at localhost:8080 

One should be able to retrieve sample books via 
GET localhost:8080/books