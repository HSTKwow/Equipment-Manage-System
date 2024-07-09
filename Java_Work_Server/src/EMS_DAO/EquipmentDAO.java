package EMS_DAO;
import Model.*;
import java.util.List;
public interface EquipmentDAO {
    Equipment findById(int equipmentId);        //通过ID查找
    List<Equipment> findAll();                  //所有的记录
    List<Equipment> findByName(String name);    //按照设备名称开始查找
    void save(Equipment equipment);             //保存
    void update(Equipment equipment);           //更新
    void delete(int equipmentId);               //删除
}
