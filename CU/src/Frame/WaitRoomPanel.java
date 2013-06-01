package Frame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Main.Client;
import Main.Room;

public class WaitRoomPanel extends JPanel{
	
	String calName[] = { "���ȣ", "����", "�����ο�","���尡��","���̵�"};
	Object rowData[][]={
//			�߰��ϰ������ ���⿡�߰�
	};
	JTable table;
	DefaultTableModel tableModel;
	JScrollPane sp;
	
	Client client;
	MainFrame mainFrame;
	
	public WaitRoomPanel(Client client) {
		// TODO Auto-generated constructor stub
		this.setLocation(200, 200);
		this.setSize(800, 400);
		tableModel = new DefaultTableModel(rowData, calName);
		table = new JTable(tableModel);
		sp = new JScrollPane(table);
		add(sp);
	}
	
	public void init(MainFrame frame){
		this.mainFrame = frame;
	}
	
	public void addData(){
		for(Room room : mainFrame.roomList){
				Object data[] = {
						room.getrNo(), room.getrMaster(), room.getNumUser(), 
						room.getPlay(), room.getLevel()
				};
				tableModel.addRow(data);

		}
	}

}
