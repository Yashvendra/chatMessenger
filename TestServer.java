import javax.swing.JFrame;

public class TestServer{
public static void main(String args[]){
Server ob=new Server();
ob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
ob.startRunning();
}

}
