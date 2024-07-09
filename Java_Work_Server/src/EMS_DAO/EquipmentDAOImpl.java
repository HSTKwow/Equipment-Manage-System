package EMS_DAO;
import Model.*;
import java.sql.*;
import EMS_Util.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAOImpl implements EquipmentDAO{
    Connection conn;
    //构造函数
    public EquipmentDAOImpl() {     //构造函数
       conn = DatabaseUtil.getConnection();
       System.out.println("数据库连接成功");
    }
    //按照ID查询
    public Equipment findById(int id) {
        Equipment e = null;
        String sql = "SELECT * FROM Equipment WHERE equipment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                e = new Equipment();
                e.setEquipmentId(rs.getInt("equipment_id"));
                e.setEquipmentName(rs.getString("equipment_name"));
                e.setEquipmentCategory(rs.getString("equipment_category"));
                e.setSpecification(rs.getString("specification"));
                e.setUnitPrice(rs.getDouble("unit_price"));
                e.setQuantity(rs.getInt("quantity"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setManufacturer(rs.getString("manufacturer"));
                e.setWarrantyPeriod(rs.getString("warranty_period"));
                e.setHandledBy(rs.getString("handled_by"));
                e.setState(rs.getString("state"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return e;
    }
    //按照设备名称查询
    public List<Equipment> findByName(String name){
        List<Equipment> equipList =new ArrayList<>();
        String sql ="SELECT * FROM Equipment WHERE equipment_name = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Equipment e = new Equipment();
                e.setEquipmentId(rs.getInt("equipment_id"));
                e.setEquipmentName(rs.getString("equipment_name"));
                e.setEquipmentCategory(rs.getString("equipment_category"));
                e.setSpecification(rs.getString("specification"));
                e.setUnitPrice(rs.getDouble("unit_price"));
                e.setQuantity(rs.getInt("quantity"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setManufacturer(rs.getString("manufacturer"));
                e.setWarrantyPeriod(rs.getString("warranty_period"));
                e.setHandledBy(rs.getString("handled_by"));
                e.setState(rs.getString("state"));
                equipList.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipList;
    }
    //查找所有的设备
    public List<Equipment> findAll() {
        List<Equipment> equipList = new ArrayList<>();
        String sql = "SELECT * FROM Equipment";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                Equipment e = new Equipment();
                e.setEquipmentId(rs.getInt("equipment_id"));
                e.setEquipmentName(rs.getString("equipment_name"));
                e.setEquipmentCategory(rs.getString("equipment_category"));
                e.setSpecification(rs.getString("specification"));
                e.setUnitPrice(rs.getDouble("unit_price"));
                e.setQuantity(rs.getInt("quantity"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setManufacturer(rs.getString("manufacturer"));
                e.setWarrantyPeriod(rs.getString("warranty_period"));
                e.setHandledBy(rs.getString("handled_by"));
                e.setState(rs.getString("state"));
                equipList.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipList;
    }
    //保存一个
    public void save(Equipment equipment) {
        String sql = "INSERT INTO Equipment (equipment_id, equipment_name, equipment_category, specification, unit_price, " +
                "quantity, purchase_date, manufacturer, warranty_period, handled_by, state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            equipment.setEquipmentId(TimestampIdGenerator.generateId());
            stmt.setInt(1, equipment.getEquipmentId());
            System.out.println(equipment.getEquipmentId());
            stmt.setString(2, equipment.getEquipmentName());
            stmt.setString(3, equipment.getEquipmentCategory());
            stmt.setString(4, equipment.getSpecification());
            stmt.setDouble(5, equipment.getUnitPrice());
            stmt.setInt(6, equipment.getQuantity());
            // 转换 java.util.Date 为 java.sql.Date
            Date purchaseDate = new Date(equipment.getPurchaseDate().getTime());
            stmt.setDate(7, purchaseDate);
            stmt.setString(8, equipment.getManufacturer());
            stmt.setString(9, equipment.getWarrantyPeriod());
            stmt.setString(10, equipment.getHandledBy());
            stmt.setString(11, equipment.getState());
            stmt.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    //更新一个
    public void update(Equipment equipment) {
        String sql = "UPDATE Equipment SET equipment_name = ?, equipment_category = ?, specification = ?, " +
                "unit_price = ?, quantity = ?, purchase_date = ?, manufacturer = ?, warranty_period = ?, " +
                "handled_by = ?, state = ? WHERE equipment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipment.getEquipmentName());
            stmt.setString(2, equipment.getEquipmentCategory());
            stmt.setString(3, equipment.getSpecification());
            stmt.setDouble(4, equipment.getUnitPrice());
            stmt.setInt(5, equipment.getQuantity());
            // 转换 java.util.Date 为 java.sql.Date
            Date purchaseDate = new Date(equipment.getPurchaseDate().getTime());
            stmt.setDate(6, purchaseDate);
            stmt.setString(7, equipment.getManufacturer());
            stmt.setString(8, equipment.getWarrantyPeriod());
            stmt.setString(9, equipment.getHandledBy());
            stmt.setInt(11, equipment.getEquipmentId());
            stmt.setString(10, equipment.getState());
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    //删除一个
    public void delete(int equipmentId) {
        String sql = "DELETE FROM Equipment WHERE equipment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, equipmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
