package chatServer;

public class Application {
    
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
        System.out.println("Testing!");
    }
}
