import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

class Game extends Thread{
	PrintWriter w;
	Socket socket;
	ServerDbAdministrator DbManager = new ServerDbAdministrator();
	Vector<String> word = new Vector<String>();
	String mode;
	public Game(Socket socket){
		this.socket = socket;
		this.word = DbManager.getWord();
	}
	public void run(){
		try{
			w = new PrintWriter(socket.getOutputStream());
			while(true){
				
			}
		}catch(Exception e){
			
		}
	}
}

class Receiver extends Thread{
	private Socket socket;
	private Vector<Socket> sVector;
	ServerDbAdministrator DbManager = new ServerDbAdministrator();
	public Receiver(Socket socket, Vector<Socket> sVector){
		this.socket = socket;
		this.sVector = sVector;
	}
	public void run(){
		BufferedReader reader;
		String msg;
		int selectNum;
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				msg = reader.readLine();
				selectNum = Integer.parseInt(msg.split(":")[0]);
				switch (selectNum){
				case 1:
					DbManager.CheckLogin();		//Login
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:							//Draw
					toClient(selectNum,msg.split(":")[1]);
					break;
				case 5:							//Chat
					toClient(selectNum,msg.split(":")[1]);
					break;
				}
			}
		}catch(Exception e){}
	}
	public void toClient(int actionNo ,String msg){
		if(actionNo==4){
			try{
				for(Socket socket : sVector){
					if(this.socket!=socket){
						PrintWriter w = new PrintWriter(socket.getOutputStream());
						w.println(msg);
						w.flush();
					}
				}
			}catch(Exception e){}
		}
		else{
			try{			
				for(Socket socket : sVector){
					PrintWriter w = new PrintWriter(socket.getOutputStream());
					w.println(msg);
					w.flush();
				}
			}catch(Exception e){}
		}
	}
}