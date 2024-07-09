package EMS_DAO;
import Model.*;

import java.util.List;

public interface PurchaseRequestsDAO {
    PurchaseRequest findById(int requestId);
    List<PurchaseRequest> findAll();
    void save(PurchaseRequest purchaseRequest);
    void update(PurchaseRequest purchaseRequest);
    void delete(int requestId);
}
