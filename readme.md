<h1 align="center">
  <br>
  <a href="https://notion-emojis.s3-us-west-2.amazonaws.com/v0/svg-twitter/2615.svg"><img src="https://notion-emojis.s3-us-west-2.amazonaws.com/v0/svg-twitter/2615.svg" alt="Virtual coffee Logo" width="200"></a>
  <br>
      Think-it Java: Virtual coffee - Command Line Tool
  <br>
</h1>


This Command Line Tool is built using the framework Micronaut and the Java 11 language. The main goal of this tool is to send requests to the server to get the available Thinkiters to a Virtual Coffee !

The architecture of the main project is : 

 ├──main->java->asklepios->thinkit.
                                  ├── models                  # where we are building the objects that we are working with
                                  ├── services                # Contains custom functions and services used by this tool                      
                                  └── Client.java             # The main script that runs the Tool and identify the command lines  


***

<h3 align="center" >
  <a href="https://webeha.com/">
    Running the Command Line Tool
  </a>
</h3>

***

## Build the JAR file

```
./gradlew --no-daemon assemble
```

## Run command as a Java program

```
java -jar build/libs/commandlineApp-0.1-all.jar -s "10am" -e "4pm" -o "gmt+1" -n 3

Usage: client -s "[Hour of start of the availability slot]" -e "[Hour of End of the availability slot]" -o "[Offset to GMT following the format "gmt+X" or "gmt-Y" where X and Y are the offset]" -n [Number of desired participants <= 3]
```

## Compile native binary file


```
$ sdk use java 20.1.0.r11-grl 

Using java version 20.1.0.r11-grl in this shell.

$ gu install native-image

$ native-image --no-server -cp build/libs/commandlineApp-0.1-all.jar

```


Then the command line can be called as :

```
$ ./client -s "10am" -e "4pm" -o "gmt+1" -n 3

```
