package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<TransactionDTO> getHistoryByAccount(int accountId) {

        String sql =
            "SELECT symbol, type, quantity, price, created_at " +
            "FROM transaction_history " +
            "WHERE account_id = ? " +
            "ORDER BY created_at DESC";

        List<TransactionDTO> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionDTO dto = new TransactionDTO();

                dto.setAccountId(accountId);
                dto.setSymbol(rs.getString("symbol"));
                dto.setType(rs.getString("type"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setPrice(rs.getDouble("price"));
                dto.setCreatedAt(rs.getString("created_at"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertHistory(int accountId, String symbol, String type, int qty, double price){
        String sql="INSERT INTO transaction_history(account_id,symbol,type,quantity,price) VALUES(?,?,?,?,?)";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1,accountId);
            ps.setString(2,symbol);
            ps.setString(3,type);
            ps.setInt(4,qty);
            ps.setDouble(5,price);
            ps.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
    }



}