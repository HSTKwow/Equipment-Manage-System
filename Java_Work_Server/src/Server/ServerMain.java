package Server;


public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start();
    }
}


