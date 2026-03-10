package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDAO {

    // 계좌의 전체 보유 종목 조회
    public List<PortfolioDTO> getPortfolioByAccount(int accountId) {
        List<PortfolioDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM portfolio WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PortfolioDTO dto = new PortfolioDTO();
                dto.setId(rs.getInt("id"));
                dto.setAccountId(rs.getInt("account_id"));
                dto.setSymbol(rs.getString("symbol"));
                dto.setName(rs.getString("name"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setAveragePrice(rs.getLong("avg_price"));
                dto.setCurrentPrice(rs.getLong("current_price"));

                list.add(dto);
            }

        } catch (Exception e) { 
            e.printStackTrace(); 
        }

        return list;
    }

    // 매수 로직
    public void buy(int accountId, String symbol, String name, int qty, double price) {

        String check = "SELECT * FROM portfolio WHERE account_id=? AND symbol=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(check)) {

            ps.setInt(1, accountId);
            ps.setString(2, symbol);
            ResultSet rs = ps.executeQuery();

            // 이미 보유한 종목이면 → 평균단가 재계산
            if (rs.next()) {

                int oldQty = rs.getInt("quantity");
                double oldAvg = rs.getDouble("avg_price");

                int newQty = oldQty + qty;
                double newAvg = (oldQty * oldAvg + qty * price) / newQty;

                String update =
                    "UPDATE portfolio SET quantity=?, avg_price=?, current_price=? WHERE account_id=? AND symbol=?";

                try (PreparedStatement ps2 = conn.prepareStatement(update)) {
                    ps2.setInt(1, newQty);
                    ps2.setDouble(2, newAvg);
                    ps2.setDouble(3, price);
                    ps2.setInt(4, accountId);
                    ps2.setString(5, symbol);
                    ps2.executeUpdate();
                }

            } else {
                // 새 종목 추가
                String insert =
                    "INSERT INTO portfolio(account_id, symbol, name, quantity, avg_price, current_price) VALUES (?,?,?,?,?,?)";

                try (PreparedStatement ps3 = conn.prepareStatement(insert)) {
                    ps3.setInt(1, accountId);
                    ps3.setString(2, symbol);
                    ps3.setString(3, name);
                    ps3.setInt(4, qty);
                    ps3.setDouble(5, price);
                    ps3.setDouble(6, price);
                    ps3.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 현재 보유 수량 조회
    public int getQuantity(int accountId, String symbol){
        String sql = "SELECT quantity FROM portfolio WHERE account_id=? AND symbol=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, symbol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt("quantity");

        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        return 0;
    }

    //  평균단가 조회 (SELL 수익 계산용)
    public double getAveragePrice(int accountId, String symbol) {
        String sql = "SELECT avg_price FROM portfolio WHERE account_id = ? AND symbol = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, symbol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //  매도 처리
    public void sell(int accountId, String symbol, int qty, double price){

        String sql = "SELECT quantity FROM portfolio WHERE account_id=? AND symbol=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, symbol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int oldQty = rs.getInt("quantity");

                if (oldQty - qty <= 0) {
                    // 전량 매도 → 삭제
                    try (PreparedStatement del = conn.prepareStatement(
                            "DELETE FROM portfolio WHERE account_id=? AND symbol=?")) {

                        del.setInt(1, accountId);
                        del.setString(2, symbol);
                        del.executeUpdate();
                    }
                } else {
                    // 일부 매도 → 수량 감소만
                    try (PreparedStatement up = conn.prepareStatement(
                            "UPDATE portfolio SET quantity=? WHERE account_id=? AND symbol=?")) {

                        up.setInt(1, oldQty - qty);
                        up.setInt(2, accountId);
                        up.setString(3, symbol);
                        up.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
