package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器监听在 " + port + " 端口");
            while (true) {
                Socket socket = serverSocket.accept();              //等待连接
                System.out.println(socket.getInetAddress().toString()+" 客户端连接");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
