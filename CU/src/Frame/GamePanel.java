package Frame;

import java.awt.Image;

import javax.swing.JPanel;

import Main.Client;

public class GamePanel extends JPanel{
	
	//Association-high
	Client client;
	MainFrame mainFrame;
	
	//Aggregation-자식-포함관계
	DrawingPanel drawingPanel;
	
	
	public GamePanel(Client client){

		this.client=client;
		drawingPanel = new DrawingPanel();
		drawingPanel.init(this);
		
//		챗패널에도 drawingPanel과같이해줘야함
		
	}
	
	public void init(MainFrame mainFrame){
		this.mainFrame=mainFrame;
	}
}
