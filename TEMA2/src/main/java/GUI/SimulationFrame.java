package GUI;

import BusinessLogic.SimulationManager;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SimulationFrame extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;

    public SimulationFrame() {
        setFont(new Font("Arial", Font.PLAIN, 12));
        setTitle("Threads");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 533);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Time limit");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(21, 84, 70, 39);
        contentPane.add(lblNewLabel);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(317, 96, 543, 372);
        contentPane.add(textArea);

        //bara de scroll
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        scrollPane.setBounds(317, 96, 543, 372);

        JLabel lblNewLabel_1 = new JLabel("NrOfClients");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(21, 133, 97, 39);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("NrOfServers");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(21, 182, 86, 39);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("MinArrivalTime");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_2.setBounds(21, 231, 112, 39);
        contentPane.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("MaxArrivalTime");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_3.setBounds(21, 280, 112, 39);
        contentPane.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_4 = new JLabel("MinServiceTime");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_4.setBounds(21, 329, 112, 39);
        contentPane.add(lblNewLabel_1_4);

        JLabel lblNewLabel_1_4_1 = new JLabel("MaxServiceTime");
        lblNewLabel_1_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_4_1.setBounds(21, 378, 112, 39);
        contentPane.add(lblNewLabel_1_4_1);

        textField = new JTextField();
        textField.setBounds(148, 96, 59, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(148, 145, 59, 19);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(148, 194, 59, 19);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(148, 243, 59, 19);
        contentPane.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(148, 292, 59, 19);
        contentPane.add(textField_4);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(148, 341, 59, 19);
        contentPane.add(textField_5);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(148, 390, 59, 19);
        contentPane.add(textField_6);

        //creez buton de start si cand se apasa va transmite programului valorile introduse in textfield-uri
        JButton btnNewButton = new JButton("Start");
        btnNewButton.setBackground(Color.YELLOW);
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton.setFont(new Font("Vladimir Script", Font.PLAIN, 30));
        btnNewButton.setBounds(108, 447, 125, 39);
        btnNewButton.addActionListener(e->{
            int time = Integer.parseInt(textField.getText());
            int maxa = Integer.parseInt(textField_1.getText());
            int mina =Integer.parseInt(textField_2.getText());
            int mins = Integer.parseInt(textField_3.getText());
            int maxs = Integer.parseInt(textField_4.getText());
            int nrs = Integer.parseInt(textField_5.getText());;
            int nrc = Integer.parseInt(textField_6.getText());;
            SimulationManager m = new SimulationManager(time, maxa, mina, mins, maxs, nrs, nrc,textArea);
            Thread t = new Thread(m);
            t.start();
        });
        contentPane.add(btnNewButton);

        JLabel lblQueuesManagement = new JLabel("QUEUES MANAGEMENT");
        lblQueuesManagement.setForeground(Color.WHITE);
        lblQueuesManagement.setFont(new Font("Bauhaus 93", Font.ITALIC, 30));
        lblQueuesManagement.setBounds(289, 25, 386, 39);
        contentPane.add(lblQueuesManagement);

        Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Facultate\\Anul 2\\SEM2\\TP\\PT2023_30223_Boar_Daniel_Assignment_2\\picture.jpg"); //adaug o imagine frameului
        setIconImage(icon);
    }
}

