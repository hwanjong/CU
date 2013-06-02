import java.io.*;
import java.net.*;
import java.util.Vector;

public class Game extends Thread{
	int count=0;
	boolean running = false;
	String level;
	int turn;
	Vector<String> userId = new Vector<String>();
	ServerDbAdministrator dbAdmin = new ServerDbAdministrator();
	Vector<String> word = new Vector<String>();
	public Game(String level){
		this.word = dbAdmin.GetWord(level);
	}
	
	public void run(){
		running = true;
		System.out.println("Game Start");
//		while(running){
//			
//		}
	}//run
	public void SendWord(){
		
	}
	public void SendTurn(){
		count++;
		if(count >= 10) running = false;
	}
}
