package EMS_DAO;

import EMS_Util.DatabaseUtil;
import EMS_Util.TimestampIdGenerator;
import Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairRecordsDAOImpl implements RepairRecordsDAO {
    private Connection conn;

    public RepairRecordsDAOImpl() {
        // 初始化数据库连接
        conn = DatabaseUtil.getConnection();
    }

    public RepairRecord findById(int recordId) {
        RepairRecord record = null;
        String sql = "SELECT * FROM RepairRecords WHERE record_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                record = new RepairRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setEquipmentId(rs.getInt("equipment_id"));
                record.setEquipmentName(rs.getString("equipment_name"));
                record.setRepairDate(rs.getDate("repair_date"));
                record.setRepairCompany(rs.getString("repair_company"));
                record.setRepairCost(rs.getDouble("repair_cost"));
                record.setResponsiblePerson(rs.getString("responsible_person"));
                record.setRepairDescription(rs.getString("repair_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public List<RepairRecord> findAll() {
        List<RepairRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM RepairRecords";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RepairRecord record = new RepairRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setEquipmentId(rs.getInt("equipment_id"));
                record.setEquipmentName(rs.getString("equipment_name"));
                record.setRepairDate(rs.getDate("repair_date"));
                record.setRepairCompany(rs.getString("repair_company"));
                record.setRepairCost(rs.getDouble("repair_cost"));
                record.setResponsiblePerson(rs.getString("responsible_person"));
                record.setRepairDescription(rs.getString("repair_description"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void save(RepairRecord record) {
        String sql = "INSERT INTO RepairRecords (record_id, equipment_id, equipment_name, repair_date, " +
                "repair_company, repair_cost, responsible_person, repair_description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int generatedId = TimestampIdGenerator.generateId();
            record.setRecordId(generatedId);
            stmt.setInt(1, record.getRecordId());
            stmt.setInt(2, record.getEquipmentId());
            stmt.setString(3, record.getEquipmentName());
            Date RepairDate = new Date(record.getRepairDate().getTime());
            stmt.setDate(4, RepairDate);
            stmt.setString(5, record.getRepairCompany());
            stmt.setDouble(6, record.getRepairCost());
            stmt.setString(7, record.getResponsiblePerson());
            stmt.setString(8, record.getRepairDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(RepairRecord record) {
        String sql = "UPDATE RepairRecords SET  equipment_name = ?, repair_date = ?, " +
                "repair_company = ?, repair_cost = ?, responsible_person = ?, repair_description = ? " +
                "WHERE record_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, record.getEquipmentName());
            stmt.setDate(2, new java.sql.Date(record.getRepairDate().getTime()));
            stmt.setString(3, record.getRepairCompany());
            stmt.setDouble(4, record.getRepairCost());
            stmt.setString(5, record.getResponsiblePerson());
            stmt.setString(6, record.getRepairDescription());
            stmt.setInt(7, record.getRecordId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int recordId) {
        String sql = "DELETE FROM RepairRecords WHERE record_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recordId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
