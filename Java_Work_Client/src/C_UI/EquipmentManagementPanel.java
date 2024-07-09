package C_UI;

import Client.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EquipmentManagementPanel extends JPanel {
    private JTable equipmentTable;          //表格
    private DefaultTableModel tableModel;   //表格模式
    private Client client;

    public EquipmentManagementPanel(Client client) {
        this.client = client;

        setLayout(new BorderLayout());

        JButton addButton = new JButton("添加设备");
        JButton modifyButton = new JButton("修改设备");
        JButton deleteButton = new JButton("删除设备");
        JButton queryButton = new JButton("查询设备信息");
        JButton refreshButton = new JButton("刷新页面");

        JPanel buttonPanel = new JPanel();      //按钮页面
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(queryButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.NORTH);

        //创建表格模型并添加到表格中
        tableModel = new NonEditableTableModel();
        String[] columnNames = {"设备编号", "设备名称", "设备类别", "规格", "单价", "数量", "购置日期", "生产厂家", "保质期", "经办人", "设备状态"};
        tableModel.setColumnIdentifiers(columnNames);
        equipmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        add(scrollPane, BorderLayout.CENTER);

        //刷新按钮的监听器
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {openFresh();}
        });
        //添加按钮的监听器
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddEquipmentDialog();
            }
        });
        //删除按钮的监听器
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDeleteEquipmentDialog();
            }
        });
        //修改按钮的监听器
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openModifyEquipmentDialog();
            }
        });
        //查询按钮的监听器
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openQueryDialog();
            }
        });
    }
    //todo:将这些方法写成接口
    //添加
    private void openAddEquipmentDialog() {
        JDialog dialog = new JDialog((Frame) null, "添加设备", true);
        dialog.setLayout(new GridLayout(11, 2));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField specificationField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField purchaseDateField = new JTextField();
        JTextField manufacturerField = new JTextField();
        JTextField warrantyPeriodField = new JTextField();
        JTextField handledByField = new JTextField();
        JTextField statedByField = new JTextField();

        dialog.add(new JLabel("设备名称:"));
        dialog.add(nameField);
        dialog.add(new JLabel("设备类别:"));
        dialog.add(categoryField);
        dialog.add(new JLabel("规格:"));
        dialog.add(specificationField);
        dialog.add(new JLabel("单价:"));
        dialog.add(priceField);
        dialog.add(new JLabel("数量:"));
        dialog.add(quantityField);
        dialog.add(new JLabel("购置日期:"));
        dialog.add(purchaseDateField);
        dialog.add(new JLabel("生产厂家:"));
        dialog.add(manufacturerField);
        dialog.add(new JLabel("保质期:"));
        dialog.add(warrantyPeriodField);
        dialog.add(new JLabel("经办人:"));
        dialog.add(handledByField);
        dialog.add(new JLabel("设备状态:"));
        dialog.add(statedByField);
        dialog.add(new JLabel(""));     //占位

        JButton submitButton = new JButton("提交");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                String specification = specificationField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());
                String purchaseDate = purchaseDateField.getText().trim();
                String manufacturer = manufacturerField.getText().trim();
                String warrantyPeriod = warrantyPeriodField.getText().trim();
                String handledBy = handledByField.getText().trim();
                String state = statedByField.getText().trim();
                //给服务器发送的消息
                String message = String.format("1,INSERT,%s,%s,%s,%.2f,%d,%s,%s,%s,%s,%s",
                        name, category, specification, price, quantity, purchaseDate, manufacturer, warrantyPeriod, handledBy, state);
                try {
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    //System.out.println(response);
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
    //删除
    private void openDeleteEquipmentDialog() {
        JDialog dialog = new JDialog((Frame) null, "删除设备", true);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField equipmentIdField = new JTextField();

        dialog.add(new JLabel("设备编号:"));
        dialog.add(equipmentIdField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long equipmentId = Long.parseLong(equipmentIdField.getText().trim());

                String message = String.format("1,DELETE,%d", equipmentId);
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
    //修改
    private void openModifyEquipmentDialog() {
        JDialog dialog = new JDialog((Frame) null, "修改设备", true);
        dialog.setLayout(new GridLayout(12, 2));

        JTextField equipmentIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField specificationField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField purchaseDateField = new JTextField();
        JTextField manufacturerField = new JTextField();
        JTextField warrantyPeriodField = new JTextField();
        JTextField handledByField = new JTextField();
        JTextField stateByField = new JTextField();

        dialog.add(new JLabel("设备编号:"));
        dialog.add(equipmentIdField);
        dialog.add(new JLabel("设备名称:"));
        dialog.add(nameField);
        dialog.add(new JLabel("设备类别:"));
        dialog.add(categoryField);
        dialog.add(new JLabel("规格:"));
        dialog.add(specificationField);
        dialog.add(new JLabel("单价:"));
        dialog.add(priceField);
        dialog.add(new JLabel("数量:"));
        dialog.add(quantityField);
        dialog.add(new JLabel("购置日期:"));
        dialog.add(purchaseDateField);
        dialog.add(new JLabel("生产厂家:"));
        dialog.add(manufacturerField);
        dialog.add(new JLabel("保质期:"));
        dialog.add(warrantyPeriodField);
        dialog.add(new JLabel("经办人:"));
        dialog.add(handledByField);
        dialog.add(new JLabel("状态"));
        dialog.add(stateByField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long equipmentId = Long.parseLong(equipmentIdField.getText().trim());
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                String specification = specificationField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());
                String purchaseDate = purchaseDateField.getText().trim();
                String manufacturer = manufacturerField.getText().trim();
                String warrantyPeriod = warrantyPeriodField.getText().trim();
                String handledBy = handledByField.getText().trim();
                String state = stateByField.getText().trim();

                String message = String.format("1,UPDATE,%s,%s,%s,%.2f,%d,%s,%s,%s,%s,%d,%s",
                        name, category, specification, price, quantity, purchaseDate, manufacturer, warrantyPeriod, handledBy, equipmentId, state);
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
    //查找
    private void openQueryDialog() {
        JDialog dialog = new JDialog((Frame) null, "查询设备信息", true);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        //按设备编号查询面板
        JPanel idQueryPanel = new JPanel(new GridLayout(2, 2));
        JTextField idField = new JTextField();
        JButton idSearchButton = new JButton("查询");

        idQueryPanel.add(new JLabel("设备编号:"));
        idQueryPanel.add(idField);
        idQueryPanel.add(new JLabel(""));       // 占位
        idQueryPanel.add(idSearchButton);

        idSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int requestId = Integer.parseInt(idField.getText().trim());
                    String message = String.format("1,FIND,%d", requestId);
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

        tabbedPane.addTab("按设备编号查询", idQueryPanel);

        //按设备编号查询面板
        JPanel nameQueryPanel = new JPanel(new GridLayout(2, 2));
        JTextField nameField = new JTextField();
        JButton nameSearchButton = new JButton("查询");

        nameQueryPanel.add(new JLabel("设备名称:"));
        nameQueryPanel.add(nameField);
        nameQueryPanel.add(new JLabel(""));       // 占位
        nameQueryPanel.add(nameSearchButton);

        nameSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String message = String.format("1,FIND,%s",name);
                    client.sendMessage(message);
                    String response = client.receiveMessage();
                    processServerResponse(response);
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        tabbedPane.addTab("按设备名称查询", nameQueryPanel);

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
            String message = "1,FIND";
            //client是你的客户端实例，可以调用它来与服务器通信
            EquipmentManagementPanel.this.client.sendMessage(message);
            serverResponse = EquipmentManagementPanel.this.client.receiveMessage();
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
                if (parts.length == 11) {               //一行的列数有11个
                    long equipmentId = Long.parseLong(parts[0]);
                    String equipmentName = parts[1];
                    String equipmentCategory = parts[2];
                    String specification = parts[3];
                    double unitPrice = Double.parseDouble(parts[4]);
                    int quantity = Integer.parseInt(parts[5]);
                    String purchaseDate = parts[6]; // 假设有一个方法来解析日期
                    String manufacturer = parts[7];
                    String warrantyPeriod = parts[8];
                    String handledBy = parts[9];
                    String state = parts[10];
                    // 将数据添加到表格模型中
                    Object[] rowData = {equipmentId, equipmentName, equipmentCategory, specification, unitPrice, quantity, purchaseDate, manufacturer, warrantyPeriod, handledBy, state};
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