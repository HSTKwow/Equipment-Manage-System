package C_UI;

import Client.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ScrapRecordsPanel extends JPanel {
    private JTable scrapTable;      //表格
    private DefaultTableModel tableModel;
    private Client client;
    public ScrapRecordsPanel(Client client) {
        this.client=client;
        setLayout(new BorderLayout());
        JButton addButton = new JButton("增加报废记录");
        JButton modifyButton = new JButton("修改报废记录");
        JButton deleteButton = new JButton("删除报废记录");
        JButton freshButton = new JButton("刷新页面");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(freshButton);

        add(buttonPanel, BorderLayout.NORTH);

        //创建表格模型并添加到表格中
        tableModel = new ScrapRecordsPanel.NonEditableTableModel();
        String[] columnNames = {"报废编号", "设备编号", "设备名称", "报废日期", "报废原因", "处理方法"};
        tableModel.setColumnIdentifiers(columnNames);
        scrapTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scrapTable);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openModifyDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDeleteDialog();
            }
        });

        freshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openfresh();
            }
        });

    }
    //添加
    private void openAddDialog(){
        JDialog dialog = new JDialog((Frame) null, "添加设备", true);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField equipmentIdField = new JTextField();
        JTextField equipmentNameField = new JTextField();
        JTextField scrapDateField = new JTextField();
        JTextField scrapReasonField = new JTextField();
        JTextField scrapMethodField = new JTextField();

        dialog.add(new JLabel("设备编号"));
        dialog.add(equipmentIdField);
        dialog.add(new JLabel("设备名称"));
        dialog.add(equipmentNameField);
        dialog.add(new JLabel("报废日期"));
        dialog.add(scrapDateField);
        dialog.add(new JLabel("报废原因"));
        dialog.add(scrapReasonField);
        dialog.add(new JLabel("处理方式"));
        dialog.add(scrapMethodField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String equipmentId = equipmentIdField.getText().trim();
                String equipmentName = equipmentNameField.getText().trim();
                String scrapDate = scrapDateField.getText().trim();
                String scrapReason = scrapReasonField.getText().trim();
                String scrapMethod = scrapMethodField.getText().trim();

                //给服务器发送的消息
                String message = String.format("5,INSERT,%s,%s,%s,%s,%s",
                        equipmentId, equipmentName, scrapDate, scrapReason, scrapMethod);
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
        dialog.setLayout(new GridLayout(6, 2));

        JTextField scrapIdField = new JTextField();
        JTextField equipmentNameField = new JTextField();
        JTextField scrapDateField = new JTextField();
        JTextField scrapReasonField = new JTextField();
        JTextField scrapMethodField = new JTextField();

        dialog.add(new JLabel("报废编号:"));
        dialog.add(scrapIdField);
        dialog.add(new JLabel("设备名称:"));
        dialog.add(equipmentNameField);
        dialog.add(new JLabel("报废日期:"));
        dialog.add(scrapDateField);
        dialog.add(new JLabel("报废原因:"));
        dialog.add(scrapReasonField);
        dialog.add(new JLabel("处理方法:"));
        dialog.add(scrapMethodField);
        dialog.add(new JLabel(""));


        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int scrapId = Integer.parseInt(scrapIdField.getText().trim());
                String equipmentName = equipmentNameField.getText().trim();
                String scrapDate = scrapDateField.getText().trim();
                String scrapReason = scrapReasonField.getText().trim();
                String scrapMethod = scrapMethodField.getText().trim();

                String message = String.format("5,UPDATE,%d,%s,%s,%s,%s",
                        scrapId,equipmentName, scrapDate, scrapReason, scrapMethod);
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

        JTextField scrapIdField = new JTextField();

        dialog.add(new JLabel("设备编号:"));
        dialog.add(scrapIdField);
        dialog.add(new JLabel(""));

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long equipmentId = Long.parseLong(scrapIdField.getText().trim());

                String message = String.format("5,DELETE,%d", equipmentId);
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
    //刷新
    private void openfresh(){
        String serverResponse = null;
        try {
            //向服务器发送请求并获取设备信息
            String message = "5,FIND";
            //client是你的客户端实例，可以调用它来与服务器通信
            ScrapRecordsPanel.this.client.sendMessage(message);
            serverResponse = ScrapRecordsPanel.this.client.receiveMessage();
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
                if (parts.length == 6) {               //一行的列数有6个
                    int scrapId = Integer.parseInt(parts[0]);
                    int equipmentId = Integer.parseInt(parts[1]);
                    String equipmentName = parts[2];
                    String scrapDate = parts[3];
                    String scrapReason = parts[4];
                    String scrapMethod = parts[5];
                    // 将数据添加到表格模型中
                    Object[] rowData = {scrapId, equipmentId, equipmentName, scrapDate, scrapReason, scrapMethod};
                    tableModel.addRow(rowData);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "未能获取设备信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    //子框是否可以修改
    private static class NonEditableTableModel extends DefaultTableModel {
        public boolean isCellEditable(int row, int column) {
            return true;  //设置所有单元格都不可编辑
        }
    }
}
