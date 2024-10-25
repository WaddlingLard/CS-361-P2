package fa.nfa;

import fa.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class NFAState extends State {

    private Map<Character, Set<NFAState>> states;
    public NFAState(String name) {
        super(name);
        this.states = new HashMap<>();
    }

    /*

        @param
        @param
        @return
     */
    public Set<NFAState> addTransition(NFAState location, Character sigma) {
        Set<NFAState> currentStates = this.getNFATransition(sigma);
        if (currentStates == null) { // Need to create new set
            Set<NFAState> newSet = new HashSet<>();
            newSet.add(location);
            return states.put(sigma, newSet);
        } else { // There is a set holding transitions currently
            currentStates.add(location);
            return states.put(sigma, currentStates);
        }
    }

    /*

        @param
        @return
     */
    public Set<NFAState> getNFATransition(Character sigma) { return states.get(sigma); }

    /*
        A toString() method primarily used for testing purposes
        @return Output of name and map transitions
     */
    public String toString() {
        String output = "";
        output += getName() + "\n";
        output += states.toString();
        return output;
    }


}
