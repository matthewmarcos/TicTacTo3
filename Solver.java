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

        minMaxAlgo(leafNodes, in).printMe();

        //System.out.println("length of leafNodes: " + leafNodes.size());

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
                // Make sure it is a leaf node and it does not exist
                if(f.isLeafNode() && !exists(leafNodes, f)) {
                    leafNodes.add(f);
                }
            }

            for(State f : leafNodes) {
                results.remove(f);
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

        // Set utility of each leaf node
        for (State state : leafNodes) {
            state.setUtility(updateUtility(state));
        }

        return leafNodes;
    }

    public static State minMaxAlgo(ArrayList<State> leafNodes, State currentState) {
        ArrayList<State> parentStore = new ArrayList<State>();
        ArrayList<State> childStore = new ArrayList<State>();

        while (leafNodes.get(0).getParent() != currentState) {
            parentStore.clear();
            while (leafNodes.size() > 0) {
                childStore.clear();
                State parent = leafNodes.get(0).getParent();

                childStore.addAll(parent.getChildren());

                for (State child : childStore) {
                    boolean flag = true;

                    if (parent.getTurn().equals("X")) {
                        if (child.getUtility() == -1) {
                            parent.setUtility(-1);
                            break;
                        }
                    } else if (parent.getTurn().equals("O")) {
                        if (child.getUtility() == 1) {
                            parent.setUtility(1);
                            break;
                        }
                    }

                    parent.setUtility(0);
                }

                for (State leaf : childStore)
                    leafNodes.remove(leaf);

                parentStore.add(parent);
            }

            leafNodes.clear();
            leafNodes.addAll(parentStore);
        }

        return leafNodes.get(0);
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
