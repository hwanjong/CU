package Driver;

import Frame.MainFrame;
import Main.Client;

public class ClientLauncher {
	
	public static void main(String args[]){

//		LoginFrame loginFrame = new LoginFrame();
//		loginFrame.initialize();
		
		
		Client client = new Client();
		MainFrame main = new MainFrame(client);
		
	}
}
