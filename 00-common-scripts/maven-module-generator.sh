#create new module. replace "99-spring-module" with the actual module name
MODULE_NAME=99-spring-module

# create module one level above this script - where all maven modules reside, and change to that directory
mkdir ../$MODULE_NAME && cd ../$MODULE_NAME || exit
mkdir -p src/main/java src/main/resources src/test/java src/test/resources
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.yrs</groupId>
    <artifactId>$MODULE_NAME</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>25</java.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>com.yrs</groupId>
            <artifactId>00-common-utilities</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

    </dependencies>

</project>" > pom.xml
