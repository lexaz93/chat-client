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

            registrationOrAuthirization(messageReceiver, messageSender);

            new Thread(new SocketRunnable(socket)).start();

            String messageFromConsole;
            while ((messageFromConsole = messageReceiver.readMessage()) != null) {
                messageSender.sendMessage(messageFromConsole);
            }
        }
    }

    private void registrationOrAuthirization(MessageReceiver messageReceiver, MessageSender messageSender) {
        System.out.println("----------------------------");
        System.out.println("Welcome to our chat!");
        System.out.println("----------------------------");

        System.out.println("Enter: Registration or Authorization:");
        String enter = messageReceiver.readMessage();
        System.out.println("Enter name:");
        String name = messageReceiver.readMessage();
        System.out.println("Enter password:");
        String password = messageReceiver.readMessage();

        messageSender.sendMessage(enter + " " + name + " " + password);
    }


}
