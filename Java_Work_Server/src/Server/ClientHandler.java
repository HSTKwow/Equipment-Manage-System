package Server;

import EMS_Util.EventHandler;
import java.net.SocketException;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private EventHandler eventHandler;
    //构造函数
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.eventHandler=new EventHandler();
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String inputLine;
            //out.println("连接成功");
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("quit")) {
                    System.out.println("客户端关闭连接");
                    out.println("关闭连接");
                    break;
                }
                //记录收到的信息
                System.out.println("Received: " + inputLine);
                //处理事件
                String response = eventHandler.handlEvent(inputLine);
                //记录返回
                System.out.println("Sending response: " + response);
                //返回处理结果
                out.println(response);
            }
        } catch (SocketException e) {
            //客户端断开连接
            System.err.println("Client disconnected: " + e.getMessage());
        } catch (IOException e) {
            //IO错误
            System.err.println("Error handling client connection: " + e.getMessage());
        } finally {
            try {
                //确保关闭
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
