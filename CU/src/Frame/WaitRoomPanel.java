package Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Main.Client;
import Main.Constants;
import Main.Room;

public class WaitRoomPanel extends JPanel implements ActionListener{

	String calName[] = { "방번호", "방장", "참여인원","입장가능","난이도"};
	Object rowData[][]={
			//			추가하고싶으면 여기에추가
	};
	
	JTable table;
	DefaultTableModel tableModel;
	JScrollPane sp;
	JPanel roomPanel,userPanel; 
	
	//대기자는 3명까지
	JLabel user1,user2,user3;
	Vector<JLabel> users;
	JButton join,refresh,create;

	Client client;
	MainFrame mainFrame;
	
	ListSelectionModel selectionModel;
	RowListener rowListener;
	
	int selectRow=-1;

	public WaitRoomPanel(Client client) {
		this.client=client;
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		this.setLocation(200, 200);
		this.setSize(800, 400);
		tableModel = new DefaultTableModel(rowData, calName);
		table = new JTable(tableModel);
		
		//selectRow리스너등록
		selectionModel = table.getSelectionModel();  
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
		rowListener = new RowListener(this);
		selectionModel.addListSelectionListener(rowListener);
		

		sp = new JScrollPane(table);
		
		//titlePanel
		JPanel title = new JPanel();
		title.setLayout(new GridLayout(2,0));
		title.add(new JLabel("대기방리스트!!",0));

		//버튼을 타이틀에추가
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		create = new JButton("방생성");
		refresh = new JButton("새로고침");
		buttonPanel.add(create);
		buttonPanel.add(refresh);
		title.add(buttonPanel);
		add("North",title);

		//RoomPanel
		roomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		roomPanel.add(sp);
		add("Center",roomPanel);
		
		add("South", new JLabel("Catch Mind If You Can", 0));
		
		
		//userPanel
		userPanel = new JPanel(new GridLayout(25,0));
		//벡터에 유저리스트 넣기
		users = new Vector<JLabel>();
		users.add(user1 = new JLabel());
		users.add(user2 = new JLabel());
		users.add(user3 = new JLabel());
		
		userPanel.add(new JLabel("유저리스트",0));
		join = new JButton("     입   장     ");
		userPanel.add(join);
//		
		//라벨을 추가
		for(JLabel label : users){
			userPanel.add(label);
		}
		add("East",userPanel);
		
		//액션리스너달기
		create.addActionListener(this);
		refresh.addActionListener(this);
		join.addActionListener(this);
	}
	
	public void addUsers(WaitRoomPanel waitRoom){
		int select;
		int index=0;
		
		select=this.getSelectRow();
		//초기화
		for(JLabel label:users){
			label.setText("");
		}
		for(String name: mainFrame.roomList.get(select).getPartUser()){
			waitRoom.users.get(index).setText(name);
			index++;
		}
		
	}

	public void init(MainFrame frame){
		this.mainFrame = frame;
	}

	public void addData(){
		for(Room room : mainFrame.roomList){
			Object data[] = {
					room.getrNo(), room.getrMaster(), room.getNumUser(), room.getPlay(),
					room.getLevel()
			};
			tableModel.addRow(data);
		}
	}

	public int getSelectRow() {
		return selectRow;
	}

	public void setSelectRow(int selectRow) {
		this.selectRow = selectRow;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object eventSource = e.getSource();
		int index;
		String massage;
		if(eventSource==create){
			String[] selections = {"Hard", "Easy"};
			String level = (String) JOptionPane.showInputDialog(null, "난이도를 선택하세요.", 
					"난이도 설정", JOptionPane.QUESTION_MESSAGE, null, selections, "두번째값");
			client.sendMassage("5:"+level+",");
			massage = client.receiveMassage();
			if(massage.equals("Create")){
//				cardchange추가하기
				
			}
			return;
		}
		if(eventSource == join){
			int vectorIndex = this.getSelectRow();
			int rNo= mainFrame.getRoomList().get(vectorIndex).getrNo();
			client.sendMassage("6:"+rNo+",");
			massage = client.receiveMassage();
			
		}
		if(eventSource == refresh){
			mainFrame.roomList.clear();
//			잠시 리스너를 해제한다.
			selectionModel.removeListSelectionListener(rowListener);
			
			for(index=0 ; mainFrame.rCount>index; index++){
				System.out.println(index);
				this.tableModel.removeRow(0);
			}
			
			client.sendMassage("4: ");
			mainFrame.rCount = mainFrame.insertRoom();
			
			mainFrame.selectPanel(Constants.EPanel.대기방.getName());
			selectionModel.addListSelectionListener(rowListener);
		}
		
	}
	
}

class RowListener implements ListSelectionListener  
{  
    WaitRoomPanel nowPanel;  
    JTable table;  
   
    public RowListener(WaitRoomPanel waitRoomPanel)  
    {  
        nowPanel = waitRoomPanel ;  
        table = nowPanel.table;  
    }  
   
    public void valueChanged(ListSelectionEvent e)  
    {  
        if(!e.getValueIsAdjusting())  
        {  
            ListSelectionModel model = table.getSelectionModel();  
            int lead = model.getLeadSelectionIndex();
            //선택이되었는지확인
            System.out.println(lead);
            //선택된값주기
            nowPanel.setSelectRow(lead);
            nowPanel.addUsers(nowPanel);
            
        }  
    }  
}  
