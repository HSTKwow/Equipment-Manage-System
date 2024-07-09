package Client;

import C_UI.LoginFrame;
import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("加载Nimbus失败");
        }

        new LoginFrame();       //进入登录界面
    }
}

/*

表5添加格式
5,INSERT,1719757189,测试设备,报废时间,报废原因,处理方式
表5删除格式
5,DELETE
表5查找格式
5,FIND
表5修改格式
5,UPDATE

表4添加格式
4,INSERT,1719757189,测试设备4,2024.2.14,test company,500.0,fixer0,aaaabbbbbbb
表4删除格式
4,DELETE,
表4查找格式
4,FIND,
表4修改格式
4,UPDATE,

表3添加格式
3,INSERT,
表3删除格式
3,DELETE
表3查找格式
3,FIND
表3修改格式
3,UPDATE

表2添加格式
2,INSERT,1719757189,正常,2024.5.31
表2删除格式
2,DELETE,1719757189
表2查找格式
2,FIND
2,FIND,
表2修改格式
2,UPDATE,1719757189,损坏,2024.5.4

表1添加格式750
1,INSERT,测试设备3,C,C,.0,20,2024.5.27,test company,1 years,Handler0
表1删除格式
1,DELETE,1719756432
表1查找格式
1,FIND
1,FIND,测试设备3
1,FIND,1719756432
表1修改格式
1,UPDATE,测试设备3,C,C,750.0,20,2024.5.27,test company,1 years,Handler0,1719793311

 */