package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    // 계좌 생성
    public boolean createAccount(AccountDTO dto) {
        String sql = "INSERT INTO account (user_id, account_name, balance, available, investing, profit) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getUserId());
            ps.setString(2, dto.getAccountName());
            ps.setLong(3, dto.getBalance());
            ps.setLong(4, dto.getAvailable());
            ps.setLong(5, 0);   
            ps.setLong(6, 0);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 계좌 불러오기
    public AccountDTO getAccountByUser(String userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountId(rs.getInt("account_id"));
                dto.setUserId(rs.getString("user_id"));
                dto.setAccountName(rs.getString("account_name"));
                dto.setBalance(rs.getLong("balance"));
                dto.setAvailable(rs.getLong("available"));
                dto.setInvesting(0);  // 사용 안 함
                dto.setProfit(rs.getLong("profit"));
                return dto;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //매수 → available 감소만
    public void decreaseAvailable(int accountId, long amount) {
        String sql = "UPDATE account SET available = available - ? WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 매도 → available 증가만
    public void increaseAvailable(int accountId, long amount) {
        String sql = "UPDATE account SET available = available + ? WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 판매 수익 추가
    public void addProfit(String userId, double profit) {
        String sql = "UPDATE account SET profit = profit + ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, profit);
            ps.setString(2, userId);
            ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }
}
