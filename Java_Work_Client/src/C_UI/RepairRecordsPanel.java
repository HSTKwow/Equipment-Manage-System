package C_UI;

import Client.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RepairRecordsPanel extends JPanel {
    private Client client;
    private JTable repairTable;      //表格
    private DefaultTableModel tableModel;

    public RepairRecordsPanel(Client client) {
        this.client = client;
        setLayout(new BorderLayout());
        //创建按钮
        JButton addButton = new JButton("添加修理记录");
        JButton modifyButton = new JButton("修改修理记录");
        JButton deleteButton = new JButton("删除修理记录");
        JButton queryButton = new JButton("查询修理记录");
        JButton freshButton = new JButton("刷新页面");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(queryButton);
        buttonPanel.add(freshButton);

        add(buttonPanel, BorderLayout.NORTH);

        //创建表格模型并添加到表格中
        tableModel = new RepairRecordsPanel.NonEditableTableModel();
        String[] columnNames = {"维修编号", "设备编号", "设备名称", "维修日期", "维修公司","维修花费","负责人","维修描述"};
        tableModel.setColumnIdentifiers(columnNames);
        repairTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(repairTable);
        add(scrollPane, BorderLayout.CENTER);
        //添加按钮监听器
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });
        //修改按钮监听器
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openModifyDialog();
            }
        });
        //删除按钮监听器
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDeleteDialog();
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
                openQueryDialog();
            }
        });
    }
    //todo:将这些方法写成接口
    //添加
    private void openAddDialog(){
        JDialog dialog = new JDialog((Frame) null, "添加设备", true);
        dialog.setLayout(new GridLayout(8, 2));

        JTextField equipmentIdField = new JTextField();
        JTextField equipmentNameField = new JTextField();
        JTextField repairDateField = new JTextField();
        JTextField repairCompanyField = new JTextField();
        JTextField repairCostField = new JTextField();
        JTextField responsePersonField = new JTextField();
        JTextField repairDescriptionField = new JTextField();

        dialog.add(new JLabel("设备编号"));
        dialog.add(equipmentIdField);
        dialog.add(new JLabel("设备名称"));
        dialog.add(equipmentNameField);
        dialog.add(new JLabel("维修日期"));
        dialog.add(repairDateField);
        dialog.add(new JLabel("维修公司"));
        dialog.add(repairCompanyField);
        dialog.add(new JLabel("维修花费"));
        dialog.add(repairCostField);
        dialog.add(new JLabel("责任人"));
        dialog.add(responsePersonField);
        dialog.add(new JLabel("维修描述"));
        dialog.add(repairDescriptionField);
        dialog.add(new JLabel(""));
        JButton submitButton = new JButton("提交");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String equipmentId = equipmentIdField.getText().trim();
                String equipmentName = equipmentNameField.getText().trim();
                String repairDate = repairDateField.getText().trim();
                String repairCompany = repairCompanyField.getText().trim();
                String repairCost = repairCostField.getText().trim();
                String responsePerson = responsePersonField.getText().trim();
                String repairDescription = repairDescriptionField.getText().trim();

                //给服务器发送的消息
                String message = String.format("4,INSERT,%s,%s,%s,%s,%s,%s,%s",
                        equipmentId, equipmentName,repairDate,repairCompany,repairCost,responsePerson,repairDescription);
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
    //修改
    private void openModifyDialog(){
        JDialog dialog = new JDialog((Frame) null, "修改设备", true);
        dialog.setLayout(new GridLayout(8, 2));

        JTextField repairIdField = new JTextField();
        JTextField equipmentNameField = new JTextField();
        JTextField repairDateField = new JTextField();
        JTextField repairCompanyField = new JTextField();
        JTextField repairCostField = new JTextField();
        JTextField responsePersonField = new JTextField();
        JTextField repairDescriptionField = new JTextField();

        dialog.add(new JLabel("维修编号:"));
        dialog.add(repairIdField);
        dialog.add(new JLabel("设备名称:"));
        dialog.add(equipmentNameField);
        dialog.add(new JLabel("维修日期:"));
        dialog.add(repairDateField);
        dialog.add(new JLabel("维修公司:"));
        dialog.add(repairCompanyField);
        dialog.add(new JLabel("维修花费:"));
        dialog.add(repairCostField);
        dialog.add(new JLabel("责任人:"));
        dialog.add(responsePersonField);
        dialog.add(new JLabel("维修描述:"));
        dialog.add(repairDescriptionField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int repairId = Integer.parseInt(repairIdField.getText().trim());
                String equipmentName = equipmentNameField.getText().trim();
                String repairDate = repairDateField.getText().trim();
                String repairCompany = repairCompanyField.getText().trim();
                String repairCost = repairCostField.getText().trim();
                String responsePerson = responsePersonField.getText().trim();
                String repairDescription = repairDescriptionField.getText().trim();

                String message = String.format("4,UPDATE,%d,%s,%s,%s,%s,%s,%s",
                        repairId, equipmentName, repairDate, repairCompany, repairCost, responsePerson, repairDescription);
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    System.out.println(response);
                    if (response != null && response.equals("更新成功")) {
                        JOptionPane.showMessageDialog(dialog, "设备修改成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "设备修改失败", "错误", JOptionPane.ERROR_MESSAGE);
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
    //删除
    private void openDeleteDialog(){
        JDialog dialog = new JDialog((Frame) null, "删除设备", true);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField repairIdField = new JTextField();

        dialog.add(new JLabel("维修编号:"));
        dialog.add(repairIdField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int repairId = Integer.parseInt(repairIdField.getText().trim());

                String message = String.format("4,DELETE,%d", repairId);
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
    //查询
    private void openQueryDialog() {
        JDialog dialog = new JDialog((Frame) null, "查询维修信息", true);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        //按设备编号查询面板
        JPanel idQueryPanel = new JPanel(new GridLayout(2, 2));
        JTextField idField = new JTextField();
        JButton idSearchButton = new JButton("查询");

        idQueryPanel.add(new JLabel("维修编号:"));
        idQueryPanel.add(idField);
        idQueryPanel.add(new JLabel(""));       // 占位
        idQueryPanel.add(idSearchButton);

        idSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int requestId = Integer.parseInt(idField.getText().trim());
                    String message = String.format("4,FIND,%d", requestId);
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    processServerResponse(response);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "请输入有效的维修编号", "错误", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        tabbedPane.addTab("按维修编号查询", idQueryPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.setSize(500, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //刷新
    private void openFresh(){
        String serverResponse = null;
        try {
            //向服务器发送请求并获取设备信息
            String message = "4,FIND";
            //client是客户端实例，可以调用它来与服务器通信
            RepairRecordsPanel.this.client.sendMessage(message);
            serverResponse = RepairRecordsPanel.this.client.receiveMessage();
            System.out.println(serverResponse);
            //处理从服务器返回的设备信息字符串
            processServerResponse(serverResponse);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
                if (parts.length == 8) {               //一行的列数有6个   //equipmentId, equipmentName,repairDate,repairCompany,repairCost,responsePerson,repairDescription
                    int repairId = Integer.parseInt(parts[0]);
                    int equipmentId = Integer.parseInt(parts[1]);
                    String equipmentName = parts[2];
                    String repairDate = parts[3];
                    String repairCompany = parts[4];
                    String repairCost = parts[5];
                    String responsePerson = parts[6];
                    String repairDescription = parts[7];
                    // 将数据添加到表格模型中
                    Object[] rowData = {repairId, equipmentId, equipmentName, repairDate, repairCompany, repairCost,responsePerson,repairDescription};
                    tableModel.addRow(rowData);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "未能获取设备信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    //子框是否可修改
    private static class NonEditableTableModel extends DefaultTableModel {
        public boolean isCellEditable(int row, int column) {
            return true;  //设置所有单元格都不可编辑
        }
    }

}