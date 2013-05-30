
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
				socket = serverSocket.accept(); //���
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "���� �����Ͽ����ϴ�.");

				userId = reader.readLine();	//Ŭ���̾�Ʈ�� �����ϸ鼭 ���̵� ����
				UserList user = new UserList(userId);
				users.add(user);
				
				System.out.println(userId+" ����");
				
				sVector.add(socket);
				for(Socket socket : sVector){
					writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer.println(userId+"���� �����ϼ̽��ϴ�.");
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
