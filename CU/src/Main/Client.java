package Main;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Administrator 평가4용 client
 */
public class Client {
	private Socket socket; // 소켓
	private PrintWriter writer;
	private BufferedReader reader;
	String massage;

	public Client() {
		try {
			socket = new Socket("192.168.39.22", 10005);//진성
//			socket = new Socket("202.30.111.135", 10001);//민철
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			writer.println("hi");
//			writer.flush();
//			massage = reader.readLine();
//			System.out.println(massage);

		} catch (Exception e) {
		}
	}
	
	public void sendMassage(String massage){
		try{
			writer.println(massage);
			writer.flush();

		}catch(Exception e){
			System.out.println("메시지를 보낼수없음");
		
		}
	}
	public String receiveMassage(){
		try {
			massage = reader.readLine();
			System.out.println(massage);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("메시지를 보낼수없음");
		}
		return massage;
		
	}
}
