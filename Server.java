

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{

private JTextField userText;
private JTextArea chatWindow;
private ObjectOutputStream output;
private ObjectInputStream input;
private ServerSocket server;
private Socket connection;
//in java connections are referred as Sockets

//constructor
public Server(){
  super("Y@$# Messenger");
    userText=new JTextField();
    
//Before connection setup u can not send message, threfore fistly the textfield is noneditible
    userText.setEditable(false);
    userText.addActionListener(
       new ActionListener(){
          public void actionPerformed(ActionEvent e){
//getActionCommand() method retrieves the entered text in the field
              sendMessage(e.getActionCommand());
              userText.setText("");
    }
    }
      
    );
    add(userText,BorderLayout.NORTH);
    chatWindow=new JTextArea();
    add(new JScrollPane(chatWindow));
    setSize(400,400);
    setVisible(true);

}

//set up and run the server
public void startRunning(){
/**For creating the server
 * setup first the port no. here it is 1234
 * and the max no. of people can wait to talk is 100 at once
 */ 


try{

server = new ServerSocket(1234,100);
while(true){
   try{
       //connect and have conversation
       waitForConnection();
       setupStreams();
       whileChatting();  
       
    }
    catch(EOFException e2){
       showMessage("\n Server ended the connection!");
    }
    finally{
      close();
    }
}

}catch(IOException e){
//to print the error
e.printStackTrace();

}

}

//wait for connection, then display the connection information

private void waitForConnection()throws IOException{
     showMessage("Waiting for someone to connect.....!\n"); 
     
     //set up the socket: it is the connection b/w u and other computer
     connection =server.accept();
     
     //hostname gets the IP address
     showMessage("Now connected to "+connection.getInetAddress().getHostName());
     
     
}

//get stream to sent and recieve data
private void setupStreams()throws IOException{
    output=new ObjectOutputStream(connection.getOutputStream());
    output.flush();
    input=new ObjectInputStream(connection.getInputStream());
    showMessage("\n Streams are now setup! \n");
    
}

//while chatting with server
private void whileChatting()throws IOException{
String message="";
ableToType(true);// for able to type
do{
try{
message=(String) input.readObject();
showMessage("\n"+message);
}catch(ClassNotFoundException e){
showMessage("\n I Dont know the type of Object");
}

}while(!message.equals("SERVER: END"));
}

//close streams and sockets after chatting
private void close(){
   showMessage("\nClosing Connections..\n");
   ableToType(false);
   try{
    output.close();
    input.close();
    connection.close();
    }catch(IOException e){
      e.printStackTrace();
    }
   
}

//send messages to client
private void sendMessage(String message){
try{
//sends a message through output stream to the client
  output.writeObject("SERVER:  "+message); 
  output.flush();
  showMessage("\n SERVER: "+message);
}catch(IOException e){
  chatWindow.append("\n ERROR: I CANNOT SEND MESSAGE!");
}


}

//updates chatWindow
private void showMessage(final String text){
   SwingUtilities.invokeLater(
    new Runnable(){
      public void run(){
           chatWindow.append(text);  
    }
    }
   
   );

}

//let the user type
private void ableToType(final boolean t){
   SwingUtilities.invokeLater(
    new Runnable(){
      public void run(){
          userText.setEditable(t);
    }
    }
   
   );
}


}
