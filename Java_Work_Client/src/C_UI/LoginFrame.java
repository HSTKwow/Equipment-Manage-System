package C_UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

import Client.*;

//todo: 引入MVC模式，将界面逻辑与业务逻辑分离，增强代码的可维护性

public class LoginFrame extends JFrame {
    private Client client;
    private boolean isConnect = false;
    private JTextField ipField;
    private JTextField portField;
    private JLabel userNameLabel;
    private JLabel userPasswordLabel;
    private JTextField userNameField;
    private JPasswordField userPasswordField;
    private JButton connectButton;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;

    public LoginFrame() {
        setTitle("连接服务器");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 初始化组件
        ipField = new JTextField(15);
        portField = new JTextField(7);
        connectButton = new JButton("连接");
        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        statusLabel = new JLabel("状态: 未连接");
        userNameLabel = new JLabel("用户名");
        userNameField = new JTextField(15);
        userPasswordLabel = new JLabel("密码");
        userPasswordField = new JPasswordField(15);

        // 设置提示文本和焦点监听器
        setPlaceholder(ipField, "127.0.0.1");
        setPlaceholder(portField, "8080");
        setPlaceholder(userNameField, "请输入用户名");

        // 设置按钮的监听器
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openConnect();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRegister();
            }
        });

        // 创建顶部面板，用于输入字段和按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("服务器IP:"));
        topPanel.add(ipField);
        topPanel.add(new JLabel("端口:"));
        topPanel.add(portField);
        topPanel.add(connectButton);
        topPanel.add(statusLabel);

        // 创建中部面板
        SpringLayout springLayout = new SpringLayout();
        JPanel centerPanel = new JPanel(springLayout);

        // nameLabel的位置
        centerPanel.add(userNameLabel);
        SpringLayout.Constraints userNameLabelC = springLayout.getConstraints(userNameLabel);
        userNameLabelC.setX(Spring.constant(140));
        userNameLabelC.setY(Spring.constant(40));

        // nameField的位置
        centerPanel.add(userNameField);
        springLayout.putConstraint(SpringLayout.WEST, userNameField, 20, SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userNameField, -4, SpringLayout.NORTH, userNameLabel);

        // PasswordLabel的位置
        centerPanel.add(userPasswordLabel);
        springLayout.putConstraint(SpringLayout.EAST, userPasswordLabel, 0, SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userPasswordLabel, 40, SpringLayout.SOUTH, userNameLabel);

        // PasswordField的位置
        centerPanel.add(userPasswordField);
        springLayout.putConstraint(SpringLayout.WEST, userPasswordField, 20, SpringLayout.EAST, userPasswordLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userPasswordField, -4, SpringLayout.NORTH, userPasswordLabel);

        // registerButton的位置
        centerPanel.add(registerButton);
        springLayout.putConstraint(SpringLayout.EAST, registerButton, 80, SpringLayout.EAST, userPasswordLabel);
        springLayout.putConstraint(SpringLayout.NORTH, registerButton, 40, SpringLayout.SOUTH, userPasswordLabel);

        // loginButton的位置
        centerPanel.add(loginButton);
        springLayout.putConstraint(SpringLayout.WEST, loginButton, 20, SpringLayout.EAST, registerButton);
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 0, SpringLayout.NORTH, registerButton);

        // 设置框架布局
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // 设置可见性
        setVisible(true);
        connectButton.requestFocusInWindow(); // 设置初始焦点
    }

    // 聚焦
    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder); // 设置默认字体
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { // 获得焦点
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) { // 失去焦点
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    // 连接
    private void openConnect() {
        String ip = ipField.getText().trim();
        String portStr = portField.getText().trim();
        if (!isValidIP(ip)) { // 检验IP是否合法
            JOptionPane.showMessageDialog(null, "请输入有效的IP地址", "错误", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("状态: 连接失败");
            return;
        }
        if (!ip.isEmpty() && !portStr.isEmpty()) {
            try {
                int port = Integer.parseInt(portStr);
                statusLabel.setText("状态: 连接中...");
                // 与服务器建立连接
                this.client = new Client(ip, port);
                boolean connected = client.start(); // 假设start方法返回连接状态
                if (connected) {
                    statusLabel.setText("状态: 连接成功");
                    isConnect = true;
                } else {
                    // todo:加入超时处理
                    statusLabel.setText("状态: 连接失败");
                    JOptionPane.showMessageDialog(null, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入有效的端口号", "错误", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("状态: 连接失败");
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入正确的IP地址和端口号", "错误", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("状态: 连接失败");
        }
    }

    // 登录
    private void openLogin() {
        if (isConnect) {
            String name = userNameField.getText().trim();
            String password = userPasswordField.getText().trim();
            String msg = String.format("2,FIND,%s", name);
            try {
                client.sendMessage(msg);
                String response = client.receiveMessage();
                String[] parts = null;
                if (!response.equals("null")) {
                    parts = response.split(",");
                } else {
                    response = "无账户名";
                }
                if (response.equals("无账户名")) {
                    JDialog dialog = new JDialog((Frame) null, "连接", true);
                    JOptionPane.showMessageDialog(dialog, "该账户未注册", "错误", JOptionPane.ERROR_MESSAGE);
                } else if (parts[1].equals(password)) {
                    new MainFrame(client); // MainFrame是连接成功后的主界面，并传递Client对象
                } else {
                    JDialog dialog = new JDialog((Frame) null, "连接", true);
                    JOptionPane.showMessageDialog(dialog, "密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "服务器连接失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 注册
    private void openRegister() {
        JDialog dialog = new JDialog((Frame) null, "注册", true);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();

        dialog.add(new JLabel("用户名:"));
        dialog.add(nameField);
        dialog.add(new JLabel("密码:"));
        dialog.add(passwordField);
        dialog.add(new JLabel(""));
        JButton submitButton = new JButton("提交");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String password = passwordField.getText().trim();

                // 给服务器发送的消息
                String msg = String.format("2,INSERT,%s,%s", name, password);
                try {
                    client.sendMessage(msg);
                    String response = client.receiveMessage();
                    if (response != null && response.equals("添加成功")) {
                        JOptionPane.showMessageDialog(dialog, "注册成功");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
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

    // 验证IP地址格式是否正确
    private boolean isValidIP(String ip) {
        if (ip.isEmpty()) return false;
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}