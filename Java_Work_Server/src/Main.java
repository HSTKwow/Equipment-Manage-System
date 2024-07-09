//import EMS_DAO.EquipmentDAO;
//import EMS_DAO.EquipmentDAOImpl;
//
//public class Main {
//    public static void main(String[] args) {
//
//        EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
//
//        //按ID查找
////        Model.Equipment equipment = equipmentDAO.findById(1719582536);
////        System.out.println("Found Model.Equipment: " + equipment);
//
//        //查询所有设备信息
////        List<Equipment> equipmentList = equipmentDAO.findAll();
////        for (Model.Equipment e : equipmentList) {
////            System.out.println(e.getEquipmentId()+" "+e.getEquipmentName());
////        }
//
//        //按名字查找
////        List<Equipment> equipList = equipmentDAO.findByName("test1");
////        System.out.println(equipList.size());
////        for (Model.Equipment e : equipList) {
////            System.out.println(e.getEquipmentId()+" "+e.getEquipmentName());
////        }
//
//        // 添加新设备
////        Model.Equipment newEquipment = new Model.Equipment();       //创建一个对象
////        newEquipment.setEquipmentName("测试设备1");
////        newEquipment.setEquipmentCategory("A");
////        newEquipment.setSpecification("C");
////        newEquipment.setUnitPrice(1500.0);
////        newEquipment.setQuantity(2);
////        newEquipment.setPurchaseDate(new java.util.Date());
////        newEquipment.setManufacturer("test Company");
////        newEquipment.setWarrantyPeriod("3 years");
////        newEquipment.setHandledBy("Handler2");
////        equipmentDAO.save(newEquipment);
//
//        // 更新设备信息
////        equipment.setEquipmentName("Updated Model.Equipment Name");
////        equipmentDAO.update(equipment);
//
//        // 删除设备信息
////        equipmentDAO.delete(equipment.getEquipmentId());
//    }
//}
//
