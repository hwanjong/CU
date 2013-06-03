import java.sql.*;
import java.util.Vector;


public class ServerDbAdministrator {	//데이터베이스와 연동하기 위해 사용되는 객체
	
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
			Class.forName("com.mysql.jdbc.Driver");					//드라이버 로드 및 접속
			conn = DriverManager.getConnection(url,id,pwd);
		}catch (Exception e) {
			System.out.println("Database 연결에 실패했습니다.");
		}
	}//생성자
	
	public String CreateUser(String id, String pwd, String nickName){	//회원가입시
		sql = "insert into users values(?,?,?)";						//users테이블에 insert함
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
			System.out.println("Id 중복");				//ID중복 외의 예외처리는 클라이언트에서 처리
			return null;
		}
	}//CreateUser
	
	public String CheckLogin(String id,String pwd){					
		sql = "select * from users where id=? and pwd=?";
		try{											//id와 pwd를 비교하고 결과가 있으면
			pstmt = conn.prepareStatement(sql);			//로그인 성공
			pstmt.setString(1, id);						//결과가 없을시 실패
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
		sql = "insert into room (room_master,current_users,play,level) values(?,1,'O',?)";
		try{												//방을 만들면 방의 번호는 자동으로 증가
			pstmt = conn.prepareStatement(sql);				//방장의 아이디를 설정하고 참여중인 유저의 수를 1로 설정
			pstmt.setString(1, id);
			pstmt.setString(2, level);
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("Fail");
		}
	}//CreateRoom
	
	public void EnterRoom(String userId, int rNo){
		sql = "update room set current_users = current_users + 1 where room_num = ?";
		try{												//방에 입장하게되면 참여중인 유저의 수를 증가 시킨다
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rNo);
			pstmt.executeUpdate();
			
			sql = "insert into participate (id,room_num) values (?,?)";
			pstmt = conn.prepareStatement(sql);				//게임방과 유저의 관계를 나타내는 테이블에도
			pstmt.setString(1,userId);						//정보 삽입
			pstmt.setInt(2, rNo);
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Fail");				//방에 3명이 입장해 있으면 실패(current_users의 Domain은 3까지임)
		}
	}//EnterRoom
	
	public Vector<Room> GetRoomList(){				
		
		Vector<Room> rVector = new Vector<Room>();
		sql = "select * from room";							//게임방 리스트의 정보를 로드
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
				
				sql = "select * from participate where room_num = ?";	//게임방에 참여하고있는 유저의 정보를 로드
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rNo);
				rs1 = pstmt.executeQuery();
				
				while(rs1.next()){
					String userId = rs1.getString("id");
					room.getPartUser().add(userId);
				}
				rVector.add(room);
			}
			return rVector;
		}catch(Exception e){
			System.out.println("FailGetRoomList:"+e.getStackTrace());	//게임방 로드중 실패했을 때
			return null;
		}
		
	}//GetGameList
	
	public Vector<User> GetUserFromRno(int rNo){		//방 정보를 이용해서 유저 정보를 로드
		Vector<User> uVector = new Vector<User>();
		sql = "select u.name, s.score, s.grade " +
				"from users u, score s, participate p " +
				"where u.id = s.id and p.id = u.id and p.room_num = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			System.out.println(rNo+"GetUserFromRno");
			pstmt.setInt(1, rNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				String nickName = rs.getString("name");
				int tScore = rs.getInt("score");
				int cScore = 0;
				String grade = rs.getString("grade");
				
				user.setcScore(cScore);
				user.settScore(tScore);
				user.setGrade(grade);
				user.setNickName(nickName);
				
				uVector.add(user);
			}
			return uVector;
		}catch(Exception e){
			System.out.println("Fail Load UserInfo in Room");
			return null;
		}
	}//GetUserFromRno
	
	public void SetPlay(int rNo){			//입장가능한 상태 변경
		try{
			sql = "update room set play='X' where rNo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rNo);
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("SetPlay Error");
		}
	}
	public int getRnoFromUser(String userId){		//유저 아이디로 방번호 찾기
		int rNo;
		try{
			sql = "select room_num from participate where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			rs.next();
			rNo = rs.getInt("room_num");
			return rNo;
		}catch(Exception e){
			System.out.println("Error From getRnoFromUser");
			return 0;
		}
	}//SetPlay
	
	public int getRnoFromMaster(String rMaster){	//방장의 아이디로 방 번호 찾기
		int rNo;
		try{
			sql = "select room_num from room where room_master = ?";
			pstmt = conn.prepareStatement(sql);
			System.out.println(rMaster);
			pstmt.setString(1, rMaster);
			rs = pstmt.executeQuery();
			rs.next();
			rNo = rs.getInt("room_num");
			return rNo;
		}catch(Exception e){
			System.out.println("Error From getRno");
			return 0;
		}
	}//getRnoFromMaser
	
	public String PartRoom(String id,int rNo){	//게임방과 유저 의 관계를 나타내는 테이블에 데이터 삽입
		try{
			sql = "insert into participate (id,room_num) values (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, rNo);
			pstmt.executeUpdate();
			
			return id;
		}catch(Exception e){
			System.out.println("Error From insertPartRoom");
			return null;
		}
	}//PartRoom
	
	public void initRoom(){				//모든 방 정보를 초기화
		try{
			sql = "delete from participate";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "delete from room";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Error From Room Initialization");
		}
	}//initRoom
	
	public Vector<String> GetWord(String level){	//제시어를 데이터베이스로부터 가져옴
		Vector<String> word = new Vector<String>();
		try{
			sql = "select word from word where level = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, level);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				word.add(rs.getString("word"));
			}
		}catch(Exception e){
			System.out.println("Fail word from DataBase");
		}
		return word;
	}//GetWord

	public void DeleteRoom(String userId) {
		try{
			sql = "delete from participate where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
			
			sql = "delete from room where room_master = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
			
			System.out.println(userId+"에 대한 방정보 삭제 완료");
		}catch(Exception e){
			System.out.println("Fail delete room");
		}
	}//delete room
}//Class
