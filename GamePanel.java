import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener{
    private static JFrame frame;
    private JPanel content;
    private JPanel startPanel;
    private JButton b[][] = new JButton[3][3];
    private State currentState;
    private int turn;

    public GamePanel() {
        turn = 1;
        showBoardGui();
    }

    public void createAndShowGui() {
        // Create the "New Game" button
        JButton newGameButton = new JButton();
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        newGameButton.setBackground(new Color(0, 100, 0));
        newGameButton.setForeground(Color.white);
        newGameButton.setText("New Game");
        newGameButton.setPreferredSize(new Dimension(500, 50));
        newGameButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent ev){
                frame.dispose();
                GamePanel game = new GamePanel();
                game.createAndShowGui();
            }
            public void mousePressed(MouseEvent ev){}
            public void mouseEntered(MouseEvent ev){}
            public void mouseReleased(MouseEvent ev){}
            public void mouseExited(MouseEvent ev){}
        });

        // Creates the starting panel
        JButton p1Button = new JButton();
        JButton p2Button = new JButton();
        JLabel turnLabel = new JLabel("Choose your turn:");
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        turnLabel.setPreferredSize(new Dimension(200, 200));
        turnLabel.setForeground(Color.white);
        p1Button.setFont(new Font("Arial", Font.PLAIN, 20));
        p1Button.setBackground(new Color(250, 250, 250));
        p1Button.setText("Player 1");
        p1Button.setPreferredSize(new Dimension(200, 50));
        p1Button.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent ev){
                turn = 1;
                content.remove(startPanel);
                content.revalidate();
                content.repaint();
                createBoardGui();
            }
            public void mousePressed(MouseEvent ev){}
            public void mouseEntered(MouseEvent ev){}
            public void mouseReleased(MouseEvent ev){}
            public void mouseExited(MouseEvent ev){}
        });

        p2Button.setFont(new Font("Arial", Font.PLAIN, 20));
        p2Button.setBackground(new Color(250, 250, 250));
        p2Button.setText("Player 2");
        p2Button.setPreferredSize(new Dimension(200, 50));
        p2Button.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent ev){
                turn = 2;
                content.remove(startPanel);
                content.revalidate();
                content.repaint();
                createBoardGui();
            }
            public void mousePressed(MouseEvent ev){}
            public void mouseEntered(MouseEvent ev){}
            public void mouseReleased(MouseEvent ev){}
            public void mouseExited(MouseEvent ev){}
        });

        JPanel buttonHolder = new JPanel();
        buttonHolder.setBackground(Color.black);
        buttonHolder.add(p1Button);
        buttonHolder.add(p2Button);

        startPanel = new JPanel();
        startPanel.setBackground(Color.black);
        startPanel.setLayout(new BorderLayout());
        startPanel.add(turnLabel, BorderLayout.NORTH);
        startPanel.add(buttonHolder, BorderLayout.CENTER);

        // Creates the content panel
        content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(startPanel, BorderLayout.CENTER);
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

    public void createBoardGui() {
        // Create the tiles
        this.removeAll();

        resetState();
        drawBoard();

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                b[i][j].setForeground(Color.white);
                b[i][j].addActionListener(this);
                add(b[i][j]);
            }
        }

        // Create the Board Panel Layout
        this.setLayout(new GridLayout(3,3));
        this.setOpaque(true);
        this.setBackground(new Color(3, 3, 3));

        content.add(this, BorderLayout.CENTER);
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
                    State resultingState = new State(currentState, i, j, character);
                    this.currentState = resultingState.clone();

                    Solver.nextMove(resultingState, character);

                    turn = (turn == 1) ? 0: 1;
                    System.out.println("==============================================");
                }
            }
        }

        drawBoard();

        String winner = currentState.getWinner();
        if(!winner.equals("")) {

            JOptionPane.showMessageDialog(null, "Winner: " + winner);

            frame.dispose();
            GamePanel game = new GamePanel();
            game.createAndShowGui();
        } else if(currentState.isLeafNode()) {
            JOptionPane.showMessageDialog(null, "Draw!");

            frame.dispose();
            GamePanel game = new GamePanel();
            game.createAndShowGui();
        }

    }

    private void showBoardGui(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                b[i][j] = new JButton();
                b[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                b[i][j].setBackground(new Color(3, 3, 3));
            }
        }
    }
}
