import java.io.*;
import java.net.*;
import java.util.Vector;

public class Game extends Thread{
	int count=0;
	boolean running = false;
	String level;
	int turn;
	String word;
	Vector<String> userId = new Vector<String>();
	ServerDbAdministrator dbAdmin = new ServerDbAdministrator();
	Vector<String> wVector = new Vector<String>();
	Vector<Receiver> rVector;
	Receiver receiver;
	int rNo;
	
	public Game(String level,Receiver receiver,int rNo){
		this.wVector = dbAdmin.GetWord(level);
		this.rVector = receiver.server.rVector;
		this.rNo = rNo;
	}
	
	public void run(){
		running = true;
		System.out.println("Game Start");
		while(running){
			
			SendTurn();
			SendWord();
		}
		count = 0;
	}//run
	public void SendWord(){
		int i = (int)(Math.random()*20)+1;
		word = wVector.get(i);
	}
	public void SendTurn(){
		
		count++;
		if(count >= 10) running = false;
	}
}
