package EMS_DAO;

import Model.*;
import java.util.List;

public interface RepairRecordsDAO {
    RepairRecord findById(int recordId);
    List<RepairRecord> findAll();
    void save(RepairRecord repairRecord);
    void update(RepairRecord repairRecord);
    void delete(int recordId);
}
