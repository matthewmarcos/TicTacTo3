import java.awt.*;
import java.util.*;
import javax.swing.*;

/*
    This class takes an input state and the turn, and returns
    the next state that would be best for that player.
*/
public class Solver {

    public static State nextMove(State in) {

        State currentState = in;
       /* ArrayList<State> leafNodes = getNextMove(in); 

        // If there is a winning state in that tier, pick it
        for(State f : leafNodes) {
            f.printMe();
        }

        minimax(in).printMe();*/

        //System.out.println("length of leafNodes: " + leafNodes.size());

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

        Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getHeight() == o2.getHeight())
                    return 0;
                return o1.getHeight() < o2.getHeight() ? -1 : 1;
            }
        });

        // Sort by utility
        Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getUtility() == o2.getUtility())
                    return 0;
                return o1.getUtility() < o2.getUtility() ? 1 : -1;
            }
        });

        for (State s : expanded) {
            s.printMe();
        }

        System.out.println("===================================");
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

        Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getHeight() == o2.getHeight()) {
                    return 0;
                }
                return o1.getHeight() < o2.getHeight() ? 1 : -1;
            }
        });

        // Sort by utility
        Collections.sort(expanded, new Comparator<State>(){
            public int compare(State o1, State o2){
                if(o1.getUtility() == o2.getUtility())
                    return 0;
                return o1.getUtility() < o2.getUtility() ? 1 : -1;
            }
        });


        if (in.getTurn().equals("X")) {
            return expanded.get(expanded.size() - 1).getUtility();
        }
        else {
            return expanded.get(0).getUtility();
        }
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

    public static State minimaxAlgo(ArrayList<State> leafNodes, State currentState) {
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
