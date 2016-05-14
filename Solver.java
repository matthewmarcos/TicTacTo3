import java.awt.*;
import java.util.*;
import javax.swing.*;

/*
    This class takes an input state and the turn, and returns
    the next state that would be best for that player.
*/
public class Solver {

    public static State nextMove(State in) {
        return getNextMove(in);
    }

    private static State getNextMove(State in) {
        ArrayList<State> expanded = in.getPossibleStates();

        if(expanded.isEmpty()) {
            return null;
        }

        for (State state : expanded) {  // Set utility of state
            state.setUtility(minimax(state));
        }

        /*Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getHeight() == o2.getHeight())
                    return 0;
                return o1.getHeight() < o2.getHeight() ? -1 : 1;
            }
        });*/

        // Sort by utility
        Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getUtility() == o2.getUtility())
                    return 0;
                return o1.getUtility() < o2.getUtility() ? 1 : -1;
            }
        });

        if (in.getTurn().equals("X")) {
            return expanded.get(0);
        } else {
            return expanded.get(expanded.size() - 1);
        }
    }

    private static int minimax(State in) {
        if (in.isLeafNode()) {
            if (in.getWinner().equals("X")) {
                return -1;
            }
            else if (in.getWinner().equals("O")) {
                return 1;
            }
            else {
                return 0;
            }
        }

        ArrayList<State> expanded = new ArrayList<State>();
        ArrayList<Integer> scores = new ArrayList<Integer>();

        expanded.addAll(in.getPossibleStates());
        
        for (State state : expanded) {
            int temp = minimax(state);
            state.setUtility(temp);
            scores.add(temp);
        }

        Collections.sort(scores);

        if (in.getTurn().equals("X")) {
            return scores.get(scores.size() - 1);
        }
        else {
            return scores.get(0);
        }
    }

    /* 
        Updates the utility of a state
        1    Winner is X
        0    Draw
        -1   Winner is O
    */
    public static int updateUtility(State state) {
        if (!(state.getWinner().equals(""))) {
            if (state.getWinner().equals("X")) {
                return 1;
            } else return -1;
        }

        return 0;
    }

    private static boolean exists(ArrayList<State> leafNodes, State f) {
        for(State s : leafNodes) {
            if(s.equals(f)) {
                return true;
            }
        }

        return false;
    }

    private Solver() {}
}