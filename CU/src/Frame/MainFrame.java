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
		

		//Panel생성
		joinPanel = new JoinPanel(client);
		loginPanel = new LoginPanel(client); 
		waitRoomPanel = new WaitRoomPanel(client);
		

		this.init();
		
		
		pane.add(joinPanel, Constants.EPanel.가입.getName());
		pane.add(loginPanel,Constants.EPanel.로그인.getName());
		pane.add(waitRoomPanel,Constants.EPanel.대기방.getName());
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450, 250);
		setResizable(false);
		
		//시작패널
		setSize(330, 230);
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
		waitRoomPanel.init(this);
	
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
			this.setSize(330, 230);
			break;
			
		case "waitRoom" :
			card.show(this.getContentPane(), Constants.EPanel.대기방.getName());
			waitRoomPanel.addData();
			this.setLocation(230, 100);
			this.setSize(700, 500);
			break;
		
		default:
			System.out.println("패널이동실패");
			break;
		}
		
	}
	public int insertRoom(){
		int count=0;
		while(true){
			String massage=client.receiveMassage();
			if(massage.equals("End"))
				break;
			String[] splitMassage=massage.split(",");
			int rNo = Integer.parseInt(splitMassage[0]);
			String rMaster = splitMassage[1];
			int numUser = Integer.parseInt(splitMassage[2]);
			String play = splitMassage[3];
			String level = splitMassage[4];
			splitMassage = splitMassage[5].split("_");
			
			Room room = new Room();
			room.setrNo(rNo);
			room.setrMaster(rMaster);
			room.setNumUser(numUser);
			room.setPlay(play);
			room.setLevel(level);
			
			for(String userid : splitMassage){
				room.addPartUser(userid);
//				참여userid들어오는지 확인
//				System.out.println(userid);
			}
			this.roomList.add(room);
			count++;
		}
		return count;
	}

	public Vector<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(Vector<Room> roomList) {
		this.roomList = roomList;
	}

}
