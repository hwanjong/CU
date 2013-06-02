package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements MouseMotionListener{

	GamePanel gamePanel;
	
	private Graphics gImg = null;
	public Image img = null;

	private int x = 0;
	private int y = 0;
	
	public DrawingPanel(){
		addMouseMotionListener(this);
		setBounds(100, 100, 500, 500);
		
//		img = createImage(500, 500);
//		gImg = img.getGraphics(); 
//		repaint();
			
	}
	
	public void init(GamePanel gamepanel){
		this.gamePanel=gamepanel;
	}
	
	public void paint(Graphics g) {
		update(g);
	}
	public void update(Graphics g){
		Dimension d = size();
		if(img==null){
			img = createImage(500,500);
			gImg = img.getGraphics();
			setBackground(Color.gray);
		}
		g.drawImage(img, x, y, );
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		gImg.drawLine(x, y, e.getX(), e.getY());
		x = e.getX();
		y = e.getY();
		repaint();
		gamePanel.client.sendMassage("8:" + x + "_" + y);
		gamePanel.client.receiveMassage();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
//		writer.println("MOVE:" + x + "_" + y);
//		writer.flush();
	}

}
