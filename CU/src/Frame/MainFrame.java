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
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.Client;
import Main.Constants;

public class MainFrame extends JFrame{

	//Association
	Client client;
	CardLayout card;
	Container pane;
	
	JoinPanel joinPanel;
	LoginPanel loginPanel; 
	
	int choice;
	Scanner scan = new Scanner(System.in);
	
	
	public MainFrame(Client client){

		super("CatchMind if U can");
		
		setClient(client);
		card = new CardLayout();
		
		pane=getContentPane();
		pane.setLayout(card);
		

		//Panel����
		joinPanel = new JoinPanel(client);
		loginPanel = new LoginPanel(client); 

		this.init();
		
		
		pane.add(joinPanel, Constants.EPanel.����.getName());
		pane.add(loginPanel,Constants.EPanel.�α���.getName());
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450, 250);
		setResizable(false);
		
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
		
		default:
			System.out.println("�г��̵�����");
			break;
		}
		
	}

}
