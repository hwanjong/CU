
public class UserList {
	private String id;
	private String name;
	private int rNo;
	private int cScore;
	private int tScore;
	public UserList(String id){
		this.id = id;
		this.cScore = 0;
		this.rNo = 0;
		this.tScore = ServerDbAdministrator.getTscore();
		this.name = ServerDbAdministrator.getName();
	}
	public void setName(String name){
		this.name = name;
	}
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public int getRno(){
		return rNo;
	}
	public int getcCscore(){
		return cScore;
	}
	public int getTscore(){
		return tScore;
	}
	
}
