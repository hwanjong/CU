import java.sql.*;
import java.util.Vector;


public class ServerDbAdministrator {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	String sql;
	String url = "jdbc:mysql://localhost:3306/cu";
	String id = "root";
	String pwd = "wlstjd12";
	public ServerDbAdministrator(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,id,pwd);
		}catch (Exception e) {
			System.out.println("Database 연결에 실패했습니다.");
		}
	}
	public String CreateUser(String id, String pwd, String nickName){
		sql = "insert into users values(?,?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, nickName);
			pstmt.executeUpdate();
			
			return id;
		}catch(Exception e){
			System.out.println("Id 중복");
			return null;
		}
	}
	public String CheckLogin(String id,String pwd){
		sql = "select * from users where id=? and pwd=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				return id;
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}
	public void CreateRoom(String id){
		sql = "insert into room (rMaster,current_users) values(?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, 1);
			pstmt.executeQuery();
		}catch(Exception e){}
	}
	public Vector<Room> GetRoomList(){
		Room room = new Room();
		Vector<Room> rVector = new Vector<Room>();
		sql = "select * from room";
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				int rNo = rs.getInt("room_num");
				String rMaster = rs.getString("room_master");
				int isPlay = rs.getInt("play");
				int numUsers = rs.getInt("current_users");
				
				room.setNumUser(numUsers);
				room.setPlay(isPlay);
				room.setrMaster(rMaster);
				room.setrNo(rNo);
				
				sql = "select * from participate where room_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rNo);
				rs1 = pstmt.executeQuery();
				
				while(rs1.next()){
					String userId = rs.getString("id");
					room.getPartUser().add(userId);
				}
				rVector.add(room);
			}
		}catch(Exception e){
			System.out.println("FailGetRoomList:"+e.getStackTrace());
		}
		return rVector;
	}
}
