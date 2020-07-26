import lombok.SneakyThrows;
import utils.MyResourceBundle;
import utils.Props;

import java.net.Socket;
import java.util.Locale;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 8080;
    private final  static MyResourceBundle RESOURCE_BUNDLE = new MyResourceBundle(new Locale(Props.getValueFromProperties("language"), Props.getValueFromProperties("country")));

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
        System.out.println(RESOURCE_BUNDLE.getValue("welcome"));
//        System.out.println("Welcome to our chat!");
        System.out.println("----------------------------");

        System.out.println(RESOURCE_BUNDLE.getValue("main_enter"));
//        System.out.println("Enter: Registration(1) or Authorization(2):");
        int enter = Integer.parseInt(messageReceiver.readMessage().trim());
        if (enter == 1) {
            String name = getString(messageReceiver, RESOURCE_BUNDLE.getValue("registration")).toUpperCase();
//            String name = getString(messageReceiver, "Registration:\nEnter name:").toUpperCase();
            String password = getString(messageReceiver, RESOURCE_BUNDLE.getValue("password_enter"));
//            String password = getString(messageReceiver, "Enter password:");
            messageSender.sendMessage("Registration" + " " + name + " " + password);
        } else if (enter == 2) {
            String name = getString(messageReceiver, RESOURCE_BUNDLE.getValue("authorization")).toUpperCase();
//            String name = getString(messageReceiver, "Authorization:\nEnter name:").toUpperCase();
            String password = getString(messageReceiver, RESOURCE_BUNDLE.getValue("password_enter"));
//            String password = getString(messageReceiver, "Enter password:");
            messageSender.sendMessage("Authorization" + " " + name + " " + password);
        } else {
            System.err.println(RESOURCE_BUNDLE.getValue("main_enter"));
//            System.err.println("Enter: Registration(1) or Authorization(2):");
            registrationOrAuthirization(messageReceiver,messageSender);
        }
    }

    private String getString(MessageReceiver messageReceiver, String userHint) {
        System.out.println(userHint);
        return messageReceiver.readMessage().trim();
    }




}
