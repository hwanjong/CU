
import java.util.Vector;
import java.io.*;
import java.net.*;

public class Server{
	private Vector<UserList> users = new Vector<UserList>();
	private Vector<Socket> sVector = new Vector<Socket>();
	private Socket socket;
	private ServerSocket serverSocket;
	private BufferedReader reader;
	private String userId;
	private PrintWriter writer;
	public Server(){
		try {
			serverSocket = new ServerSocket(10005);
			System.out.println("Server On");
			while(true){
				socket = serverSocket.accept(); //대기
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");

				userId = reader.readLine();	//클라이언트가 접속하면서 아이디 전송
				UserList user = new UserList(userId);
				users.add(user);
				
				System.out.println(userId+" 접속");
				
				sVector.add(socket);
				for(Socket socket : sVector){
					writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer.println(userId+"님이 접속하셨습니다.");
					writer.flush();
				}
				
				Receiver r = new Receiver(socket, sVector);
				r.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
			socket.close();
			reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
