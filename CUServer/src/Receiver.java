import java.net.*;
import java.io.*;

public class Receiver implements Runnable {
	BufferedReader reader;
	PrintWriter writer;
	String name="";
	Socket socket;
	public Receiver(Socket socket){
		try{
			this.socket = socket;
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			name = reader.readLine();
			System.out.println(name+"님이 들어왔음");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run(){
		try{
			writer.println("Welcome");
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}



