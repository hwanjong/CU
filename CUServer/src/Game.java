import java.io.*;
import java.net.*;
import java.util.Vector;

public class Game extends Thread{
	boolean running = false;
	int rNo;
	int numUser;
	int turn;
	Vector<String> userId = new Vector<String>();
	public Game(Room room){
		this.rNo = room.getrNo();
		this.numUser = room.getNumUser();
		this.userId = room.getPartUser();
	}
	
	public void run(){
		running = true;
		while(running){
			
		}
	}
}
