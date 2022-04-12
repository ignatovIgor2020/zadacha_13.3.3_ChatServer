import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket socket;
    ChatServer chatServer;
    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.chatServer = server;
        //запуск потока
        new Thread(this).start();
    }

    void recieveData(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //ввод вывод данных
            in = new Scanner(is);
            out = new PrintStream(os);
            //приветствие новому клиенту
            out.println(" Welcome to Chat");
            //считываем данные от клиента в новую строку
            String input = in.nextLine();
            //проверка на значение выхода
            while (!input.equals("bye")) {

                chatServer.sendAll(input);
                //повтор считывание о клиента
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
