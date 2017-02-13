/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.FlightInfo;
import Server.ServerStuff;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sab
 */
public class Swing_handler {

    private JFrame mainFrame;
    private JPanel controlPanel;
    private JTextField originText;
    private JTextField destinationText;
    private JTextField dateText;
    private JComboBox<String> adultNo;
    private JComboBox<String> childNo;
    private JComboBox<String> infantNo;
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"Orig.", "Dest.", "Date", "Arpln Code", "Flght No.", "Arpln Model", "Class", "Price"};
    private Object[][] initialData = new Object[1][8];
    private Object[][] data = new Object[50][8];

    public Swing_handler() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("سیستم جستجو بلیت هواپیما");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(2, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        table = new JTable(initialData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);

        mainFrame.add(controlPanel);
        mainFrame.add(scrollPane);
        mainFrame.setVisible(true);
    }

    public void searchFlight() {
        JPanel panel = new JPanel();
        panel.setSize(300, 300);

        GridLayout layout = new GridLayout(7, 2, 10, 10);
        layout.setVgap(10);
        panel.setLayout(layout);

        JLabel originLabel = new JLabel("مبدا: ", JLabel.CENTER);
        JLabel destinationLabel = new JLabel("مقصد: ", JLabel.CENTER);
        JLabel dateLabel = new JLabel("تاریخ: ", JLabel.CENTER);
        JLabel adultLabel = new JLabel("بزرگسالان: ", JLabel.CENTER);
        JLabel childLabel = new JLabel("کودکان: ", JLabel.CENTER);
        JLabel infantLabel = new JLabel("خردسالان: ", JLabel.CENTER);

        String[] NumberofPassengers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};

        originText = new JTextField(10);
        destinationText = new JTextField(10);
        dateText = new JTextField(10);

        adultNo = new JComboBox<>(NumberofPassengers);
        childNo = new JComboBox<>(NumberofPassengers);
        infantNo = new JComboBox<>(NumberofPassengers);

        JButton searchButton = new JButton("جستجو");
        searchButton.addActionListener(new ButtonClickListener());

        panel.add(originText);
        panel.add(originLabel);
        panel.add(destinationText);
        panel.add(destinationLabel);
        panel.add(dateText);
        panel.add(dateLabel);
        panel.add(adultNo);
        panel.add(adultLabel);
        panel.add(childNo);
        panel.add(childLabel);
        panel.add(infantNo);
        panel.add(infantLabel);
        panel.add(searchButton);

        controlPanel.add(panel);
        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String origin = originText.getText();
            String destination = destinationText.getText();
            String date = dateText.getText();
            String adult = (String) adultNo.getSelectedItem();
            String child = (String) childNo.getSelectedItem();
            String infant = (String) infantNo.getSelectedItem();

            String searchInfo = origin + " " + destination + " " + date + " " + adult + " " + child + " " + infant;
            //sendSearchCommand(searchInfo);
            //showSearchResult();
            sendSearchCommand(searchInfo);
            //System.out.println(searchInfo);
        }
    }

    private void sendSearchCommand(String searchInfo) {
        ServerStuff s = new ServerStuff();
        int i = 0;
        ArrayList<FlightInfo> result = s.searchRequest(searchInfo);
        for (FlightInfo fi : result) {
            Iterator it = fi.getClassSeat().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                data[i][0] = originText.getText();
                data[i][1] = destinationText.getText();
                data[i][2] = dateText.getText();
                data[i][3] = fi.getAirlineCode();
                data[i][4] = fi.getFlightNo();
                data[i][5] = fi.getAirplaneModel();
                data[i][6] = e.getKey();
                if (fi.getClassPrice().get(e.getKey()) != -1) {
                    data[i][7] = fi.getClassPrice().get(e.getKey());
                }
                System.out.println(i);
                i++;
                //data[i]= new Object[][8];
            }
        }
        mainFrame.remove(scrollPane);
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane);
        mainFrame.setVisible(true);
        //System.out.print(result);
//        ServerStuff s = new ServerStuff();
//        String result = s.receiveRequest(searchInfo);
//        System.out.print(result);
    }

    public static String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }
    //public static int numberOfClass
}
