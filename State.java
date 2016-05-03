import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;


public class State {
    private String[][] state;
    private int utility;
    private State parent;

    public State() {
        this.state = new String[3][3];
        this.parent = null;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = "";
            }
        }


    }

    public State(State parent, int x, int y, String value) {
        this.state = new String[3][3];
        this.parent = parent;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = new String(parent.getState()[i][j]);
            }
        }

        // Dapat ang linalagyang x and y ay walang laman
        if(this.state[x][y].equals("")) {
            this.state[x][y] = new String(value);
        }
        else {
            // Error!
            System.out.println("Already has a value");
        }
    }

    public String[][] getState() {
        return this.state;
    }

    public String getWinner() {
        String winner = "";
        String value;

        // Check rows
        for (int i = 0 ; i < 3 ; i++) {
            winner = "";
            value = "";

            for(int j = 0 ; j < 3 ; j++) {
                value += this.state[i][j];
            }

            winner = checkWinner(value);
            if(!winner.equals("")) {
                System.out.println("Winner: " + winner);
                return winner;
            }
        }

        return "";
    }

    private String checkWinner(String value) {
        if(value.equals("XXX")) {
            return "X";
        }
        else if(value.equals("OOO")) {
            return "O";
        }
        else {
            return "";
        }
    }
}
