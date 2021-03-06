package Model;
import java.util.Vector;

public class Room {
	private int rNo;
	private String rMaster;
	private int numUser;
	private String play;
	private String level;
	private Vector<String> partUser = new Vector<String>();
	
	public int getrNo() {
		return rNo;
	}
	public void setrNo(int rNo) {
		this.rNo = rNo;
	}
	public String getrMaster() {
		return rMaster;
	}
	public void setrMaster(String rMaster) {
		this.rMaster = rMaster;
	}
	public Vector<String> getPartUser() {
		return partUser;
	}
	public void setPartUser(Vector<String> partUser) {
		this.partUser = partUser;
	}
	public String getPlay() {
		return play;
	}
	public void setPlay(String play) {
		this.play = play;
	}
	public int getNumUser() {
		return numUser;
	}
	public void setNumUser(int numUser) {
		this.numUser = numUser;
	}
	public void addPartUser(String userId){
		this.partUser.add(userId);
	}
	public String getPartUser(int index){
		return this.partUser.get(index);
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
