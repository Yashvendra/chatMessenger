
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{

private JTextField userText;
private JTextArea chatWindow;
private ObjectOutputStream output;

private ObjectInputStream input;
private String message="";
private String serverIP;
private Socket connection;

//constructor
public Client (String host){
super("Client !");
serverIP=host;
userText = new JTextField();
userText.setEditable(false);
userText.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
sendMessage(e.getActionCommand());
userText.setText("");
}
});
add(userText, BorderLayout.NORTH);
chatWindow=new JTextArea();
add(new JScrollPane(chatWindow),BorderLayout.CENTER);
setSize(400,400);
setVisible(true);


}
//starting the client window

public void startRunning(){
try{
connectToServer();
setupStreams();
whileChatting();


}
catch(EOFException e){
showMessage("\n Client Terminated the Connection");
}
catch(IOException e){
e.printStackTrace();
}
finally{
close();
}
}

//connect to the server
public void connectToServer()throws IOException{
showMessage("connecting...");
connection =new Socket(InetAddress.getByName(serverIP), 1234);
showMessage("Connected to: "+connection.getInetAddress().getHostName());
}

//set up streams

private void setupStreams()throws IOException{
output= new ObjectOutputStream(connection.getOutputStream());
output.flush();
input=new ObjectInputStream(connection.getInputStream());
showMessage("Your streams are connected.");
}

//while chatting with server
private void whileChatting()throws IOException{
ableToType(true);
do{
try{
message=(String)input.readObject();
showMessage("\n"+message);

}
catch(ClassNotFoundException e){
showMessage("\n Didn't get that!");
}
}while(!message.equals("SERVER: END"));

}

//close the streams and sockets

private void close(){
showMessage("\n Closing the stream and sockets");
ableToType(false);
try{
output.close();
input.close();
connection.close();

}catch(IOException e){
e.printStackTrace();
}
}

//send messages to the servers
private void sendMessage(String message){
try{
output.writeObject("CLIENT: "+message);
output.flush();
showMessage("\nCLIENT: "+ message);
}catch(IOException e){
chatWindow.append("\nsomething went wrong while sending the message");
}
}

//GUI display in chat  window
private void showMessage(final String m){
SwingUtilities.invokeLater(new Runnable(){
public void run(){
chatWindow.append(m); //append the message always at the end of the window
}
});

}

//permission to type the text in the text box
private void ableToType(final boolean f){
SwingUtilities.invokeLater(new Runnable(){
public void run(){
userText.setEditable(f);
}

});
}

}
