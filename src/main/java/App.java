import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String args[]) {
        //Окно
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        //Создаем панель для регистрации/авторизации
        JPanel authAndReg = new JPanel(); // прозрачная панель
        JLabel entLog = new JLabel("Enter login:");
        JTextField log = new JTextField(10); // пока по 10 элементов вводим
        JLabel entPas = new JLabel("Enter password:");
        JTextField pas = new JTextField(10); // пока по 10 элементов вводим
        JButton bReg = new JButton("Registration");
        bReg.setActionCommand("Registration for: ");
        JButton bAuth = new JButton("Authorization");
        bAuth.setActionCommand("Authorization for: ");
        authAndReg.add(entLog);
        authAndReg.add(log);
        authAndReg.add(entPas);
        authAndReg.add(pas);
        authAndReg.add(bReg);
        authAndReg.add(bAuth);
        //Панель для ввода сообщения
        JPanel panel = new JPanel(); // прозрачная панель
        JLabel label = new JLabel("Enter text");
        JTextField tf = new JTextField(40); // пока по 10 элементов вводим
        JButton send = new JButton("Send");
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        //Область получения сообщения с сервера
        JTextArea ta = new JTextArea();
        JScrollPane scrollBar = new JScrollPane(ta);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //Расставляем компоненты по областям окна
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, authAndReg);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.getContentPane().add(BorderLayout.LINE_END, scrollBar);
        frame.setVisible(true);


        bReg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(e.getActionCommand() + log.getText());
                System.out.println("1\n" + e.getActionCommand() + log.getText() + " " + pas.getText());
            }
        });

        bAuth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(e.getActionCommand() + log.getText());
                System.out.println("2\n" + e.getActionCommand() + log.getText() + " " + pas.getText());
            }
        });

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.append("\n" + tf.getText());
                System.out.println(tf.getText());
                tf.setText(null);
            }
        });

    }
}