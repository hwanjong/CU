import java.util.Hashtable;
import java.io.*;
import java.net.*;

public class Server{
	protected Hashtable<String, PrintWriter> users = new Hashtable<String, PrintWriter>();
	protected ServerSocket serverSocket;
	protected Socket socket;
	public Server(){
		try {
			serverSocket = new ServerSocket(10005);
			System.out.println("Server On");
			while(true){
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
				Receiver r = new Receiver(socket);
				Thread receiver = new Thread(r);
				receiver.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
			socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
