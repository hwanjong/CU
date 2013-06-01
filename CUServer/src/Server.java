import java.net.*;
import java.io.*;
import java.util.Vector;

class Receiver extends Thread{
	Socket socket;
	Server server;
//	Vector<Socket> sVector;
	Vector<Room> rVector = new Vector<Room>();
	BufferedReader reader;
	String msg;
	int select;
	String contents;
	String userId;
	ServerDbAdministrator dbAdmin;
	public Receiver(Socket socket,Server server){
		dbAdmin = new ServerDbAdministrator();
		this.socket = socket;
		this.server = server;
	}//생성자
	public void run(){
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			server.sVector.get(0);
			while(true){
				msg = reader.readLine();
				System.out.println(msg);
				select = Integer.parseInt(msg.split(":")[0]);
				System.out.println(select);
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
					userId = null;
				}//회원가입 요청
				
				else if(select==2){		
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					userId = dbAdmin.CheckLogin(id,pwd);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Login");
					}
				}//로그인 요청
				
				else if(select==3){		
					ToAll(contents);
				}//채팅
				
				else if(select==4){	
					rVector = dbAdmin.GetRoomList();
					for(Room room : rVector){
						Vector<String> pUser = room.getPartUser();
						String msg = room.getrNo()+","+room.getrMaster()+","+
									room.getNumUser()+","+room.getPlay()+","+room.getLevel()+",";
						System.out.println(msg);
						for(String m : pUser){
							msg = msg+m+"_";
						}//for
						System.out.println(msg);
						ToClient(socket,msg);
					}//for
					ToClient(socket,"End");
				}//게임방 리스트 요청
				
				else if(select==5){	
					String id = contents.split(",")[0];
					String level = contents.split(",")[1];
					dbAdmin.CreateRoom(id,level);
				}//게임방 생성 요청
				
				else if(select==6){
					
				}
				
				else if(select==7){
					
				}
			}//while
		}catch(Exception e){			//접속이 종료될때
			if(userId == null){			//로그인하지 않았을 경우 아이피만을 확인
				System.out.println(socket.getInetAddress()+" 에서 접속을 종료");
			}else{						//로그인 했을 경우 접속을 끊은 아이디를 확인
				System.out.println(userId+" 접속 종료");
			}
			try{
				this.socket.close();	//종료한 아이피에 해당하는 소켓을 종료
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}//run
	public void ToClient(Socket socket,String msg){	//해당 클라이언트에게만 메시지를 보냄
		try{
			PrintWriter writer = new PrintWriter ( new OutputStreamWriter( socket.getOutputStream()));
			writer.println(msg);
			writer.flush();
		}catch(Exception e){
			System.out.println("ToClient Method");
		}
	}//ToClient
	public void ToOtherClient(String msg){			//해당 클라이언트를 제외한 클라이언트에게 메시지 보냄
		try{
			for(Socket socket : server.sVector){
				if(this.socket != socket){
					PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					w.println(server.sVector);
					w.flush();
				}
			}
		}catch(Exception e){
			System.out.println("ToOtherClient");
		}
	}//ToOtherClient
	public void ToAll(String msg){			//모든 클라이언트에게 메시지 보냄
		try{
			for(Socket socket : server.sVector){
				PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				w.println(msg);
				w.flush();
			}
		}catch(Exception e){
			System.out.println("ToAll Method");
		}
	}//ToAll
}//Receiver

public class Server {
	Socket socket;
	Vector<Socket> sVector;
	ServerSocket server;
	BufferedReader reader;
	PrintWriter writer;
	public Server(){
		sVector = new Vector<Socket>();
		try{
			server = new ServerSocket(10005);		//서버소켓 생성
			System.out.println("Server On");
			while(true){
				socket = server.accept();			//소켓연결 대기
				sVector.add(socket);
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" 에서 접속");
				
				Receiver receiver = new Receiver(socket,this);
				receiver.start();					//요청을 받는 receiver 쓰레드 시작
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
