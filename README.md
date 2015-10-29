Demonstration of DATAGRAPH-781
==============================

Start the app:

    mvn spring-boot:run

To create a user:

    curl http://192.168.1.79:8080/create-user
    
To login:

    curl -X POST http://localhost:8080/oauth/token -u client:password -d grant_type=password -d username=test@example.com -d password=1234 -i
    

