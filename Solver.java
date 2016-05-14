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
        ArrayList<State> leafNodes = getLeafNodes(in);

        // If there is a winning state in that tier, pick it
        for(State f : leafNodes) {
            f.printMe();
        }

        return new State();
    }

    private static ArrayList<State> getLeafNodes(State in) {
        ArrayList<State> toExpand = new ArrayList<State>();
        ArrayList<State> results = new ArrayList<State>();
        ArrayList<State> leafNodes = new ArrayList<State>();

        toExpand.add(in);

        while(!toExpand.isEmpty()) {
            // Expand all states per tier
            for(State f : toExpand) {
                results.addAll(f.getPossibleStates());
            }

            toExpand.clear();

            // put leaf nodes from results to leafNodes
            for(State f : results) {
                if(f.isLeafNode() || !exists(leafNodes, f)) {
                    leafNodes.add(f);
                }
            }

            toExpand.addAll(results);
            results.clear();
        }

        Collections.sort(leafNodes, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getHeight() == o2.getHeight())
                    return 0;
                return o1.getHeight() < o2.getHeight() ? 1 : -1;
            }
        });

        return leafNodes;

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
