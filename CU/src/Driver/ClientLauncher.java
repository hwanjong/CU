package Driver;

import Frame.MainFrame;
import Main.Client;

public class ClientLauncher {
	
	public static void main(String args[]){

		Client client = new Client();
		MainFrame main = new MainFrame(client);
		
	}
}
