package Frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Client;
import Main.Constants;
import Model.Room;

public class JoinPanel extends JPanel implements ActionListener{

	//Associate
	Client client;
	MainFrame mainFrame;

	JTextField txtName=new JTextField(20);
	JTextField txtID=new JTextField(20);
	JPasswordField txtPwd = new JPasswordField(20);
	JPasswordField txtPwd02 = new JPasswordField(20);


	JButton btnAdd = new JButton("가입하기");
	JButton reSet = new JButton("다시쓰기");

	String massage;

	public JoinPanel (Client client){

		this.client = client;
		setLayout(new GridLayout(6,0));

		JPanel p00=new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel joinLabel =new JLabel("회 원 가 입");
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

		JPanel p03 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p03.add(new JLabel("패스워드 재입력 : "));
		p03.add(txtPwd02);
		add(p03);

		JPanel p04 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p04.add(new JLabel("닉네임 : "));
		p04.add(txtName);
		add(p04);

		JPanel p05 = new JPanel();
		p05.add(btnAdd);
		p05.add(reSet);

		add(p05);
		setBackground(Color.black);


		btnAdd.addActionListener(this);
		reSet.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String nicName=txtName.getText().trim();
		String sID=txtID.getText().trim();
		String sPwd=txtPwd.getText().trim();
		String sPwd2=txtPwd02.getText().trim();

		txtName.setText("");
		txtID.setText("");
		txtPwd.setText("");
		txtPwd02.setText("");

		Object eventSource = e.getSource();

		if(eventSource==reSet){
			JOptionPane.showMessageDialog(null, "다시입력하세요.");
			return;
		}



		//ADD를 눌렸을때 동작
		if(eventSource==btnAdd){
			if(nicName.equals("")||sID.equals("")||sPwd.equals("")||sPwd2.equals("")){
				JOptionPane.showMessageDialog(null, "모든항목을채워주세요");
				return;
			}else if(sPwd.equals(sPwd2)==false){
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
				return;
			}else if(sID.length()>20||sPwd.length()>20){
				JOptionPane.showMessageDialog(null, "ID나 PWD는 최대 20자입니다");
			}else if(nicName.length()>10){
				JOptionPane.showMessageDialog(null, "닉네임은 최대 10자입니다");
				return;
			}else{

				//모든 예외가 없을시

				String msg="";
				msg+="1:"+sID+","+sPwd+","+nicName;

				//send & receive
				client.sendMassage(msg);
				massage=client.receiveMassage();
				
				
				//성공했을시
				if(massage.equals("Success")){
					JOptionPane.showMessageDialog(null, "회원가입 완료!");
					mainFrame.selectPanel(Constants.EPanel.로그인.getName());

				}else{
					JOptionPane.showMessageDialog(null, "중복된 ID가 있습니다.");
					return;
				}
			}
		}
	}

	
	public void init(MainFrame frame){
		this.mainFrame = frame;
	}
	
}
