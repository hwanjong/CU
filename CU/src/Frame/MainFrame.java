package Frame;

import javax.swing.JFrame;

import Main.Client;

public class MainFrame extends JFrame{
	Client client;
	
	public MainFrame(Client client){
		setClient(client);
		JoinPanel joinPanel = new JoinPanel(client);
		
		this.add(joinPanel);
		
		pack();
		this.setBounds(420, 180, 400, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void setClient(Client client){
		this.client = client;
		
	}

}
