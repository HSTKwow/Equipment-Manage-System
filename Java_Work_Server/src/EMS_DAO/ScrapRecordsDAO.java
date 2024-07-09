package EMS_DAO;

import Model.*;
import java.util.List;

public interface ScrapRecordsDAO {
    ScrapRecord findById(int scrapId);
    List<ScrapRecord> findAll();
    void save(ScrapRecord scrapRecord);
    void update(ScrapRecord scrapRecord);
    void delete(int scrapId);
}
