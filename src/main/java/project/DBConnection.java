package project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // 여기서 servletdb 로 변경
	private static final String URL =
		    "jdbc:mysql://localhost:3306/servletdb?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";


    // MySQL 계정 정보 (네 PC에 맞게 바꿔줘)
    private static final String USER = "root";
    private static final String PASS = "";  // 비번 다르면 여기 수정

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 8.x 드라이버
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
