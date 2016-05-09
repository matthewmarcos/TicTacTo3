import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;


public class State {
    private String[][] state;
    private int utility;
    private State parent;
    private String turn;

    public State() {
        this.state = new String[3][3];
        this.parent = null;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = "";
            }
        }
    }

    /*
        Public constructor
        @Params:
            State parent: Preceding state
            int x, y: Coordinates of new move
            String value: value of the new move -> Also add to turn.
    */
    public State(State parent, int x, int y, String value) {
        this.state = new String[3][3];
        this.parent = parent;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = new String(parent.getState()[i][j]);
            }
        }

        // [x][y] should have nothing in it
        if(this.state[x][y].equals("")) {
            this.state[x][y] = new String(value);
            this.turn = new String(value);
        }
        else {
            // [x][y] already has a value
        }
    }

    /*
        Public Method printMe():
        - Prints the details to console for better debug
    */
    public void printMe() {
        System.out.println("");
        System.out.println("");
        System.out.println("Turn: " + this.turn);
        System.out.println("Utility: " + this.utility);

        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(!this.state[i][j].equals("")) {
                    System.out.print(this.state[i][j]);
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println("");
        }
    }

    public String getTurn() {
        return new String(this.turn);
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
