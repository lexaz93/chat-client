import lombok.SneakyThrows;

import java.net.Socket;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 8080;

    @SneakyThrows
    public void start() {
        Socket socket = new Socket(IP, PORT);

        if (socket.isConnected()) {
            MessageReceiver messageReceiver = new MessageReceiver(System.in);
            MessageSender messageSender = new MessageSender(socket.getOutputStream());

            registration(messageReceiver, messageSender);

            new Thread(new SocketRunnable(socket)).start();

            String messageFromConsole;
            while ((messageFromConsole = messageReceiver.readMessage()) != null) {
                messageSender.sendMessage(messageFromConsole);
            }
        }
    }

    private void registration(MessageReceiver messageReceiver, MessageSender messageSender) {
        System.out.println("----------------------------");
        System.out.println("Добро пожаловать в наш Чат!");
        System.out.println("----------------------------");

        System.out.println("Введите имя:");
        String name = messageReceiver.readMessage();
        System.out.println("Введите пароль:");
        String password = messageReceiver.readMessage();

        messageSender.sendMessage("Registration " + name + " " + password);
    }

}
