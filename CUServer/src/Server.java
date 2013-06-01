import java.net.*;
import java.io.*;
import java.util.Vector;

class Receiver extends Thread{
	Socket socket;
	Vector<Socket> sVector = new Vector<Socket>();
	Vector<Room> rVector = new Vector<Room>();
	BufferedReader reader;
	String msg;
	int select;
	String contents;
	ServerDbAdministrator dbAdmin;
	public Receiver(Socket socket,Vector<Socket> sVector){
		dbAdmin = new ServerDbAdministrator();
		this.socket = socket;
		this.sVector = sVector;
	}
	public void run(){
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				msg = reader.readLine();
				System.out.println(msg);
				select = Integer.parseInt(msg.split(":")[0]);
				System.out.println(select);
				contents = msg.split(":")[1];
				System.out.println(contents);
				if(select==1){
					System.out.println(contents);
					String id = contents.split(",")[0];
					System.out.println(id);
					String pwd = contents.split(",")[1];
					String nickName = contents.split(",")[2];
					String userId = dbAdmin.CreateUser(id,pwd,nickName);
					if(userId==null){
						ToClient(socket,"Fail:중복된 ID가 존재 합니다.");
					}else{
						ToClient(socket,"Create:ID 생성 완료");
					}
				}
				
				else if(select==2){
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					boolean isOk = dbAdmin.CheckLogin(id,pwd);
					if(!isOk){
						ToClient(socket,"Fail:로그인 실패");
					}else{
						ToClient(socket,"Login:로그인 완료");
//						rVector = dbAdmin.getRoom();
//						for(Room room : rVector){
//							ToClient(socket,room);
//						}
					}
				}
				else if(select==3){
					ToAll(contents);
				}else if(select==4){
					
				}else if(select==5){
					
				}else if(select==6){
					
				}else if(select==7){
					
				}
			}
		}catch(Exception e){
			System.out.println("Run");
			System.out.println(e.getStackTrace());
		}
	}
	public void ToClient(Socket socket,String msg){
		try{
			PrintWriter writer = new PrintWriter ( new OutputStreamWriter( socket.getOutputStream()));
			writer.println(msg);
			writer.flush();
		}catch(Exception e){
			System.out.println("ToClient Method");
		}
	}
	public void ToAll(String msg){
		try{
			for(Socket socket : sVector){
				PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				w.println(msg);
				w.flush();
			}
		}catch(Exception e){
			System.out.println("ToAll Method");
		}
	}
}

public class Server {
	Socket socket;
	Vector<Socket> sVector;
	ServerSocket server;
	BufferedReader reader;
	PrintWriter writer;
	public Server(){
		sVector = new Vector<Socket>();
		try{
			server = new ServerSocket(10005);
			System.out.println("Server On");
			while(true){
				socket = server.accept();
				sVector.add(socket);
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" 입장");
				
				Receiver receiver = new Receiver(socket,sVector);
				receiver.start();
			}
			
		}catch(Exception e){
			System.out.println("Server Class");
			e.printStackTrace();
		}finally{
			try{
				server.close();
			}catch(Exception e){}
		}
	}
}
