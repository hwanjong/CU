package Main;

public class Constants {
	
	public static enum EPanel{
		����("join"), �α���("login"),����("waitRoom");
		private String name;
		private EPanel(String s) {this.name = s;}
		public String getName() {return this.name;}
	}

}
