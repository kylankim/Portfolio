package kr.co.GameWorld.Layout;

import kr.co.GameWorld.Element.Cell;
import kr.co.GameWorld.Element.Check;
import kr.co.GameWorld.Element.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameStart implements ActionListener  {
    private final int GAME_TIME = 60;

    private Grid grid;
    private Timer timer;
    private int row;
    private int column;

    private ArrayList<JButton[]> CellList;
    private JButton startBtn;

    private JPanel gridJPanel;
    private JFrame jFrame;
    private JLabel jLabel;
    GridBagConstraints[] gbc;

    //Inner Class Share
    private int timestamp = 0;
    private boolean flag = false;
    public GameStart(int r, int c){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.row = r;
        this.column = c;

        CellList = new ArrayList<>();
        startBtn = new JButton();
        jFrame = new JFrame();
        jLabel = new JLabel();

        gridJPanel = new JPanel();
        gridJPanel.setLayout(new GridBagLayout());
        grid = new Grid(row, column);
        timer = new Timer();
        timer.start();
        System.out.println(timestamp);
        gbc = new GridBagConstraints[(row * column +2)];

        startBtn.setText("Let's Go");
        startBtn.setPreferredSize(new Dimension(45, 28));
        startBtn.addActionListener(this);

        jLabel.setText("남은 시간");
        for(int i = 0; i < r; i++){
            JButton[] btnlist = new JButton[c];
            for(int t = 0; t < c; t++){
                btnlist[t] = new JButton();
                btnlist[t].setPreferredSize(new Dimension(45, 28));
                btnlist[t].addActionListener(this);
            }
            CellList.add(btnlist);
        }


        int curr = 0;
        for(int i = 0; i < row ; i++){
            for (int j = 0 ; j < column; j++){
                gbc[curr] = new GridBagConstraints();
                gbc[curr].gridx = j;
                gbc[curr].gridy = i;
                curr++;
            }
        }
        curr = 0 ;
        for(int i = 0 ; i<CellList.size(); i++){
            for(int j = 0 ; j < CellList.get(i).length; j++){
                gridJPanel.add(CellList.get(i)[j], gbc[curr]);
                curr++;
            }
        }
        int gbcLength = gbc.length - 1;
        gbc[gbcLength] = new GridBagConstraints();
        if(c%2 == 0){
            gbc[gbcLength].gridwidth = 2;
        }else {
            gbc[gbcLength].gridwidth = 3;
        }
        gbc[gbcLength].gridx = c/2-1;
        gbc[gbcLength].gridy = r + 2;

        gbc[gbcLength].fill = GridBagConstraints.HORIZONTAL;

        gridJPanel.add(startBtn,gbc[gbcLength]);

        gbc[gbcLength-1] = new GridBagConstraints();
        gbc[gbcLength-1].gridx = c/2;
        gbc[gbcLength-1].gridy = r + 1;
        gbc[gbcLength-1].gridwidth = c;
        gbc[gbcLength-1].fill = GridBagConstraints.HORIZONTAL;

        gridJPanel.add(jLabel,gbc[gbcLength-1]);
        jFrame.setTitle("Grid Setting");
        jFrame.setContentPane(gridJPanel);
        jFrame.setSize(500, 500);
        jFrame.setLocation((dim.width/2)-(jFrame.getWidth()/2), (dim.height/2)-(jFrame.getHeight()/2));
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0 ; i< CellList.size(); i++){
            for(int j = 0 ; j < CellList.get(i).length; j++){
                if(e.getSource().equals(CellList.get(i)[j])){
                    System.out.println(i+","+j);
                    if(grid.getGrid().get(i)[j].getState() == true){
                        CellList.get(i)[j].setBackground(null);
                        grid.getGrid().get(i)[j].setState(false);
                    }else{
                        CellList.get(i)[j].setBackground(Color.GREEN);
                        grid.getGrid().get(i)[j].setState(true);
                    }
                }
            }
        }

        if(e.getSource().equals(startBtn)){
                if(timer.getFlag() == true){
                    startBtn.setText("ReStart");
                    timer.setFlag(false);
                }else{
                    startBtn.setText("Stop");
                    timer.setTime(GAME_TIME);
                    timer.setFlag(true);
                    timer.resume();
            }
        }
    }

    public void drawGrid(){
       ArrayList<Cell[]> gridList =  grid.getGrid();
       for(int i = 0; i < gridList.size(); i++){
           for(int j = 0; j < gridList.get(i).length; j++){
               if(grid.getCellState(i,j)){
                   CellList.get(i)[j].setBackground(Color.GREEN);
               }else{
                   CellList.get(i)[j].setBackground(null);
               }
           }
       }
    }

    public int checkAlive(){
        ArrayList<Cell[]> gridList =  grid.getGrid();
        int result = 0;
        for(int i = 0; i < gridList.size(); i++){
            for(int j = 0; j < gridList.get(i).length; j++){
                if(grid.getCellState(i,j))
                    result += 1;
            }
        }
        return  result;
    }


    public class Timer extends Thread implements Runnable{
        public void setTime(int time) {
            timestamp = time * 1000;
        }

        public int getTimeStamp() {
            return timestamp;
        }

        public void setFlag(boolean b){
            flag = b;
        }

        public boolean getFlag(){
            return flag;
        }
        public void run() {
            while (true){
                while(flag){
                    if(timestamp == 0) {
                        Check check = new Check(grid);
                        grid=check.check();
                        drawGrid();
                        if(checkAlive() == 0){
                            flag = false;
                        }else{
                           setTime(GAME_TIME);
                        }
                    }

                    try {
                        Thread.sleep(1000);
                        System.out.println(timestamp/1000 +"초 남았습니다.");
                        jLabel.setText(timestamp/1000 +"초 남았습니다.");
                        timestamp -= 1000;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
