import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.net.Socket;

@AllArgsConstructor
public class SocketRunnable implements Runnable {
    private final Socket socket;

    @SneakyThrows
    @Override
    public void run() {
        MessageReceiver receiverFromServer = new MessageReceiver(socket.getInputStream());

        String messageFromServer;
        while ((messageFromServer = receiverFromServer.readMessage()) != null) {
            if (messageFromServer.contains(" failed")) {
                System.out.println(messageFromServer);
                System.exit(0);
            }
            System.out.println(messageFromServer);
        }
    }

}
