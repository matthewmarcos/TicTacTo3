import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{
    private static JFrame frame;
    private JButton b[][] = new JButton[3][3];
    private State currentState;
    private int turn;

    public GamePanel() {
        turn = 1;
        showBoardGui();
    }

    public void createAndShowGui() {
        // Create the tiles
        this.removeAll();

        currentState = new State();
        drawBoard();

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                b[i][j].addActionListener(this);
                add(b[i][j]);
            }
        }

        // Creates the Button Panel
        JPanel buttonHolder = new JPanel();
        buttonHolder.setOpaque(true);

        // Creates the Board
        this.setLayout(new GridLayout(3,3));
        this.setOpaque(true);
        this.setBackground(new Color(224, 255, 255));

        // Creates the content panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(this, BorderLayout.CENTER);
        content.add(buttonHolder, BorderLayout.SOUTH);

        // Creates the window
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 560));
        frame.setContentPane(content);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }


    // Redraws the button
    private void drawBoard() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                b[i][j].setText(currentState.getState()[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        int temp;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                // Linear search for the button that got clicked

                if(b[i][j].equals(e.getSource())){
                    if (((JButton)e.getSource()).getText().equals("")) {
                        if (turn == 1) {
                            this.currentState = new State(currentState, i, j, "X");
                            this.currentState.printMe();
                        }
                        else {
                            this.currentState = new State(currentState, i, j, "O");
                            this.currentState.printMe();
                        }
                    }
                }
            }
        }

        if(!currentState.getWinner().equals("")) {
            System.out.println(currentState.getWinner());
        }
        drawBoard();

        if (turn == 1) turn = 0;
        else turn = 1;
    }

    private void showBoardGui(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                b[i][j] = new JButton();
                b[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                b[i][j].setBackground(new Color(64, 224, 208));
            }
        }
    }
}
