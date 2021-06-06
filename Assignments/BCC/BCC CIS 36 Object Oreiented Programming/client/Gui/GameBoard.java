package Gui;

import Element.Grid;
import Element.ShipContainer;
import Element.ShipPutDel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class GameBoard {

    Grid person1_grid;
    Grid person2_grid;
    ShipContainer shipContainer;
    ShipContainer shipContainer2;
    ShipPutDel person1ShipPutDel;
    ShipPutDel person2ShipPutDel;

    JFrame mainFrame;
    JPanel mainPanel;

    JPanel personPanel1;
    JPanel personPanel2;
    JPanel chatPanel;
    JPanel sendPanel;

    JButton buttonSend;
    JTextField chatTextField;
    TextArea chatTextArea;
    TextArea localTextArea;
    Socket socket;

    public GameBoard(String name, Socket socket){
        this.socket = socket;
        new ClientReceiveThread(socket).start();

        mainFrame = new JFrame();

        person1_grid = new Grid();
        person2_grid = new Grid();



        mainPanel = new JPanel();
        chatPanel = new JPanel();
        sendPanel = new JPanel();
        personPanel1 = new JPanel();
        personPanel2 = new JPanel();

        buttonSend = new JButton("Send");
        chatTextField = new JTextField();
        chatTextArea = new TextArea(10, 30);
        localTextArea = new TextArea(5, 50);

        shipContainer = new ShipContainer();
        shipContainer2 = new ShipContainer();
        person1ShipPutDel = new ShipPutDel(person1_grid,localTextArea,shipContainer);
        person2ShipPutDel = new ShipPutDel(person2_grid,localTextArea,shipContainer2);


        buttonSend.setBackground(Color.GRAY);
        buttonSend.setForeground(Color.WHITE);
        buttonSend.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                sendMessage();
            }
        });

        chatTextField.setColumns(30);
        chatTextField.addKeyListener( new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        sendPanel.setBackground(Color.LIGHT_GRAY);
        localTextArea.setEditable(false);
        sendPanel.add(localTextArea);
        sendPanel.add(chatTextField);
        sendPanel.add(buttonSend);
        chatTextArea.setEditable(false);

        chatPanel.add(BorderLayout.SOUTH, sendPanel);
        chatPanel.add(BorderLayout.CENTER, chatTextArea);

        BorderLayout fl = new BorderLayout();
        mainPanel.setLayout(fl);

        mainPanel.add(person1_grid.getJGrid(),BorderLayout.WEST);
        mainPanel.add(person2_grid.getJGrid(),BorderLayout.EAST);
        mainPanel.add(chatPanel,BorderLayout.SOUTH);
        mainFrame.add(mainPanel);

        mainFrame.pack();
        Dimension dim = new Dimension(1000,700);
        mainFrame.setPreferredSize(dim);
        mainFrame.setVisible(true);


    }

    private void sendMessage() {
        try {
            PrintWriter pw;
            String message = chatTextField.getText();
            if(message.indexOf("%") != -1){
                int start = message.indexOf("%");
                int end = message.indexOf("%", start +1);
                String point = message.substring(start+1,end);
                String[] tokens = point.split("&");
                String pointA = (tokens[0]);
                int xVec = Integer.parseInt(tokens[1]);
                int yVec = Integer.parseInt(tokens[2]);
                int shipIndex = Integer.parseInt(tokens[3]);
                person1ShipPutDel.put(pointA,xVec,yVec,shipIndex);
            }else if(message.indexOf("?") != -1){
                for(int i =0; i < shipContainer.getShipArray().size(); i++){
                    localTextArea.append("Index : " + i +"Ship Name :" + shipContainer.getShipArray().get(i).getShipName() +
                                          " Ship Size : " +shipContainer.getShipArray().get(i).getSize() +
                                         "Ship Num : " + shipContainer.getShipArray().get(i).getNum()+"\n");
                }
                chatTextField.setText("");
            }
            else {
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    String request = "message:" + message + "\r\n";
                    pw.println(request);

                    chatTextField.setText("");
                    chatTextField.requestFocus();



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientReceiveThread extends Thread{
        Socket socket = null;

        ClientReceiveThread(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                while(true) {
                    String msg = br.readLine();
                    String[] token = msg.split(":");
                    
                    chatTextArea.append(msg);
                    chatTextArea.append("\n");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
