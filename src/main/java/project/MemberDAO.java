package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    // 회원가입
    public int join(MemberDTO dto) {
        String sql = "INSERT INTO member (id, password, nickname) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getId());
            ps.setString(2, dto.getPassword());
            ps.setString(3, dto.getNickname());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 로그인
    public MemberDTO login(String id, String pw) {

        String sql = "SELECT * FROM member WHERE id=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new MemberDTO(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("nickname")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
