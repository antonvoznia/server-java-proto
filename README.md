# server-java-proto-mess
Server is written in Java 1.8 to process data in Proto Buff and to response the processed data. The server uses epoll mechanism.

### Needed libraries (all needed is in pom.xml)
* Protocol Buffer https://developers.google.com/protocol-buffers/docs/javatutorial

### Compile and run
```
mvn compile
mvn exec:java -Dexec.mainClass="com.voznia.Main" -Dexec.args="12331"
```

### Logic
The server uses few threads (com.voznia.Serve variable COUNT_THREADS).
In the main thread created socket server to accept new connections.
Other threads are adding in thread pool via ExecutorService.
* Server - class to accept connection
* Worker - to process data and response to client  
