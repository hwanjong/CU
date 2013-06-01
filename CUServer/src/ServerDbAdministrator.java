import java.sql.*;


public class ServerDbAdministrator {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql;
	String url = "jdbc:mysql://localhost:3306/cu";
	String id = "root";
	String pwd = "wlstjd12";
	public ServerDbAdministrator(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,id,pwd);
		}catch (Exception e) {
			System.out.println("Db »ý¼ºÀÚ"+e.getStackTrace());
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
			System.out.println(e.getStackTrace());
			return null;
		}
	}
	public boolean CheckLogin(String id,String pwd){
		sql = "select * from users where id=? and pwd=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
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
	public Room GetRoom(){
		Room room = new Room();
	}
}
