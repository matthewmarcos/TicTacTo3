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
                return winner;
            }
        }

        // Check Columns
        for (int i = 0 ; i < 3 ; i++) {
            winner = "";
            value = "";

            for(int j = 0 ; j < 3 ; j++) {
                value += this.state[j][i];
            }

            winner = checkWinner(value);
            if(!winner.equals("")) {
                return winner;
            }
        }

        // Check backslash
        value = this.state[0][0] + this.state[1][1] + this.state[2][2];
        winner = checkWinner(value);
        if(!winner.equals("")) {
            return winner;
        }

        // Check slash
        value = this.state[0][2] + this.state[1][1] + this.state[2][0];
        winner = checkWinner(value);
        if(!winner.equals("")) {
            return winner;
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
