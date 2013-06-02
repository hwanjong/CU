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

	String calName[] = { "���ȣ", "����", "�����ο�","���尡��","���̵�"};
	Object rowData[][]={
			//			�߰��ϰ������ ���⿡�߰�
	};
	
	JTable table;
	DefaultTableModel tableModel;
	JScrollPane sp;
	JPanel roomPanel,userPanel; 
	
	//����ڴ� 3�����
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
		
		//selectRow�����ʵ��
		selectionModel = table.getSelectionModel();  
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
		rowListener = new RowListener(this);
		selectionModel.addListSelectionListener(rowListener);
		

		sp = new JScrollPane(table);
		
		//titlePanel
		JPanel title = new JPanel();
		title.setLayout(new GridLayout(2,0));
		title.add(new JLabel("���渮��Ʈ!!",0));

		//��ư�� Ÿ��Ʋ���߰�
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		create = new JButton("�����");
		refresh = new JButton("���ΰ�ħ");
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
		//���Ϳ� ��������Ʈ �ֱ�
		users = new Vector<JLabel>();
		users.add(user1 = new JLabel());
		users.add(user2 = new JLabel());
		users.add(user3 = new JLabel());
		
		userPanel.add(new JLabel("��������Ʈ",0));
		join = new JButton("     ��   ��     ");
		userPanel.add(join);
//		
		//���� �߰�
		for(JLabel label : users){
			userPanel.add(label);
		}
		add("East",userPanel);
		
		//�׼Ǹ����ʴޱ�
		create.addActionListener(this);
		refresh.addActionListener(this);
		join.addActionListener(this);
	}
	
	public void addUsers(WaitRoomPanel waitRoom){
		int select;
		int index=0;
		
		select=this.getSelectRow();
		//�ʱ�ȭ
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
			String level = (String) JOptionPane.showInputDialog(null, "���̵��� �����ϼ���.", 
					"���̵� ����", JOptionPane.QUESTION_MESSAGE, null, selections, "�ι�°��");
			client.sendMassage("5:"+level+",");
			massage = client.receiveMassage();
			if(massage.equals("Create")){
//				cardchange�߰��ϱ�
				
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
//			��� �����ʸ� �����Ѵ�.
			selectionModel.removeListSelectionListener(rowListener);
			
			for(index=0 ; mainFrame.rCount>index; index++){
				System.out.println(index);
				this.tableModel.removeRow(0);
			}
			
			client.sendMassage("4: ");
			mainFrame.rCount = mainFrame.insertRoom();
			
			mainFrame.selectPanel(Constants.EPanel.����.getName());
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
            //�����̵Ǿ�����Ȯ��
            System.out.println(lead);
            //���õȰ��ֱ�
            nowPanel.setSelectRow(lead);
            nowPanel.addUsers(nowPanel);
            
        }  
    }  
}  
