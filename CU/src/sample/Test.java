package test;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * 
 * @author Administrator 평가4용 client
 */
public class Test extends Frame implements MouseMotionListener {
	private Socket socket;  // 소켓
	private PrintWriter writer;  
	private BufferedReader reader;

	private Image img = null;
	private Graphics gImg = null;

	private int x = 0;
	private int y = 0;

	public Test(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		addMouseMotionListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setBounds(100, 100, 500, 500);
		setVisible(true);

		img = createImage(500, 500);
		gImg = img.getGraphics();
//		repaint();

		try {
			socket = new Socket("localhost", 10001);
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		new Test("Client");
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		gImg.drawLine(x, y, e.getX(), e.getY());
		x = e.getX();
		y = e.getY();
		repaint();
		
		writer.println("DRAW:" + x + "_" + y);
		writer.flush();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		writer.println("MOVE:" + x + "_" + y);
		writer.flush();
	}
}
