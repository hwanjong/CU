
public class User{
	private String name;
	private int cScore;
	private int uScore;
	User(String name){
		cScore=0;
		this.name=name;
	}
	public void setcScore(int score){
		this.cScore = score;
	}
	public void setuScore(int score){
		this.uScore = score;
	}
	public String getName(){
		return name;
	}
	public int getcScore(){
		return cScore;
	}
	public int uScore(){
		return uScore;
	}
}
