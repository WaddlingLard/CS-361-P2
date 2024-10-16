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


    public NFA(){
        this.F = new LinkedHashSet<>();
        this.Q = new LinkedHashSet<>();
        this.Sigma = new LinkedHashSet<>();
        this.q0 = null;
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

    /*
        Different implementation from P1
     */
    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        return false;
    }

    @Override
    public boolean isDFA() {
        return false;
    }
}
