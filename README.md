
OpenJDK version: "11.0.2"


Command to build:   'mvn clean install'
Please check the following class (added comments in class for extra details)
This class acts as a driver class and test the authorization functionality.
    1. /authservice/src/test/java/locus/auth/service/AuthServiceImplTest.java

 More test files
    2. /authservice/src/test/java/locus/auth/service/ResourceManagerImplTest.java
    3. /authservice/src/test/java/locus/auth/service/AccountServiceImplTest.java

From root dir 'authservice' use the following command to run this test
     command: 'mvn -Dtest={ClassName} test'
     example: 'mvn -Dtest=AuthServiceImplTest test'

Assumptions:
    1. A role is assinged to the user only if the role is already added in the system.
    2. In case the role is not added in the system and add required role to resource call is made the the given role is first added in the system.
    3. Resource can have list of roles for a action type.
    4. User need at least one role from the list of resource required roles for a given action type to get authorised.
