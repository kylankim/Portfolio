package GameOfLife.Layout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardSetting extends JFrame implements ActionListener{
    //JPanel
    JPanel pn = new JPanel();

    //JButton
    JButton submit = new JButton("submit");

    //JLabel
    JLabel[] label = new JLabel[3];

    String[] labelStr = {"Row", "Column", "X"};

    //JTextField
    JTextField[] txt = new JTextField[2];

    //Grid Constraints
    GridBagConstraints[] gbc = new GridBagConstraints[7];
    int[] grid_x_y= {0, 0, 0, 1, 0,2,
            1, 0, 1, 1, 1, 2, 1, 3};

    //Constructor of the game board
    public BoardSetting() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        for(int i = 0; i < label.length; i++) {
            label[i] = new JLabel(labelStr[i]);
        }

        for(int i = 0; i < txt.length; i++) {
            txt[i] = new JTextField(3);
        }

        int idx = 0;
        for(int i = 0; i < gbc.length; i++) {

            gbc[i] = new GridBagConstraints();
            gbc[i].gridy = grid_x_y[idx];
            idx++;
            gbc[i].gridx = grid_x_y[idx];
            idx++;
        }
        pn.setLayout(new GridBagLayout());
        pn.add(label[0],gbc[0]);
        pn.add(label[2],gbc[1]);
        pn.add(label[1],gbc[2]);

        pn.add(txt[0],gbc[3]);
        pn.add(label[2],gbc[4]);
        pn.add(txt[1],gbc[5]);
        pn.add(submit,gbc[6]);

        this.setTitle("Grid Setting");
        this.setContentPane(pn);
        this.setSize(250, 150);
        this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getHeight()/2));
        this.setVisible(true);

        submit.addActionListener(this);
    }

    //Effect after clicking the cell
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(submit))
        {
            int row = 0;
            int column = 0;
            if(!txt[0].getText().equals("")) {
                row = Integer.parseInt(txt[0].getText());
            }
            if(!txt[1].getText().equals("")) {
                column = Integer.parseInt(txt[1].getText());
            }


            JOptionPane.showMessageDialog(this, "Create Game"+row+" X " + column);
            GameStart game = new GameStart(row,column);
            dispose();
        }


    }
}
