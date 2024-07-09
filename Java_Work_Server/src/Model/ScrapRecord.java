package Model;

import java.util.Date;

public class ScrapRecord {
    private int scrapId;
    private int equipmentId;
    private String equipmentName;
    private Date scrapDate;
    private String scrapReason;
    private String disposalMethod;

    public int getScrapId() {
        return scrapId;
    }

    public void setScrapId(int scrapId) {
        this.scrapId = scrapId;
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

    public Date getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(Date scrapDate) {
        this.scrapDate = scrapDate;
    }

    public String getScrapReason() {
        return scrapReason;
    }

    public void setScrapReason(String scrapReason) {
        this.scrapReason = scrapReason;
    }

    public String getDisposalMethod() {
        return disposalMethod;
    }

    public void setDisposalMethod(String disposalMethod) {
        this.disposalMethod = disposalMethod;
    }

    public String toString() {
        return "ScrapRecord{" +
                "scrapId=" + scrapId +
                ", equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", scrapDate=" + scrapDate +
                ", scrapReason='" + scrapReason + '\'' +
                ", disposalMethod='" + disposalMethod + '\'' +
                '}';
    }
}
