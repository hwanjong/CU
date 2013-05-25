package sample;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClass extends Frame {
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static BufferedReader reader;
	private int x = 0;
	private int y = 0;
	
	private Image img = null;
	private Graphics gImg = null;
	
	public MainClass(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		
		setBounds(100, 100, 500, 500);
		setVisible(true);
		
		img = createImage(500, 500);
		gImg = img.getGraphics();
		repaint();
		
		try {
			serverSocket = new ServerSocket(10001);
			socket = serverSocket.accept();
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			System.out.println("연결됨");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String msg = "";
				try {
					while ((msg = reader.readLine()) != null) {
						String[] tokens = msg.split(":");

						if (tokens[0].equals("DRAW")) {
							String[] xy = tokens[1].split("_");
							gImg.drawLine(x, y, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
							x = Integer.parseInt(xy[0]);
							y = Integer.parseInt(xy[1]);
							repaint();
						} else if (tokens[0].equals("MOVE")) {
							String[] xy = tokens[1].split("_");
							x = Integer.parseInt(xy[0]);
							y = Integer.parseInt(xy[1]);
						}
					}
				} catch (Exception e) {

				}
			}
		}).start();
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
	
	public static void main(String[] args) {
		new MainClass("Server");
	}
}