package C_UI;

import Client.Client;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Client client;
    private JTabbedPane tabbedPane;

    public MainFrame(Client client) {
        this.client = client;           //接收loginframe传来的socket
        setTitle("设备管理系统");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //添加各个模块的UI组件
        tabbedPane = new JTabbedPane();
        tabbedPane.add("设备管理", new EquipmentManagementPanel(client));
        tabbedPane.add("修理记录", new RepairRecordsPanel(client));
        tabbedPane.add("报废记录", new ScrapRecordsPanel(client));
        tabbedPane.add("购置申请", new PurchaseRequestsPanel(client));
        //tabbedPane.add("查询统计", new QueryStatisticsPanel(client));
        add(tabbedPane);
        setVisible(true);

    }
}
