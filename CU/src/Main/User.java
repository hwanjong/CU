package Main;

public class User {
	
	private String ID;
	private String pwd;
	private int roomNo;
	private boolean Permit;
	private int currentScore;
	
	public User(String ID){
		setID(ID);
		setRoomNo(0);
		setPermit(false);		
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public boolean isPermit() {
		return Permit;
	}

	public void setPermit(boolean permit) {
		Permit = permit;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
