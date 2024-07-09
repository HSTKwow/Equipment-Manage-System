package Model;

import java.util.Date;

public class RepairRecord {
    private int recordId;
    private int equipmentId;
    private String equipmentName;
    private Date repairDate;
    private String repairCompany;
    private double repairCost;
    private String responsiblePerson;
    private String repairDescription;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairCompany() {
        return repairCompany;
    }

    public void setRepairCompany(String repairCompany) {
        this.repairCompany = repairCompany;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    @Override
    public String toString() {
        return "RepairRecord{" +
                "recordId=" + recordId +
                ", equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", repairDate=" + repairDate +
                ", repairCompany='" + repairCompany + '\'' +
                ", repairCost=" + repairCost +
                ", responsiblePerson='" + responsiblePerson + '\'' +
                ", repairDescription='" + repairDescription + '\'' +
                '}';
    }
}
