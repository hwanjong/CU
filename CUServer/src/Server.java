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
	String userId;
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
				contents = msg.split(":")[1];
				if(select==1){
					System.out.println(contents);
					String id = contents.split(",")[0];
					System.out.println(id);
					String pwd = contents.split(",")[1];
					String nickName = contents.split(",")[2];
					String userId = dbAdmin.CreateUser(id,pwd,nickName);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Success");
					}
				}
				
				else if(select==2){
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					userId = dbAdmin.CheckLogin(id,pwd);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Login");
						rVector = dbAdmin.GetRoomList();
						for(Room room : rVector){
							Vector<String> pUser = room.getPartUser();
							String msg = room.getrNo()+","+room.getrMaster()+","+room.getNumUser()+","+room.getPlay();
							for(String m : pUser){
								msg + "_"+pUser;
							}
							ToClient(socket,msg);
						}
					}
				}
				else if(select==3){
					ToAll(contents);
				}else if(select==4){
					ToOtherClient(contents);
				}else if(select==5){
					
				}else if(select==6){
					
				}else if(select==7){
					
				}
			}
		}catch(Exception e){
			if(userId == null){
				System.out.println(socket.getInetAddress()+" 에서 접속을 종료");
			}else{
				System.out.println(userId+" 접속 종료");
			}
			try{
				this.socket.close();
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
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
	public void ToOtherClient(String msg){
		try{
			for(Socket socket : sVector){
				if(this.socket != socket){
					PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					w.println(sVector);
					w.flush();
				}
			}
		}catch(Exception e){
			System.out.println("ToOtherClient");
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
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" 에서 접속");
				
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
