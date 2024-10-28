package fa.nfa;

import fa.State;

import java.util.*;

/*
    This class is used as an acting NFA which enables the user to create a Non-Deterministic Finite Automata.

    @author Brian Wu
    @author Max Ma
    @version 1.0
 */

public class NFA implements NFAInterface{

    private Set<NFAState> Q;
    private Set<NFAState> F;
    private Set<Character> Sigma;
    private NFAState q0;
    private boolean epsilonTransition;
    private final char EPSILON = 'e';
    private static int maxStates = 0;

    public NFA(){
        this.F = new LinkedHashSet<>();
        this.Q = new LinkedHashSet<>();
        this.Sigma = new LinkedHashSet<>();
        this.q0 = null;
        this.epsilonTransition = false;
    }

    @Override
    public boolean addState(String name) {
        NFAState newState = new NFAState(name);
        for (NFAState state: Q) {
            if (state.getName().equals(newState.getName())) {
                return false;
            }
        }
        return Q.add(newState);
    }

    @Override
    public boolean setFinal(String name) {
        for(NFAState state: Q){
            if (state.getName().equals(name)){
                return F.add(state);
            }
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        for(NFAState state: Q){
            if(state.getName().equals(name)){
                q0 = state;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        if (EPSILON == symbol) { // Epsilon is not a character in the alphabet
            return;
        }
        Sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {

        // Validating string
        char[] process = s.toCharArray();
        for (char letter: process) {
            if (!inSigma(letter) || EPSILON == letter) { // Just grab eClosure
                Set<NFAState> temp = eClosure(this.q0);
                maxStates = temp.size();
                return false; // Invalid string!
            }
        }

        Queue<NFAState> nfaQueue = new LinkedList<NFAState>();
        NFAState currentState = this.q0;
        int size = 0;
        int maxCopies = 0;

        // Initialize the queue
        Set<NFAState> currentClosure = this.eClosure(currentState);
        for (NFAState state: currentClosure) {
            nfaQueue.add(state);
        }

        for (char letter: process) {
            // First do eClosure transitions
            for (int i = 0; i < size; i++) {
                currentState = nfaQueue.remove();
                currentClosure = eClosure(currentState);
                for (NFAState state: currentClosure) {
                    nfaQueue.add(state);
                }
            }

            // Update maxStates after processing epsilon transitions
            Set<NFAState> temp = new HashSet<>();
            temp.addAll(nfaQueue);
            maxCopies = Math.max(maxCopies, temp.size());
            maxStates = maxCopies;

            size = nfaQueue.size(); // Number of states to process

            // Process letter from string (On current level)
            for (int i = 0; i < size; i++) {
                currentState = nfaQueue.remove(); // Grabbing a state to process
                Set<NFAState> newStates = getToState(currentState, letter); // Grabbing all transitions from given letter
                if (newStates == null) { // No valid transitions
                    continue;
                }

                for (NFAState state: newStates) {
                    nfaQueue.add(state); // Add all new states from the set
                }
            }

            // Update maxStates after processing a character
            Set<NFAState> temp2 = new HashSet<>();
            temp2.addAll(nfaQueue);
            maxCopies = Math.max(maxCopies, temp2.size());
            maxStates = maxCopies;

            size = nfaQueue.size(); // How many elements to get eClosure
        }

        Set<NFAState> finalstates = new HashSet<>();
        // Final eClosure call (used to update maxcopies)
        for (int i = 0; i < size; i++) {
            currentState = nfaQueue.remove();
            currentClosure = eClosure(currentState);
            for (NFAState state: currentClosure) {
                finalstates.add(state);
            }
        }

        maxCopies = Math.max(maxCopies, finalstates.size());
        maxStates = maxCopies;

        // Is anything in final?
        for (NFAState state: finalstates) {
            if (isFinal(state.getName())) {
                return true; // Is found!
            }
        }
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        Set<Character> tempSigma = new HashSet<>(); // Used for encapsulation, could also use LinkedHashSet
        Object[] temp = Sigma.toArray();
        for (Object item: temp) {
            tempSigma.add((Character) item);
        }
        return tempSigma;
    }

    @Override
    public NFAState getState(String name) {
        for (NFAState state : Q) {
            if (state.getName().equals(name)) { // State located!
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        for(NFAState state: F){
            if(state.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return q0.getName().equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> states = from.getNFATransition(onSymb);
        return states;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);

        while(!stack.isEmpty()){
            NFAState curr = stack.pop();
            if(!closure.contains(curr)){
                closure.add(curr);

                Set<NFAState> epsilonStates = curr.getEpsilonTransitions();
                if (epsilonStates != null) {
                    for (NFAState nextState : epsilonStates) {
                        if (!closure.contains(nextState)) {
                            stack.push(nextState); // Only push if not already visited
                        }
                    }
                }
            }
        }
        return closure;
    }

    @Override
    public int maxCopies(String s) {
        this.accepts(s);
        return maxStates;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState currentState = getState(fromState);
        if (currentState == null || !inSigma(onSymb) || !statesInMachine(toStates)) { // fromState not valid in machine or onSymb not present in alphabet
            return false;
        }

        if (EPSILON == onSymb) { // Flagging epsilon transition flag
            epsilonTransition = true;
        }
        // Adding transition
        for (String state: toStates) {
            NFAState transition = getState(state);
            Set<NFAState> transitionSet = currentState.addTransition(transition, onSymb);
            if (!transitionSet.contains(transition)) { // For testing if not adding properly
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isDFA() {
        if (epsilonTransition) { // DFA do not have epsilon transitions
            return false;
        }
        // Checking ALL transitions on ALL states
        for (NFAState state: Q) { // Grabbing all states
            for (Character letter: Sigma) { // Comparing with all elements in library
                Set<NFAState> test = state.getNFATransition(letter);
                if (test == null) {
                    continue;
                }
                if (test.size() > 1) { // More than one option on a single character
                    return false;
                }
            }
        }
        return true; // Passes all criteria
    }

    private boolean statesInMachine(Set<String> states) {
        for (String state: states) {
            State testState = getState(state);
            if (testState == null) { // State not in machine
                return false;
            }
        }
        return true;
    }

    private boolean inSigma(char symbol) {
        if (EPSILON == symbol) { // Epsilon not a letter in the alphabet but is technically a valid transitional character
            return true;
        }

        for (Character letter: this.Sigma) {
            if (letter == (Character) symbol) {
                return true;
            }
        }
        return false;
    }
}
