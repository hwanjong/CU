import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;

public class Server {
	Vector<Socket> sVector;
	Vector<Receiver> rVector = new Vector<Receiver>();

	ServerSocket server;
	public Server(){
		sVector = new Vector<Socket>();
		try{
			server = new ServerSocket(10005);		//�������� ����
			System.out.println("Server On");
			while(true){
				Socket socket = server.accept();			//���Ͽ��� ���
				sVector.add(socket);
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" ���� ����");
				
				Receiver receiver = new Receiver(socket,this);
				rVector.add(receiver);				//����� ���ϵ��� �̿��ϱ� ���� Receiver�� ���Ϳ� ����
				receiver.setDaemon(true);
				receiver.start();					//�ϳ��� ���Ͽ� ����� receiver ������ ����
			}
		}catch(Exception e){
			System.out.println("Server Class");
//			ServerDbAdministrator dbAdmin = new ServerDbAdministrator();
//			dbAdmin.initRoom();
			e.printStackTrace();
		}finally{
			try{
				System.out.println("Server Off");
				server.close();
			}catch(Exception e){}
		}
	}//Server()
	
	
}
