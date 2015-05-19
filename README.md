# infosnake

## Overview

A Spring boot application to manage values between a REST service (Infosnake) and a SOAP service (Cleverreach)

Sheduling has been introduced so that the application runs continuously

## How do I run it?

### Maven

```
git clone https://github.com/javi-more-garc/infosnake.git
cd infosnake/interface-infosnake-cleverreach
update properties files under src/main/resources (e.g. cleverreach.properties and infosnake.properties for passwords)
mvn spring-boot:run
```
