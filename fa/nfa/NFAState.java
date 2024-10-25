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

    public Set<NFAState> getEpsilonTransitions() {

    if (states.containsKey('e')) { // Check if there are states reachable via ε transitions
        
            return states.get('e'); // Return the states with the ε transition
} else {
            return new HashSet<>(); // Return an empty set if no ε transitions exist
    }
        }

}


