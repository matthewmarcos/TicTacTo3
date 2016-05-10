import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;
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

        resetState();
        drawBoard();

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                b[i][j].addActionListener(this);
                add(b[i][j]);
            }
        }

        // Create the "New Game" button
        JButton newGameButton = new JButton();
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        newGameButton.setBackground(new Color(64, 224, 208));
        newGameButton.setText("New Game");
        newGameButton.setPreferredSize(new Dimension(500, 50));
        newGameButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent ev){
                // Reset state here
            }
            public void mousePressed(MouseEvent ev){}
            public void mouseEntered(MouseEvent ev){}
            public void mouseReleased(MouseEvent ev){}
            public void mouseExited(MouseEvent ev){}

        });

        // Create the Board Panel Layout
        this.setLayout(new GridLayout(3,3));
        this.setOpaque(true);
        this.setBackground(new Color(224, 255, 255));

        // Creates the content panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(this, BorderLayout.CENTER);
        content.add(newGameButton, BorderLayout.SOUTH);

        // Creates the window
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 580));
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

    public void resetState() {
        currentState = new State();
    }

    public void actionPerformed(ActionEvent e) {
        int temp;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {


                // Linear search for the button that got clicked
                if(b[i][j].equals(e.getSource()) &&
                ((JButton)e.getSource()).getText().equals("")){
                    String character = (turn == 1) ? "X" : "O";
                    this.currentState = new State(currentState, i, j, character);

                    // Code prints the next possible moves
                    this.currentState.printMe();
                    System.out.println("childrenStates:");
                    for(State f: this.currentState.getPossibleStates()) {
                        f.printMe();
                    }
                }
            }
        }

        drawBoard();

        String winner = currentState.getWinner();
        if(!winner.equals("")) {
            JOptionPane.showMessageDialog(null, "Winner: " + winner);
        }

        turn = (turn == 1) ? 0: 1;
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
