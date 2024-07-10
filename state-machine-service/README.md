# Michael-Trembovler-Machine-state-service

# Create database:
    CREATE DATABASE nanox
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

# Postman collection: 
  See Nanox.postman_collection.json

# Tests
Provided tests are a combination of both, utilizing JUnit for the test infrastructure and assertions, 
and Mockito for mocking dependencies and specifying their behaviors. This combination is a 
common practice in unit testing for Java applications, 
allowing for more focused and isolated tests.

To run tests use command:  mvn test -Dtest=ActionServiceTest or via Maven test.


# Flows and States:
- **Flow1** corresponds to state1, state2
- **Flow2** corresponds to state3
