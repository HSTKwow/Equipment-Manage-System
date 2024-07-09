package EMS_Util;

import EMS_DAO.*;
        import Model.*;

        import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MsgHandler {
    public MsgHandler(){}

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //插入方法
    public String handleInsert1(String[] parts) {
        String res="添加失败";

        // 创建一个新的 Equipment 对象并设置属性
        Equipment e = new Equipment();
        e.setEquipmentName(parts[2].trim());
        e.setEquipmentCategory(parts[3].trim());
        e.setSpecification(parts[4].trim());
        e.setUnitPrice(Double.parseDouble(parts[5].trim()));
        e.setQuantity(Integer.parseInt(parts[6].trim()));
        // 解析购置日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date purchaseDate = dateFormat.parse(parts[7].trim());
            e.setPurchaseDate(purchaseDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid purchase date format", ex);
        }

        e.setManufacturer(parts[8].trim());
        e.setWarrantyPeriod(parts[9].trim());
        e.setHandledBy(parts[10].trim());
        e.setState(parts[11].trim());

        EquipmentDAO ed = new EquipmentDAOImpl();
        ed.save(e);

        res = "添加成功";
        return res;
    }
    public String handleInsert2(String[] parts){
        String res;
        //创建一个新的 Equipment 对象并设置属性
        User u = new User();
        u.setUserName(parts[2].trim());
        u.setUserPassward(parts[3].trim());
        usersDAO ud = new usersDAOImpl();
        ud.save(u);
        res = "添加成功";
        return res;
    }
    public String handleInsert3(String[] parts){
        String res="添加失败";
        //System.out.println("I3");
        // 创建一个新的 RepairRecord 对象并设置属性
        PurchaseRequest pr = new PurchaseRequest();
        pr.setEquipmentName(parts[2].trim());
        pr.setCategory(parts[3].trim());
        pr.setQuantity(Integer.parseInt(parts[4].trim()));
        //解析请求日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date repairDate = dateFormat.parse(parts[5].trim());
            pr.setRequestDate(repairDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        pr.setRequester(parts[6].trim());
        pr.setApprover(parts[7].trim());
        //解析批准日期
        try {
            Date approvaDate = dateFormat.parse(parts[8].trim());
            pr.setApprovalDate(approvaDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        pr.setStatus(parts[9].trim());

        PurchaseRequestsDAO prd=new PurchaseRequestsDAOImpl();
        prd.save(pr);
        res="添加成功";
        return res;
    }
    public String handleInsert4(String[] parts){
        String res="添加失败";
        //System.out.println("I4");
        // 创建一个新的 RepairRecord 对象并设置属性
        RepairRecord rr = new RepairRecord();
        rr.setEquipmentId(Integer.parseInt(parts[2].trim())); //设备编号
        rr.setEquipmentName(parts[3].trim());

        // 解析修理日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date repairDate = dateFormat.parse(parts[4].trim());
            rr.setRepairDate(repairDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid repair date format", ex);
        }

        rr.setRepairCompany(parts[5].trim());
        rr.setRepairCost(Double.parseDouble(parts[6].trim()));
        rr.setResponsiblePerson(parts[7].trim());
        rr.setRepairDescription(parts[8].trim());

        RepairRecordsDAO rrd = new RepairRecordsDAOImpl();
        rrd.save(rr);

        //将设备状态修改维修中
        EquipmentDAO ed=new EquipmentDAOImpl();
        Equipment e=ed.findById(rr.getEquipmentId());
        e.setState("维修中");
        ed.update(e);

        res="添加成功";
        return res;
    }
    public String handleInsert5(String[] parts){
        String res="添加失败";
        //System.out.println("I5");
        // 创建一个新的 RepairRecord 对象并设置属性
        ScrapRecord sr = new ScrapRecord();
        sr.setEquipmentId(Integer.parseInt(parts[2].trim())); //设备编号
        sr.setEquipmentName(parts[3].trim());
        // 解析修理日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date scrapDate = dateFormat.parse(parts[4].trim());
            sr.setScrapDate(scrapDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid repair date format", ex);
        }
        sr.setScrapReason(parts[5].trim());
        sr.setDisposalMethod(parts[6].trim());
        ScrapRecordsDAO srd = new ScrapRecordsDAOImpl();
        srd.save(sr);

        //将设备修改成报废状态
        EquipmentDAO ed=new EquipmentDAOImpl();
        Equipment e=ed.findById(sr.getEquipmentId());
        e.setState("报废");
        ed.update(e);

        res="添加成功";
        return res;
    }

    //删除方法
    public String handleDelete1(String[] parts){
        String res="删除失败";
        EquipmentDAO ed = new EquipmentDAOImpl();
        //System.out.println(Integer.parseInt(parts[2].trim()));
        ed.delete(Integer.parseInt(parts[2].trim()));
        res="删除成功";
        return res;
    }
    public String handleDelete2(String[] parts){
        String res="删除失败";
        //todo:删除用户
        res="删除成功";
        return res;
    }
    public String handleDelete3(String[] parts){
        String res="删除失败";
        PurchaseRequestsDAO prd = new PurchaseRequestsDAOImpl();
        prd.delete(Integer.parseInt(parts[2].trim()));
        res="删除成功";
        return res;
    }
    public String handleDelete4(String[] parts){
        String res="删除失败";
        RepairRecordsDAO rrd=new RepairRecordsDAOImpl();
        rrd.delete(Integer.parseInt(parts[2].trim()));
        res="删除成功";
        return res;
    }
    public String handleDelete5(String[] parts){
        String res="删除失败";
        ScrapRecordsDAO srd=new ScrapRecordsDAOImpl();
        srd.delete(Integer.parseInt(parts[2].trim()));
        res="删除成功";
        return res;
    }

    //查找方法
    public String handleFind1(String[] parts){
        String res="查找失败";
        //System.out.println("FindAll");
        EquipmentDAO ed = new EquipmentDAOImpl();
        StringBuilder result = new StringBuilder();
        //进行find的区分
        if(parts.length==2){
            List<Equipment> eList = ed.findAll();
            //将列表转换为String返回
            for (Equipment e : eList) {
                result.append(e.getEquipmentId()).append(",");
                result.append(e.getEquipmentName()).append(",");
                result.append(e.getEquipmentCategory()).append(",");
                result.append(e.getSpecification()).append(",");
                result.append(e.getUnitPrice()).append(",");
                result.append(e.getQuantity()).append(",");
                result.append(e.getPurchaseDate()).append(",");
                result.append(e.getManufacturer()).append(",");
                result.append(e.getWarrantyPeriod()).append(",");
                result.append(e.getHandledBy()).append(",");
                result.append(e.getState()).append("#");
            }
            res= result.toString();
        }else if(parts.length==3){
            String operation = parts[2].trim();         //判断find的类型
            System.out.println(operation);
            if (!isNumeric(operation)) {
                String name = parts[2].trim(); // 假设第二部分是名称
                List<Equipment> eList = ed.findByName(name);
                // 将列表转换为String返回
                for (Equipment e : eList) {
                    result.append(e.getEquipmentId()).append(",");
                    result.append(e.getEquipmentName()).append(",");
                    result.append(e.getEquipmentCategory()).append(",");
                    result.append(e.getSpecification()).append(",");
                    result.append(e.getUnitPrice()).append(",");
                    result.append(e.getQuantity()).append(",");
                    result.append(formatDate(e.getPurchaseDate())).append(",");
                    result.append(e.getManufacturer()).append(",");
                    result.append(e.getWarrantyPeriod()).append(",");
                    result.append(e.getHandledBy()).append(",");
                    result.append(e.getState()).append("#");
                }
                res = result.toString();
            } else if (isNumeric(operation)) {
                int id = Integer.parseInt(parts[2].trim()); // 假设第二部分是ID
                Equipment e = ed.findById(id);
                if (e != null) {
                    result.append(e.getEquipmentId()).append(",");
                    result.append(e.getEquipmentName()).append(",");
                    result.append(e.getEquipmentCategory()).append(",");
                    result.append(e.getSpecification()).append(",");
                    result.append(e.getUnitPrice()).append(",");
                    result.append(e.getQuantity()).append(",");
                    result.append(formatDate(e.getPurchaseDate())).append(",");
                    result.append(e.getManufacturer()).append(",");
                    result.append(e.getWarrantyPeriod()).append(",");
                    result.append(e.getHandledBy()).append(",");
                    result.append(e.getState()).append("#");
                    res = result.toString();
                }
            } else {
                throw new IllegalArgumentException("无效操作类型");
            }
        } else {
            throw new IllegalArgumentException("参数数量不正确");
        }
        return res;
    }
    public String handleFind2(String[] parts){
        String res="查找失败";
        //todo:使用户明可以不唯一
        usersDAO ud = new usersDAOImpl();
        StringBuilder result = new StringBuilder();
        if(parts.length==3){          //按照用户名产找
            String name = parts[2].trim();
            //System.out.println(equipmentId);
            User u = ud.findByName(name);
            if (u != null) {
                result.append(u.getUserName()).append(",");
                result.append(u.getUserPassward());
                res = result.toString();
            }
            else{
                return null;
            }
        } else {
            throw new IllegalArgumentException("参数数量不正确");
        }
        return res;
    }
    public String handleFind3(String[] parts){
        String res="查找失败";
        PurchaseRequestsDAO prd=new PurchaseRequestsDAOImpl();
        StringBuilder result=new StringBuilder();
        if(parts.length==2){        //执行FindAll
            //System.out.println("findall");
            List<PurchaseRequest> prList = prd.findAll();
            //将列表转换为String返回
            for (PurchaseRequest pr : prList) {
                result.append(pr.getRequestId()).append(",");
                result.append(pr.getEquipmentName()).append(",");
                result.append(pr.getCategory()).append(",");
                result.append(pr.getQuantity()).append(",");
                result.append(pr.getRequestDate()).append(",");
                result.append(pr.getRequester()).append(",");
                result.append(pr.getApprover()).append(",");
                //System.out.println(pr.getApprovalDate().toString());
                if(pr.getApprovalDate().toString().equals("1900-01-01")){
                    result.append("无").append(",");
                }else{
                    result.append(pr.getApprovalDate()).append(",");
                }
                //System.out.println(pr.getStatus());
                result.append(pr.getStatus()).append("#");
            }
            res= result.toString();
        }else if(parts.length==3){      //执行FindById
            PurchaseRequest pr = prd.findById(Integer.parseInt(parts[2].trim()));
            // 将列表转换为String返回
            if (pr != null) {
                result.append(pr.getRequestId()).append(",");
                result.append(pr.getEquipmentName()).append(",");
                result.append(pr.getCategory()).append(",");
                result.append(pr.getQuantity()).append(",");
                result.append(pr.getRequestDate()).append(",");
                result.append(pr.getRequester()).append(",");
                result.append(pr.getApprover()).append(",");
                if(pr.getApprovalDate().toString().equals("1900-01-01")){
                    System.out.println("已修改日期");
                    result.append("无").append(",");
                }else{
                    result.append(pr.getApprovalDate()).append(",");
                }
                result.append(pr.getStatus()).append("#");
            }
            res = result.toString();
        } else {
            throw new IllegalArgumentException("参数数量不正确");
        }

        return res;
    }
    public String handleFind4(String[] parts){
        String res="查找失败";
        RepairRecordsDAO rrd=new RepairRecordsDAOImpl();
        StringBuilder result=new StringBuilder();
        if(parts.length==2){        //执行FindAll
            //System.out.println("findall");
            List<RepairRecord> rrList = rrd.findAll();
            //将列表转换为String返回
            for (RepairRecord rr : rrList) {
                result.append(rr.getRecordId()).append(",");
                result.append(rr.getEquipmentId()).append(",");
                result.append(rr.getEquipmentName()).append(",");
                result.append(rr.getRepairDate()).append(",");
                result.append(rr.getRepairCompany()).append(",");
                result.append(rr.getRepairCost()).append(",");
                result.append(rr.getResponsiblePerson()).append(",");
                result.append(rr.getRepairDescription()).append("#");
            }
            res= result.toString();
        }else if(parts.length==3){      //执行FindById
            RepairRecord rr = rrd.findById(Integer.parseInt(parts[2].trim()));
            // 将列表转换为String返回
            if (rr != null) {
                result.append(rr.getRecordId()).append(",");
                result.append(rr.getEquipmentId()).append(",");
                result.append(rr.getEquipmentName()).append(",");
                result.append(rr.getRepairDate()).append(",");
                result.append(rr.getRepairCompany()).append(",");
                result.append(rr.getRepairCost()).append(",");
                result.append(rr.getResponsiblePerson()).append(",");
                result.append(rr.getRepairDescription()).append("#");
            }
            res = result.toString();
        } else {
            throw new IllegalArgumentException("参数数量不正确");
        }

        return res;
    }
    public String handleFind5(String[] parts){
        String res="查找失败";
        ScrapRecordsDAO srd=new ScrapRecordsDAOImpl();
        StringBuilder result=new StringBuilder();

        if(parts.length==2){        //执行FindAll
            //System.out.println("findall");
            List<ScrapRecord> srList = srd.findAll();
            //将列表转换为String返回
            for (ScrapRecord sr : srList) {
                result.append(sr.getScrapId()).append(",");
                result.append(sr.getEquipmentId()).append(",");
                result.append(sr.getEquipmentName()).append(",");
                result.append(sr.getScrapDate()).append(",");
                result.append(sr.getScrapReason()).append(",");
                result.append(sr.getDisposalMethod()).append("#");
            }
            res= result.toString();
        }else if(parts.length==3){      //执行FindById
            ScrapRecord sr = srd.findById(Integer.parseInt(parts[2].trim()));
            // 将列表转换为String返回
            if (sr != null) {
                result.append(sr.getScrapId()).append(",");
                result.append(sr.getEquipmentId()).append(",");
                result.append(sr.getEquipmentName()).append(",");
                result.append(sr.getScrapDate()).append(",");
                result.append(sr.getScrapReason()).append(",");
                result.append(sr.getDisposalMethod()).append("#");
            }
            res = result.toString();
        } else {
            throw new IllegalArgumentException("参数数量不正确");
        }
        return res;
    }

    //更新方法
    public String handleUpdate1(String[] parts){
        String res="更新失败";
        // 创建一个新的 Equipment 对象并设置属性
        Equipment e = new Equipment();
        e.setEquipmentName(parts[2].trim());
        e.setEquipmentCategory(parts[3].trim());
        e.setSpecification(parts[4].trim());
        e.setUnitPrice(Double.parseDouble(parts[5].trim()));
        e.setQuantity(Integer.parseInt(parts[6].trim()));
        // 解析购置日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date purchaseDate = dateFormat.parse(parts[7].trim());
            e.setPurchaseDate(purchaseDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        e.setManufacturer(parts[8].trim());
        e.setWarrantyPeriod(parts[9].trim());
        e.setHandledBy(parts[10].trim());
        e.setEquipmentId(Integer.parseInt(parts[11].trim()));
        e.setState(parts[12].trim());

        EquipmentDAO ed = new EquipmentDAOImpl();
        ed.update(e);

        res = "更新成功";
        return res;
    }
    public String handleUpdate2(String[] parts){
        String res="更新失败";

        res = "更新成功";
        return res;
    }
    public String handleUpdate3(String[] parts){
        String res="更新失败";
        //System.out.println(parts.length);
        //创建一个新的 PurchaseRequest 对象并设置属性
        PurchaseRequest pr = new PurchaseRequest();
        pr.setRequestId(Integer.parseInt(parts[2].trim()));
        pr.setApprover(parts[3].trim());
        //解析批准日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date approvaDate = dateFormat.parse(parts[4].trim());
            pr.setApprovalDate(approvaDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        pr.setStatus(parts[5].trim());
        System.out.println(pr.getStatus());
        PurchaseRequestsDAO prd = new PurchaseRequestsDAOImpl();
        prd.update(pr);

        res = "更新成功";
        return res;
    }
    public String handleUpdate4(String[] parts){
        String res="更新失败";
        //System.out.println(parts.length);
        //创建一个新的 RepairRecord 对象并设置属性
        RepairRecord rr = new RepairRecord();
        rr.setRecordId(Integer.parseInt(parts[2].trim()));
        rr.setEquipmentName(parts[3].trim());
        //解析购置日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date repairDate = dateFormat.parse(parts[4].trim());
            rr.setRepairDate(repairDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        rr.setRepairCompany(parts[5].trim());
        rr.setRepairCost(Double.parseDouble(parts[6].trim()));
        rr.setResponsiblePerson(parts[7].trim());
        rr.setRepairDescription(parts[8].trim());

        RepairRecordsDAO rrd = new RepairRecordsDAOImpl();
        rrd.update(rr);

        res = "更新成功";
        return res;
    }
    public String handleUpdate5(String[] parts){
        String res="更新失败";
        //System.out.println(parts.length);
        //创建一个新的 RepairRecord 对象并设置属性
        ScrapRecord sr = new ScrapRecord();
        sr.setScrapId(Integer.parseInt(parts[2].trim()));
        sr.setEquipmentName(parts[3].trim());
        //解析购置日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date scrapDate = dateFormat.parse(parts[4].trim());
            sr.setScrapDate(scrapDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid date format", ex);
        }
        sr.setScrapReason(parts[5].trim());
        sr.setDisposalMethod(parts[6].trim());

        ScrapRecordsDAO srd = new ScrapRecordsDAOImpl();
        srd.update(sr);

        res = "更新成功";
        return res;
    }

}
