package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    /**
     * 관리자 페이지용 회원 정보 조회
     * - account + member 테이블 JOIN
     * - 비밀번호 포함
     * - 수익금(profit) 기준 내림차순 정렬
     */
    public List<AccountDTO> getAllAccounts() {

        String sql =
            "SELECT a.user_id, a.account_name, a.profit, m.password " +
            "FROM account a " +
            "JOIN member m ON a.user_id = m.id " +   // JOIN으로 비밀번호 가져오기
            "ORDER BY a.profit DESC";

        List<AccountDTO> list = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                AccountDTO dto = new AccountDTO();

                dto.setUserId(rs.getString("user_id"));
                dto.setAccountName(rs.getString("account_name"));
                dto.setProfit(rs.getLong("profit"));
                dto.setPassword(rs.getString("password")); // 추가됨

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
