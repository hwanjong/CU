package Frame;

import java.awt.Image;

import javax.swing.JPanel;

import Main.Client;

public class GamePanel extends JPanel{
	
	//Association-high
	Client client;
	MainFrame mainFrame;
	
	//Aggregation-�ڽ�-���԰���
	DrawingPanel drawingPanel;
	
	
	public GamePanel(Client client){

		this.client=client;
		drawingPanel = new DrawingPanel();
		drawingPanel.init(this);
		
//		ê�гο��� drawingPanel�������������
		
	}
	
	public void init(MainFrame mainFrame){
		this.mainFrame=mainFrame;
	}
}
