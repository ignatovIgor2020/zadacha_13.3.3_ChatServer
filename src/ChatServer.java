import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clients = new ArrayList<Client>();
    ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(String message) {
        for (Client client : clients) {
            client.recieveData(message);
        }
    }

    public void run() {
        while (true) {
            System.out.println("Waiting ...");
            try {
                //создаем сокет подключившегося клиента
                Socket connectedClientSocket = serverSocket.accept();
                System.out.println(" Client connected");

                //создаем клиента и добавляем его в массив
                clients.add(new Client(connectedClientSocket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        //запуск сервера
        new ChatServer().run();
    }
}
