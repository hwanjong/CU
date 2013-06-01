package Frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.Client;
import Main.Constants;
import Main.Room;

public class MainFrame extends JFrame{

	//Association
	Client client;
	CardLayout card;
	Container pane;
	
	JoinPanel joinPanel;
	LoginPanel loginPanel; 
	WaitRoomPanel waitRoomPanel;
	
	Vector<Room> roomList;
	int rCount;
	
	public MainFrame(Client client){

		super("CatchMind if U can");
		
		setClient(client);
		card = new CardLayout();
		roomList = new Vector<Room>();
		
		pane=getContentPane();
		pane.setLayout(card);
		

		//Panel����
		joinPanel = new JoinPanel(client);
		loginPanel = new LoginPanel(client); 
		waitRoomPanel = new WaitRoomPanel(client);
		

		this.init();
		
		
		pane.add(joinPanel, Constants.EPanel.����.getName());
		pane.add(loginPanel,Constants.EPanel.�α���.getName());
		pane.add(waitRoomPanel,Constants.EPanel.����.getName());
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450, 250);
		setResizable(false);
		
		//�����г�
		setSize(350, 300);
		card.show(this.getContentPane(),Constants.EPanel.�α���.getName());
		
		
	}
	
	public void setClient(Client client){
		this.client = client;
	}
	
	//�ڽ��� �����гο� �˷��ִ� �ʱ�ȭ
	public void init(){
		//����߰�
		loginPanel.init(this);
		joinPanel.init(this);
		waitRoomPanel.init(this);
	
	}
	
	public void selectPanel(String choice){
		switch(choice){
		//Constatns�� �ƴϹǷ� Enum�� �ٲ� �ٽ� �ٲ�����Ѵ�. (13.6.2)-ȯ��
		case "join" :
			
			card.show(this.getContentPane(), Constants.EPanel.����.getName());
//			bounds�����Ϸ��� �߰�
			this.setSize(400, 300);
			break;
			
		case "login" :
			card.show(this.getContentPane(), Constants.EPanel.�α���.getName());
//			bounds�����Ϸ��� �߰�
			this.setSize(400, 300);
			break;
			
		case "waitRoom" :
			card.show(this.getContentPane(), Constants.EPanel.����.getName());
			waitRoomPanel.addData();
			this.setSize(700, 500);
			break;
		
		default:
			System.out.println("�г��̵�����");
			break;
		}
		
	}

}
