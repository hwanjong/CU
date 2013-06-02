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
			server = new ServerSocket(10005);		//서버소켓 생성
			System.out.println("Server On");
			while(true){
				Socket socket = server.accept();			//소켓연결 대기
				sVector.add(socket);
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" 에서 접속");
				
				Receiver receiver = new Receiver(socket,this);
				rVector.add(receiver);				//연결된 소켓들을 이용하기 위해 Receiver를 벡터에 저장
				receiver.setDaemon(true);
				receiver.start();					//하나의 소켓에 연결된 receiver 쓰레드 시작
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
