package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	 private PreparedStatement pstmt;
	 private ResultSet rs;

	 public UserDAO() {
	  try {
	   String dbURL = "jdbc:mysql://localhost:3306/BBS";
	   String dbID = "root";
	   String dbPassword = "green1234";
	   Class.forName("com.mysql.cj.jdbc.Driver");
	   conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }

	 public int login(String userID, String userPassword) {
	  String SQL = "SELECT userPassword FROM USER WHERE userID=?";
	  try {
	   pstmt = conn.prepareStatement(SQL);
	   pstmt.setString(1, userID);
	   rs = pstmt.executeQuery();
	   if(rs.next()) {
	    if(rs.getString(1).equals(userPassword))
	    	return 1;  // 로그인 성공
	    else
	    	return 0;  // 비밀번호 불일치
	   }
	   return -1;  // 아이디가 없음
	   
	  } catch(Exception e) {
	   e.printStackTrace();
	  }
	  return -2; // 데이터 베이스 오류

	 }
	
	 public int getAdmin(String userID) {
			int ret = 0;
			try {
				PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE userID = ?");
				pst.setString(1, userID);
				rs = pst.executeQuery();
				if (rs.next()) {
					ret = rs.getInt("admin");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}
	
	public int join(User user) {
		String SQL = "insert into user values (?,?,?,?,?,?)";
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,user.getUserID());
			pstmt.setString(2,user.getUserPassword());
			pstmt.setString(3,user.getUserName());
			pstmt.setString(4,user.getUserEmail());
			pstmt.setInt(5, 0);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류
	}
}