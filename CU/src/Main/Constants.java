package Main;

public class Constants {
	
	public static enum EPanel{
		가입("join"), 로그인("login");
		private String name;
		private EPanel(String s) {this.name = s;}
		public String getName() {return this.name;}
	}

}
