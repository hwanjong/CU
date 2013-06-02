package Frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Client;
import Main.Constants;
import Model.Room;

public class LoginPanel extends JPanel implements ActionListener{

	//Associate
	Client client;
	MainFrame mainFrame;
	
	JTextField txtID=new JTextField(20);
	JPasswordField txtPwd = new JPasswordField(20);
	
	JButton btnLogin = new JButton("로그인");
	JButton btnJoin = new JButton("회원가입");
	
	public LoginPanel (Client client){
		
		this.client = client;
		setLayout(new GridLayout(4,0));
		
		JPanel p00=new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel joinLabel =new JLabel("로그인");
		p00.add(joinLabel);
		add(p00);
		
		JPanel p01 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p01.add(new JLabel("아이디 : "));
		p01.add(txtID);
		add(p01);
		
		JPanel p02 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p02.add(new JLabel("패스워드 : "));
		p02.add(txtPwd);
		add(p02);
		
		JPanel p03 = new JPanel();
		p03.add(btnLogin);
		p03.add(btnJoin);
		
		add(p03);
		
		setBackground(Color.blue);
		
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String sID=txtID.getText().trim();
		String sPwd=txtPwd.getText().trim();
		
		txtID.setText("");
		txtPwd.setText("");
		
		Object eventSource = e.getSource();
		
		
		//로그인을 눌렸을때 동작
		if(eventSource==btnLogin){
			if(sID.equals("")||sPwd.equals("")){
				JOptionPane.showMessageDialog(null, "모든항목을 채워주세요");
				return;
			}

			String msg="";
			msg+="2:"+sID+","+sPwd;
			client.sendMassage(msg);
			
			msg=client.receiveMassage();
			if(msg.equals("Login")){
				client.sendMassage("4: ");
				mainFrame.rCount = mainFrame.insertRoom();
				mainFrame.selectPanel(Constants.EPanel.대기방.getName());
			}else{
				JOptionPane.showMessageDialog(null, "ID나 PWD를 확인하세요");
				return;
			}
		}
		//회원가입을 눌렸을때
		if(eventSource==btnJoin){
//			JOptionPane.showMessageDialog(null, "회원가입창으로.");
			mainFrame.selectPanel(Constants.EPanel.가입.getName());
			
			return;
		}
		
	}
	
	public void init(MainFrame frame){
		this.mainFrame = frame;
	}
	

}
