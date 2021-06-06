package BattleShip.Gui;

import BattleShip.Element.ShipContainer;
import BattleShip.Element.ShipPutDel;
import BattleShip.Element.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Random;


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

    Button buttonSend;
    JTextField chatTextField;
    TextArea chatTextArea;
    TextArea localTextArea;
    TextArea comTextArea;

    //Constructs a game board. Defines the size of each label dimension for the game board and makes AI to put ships in the board.
    public GameBoard(){
        mainFrame = new JFrame();

        person1_grid = new Grid();
        person2_grid = new Grid();

        mainPanel = new JPanel();
        chatPanel = new JPanel();
        sendPanel = new JPanel();
        personPanel1 = new JPanel();
        personPanel2 = new JPanel();

        buttonSend = new Button("Send");
        chatTextField = new JTextField();
        chatTextArea = new TextArea(15, 30);
        localTextArea = new TextArea(15, 50);
        comTextArea = new TextArea(5,50);

        shipContainer = new ShipContainer();
        shipContainer2 = new ShipContainer();
        person1ShipPutDel = new ShipPutDel(person1_grid,localTextArea,shipContainer);
        person2ShipPutDel = new ShipPutDel(person2_grid,chatTextArea,shipContainer2);


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

        mainPanel.add(person1_grid.getJGrid(), BorderLayout.WEST);
        mainPanel.add(person2_grid.getJGrid(), BorderLayout.EAST);
        mainPanel.add(chatPanel, BorderLayout.SOUTH);
        mainFrame.add(mainPanel);

        mainFrame.pack();
        Dimension dim = new Dimension(1000,900);
        mainFrame.setPreferredSize(dim);
        mainFrame.setVisible(true);
        setComShip();

        localTextArea.append("Press '?' for description\n" +
                "\nAvailable left over ships\n" +
                "Press '/C' to know leftover ships\n" +
                "\nPress '/P' to know how to put ships into the board\n" +
                "%Starting point & Direction of X & Direction of Y & Index number of the ship %\n" +
                "Starting point  = X coordinate, Y coordinate\n" +
                "X coordinate = 1 or -1\n" +
                "Y coordinate = 1 or -1\n" +
                "ex) %G,10&1&-1&2%\n" +
                "G,10 -> G,10 as a starting point\n" +
                "1 -> Put ship in cells from starting point to (starting point + the size of the ship)\n" +
                "-1 -> Put ship in cells from starting point to (starting point = the num of the ship)\n" +
                "\nPress '/D' to know how to drop(attack)\n"+
                "|cell to drop|\n" +
                "Coordinate to attack -> X coordinate, Y coordinate\n" +
                "ex) |A,10| -> Drop(Attack) A,10 of the board\n\n" +
                "Available Ships\n\n");

        for(int i =0; i < shipContainer.getShipArray().size(); i++){
            localTextArea.append("--------------------------\n");
            localTextArea.append("Index : " + i +" Ship Name :" + shipContainer.getShipArray().get(i).getShipName() +
                    " Ship Size : " +shipContainer.getShipArray().get(i).getSize() +
                    " Ship Num : " + shipContainer.getShipArray().get(i).getNum()+"\n");
        }
        chatTextField.setText("");
        chatTextField.requestFocus();
    }

    //Takes in the message entered in the chat field and breaks the String into pieces and turns in information
    // as a parameter to each methods so methods can properly execute their job as entered
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

                chatTextField.setText("");
                chatTextField.requestFocus();
            }else if(message.indexOf("?") != -1){
                localTextArea.append("\nPress '?' for description\n" +
                        "Available left over ships\n" +
                        "Press '/C' to know leftover ships\n" +
                        "\nPress '/P' to know how to put ships into the board\n" +
                        "%Starting point & Direction of X & Direction of Y & Index number of the ship %\n" +
                        "Starting point  = X coordinate, Y coordinate\n" +
                        "X coordinate = 1 or -1\n" +
                        "Y coordinate = 1 or -1\n" +
                        "ex) %G,10&1&-1&2%\n" +
                        "G,10 -> G,10 as a starting point\n" +
                        "1 -> Put ship in cells from starting point to (starting point + the size of the ship)\n" +
                        "-1 = Put ship in cells from starting point to (starting point = the num of the ship)\n" +
                        "\nPress '/D' to know how to drop(attack)\n"+
                        "|cell to drop|\n" +
                        "Coordinate to attack -> X coordinate, Y coordinate\n" +
                        "ex) |A,10| -> Drop(Attack) A,10 of the board\n");
            }
            else if(message.indexOf("|") != -1){
                if(shipContainer.getShipArray().size() ==0){
                    int start = message.indexOf("|");
                    int end = message.indexOf("|", start +1);
                    String point = message.substring(start+1,end);
                    person2ShipPutDel.ShipDrop(point);
                    chatTextArea.append("\nPlayer :"+point+"\n");
                    chatTextField.setText("");
                    chatTextField.requestFocus();
                    comRDrop();

                }else{
                    chatTextArea.append("------------------\n");
                    chatTextArea.append("We have not yet placed all of your ships.\n");
                    chatTextArea.append("------------------\n");
                }

            }else if(message.equals("/C")){
                localTextArea.append("\nPress '/C' to know leftover ships\n"+
                        "Available Ships\n\n");
                for(int i =0; i < shipContainer.getShipArray().size(); i++){
                    localTextArea.append("--------------------------\n");
                    localTextArea.append("Index : " + i +" Ship Name :" + shipContainer.getShipArray().get(i).getShipName() +
                            " Ship Size : " +shipContainer.getShipArray().get(i).getSize() +
                            " Ship Num : " + shipContainer.getShipArray().get(i).getNum()+"\n");
                }
                chatTextField.setText("");
                chatTextField.requestFocus();
            }else if(message.equals("/P")){
                localTextArea.append(
                        "\nPress '/P' to know how to put ships into the board\n" +
                                "%Starting point & Direction of X & Direction of Y & Index number of the ship %\n" +
                                "Starting point  = X coordinate, Y coordinate\n" +
                                "X coordinate = 1 or -1\n" +
                                "Y coordinate = 1 or -1\n" +
                                "ex) %G,10&1&-1&2%\n" +
                                "G,10 -> G,10 as a starting point\n" +
                                "1 -> Put ship in cells from starting point to (starting point + the size of the ship)\n" +
                                "-1 -> Put ship in cells from starting point to (starting point = the num of the ship)\n");
                chatTextField.setText("");
                chatTextField.requestFocus();
            }else if(message.equals("/D")){
                localTextArea.append(
                        "\nPress '/D' to know how to drop(attack)\n"+
                                "|cell to drop|\n" +
                                "Coordinate to attack -> X coordinate, Y coordinate\n" +
                                "ex) |A,10| -> Drop(Attack) A,10 of the board\n");
                chatTextField.setText("");
                chatTextField.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Assigns ships randomly for AI player
    private void setComShip(){
        while (shipContainer2.getShipArray().size() != 0){
            Random r = new Random();
            int x = r.nextInt(24);
            char y = (char) (r.nextInt(24) + 65);

            int xVec = r.nextInt(1) - 1;
            int yVec = r.nextInt(1) - 1;
            int shipIndex = r.nextInt(shipContainer2.getShipArray().size());
            String point = y + "," + x;

            System.out.println("point : " + point + " xVec :" + xVec + " yVec : " + yVec + " shipIndex : " + shipIndex);
            person2ShipPutDel.put(point, xVec, yVec, shipIndex);
            if(!comGridCK()){
                setComShip();
            }
        }
    }

    //Checks the AI has put ships correctly
    private boolean comGridCK(){
        boolean result =true;
        for(int i = 0; i < 25; i++){
            if(person2_grid.getGrid().get(0)[i].getState() == 2 || person2_grid.getGrid().get(i)[1].getState() == 2){
                person2_grid = new Grid();
                shipContainer2 = new ShipContainer();
                person2ShipPutDel.setGrid(person2_grid);
                person2ShipPutDel.setShipList(shipContainer2);
                result = false;
            }
        }
        return result;
    }

    //Makes AI to attack the cell in its turn
    private void comRDrop(){
        Random r = new Random();
        int x = r.nextInt(24);
        char y = (char) (r.nextInt(24) + 65);
        String point = y + "," + x;
        person1ShipPutDel.ShipDrop(point);
        chatTextArea.append("\nCom :"+point+"\n");
    }
}