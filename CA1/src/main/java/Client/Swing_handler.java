/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sab
 */
public class Swing_handler {
    private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public Swing_handler(){
      prepareGUI();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("سیستم جستجو بلیت هواپیما");
      mainFrame.setSize(600,600);
      mainFrame.setLayout(new GridLayout(2, 1));

//      headerLabel = new JLabel("",JLabel.RIGHT);
//      statusLabel = new JLabel("",JLabel.CENTER);
//      statusLabel.setSize(350,100);

      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }
      });
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      //mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      //mainFrame.add(statusLabel);
//      GraphicsEnvironment ge
//                = GraphicsEnvironment.getLocalGraphicsEnvironment();
//
//        String[] fonts = ge.getAvailableFontFamilyNames();
//
//        final JList list = new JList(fonts);
//
//        JScrollPane spane = new JScrollPane();
//        spane.getViewport().add(list);
//      mainFrame.add(spane);
      String[] columnNames = {"Orig.",
                        "Dest.",
                        "Date",
                        "Arpln Code",
                        "Flght No.",
                        "Arpln Model",
                        "Class",
                        "Prize"};
      Object[][] data = {
    {"THR", "MHD",
        "05Feb", "IR", "652","321","Y","265000"}};

      JTable table = new JTable(data, columnNames);
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
      JScrollPane scrollPane = new JScrollPane(table);
      mainFrame.add(scrollPane);
      mainFrame.setVisible(true);
   }
   public void showTextFieldDemo(){
      //headerLabel.setText("لطفا اطلاعات را وارد کنید و کلید جستجو را بزنید");
      JPanel panel = new JPanel();
      panel.setSize(300,300);
      //panel.setBackground(Color.GRAY);
      GridLayout layout = new GridLayout(7,2,10,10);
      layout.setVgap(10);
      panel.setLayout(layout);
      JLabel  originLabel= new JLabel("مبدا: ", JLabel.CENTER);
      JLabel  destinationLabel = new JLabel("مقصد: ", JLabel.CENTER);
      JLabel  dateLabel = new JLabel ("تاریخ: ", JLabel.CENTER);
      JLabel  adultLabel = new JLabel ("بزرگسالان: ", JLabel.CENTER);
      JLabel  childLabel = new JLabel ("کودکان: ", JLabel.CENTER);
      JLabel  infantLabel = new JLabel ("خردسالان: ", JLabel.CENTER);

      Integer[] NumberofPassengers = new Integer[]{0,1,2,3,4,5,6,7,8,9,10};

      final JTextField originText = new JTextField(10);
      final JTextField destinationText = new JTextField(10);
      final JTextField dateText = new JTextField(10);

      final JComboBox<Integer> adultNo = new JComboBox<>(NumberofPassengers);
      final JComboBox<Integer> childNo = new JComboBox<>(NumberofPassengers);
      final JComboBox<Integer> infantNo = new JComboBox<>(NumberofPassengers);

      JButton searchButton = new JButton("جستجو");
//      searchButton.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            String data = "Username " + originText.getText();
//            data += ", Password: " + new String(destinationText.getText());
//            statusLabel.setText(data);
//         }
//      });
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
   private void showEventDemo(){
      headerLabel.setText("Control in action: Button");

      JButton okButton = new JButton("OK");
      JButton submitButton = new JButton("Submit");
      JButton cancelButton = new JButton("Cancel");

      okButton.setActionCommand("OK");
      submitButton.setActionCommand("Submit");
      cancelButton.setActionCommand("Cancel");

      okButton.addActionListener(new ButtonClickListener());
      submitButton.addActionListener(new ButtonClickListener());
      cancelButton.addActionListener(new ButtonClickListener());

      controlPanel.add(okButton);
      controlPanel.add(submitButton);
      controlPanel.add(cancelButton);

      mainFrame.setVisible(true);
   }
   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();

         if( command.equals( "OK" ))  {
            statusLabel.setText("Ok Button clicked.");
         } else if( command.equals( "Submit" ) )  {
            statusLabel.setText("Submit Button clicked.");
         } else {
            statusLabel.setText("Cancel Button clicked.");
         }
      }
   }
}
