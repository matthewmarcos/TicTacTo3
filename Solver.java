import java.awt.*;
import java.util.*;
import javax.swing.*;

/*
    This class takes an input state and the turn, and returns
    the next state that would be best for that player.
*/
public class Solver {

    public static State nextMove(State in, String turn) {

        State currentState = in;
        ArrayList<State> toExpand = new ArrayList<State>();
        ArrayList<State> results = new ArrayList<State>();
        ArrayList<State> leafNodes = new ArrayList<State>();

        toExpand.add(in);

        // 1) Expand all the possible children of that state.
        while(!toExpand.isEmpty()) {
            // Expand all states per tier
            for(State f : toExpand) {
                results.addAll(f.getPossibleStates());
            }

            toExpand.clear();

            // put leaf nodes from results to leafNodes
            for(State f : results) {
                if(f.isLeafNode()) {
                    leafNodes.add(f);
                }
            }

            toExpand.addAll(results);
            results.clear();
        }

        // If there is a winning state in that tier, pick it
        for(State f : leafNodes) {
            f.printMe();
        }

        return new State();

    }

    private Solver() {}
}
