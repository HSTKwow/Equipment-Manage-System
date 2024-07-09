package C_UI;

import Client.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PurchaseRequestsPanel extends JPanel {
    private Client client;
    private JTable PurchaseRequests;
    private DefaultTableModel tableModel;

    public PurchaseRequestsPanel(Client client) {
        this.client = client;
        setLayout(new BorderLayout());
        // 创建按钮
        JButton addButton = new JButton("添加申请信息");
        JButton censorButton = new JButton("审批申请记录");
        JButton deleteButton = new JButton("删除申请");
        JButton queryButton = new JButton("查询购置申请");
        JButton freshButton = new JButton("刷新页面");
        // 创建按钮面板并添加
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(censorButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(queryButton);
        buttonPanel.add(freshButton);
        // 将按钮面板和表格添加到主面板
        add(buttonPanel, BorderLayout.NORTH);
        //创建表格模型并添加到表格中
        tableModel = new PurchaseRequestsPanel.NonEditableTableModel();
        String[] columnNames = {"申请编号", "设备名称", "设备类别", "数量", "申请日期", "申请人", "批准人", "批准日期", "状态"};
        tableModel.setColumnIdentifiers(columnNames);
        PurchaseRequests = new JTable(tableModel);
        JScrollPane scrollPanel = new JScrollPane(PurchaseRequests);
        add(scrollPanel, BorderLayout.CENTER);
        //添加按钮监听器
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });
        //审查按钮监听器
        censorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCensorDialog();
            }
        });
        //刷新按钮监听器
        freshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFresh();
            }
        });
        //查询按钮监听器
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPurchaseQuery();
            }
        });
        //删除按钮监听器
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDeleteDialog();
            }
        });
    }
    //todo:将这些方法写成接口
    //添加
    private void openAddDialog(){
        JDialog dialog = new JDialog((Frame) null, "添加申请", true);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField equipmentNameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField requestDateField = new JTextField();
        JTextField requesterField  = new JTextField();

        dialog.add(new JLabel("设备名称"));
        dialog.add(equipmentNameField);
        dialog.add(new JLabel("设备类别"));
        dialog.add(categoryField);
        dialog.add(new JLabel("数量"));
        dialog.add(quantityField);
        dialog.add(new JLabel("申请日期"));
        dialog.add(requestDateField);
        dialog.add(new JLabel("申请人"));
        dialog.add(requesterField);
        dialog.add(new JLabel(""));
        JButton submitButton = new JButton("提交");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String equipmentName = equipmentNameField.getText().trim();
                String category = categoryField.getText().trim();
                String quantity = quantityField.getText().trim();
                String requestDate = requestDateField.getText().trim();
                String requester = requesterField.getText().trim();
                String approver = "无";
                String approvaDate = "1900.1.1";            //不填写默认是1900.1.1
                String state = "待审核";

                //给服务器发送的消息
                String message = String.format("3,INSERT,%s,%s,%s,%s,%s,%s,%s,%s",
                        equipmentName,category,quantity,requestDate,requester,approver,approvaDate,state);
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    System.out.println(response);
                    if (response != null && response.equals("添加成功")) {
                        JOptionPane.showMessageDialog(dialog, "设备添加成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "设备添加失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        dialog.add(submitButton);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //审查
    private void openCensorDialog(){
        JDialog dialog = new JDialog((Frame) null, "添加申请", true);
        dialog.setLayout(new GridLayout(4, 2));

        JTextField requestIdField = new JTextField();
        JTextField approverField = new JTextField();
        JTextField approvaDateField= new JTextField();

        dialog.add(new JLabel("申请ID"));
        dialog.add(requestIdField);
        dialog.add(new JLabel("批准人"));
        dialog.add(approverField);
        dialog.add(new JLabel("批准日期"));
        dialog.add(approvaDateField);
        JButton agreeButton = new JButton("批准");
        JButton disagreeButton = new JButton("拒绝");
        dialog.add(agreeButton);
        dialog.add(disagreeButton);

        agreeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int requestId = Integer.parseInt(requestIdField.getText().trim());
                String approver = approverField.getText().trim();
                String approvaDate = approvaDateField.getText().trim();

                //给服务器发送的消息
                String message = String.format("3,UPDATE,%d,%s,%s,%s",
                        requestId, approver, approvaDate, "已批准");
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    System.out.println(response);
                    if (response != null && response.equals("更新成功")) {
                        JOptionPane.showMessageDialog(dialog, "设备更新成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "设备更新失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        disagreeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int requestId = Integer.parseInt(requestIdField.getText().trim());
                String approver = approverField.getText().trim();
                String approvaDate = approvaDateField.getText().trim();
                String message = String.format("3,UPDATE,%d,%s,%s,%s",
                        requestId, approver, approvaDate,"已拒绝");
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    System.out.println(response);
                    if (response != null && response.equals("更新成功")) {
                        JOptionPane.showMessageDialog(dialog, "更新成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "更新失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //刷新
    private void openFresh(){
        String serverResponse = null;
        try {
            //向服务器发送请求并获取设备信息
            String message = "3,FIND";
            //client是你的客户端实例，可以调用它来与服务器通信
            PurchaseRequestsPanel.this.client.sendMessage(message);
            serverResponse = PurchaseRequestsPanel.this.client.receiveMessage();
            System.out.println(serverResponse);
            //处理从服务器返回的设备信息字符串
            processServerResponse(serverResponse);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //查询
    private void openPurchaseQuery() {
        JDialog dialog = new JDialog((Frame) null, "查询申请信息", true);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // 按设备类别查询
        JPanel idQueryPanel = new JPanel(new GridLayout(2, 2));
        JTextField requestIdField = new JTextField();
        JButton idSearchButton = new JButton("查询");

        idQueryPanel.add(new JLabel("申请编号:"));
        idQueryPanel.add(requestIdField);
        idQueryPanel.add(new JLabel(""));       //占位
        idQueryPanel.add(idSearchButton);

        idSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int requestId= Integer.parseInt(requestIdField.getText().trim());
                String message = String.format("3,FIND,%d", requestId);
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    processServerResponse(response);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        tabbedPane.addTab("按申请编号查询", idQueryPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.setSize(500,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //删除
    private void openDeleteDialog(){
        JDialog dialog = new JDialog((Frame) null, "删除申请", true);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField equipmentIdField = new JTextField();

        dialog.add(new JLabel("申请编号:"));
        dialog.add(equipmentIdField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long equipmentId = Long.parseLong(equipmentIdField.getText().trim());

                String message = String.format("3,DELETE,%d", equipmentId);
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    System.out.println(response);
                    if (response != null && response.equals("删除成功")) {
                        JOptionPane.showMessageDialog(dialog, "设备删除成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "设备删除失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        dialog.add(submitButton);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //处理字符串
    private void processServerResponse(String serverResponse) {
        if (serverResponse != null && !serverResponse.isEmpty()) {
            String[] lines = serverResponse.split("#");         //按#分割成行
            //System.out.println(lines.length);
            //清空表格
            tableModel.setRowCount(0);

            //遍历每一行，解析设备信息并添加到表格模型中
            for (String line : lines) {
                String[] parts = line.split(",");     //在按 , 分割列
                //System.out.println(lines.length);
                if (parts.length == 9) {               //一行的列数有11个
                    int requestId = Integer.parseInt(parts[0]);
                    String equipmentName = parts[1];
                    String category = parts[2];
                    String quantity = parts[3];
                    String requestDate = parts[4];
                    String applicant = parts[5];
                    String confirmer = parts[6];
                    String approveDate = parts[7];
                    String state = parts[8];
                    // 将数据添加到表格模型中
                    Object[] rowData = {requestId, equipmentName, category, quantity, requestDate, applicant, confirmer, approveDate, state};
                    tableModel.addRow(rowData);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "未能获取设备信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    //子框是否可编辑
    private static class NonEditableTableModel extends DefaultTableModel {
        public boolean isCellEditable(int row, int column) {
            return true;  //设置所有单元格都不可编辑
        }
    }
}
