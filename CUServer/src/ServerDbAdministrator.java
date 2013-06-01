import java.sql.*;
import java.util.Vector;


public class ServerDbAdministrator {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	String sql;
	String url = "jdbc:mysql://localhost:3306/cu";
	String id = "root";
	String pwd = "wlstjd12";
	public ServerDbAdministrator(){
		try{
			Class.forName("com.mysql.jdbc.Driver");					//����̹� �ε� �� ����
			conn = DriverManager.getConnection(url,id,pwd);
		}catch (Exception e) {
			System.out.println("Database ���ῡ �����߽��ϴ�.");
		}
	}//������
	
	public String CreateUser(String id, String pwd, String nickName){	//ȸ�����Խ�
		sql = "insert into users values(?,?,?)";						//users���̺� insert��
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, nickName);
			pstmt.executeUpdate();
			
			sql = "insert into score values(?,0,'Bronze')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.executeUpdate();
			
			return id;
		}catch(Exception e){
			System.out.println("Id �ߺ�");				//ID�ߺ� ���� ����ó���� Ŭ���̾�Ʈ���� ó��
			return null;
		}
	}//CreateUser
	
	public String CheckLogin(String id,String pwd){					
		sql = "select * from users where id=? and pwd=?";
		try{											//id�� pwd�� ���ϰ� ����� ������
			pstmt = conn.prepareStatement(sql);			//�α��� ����
			pstmt.setString(1, id);						//����� ������ ����
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
	}//CheckLogin
	public void CreateRoom(String id,String level){
		sql = "insert into room (rMaster,current_users,play,level) values(?,?,'O',?)";
		try{												//���� ����� ���� ��ȣ�� �ڵ����� ����
			pstmt = conn.prepareStatement(sql);				//������ ���̵� �����ϰ� �������� ������ ���� 1�� ����
			pstmt.setString(1, id);
			pstmt.setInt(2, 1);
			pstmt.setString(3, level);
			pstmt.executeQuery();
		}catch(Exception e){
			System.out.println("Fail");
		}
	}//CreateRoom
	
	public void EnterRoom(String userId, int rNo){
		sql = "update room set current_users = current_users + 1 where room_num = ?";
		try{												//�濡 �����ϰԵǸ� �������� ������ ���� ���� ��Ų��
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rNo);
			pstmt.executeUpdate();
			
			sql = "insert into participate (id,room_num) values (?,?)";
			pstmt = conn.prepareStatement(sql);				//���ӹ�� ������ ���踦 ��Ÿ���� ���̺���
			pstmt.setString(1,userId);						//���� ����
			pstmt.setInt(2, rNo);
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Fail");				//�濡 4���� ������ ������ ����(current_users�� Domain�� 4������)
		}
	}//EnterRoom
	
	public Vector<Room> GetRoomList(){				
		
		Vector<Room> rVector = new Vector<Room>();
		sql = "select * from room";							//���ӹ� ����Ʈ�� ������ �ε�
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Room room = new Room();
				int rNo = rs.getInt("room_num");
				String rMaster = rs.getString("room_master");
				String isPlay = rs.getString("play");
				int numUsers = rs.getInt("current_users");
				String level = rs.getString("level");
				
				System.out.println(rMaster);
				room.setNumUser(numUsers);
				room.setPlay(isPlay);
				room.setrMaster(rMaster);
				room.setrNo(rNo);
				room.setLevel(level);
				
				sql = "select * from participate where room_num = ?";	//���ӹ濡 �����ϰ��ִ� ������ ������ �ε�
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rNo);
				rs1 = pstmt.executeQuery();
				
				while(rs1.next()){
					String userId = rs1.getString("id");
					room.getPartUser().add(userId);
				}
				rVector.add(room);
			}
		}catch(Exception e){
			System.out.println("FailGetRoomList:"+e.getStackTrace());	//���ӹ� �ε��� �������� ��
		}
		return rVector;
	}//GetGameList
	
	public void SetPlay(int rNo){
		try{
			sql = "update room set play=1 where rNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rNo);
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("SetPlay Error");
		}
	}
}//Class
