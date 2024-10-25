package fa.nfa;

import fa.State;

import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;
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
        Sigma.add(symbol);
    }

    /*

        @param
        @return
     */
    private boolean inSigma(char symbol) {
        for (Character letter: this.Sigma) {
            if (letter == (Character) symbol) {
                return true;
            }
        }
        return false;
    }

    /*
        Different Implementation from P1
     */
    @Override
    public boolean accepts(String s) {

        NFAState currentState = this.q0;
//        if (s.length() == 0) {
//            return(isFinal(currentState.getName()));
//        }


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
    public State getState(String name) {
        for (State state : Q) {
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
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState currentState = (NFAState) getState(fromState);
        if (currentState == null || !inSigma(onSymb) || !statesInMachine(toStates)) { // fromState not valid in machine or onSymb not present in alphabet
            return false;
        }

        if (EPSILON == onSymb) { // Flagging epsilon transition flag
            epsilonTransition = true;
        }
        // Adding transition
        for (String state: toStates) {
            NFAState transition = (NFAState) getState(state);
            Set<NFAState> transitionSet = currentState.addTransition(transition, onSymb);
            if (!transitionSet.contains(transition)) { // For testing if not adding properly
                System.out.println("ERROR WITH ADDING TRANSITION");
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isDFA() {
        // This is a DFA if there are no epsilon or ambiguous transitions
        // Likely need to use eClosure() and a way to grab the transitions of each state
        if (epsilonTransition) {
            return false;
        }


        return true;
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
}
