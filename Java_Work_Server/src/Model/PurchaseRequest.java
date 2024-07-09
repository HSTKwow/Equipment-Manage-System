package Model;

import java.util.Date;

public class PurchaseRequest {
    private int requestId;        // 申请编号 (Primary Key)
    private String equipmentName; // 设备名称
    private String category; // 设备类别
    private int quantity;         // 数量
    private Date requestDate;     // 申请日期
    private String requester;     // 申请人
    private String approver;      // 批准人
    private Date approvalDate;    // 批准日期
    private String status;        // 状态 (待审批、已批准、已拒绝等)

    // Constructors
    public PurchaseRequest() {
    }

    public PurchaseRequest(int requestId, String equipmentName, String category, int quantity,
                           Date requestDate, String requester, String approver, Date approvalDate, String status) {
        this.requestId = requestId;
        this.equipmentName = equipmentName;
        this.category = category;
        this.quantity = quantity;
        this.requestDate = requestDate;
        this.requester = requester;
        this.approver = approver;
        this.approvalDate = approvalDate;
        this.status = status;
    }

    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

