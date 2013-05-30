package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

public class LoginFrame extends JFrame{
	
	private JTextField id, pwd;
	private JButton login,join;
	private JLabel idLabel,pwdLabel;
	
	public LoginFrame (){
		setTitle("로그인");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=this.getSize();
		int xPos = (screenSize.width  - frameSize.width) / 4;
		int yPos = (screenSize.height  - frameSize.height) / 4;
		setSize(250	,150);
		setLocation(xPos, yPos);
		
		
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		id=new JTextField();
		id.setPreferredSize(new Dimension(180,30));
		pwd = new JTextField();
		pwd.setPreferredSize(new Dimension(180, 30));
		
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		panel1.add(idLabel=new JLabel("ID : "));
		panel2.add(id);
		
		panel1.add(pwdLabel = new JLabel("PW : "));
		panel2.add(pwd);
		panel1.setBackground(Color.red);
		JPanel panel = new JPanel();
		panel.add(new JLabel("로그인"));
		
		setLayout(new BorderLayout());
		add(panel2,BorderLayout.NORTH);
		add(panel1,BorderLayout.CENTER);
		
	}
	public void initialize(){
		setVisible(true);
		
	}

}
