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
	}//������
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
				}//ȸ������ ��û
				
				else if(select==2){		
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					userId = dbAdmin.CheckLogin(id,pwd);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Login");
					}
				}//�α��� ��û
				
				else if(select==3){		
					ToAll(contents);
				}//ä��
				
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
				}//���ӹ� ����Ʈ ��û
				
				else if(select==5){	
					String id = contents.split(",")[0];
					String level = contents.split(",")[1];
					dbAdmin.CreateRoom(id,level);
				}//���ӹ� ���� ��û
				
				else if(select==6){
					
				}
				
				else if(select==7){
					
				}
			}//while
		}catch(Exception e){			//������ ����ɶ�
			if(userId == null){			//�α������� �ʾ��� ��� �����Ǹ��� Ȯ��
				System.out.println(socket.getInetAddress()+" ���� ������ ����");
			}else{						//�α��� ���� ��� ������ ���� ���̵� Ȯ��
				System.out.println(userId+" ���� ����");
			}
			try{
				this.socket.close();	//������ �����ǿ� �ش��ϴ� ������ ����
			}catch(IOException ie){
				System.out.println(ie.getMessage());
			}
		}
	}//run
	public void ToClient(Socket socket,String msg){	//�ش� Ŭ���̾�Ʈ���Ը� �޽����� ����
		try{
			PrintWriter writer = new PrintWriter ( new OutputStreamWriter( socket.getOutputStream()));
			writer.println(msg);
			writer.flush();
		}catch(Exception e){
			System.out.println("ToClient Method");
		}
	}//ToClient
	public void ToOtherClient(String msg){			//�ش� Ŭ���̾�Ʈ�� ������ Ŭ���̾�Ʈ���� �޽��� ����
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
	public void ToAll(String msg){			//��� Ŭ���̾�Ʈ���� �޽��� ����
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
			server = new ServerSocket(10005);		//�������� ����
			System.out.println("Server On");
			while(true){
				socket = server.accept();			//���Ͽ��� ���
				sVector.add(socket);
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+" ���� ����");
				
				Receiver receiver = new Receiver(socket,this);
				receiver.start();					//��û�� �޴� receiver ������ ����
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
