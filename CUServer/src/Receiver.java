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
	}//생성자
	public void run(){
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			server.sVector.get(0);
			while(true){
				msg = reader.readLine();		//메시지 읽기
				System.out.println(msg);
				select = Integer.parseInt(msg.split(":")[0]); //메시지 나누기
				System.out.println(select);
				contents = msg.split(":")[1];
				
				if(select==1){			//회원가입 요청
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
				
				else if(select==2){		//로그인 요청
					String id = contents.split(",")[0];
					String pwd = contents.split(",")[1];
					userId = dbAdmin.CheckLogin(id,pwd);
					if(userId==null){
						ToClient(socket,"Fail");
					}else{
						ToClient(socket,"Login");
					}
				}
				
				else if(select==3){			//게임방 내에서의 채팅
					int rNo = dbAdmin.getRnoFromUser(userId);
					ToRoom(rNo,contents);
				}
				
				else if(select==4){			//게임방 리스트 요청
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
				
				else if(select==5){			//게임방 생성 요청
					String level = contents.split(",")[0];
					dbAdmin.CreateRoom(userId,level);
					
					//sleep(50);
					int rNo = dbAdmin.getRnoFromMaster(userId);
					dbAdmin.PartRoom(userId,rNo);
					AddRoom(rNo,level);
					ToClient(socket,"Create:"+rNo);
				}
				
				else if(select==6){			//게임방 입장
					int rNo = Integer.parseInt(contents.split(",")[0]);
					EnterRoom(rNo);
					String id = dbAdmin.PartRoom(userId, rNo);
					if(id.equals(null)){
						ToClient(socket,"False");
					}else {
						ToClient(socket,"Enter");
					}
				}
				
				else if(select==7){			//게임방의 유저 정보 요청
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
		}catch(Exception e){			//접속이 종료될때
			if(userId == null){			//로그인하지 않았을 경우 아이피만을 확인
				System.out.println(socket.getInetAddress()+" 에서 접속을 종료");
			}else{						//로그인 했을 경우 접속을 끊은 아이디를 확인
				System.out.println(userId+" 접속 종료");
				dbAdmin.DeleteRoom(userId);
			}
			try{
				this.reader.close();
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
			System.out.println("Error From ToClient Method");
		}
	}//ToClient
	public void ToRoom(int rNo, String msg){	//방에 있는 사용자에게 메시지 전송
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