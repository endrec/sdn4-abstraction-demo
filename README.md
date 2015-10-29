Demonstration of DATAGRAPH-781
==============================

Prerequisites:
-------------

  1, A running neo4j server on `localhost:7474`, preferably with the UUID plugin installed (optional).  
     Change `application.yml` to your password (or set in n the command line).  
  2, A constraint:

        create constraint on (n:`User`) assert n.`email` is unique

Start the app:
--------------

    mvn spring-boot:run

To create a user:
-----------------

    curl http://192.168.1.79:8080/create-user
    
To login:
---------

    curl -X POST http://localhost:8080/oauth/token -u client:password -d grant_type=password -d username=test@example.com -d password=1234 -i
    
Result:
-------

Both request fails on the master branch. Switch to the workaround branch, and both works as expected.
    

