# infosnake

## Overview

A Spring boot application to manage values between a REST service (Infosnake) and a SOAP service (Cleverreach)

Sheduling has been introduced so that the application runs continuously

## How do I run it?

### Maven

```
1) git clone https://github.com/javi-more-garc/infosnake.git
2) cd infosnake/interface-infosnake-cleverreach
3) update file conf.properties under src/main/resources/conf.properties
4) mvn spring-boot:run
```
### Maven + JAR

```
1) git clone https://github.com/javi-more-garc/infosnake.git
2) cd infosnake/interface-infosnake-cleverreach
3) mvn package (this generates a jar in folder target)
4) Rename jar (e.g. myjar.jar), move it to some folder (e.g. myfolder) and go to that folder.
4) create in myfolder a file conf.properties. The values inside will take preference from the ones defined in the jar (src/main/resources/conf.properties). 
5) run the application with java -jar myjar.jar
```
