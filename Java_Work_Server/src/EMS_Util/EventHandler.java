package EMS_Util;

public class EventHandler {
    public EventHandler(){}

    public String handlEvent(String msg){
        String response = "";
        String[] parts = msg.split(",");            //对发来的消息进行分割
        //System.out.println(parts.length);
        if (parts.length < 2||msg.charAt(0)=='0') {
            return "格式错误";
        }
        String event=parts[1];
        //System.out.println(event);
        switch(event){
            case "INSERT":
                if(msg.charAt(0)=='1'){
                    response=new MsgHandler().handleInsert1(parts);
                }else if(msg.charAt(0)=='2'){
                    response=new MsgHandler().handleInsert2(parts);
                }else if(msg.charAt(0)=='3'){
                    response=new MsgHandler().handleInsert3(parts);
                }else if(msg.charAt(0)=='4'){
                    response=new MsgHandler().handleInsert4(parts);
                }else if(msg.charAt(0)=='5'){
                    response=new MsgHandler().handleInsert5(parts);
                }
                break;
            case "DELETE":
                response="执行删除操作";
                if(msg.charAt(0)=='1'){
                    response=new MsgHandler().handleDelete1(parts);
                }else if(msg.charAt(0)=='2'){
                    //todo:删除账户
                    //response=new MsgHandler().handleDelete2(parts);
                }else if(msg.charAt(0)=='3'){
                    response=new MsgHandler().handleDelete3(parts);
                }else if(msg.charAt(0)=='4'){
                    response=new MsgHandler().handleDelete4(parts);
                }else if(msg.charAt(0)=='5'){
                    response=new MsgHandler().handleDelete5(parts);
                }
                break;
            case "FIND":
                response="执行查找操作";
                if(msg.charAt(0)=='1'){
                    //System.out.println("find1");
                    response=new MsgHandler().handleFind1(parts);
                }else if(msg.charAt(0)=='2'){
                    response=new MsgHandler().handleFind2(parts);
                }else if(msg.charAt(0)=='3'){
                    response=new MsgHandler().handleFind3(parts);
                }else if(msg.charAt(0)=='4'){
                    response=new MsgHandler().handleFind4(parts);
                }else if(msg.charAt(0)=='5'){
                    response=new MsgHandler().handleFind5(parts);
                }
                break;
            case "UPDATE":
                response="执行更新操作";
                if(msg.charAt(0)=='1'){
                    response=new MsgHandler().handleUpdate1(parts);
                }else if(msg.charAt(0)=='2'){
                    //todo:修改密码
                    //response=new MsgHandler().handleUpdate2(parts);
                }else if(msg.charAt(0)=='3'){
                    response=new MsgHandler().handleUpdate3(parts);
                }else if(msg.charAt(0)=='4'){
                    response=new MsgHandler().handleUpdate4(parts);
                }else if(msg.charAt(0)=='5'){
                    response=new MsgHandler().handleUpdate5(parts);
                }
                break;
            default:
                response="无效操作";
                break;
        }

        return response;
    }
}
