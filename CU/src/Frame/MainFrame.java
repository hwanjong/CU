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
		

		//Panel생성
		joinPanel = new JoinPanel(client);
		loginPanel = new LoginPanel(client); 

		this.init();
		
		
		pane.add(joinPanel, Constants.EPanel.가입.getName());
		pane.add(loginPanel,Constants.EPanel.로그인.getName());
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450, 250);
		setResizable(false);
		
		card.show(this.getContentPane(),Constants.EPanel.로그인.getName());
		
		
	}
	
	public void setClient(Client client){
		this.client = client;
	}
	
	//자신을 하위패널에 알려주는 초기화
	public void init(){
		//계속추가
		loginPanel.init(this);
		joinPanel.init(this);
	
	}
	
	public void selectPanel(String choice){
		switch(choice){
		//Constatns가 아니므로 Enum이 바뀔때 다시 바꿔줘야한다. (13.6.2)-환종
		case "join" :
			
			card.show(this.getContentPane(), Constants.EPanel.가입.getName());
//			bounds조정하려면 추가
			this.setSize(400, 300);
			break;
			
		case "login" :
			card.show(this.getContentPane(), Constants.EPanel.로그인.getName());
//			bounds조정하려면 추가
			this.setSize(400, 300);
			break;
		
		default:
			System.out.println("패널이동실패");
			break;
		}
		
	}

}
