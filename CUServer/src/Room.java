
public class Room {
	int rNo;
	String rMaster;
	String[] partUser = new String[4];
	int play;
	ServerDbAdministrator dbAdmin = new ServerDbAdministrator();
	public Room(String userId){
		dbAdmin.CreateRoom(userId);
	}
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
	public String[] getPartUser() {
		return partUser;
	}
	public void setPartUser(String[] partUser) {
		this.partUser = partUser;
	}
	public int getPlay() {
		return play;
	}
	public void setPlay(int play) {
		this.play = play;
	}
	
}
