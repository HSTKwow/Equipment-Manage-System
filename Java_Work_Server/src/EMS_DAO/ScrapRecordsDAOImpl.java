package EMS_DAO;

import EMS_Util.DatabaseUtil;
import EMS_Util.TimestampIdGenerator;
import Model.ScrapRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ScrapRecordsDAOImpl implements ScrapRecordsDAO {
    private Connection conn;

    public ScrapRecordsDAOImpl() {
        conn = DatabaseUtil.getConnection();
    }

    public ScrapRecord findById(int equipmentId) {
        ScrapRecord scrapRecord = null;
        String sql = "SELECT * FROM ScrapRecords WHERE equipment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, equipmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                scrapRecord = new ScrapRecord();
                scrapRecord.setScrapId(rs.getInt("scrap_id"));
                scrapRecord.setEquipmentId(rs.getInt("equipment_id"));
                scrapRecord.setEquipmentName(rs.getString("equipment_name"));
                scrapRecord.setScrapDate(rs.getDate("scrap_date"));
                scrapRecord.setScrapReason(rs.getString("scrap_reason"));
                scrapRecord.setDisposalMethod(rs.getString("disposal_method"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scrapRecord;
    }

    public List<ScrapRecord> findAll() {
        List<ScrapRecord> scrapRecords = new ArrayList<>();
        String sql = "SELECT * FROM ScrapRecords";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ScrapRecord scrapRecord = new ScrapRecord();
                scrapRecord.setScrapId(rs.getInt("scrap_id"));
                scrapRecord.setEquipmentId(rs.getInt("equipment_id"));
                scrapRecord.setEquipmentName(rs.getString("equipment_name"));
                scrapRecord.setScrapDate(rs.getDate("scrap_date"));
                scrapRecord.setScrapReason(rs.getString("scrap_reason"));
                scrapRecord.setDisposalMethod(rs.getString("disposal_method"));
                scrapRecords.add(scrapRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scrapRecords;
    }

    public void save(ScrapRecord scrapRecord) {
        String sql = "INSERT INTO ScrapRecords (scrap_id, equipment_id, equipment_name, scrap_date, scrap_reason, disposal_method) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            scrapRecord.setScrapId(TimestampIdGenerator.generateId());
            stmt.setInt(1, scrapRecord.getScrapId());
            stmt.setInt(2, scrapRecord.getEquipmentId());
            stmt.setString(3, scrapRecord.getEquipmentName());
            stmt.setDate(4, new java.sql.Date(scrapRecord.getScrapDate().getTime()));
            stmt.setString(5, scrapRecord.getScrapReason());
            stmt.setString(6, scrapRecord.getDisposalMethod());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ScrapRecord scrapRecord) {
        String sql = "UPDATE ScrapRecords SET  equipment_name = ?, scrap_date = ?, " +
                "scrap_reason = ?, disposal_method = ? WHERE scrap_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scrapRecord.getEquipmentName());
            stmt.setDate(2, new java.sql.Date(scrapRecord.getScrapDate().getTime()));
            stmt.setString(3, scrapRecord.getScrapReason());
            stmt.setString(4, scrapRecord.getDisposalMethod());
            stmt.setInt(5, scrapRecord.getScrapId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int scrapId) {
        String sql = "DELETE FROM ScrapRecords WHERE scrap_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scrapId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
