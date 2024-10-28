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
       This method adds a DFA transition to a DFAState provided a key and value
       @param sigma
       @param location
       @return The origin DFAState that now has a transition attacted to it
     */


    /*
        Adds a transition to this state provided a given character

        @param location
        @param sigma
        @return A set of NFAStates that contain the end transitions
     */
    public Set<NFAState> addTransition(NFAState location, Character sigma) {
        Set<NFAState> currentStates = this.getNFATransition(sigma);
        if (currentStates == null) { // Need to create new set
            Set<NFAState> newSet = new HashSet<>();
            newSet.add(location);
            states.put(sigma, newSet);
        } else { // There is a set holding transitions currently
            currentStates.add(location);
            states.put(sigma, currentStates);
        }
        return states.get(sigma);
    }

    /*
        Gets the NFATransition set from a provided character

        @param sigma
        @return A set of NFAStates
     */
    public Set<NFAState> getNFATransition(Character sigma) { return states.get(sigma); }

    /*
        Gets the epsilon transitions of the current state

        @return A set of NFAStates
     */
    public Set<NFAState> getEpsilonTransitions() {
        if (states.containsKey('e')) { // Check if there are states reachable via ε transitions
            return states.get('e'); // Return the states with the ε transition
        } else {
            return new HashSet<>(); // Return an empty set if no ε transitions exist
        }
    }
}


