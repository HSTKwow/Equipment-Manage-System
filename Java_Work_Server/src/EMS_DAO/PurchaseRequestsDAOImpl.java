package EMS_DAO;

import EMS_Util.DatabaseUtil;
import EMS_Util.TimestampIdGenerator;
import Model.PurchaseRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestsDAOImpl implements PurchaseRequestsDAO {
    private Connection conn;

    public PurchaseRequestsDAOImpl() {
        conn = DatabaseUtil.getConnection();        //建立数据库连接
    }

    public PurchaseRequest findById(int requestId) {
        PurchaseRequest request = null;
        String sql = "SELECT * FROM purchaserequests WHERE request_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                request = new PurchaseRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setEquipmentName(rs.getString("equipment_name"));
                request.setCategory(rs.getString("equipment_category"));
                request.setQuantity(rs.getInt("quantity"));
                request.setRequestDate(rs.getDate("request_date"));
                request.setRequester(rs.getString("requester"));
                request.setApprover(rs.getString("approver"));
                request.setApprovalDate(rs.getDate("approval_date"));
                request.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    public List<PurchaseRequest> findAll() {
        List<PurchaseRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM purchaserequests";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PurchaseRequest request = new PurchaseRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setEquipmentName(rs.getString("equipment_name"));
                request.setCategory(rs.getString("equipment_category"));
                request.setQuantity(rs.getInt("quantity"));
                request.setRequestDate(rs.getDate("request_date"));
                request.setRequester(rs.getString("requester"));
                request.setApprover(rs.getString("approver"));
                request.setApprovalDate(rs.getDate("approval_date"));
                request.setStatus(rs.getString("status"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void save(PurchaseRequest request) {
        String sql = "INSERT INTO purchaserequests (request_id, equipment_name, equipment_category, quantity, " +
                "request_date, requester, approver, approval_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(request.getCategory());
            request.setRequestId(TimestampIdGenerator.generateId());
            stmt.setInt(1,request.getRequestId());
            stmt.setString(2, request.getEquipmentName());
            stmt.setString(3, request.getCategory());
            stmt.setInt(4, request.getQuantity());
            stmt.setDate(5, new java.sql.Date(request.getRequestDate().getTime()));
            stmt.setString(6, request.getRequester());
            stmt.setString(7, request.getApprover());
            stmt.setDate(8,new java.sql.Date(request.getApprovalDate().getTime()));
            stmt.setString(9, request.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PurchaseRequest request) {
        String sql = "UPDATE purchaserequests SET  approver = ?,approval_date = ?, status = ? "+
                "WHERE request_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, request.getApprover());
            stmt.setDate(2, new java.sql.Date(request.getApprovalDate().getTime()));
            stmt.setString(3,request.getStatus());
            //System.out.println(request.getStatus());
            stmt.setInt(4,request.getRequestId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int requestId) {
        String sql = "DELETE FROM purchaserequests WHERE request_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
