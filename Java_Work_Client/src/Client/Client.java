package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;          //输入缓冲区
    private PrintWriter out;            //输出缓冲区
    private boolean connected = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean start() {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            connected = true;
            System.out.println("连接成功");
            return true;
        } catch (IOException e) {
            System.err.println("连接失败: " + e.getMessage());
            return false;
        }
    }
    //发送消息
    public String sendMessage(String message) {
        if (connected && out != null) {
            out.println(message);
        }
        return message;
    }
    //接受消息
    public String receiveMessage() throws IOException {
        if (connected && in != null) {
            return in.readLine();
        }
        return null;
    }

}
