# chatMessenger
### Welcome Everyone!!


I have created a simple 💬 Chat Messenger using networking libraries of Java. For the front end I have used JFrame class of Swing.

## Usage
### 1. Set the host IP in `ClientTest.java`.
I have set it to local host, you can set it to the server's public IP if you want.
```java
import javax.swing.JFrame;

public class ClientTest {
public static void main(String args[]){
Client cl=new Client("127.0.0.1");
cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
cl.startRunning();

}
}
```
### 2. Compile the `TestServer.java` and `ClientTest.java` by `javac` compiler. 
On Server Machine:
```shell
javac TestServer.java
```
On Client Machine:
```shell
javac ClientTest.java
```

### 3. Run the compiled `TestServer.class` and `ClientTest.class` files.
On Server Machine:
```shell
java TestServer
```
You will have server window opened which starts listening for connections on port 1234.



On Client Machine:
```shell
java ClientTest
```
Connection will be established if no error is thrown and client window will popup.
Communication can be done now!


## Happy Chatting ♥️
