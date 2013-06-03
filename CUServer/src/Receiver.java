import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

class Receiver extends Thread{
	Socket socket;
	Server server;
	Vector<Room> roomVector = new Vector<Room>();
	Vector<User> uVector = new Vector<User>();
	HashMap<Integer, Vector> rMap = new HashMap<Integer, Vector>();
	HashMap<Integer, Game> gMap = new HashMap<Integer, Game>();
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
				msg = reader.readLine();		//�޽��� �б�
				System.out.println(msg);
				select = Integer.parseInt(msg.split(":")[0]); //�޽��� ������
				System.out.println(select);
				contents = msg.split(":")[1];
				
				if(select==1){			//ȸ������ ��û
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
				}											
				
				else if(select==2){		//�α��� ��û
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					userId = dbAdmin.CheckLogin(id,pwd);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Login");
					}
				}
				
				else if(select==3){			//���ӹ� �������� ä��
					int rNo = dbAdmin.getRnoFromUser(userId);
					ToRoom(rNo,contents);
				}
				
				else if(select==4){			//���ӹ� ����Ʈ ��û
					roomVector = dbAdmin.GetRoomList();
					for(Room room : roomVector){
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
				}											
				
				else if(select==5){			//���ӹ� ���� ��û
					String level = contents.split(",")[0];
					dbAdmin.CreateRoom(userId,level);
					
					//sleep(50);
					int rNo = dbAdmin.getRnoFromMaster(userId);
					dbAdmin.PartRoom(userId,rNo);
					AddRoom(rNo,level);
					ToClient(socket,"Create:"+rNo);
				}
				
				else if(select==6){			//���ӹ� ����
					int rNo = Integer.parseInt(contents.split(",")[0]);
					EnterRoom(rNo);
					String id = dbAdmin.PartRoom(userId, rNo);
					if(id.equals(null)){
						ToClient(socket,"False");
					}else {
						ToClient(socket,"Enter");
					}
				}
				
				else if(select==7){			//���ӹ��� ���� ���� ��û
					int rNo = Integer.parseInt(contents.split(",")[0]);
					System.out.println(rNo+"Receiver");
					uVector = dbAdmin.GetUserFromRno(rNo);
					for(User user: uVector){
						String msg = user.getNickName()+","+user.getcScore()+","+
									user.gettScore()+","+user.getGrade();
						ToClient(socket,msg);
						System.out.println(msg);
					}//for
					ToClient(socket,"End");
//					int rNo = Integer.parseInt(contents.split(",")[0]);
//					gMap.get(rNo).start();
				}
				else if(select==8){			//Draw
					int rNo = dbAdmin.getRnoFromUser(userId);
					int x;
					int y;
					x = Integer.parseInt(contents.split("_")[0]);
					y = Integer.parseInt(contents.split("_")[1]);
					System.out.println("From Client Draw : "+x+"_"+y);
					ToRoom(rNo,contents);
				}
			}//while
		}catch(Exception e){			//������ ����ɶ�
			if(userId == null){			//�α������� �ʾ��� ��� �����Ǹ��� Ȯ��
				System.out.println(socket.getInetAddress()+" ���� ������ ����");
			}else{						//�α��� ���� ��� ������ ���� ���̵� Ȯ��
				System.out.println(userId+" ���� ����");
				dbAdmin.DeleteRoom(userId);
			}
			try{
				this.reader.close();
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
			System.out.println("Error From ToClient Method");
		}
	}//ToClient
	public void ToRoom(int rNo, String msg){	//�濡 �ִ� ����ڿ��� �޽��� ����
		try{
			System.out.println(rNo);
			if(rMap.get(rNo)!=null){
				Vector<Receiver> rVector = rMap.get(rNo);
				for(Receiver r : rVector){
					PrintWriter w = new PrintWriter(new OutputStreamWriter (r.socket.getOutputStream()));
					w.println(msg);
					w.flush();
				}
			}
		}catch(Exception e){
			System.out.println("Error From ToRoom");
		}
	}//ToRoom
	public void AddRoom(int rNo,String level){
		rMap.put(rNo, server.rVector);
		Game game = new Game(level,this,rNo);
		gMap.put(rNo, game);
	}//AddRoom
	
	public void EnterRoom(int rNo){
		Vector<Receiver> rv = rMap.get(rNo);
		rMap.put(rNo, rv);
		dbAdmin.EnterRoom(userId, rNo);
	}//EnterRoom
}//Class