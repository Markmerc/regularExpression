package automata;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.regex.PatternSyntaxException;

public class NFA extends AbstractNFA {

    public NFA() {
        super();
    }

    public NFA(String regex) {
        super(regex);
    }

    // epsilon closure - grow start state by checking if value of edge is epsilon
    
    @Override
    protected AbstractNFA mkNFAFromRegEx(String regex) {
        Stack<AbstractNFA> nfaStack = new Stack<AbstractNFA>();
        int strlen = regex.length();
        char c;
        int pos = 0;
        
        while (pos < strlen) {
        	c = regex.charAt(pos);
        	
        	if (c == '\\') {
        		if(pos == strlen - 1) {
        			throw new IllegalArgumentException();
        		}
        		pos += 1;
        		c = regex.charAt(pos);
        		
        		if (c == 'd') {
        			nfaStack.push(mkNFAOfDigit());
        		}
        		else if (c == 'w') {
        			nfaStack.push(mkNFAOfAlphaNum());
        		}
        		else if (c == 's') {
        			nfaStack.push(mkNFAOfWhite());
        		}
        		else if (c == 't') {
        			//TODO figure out how to test whitespace
        			nfaStack.push(mkNFAOfChar('\t'));
        		}
        		else if (c =='|') {
        			nfaStack.push(mkNFAOfChar('|'));
        		}
        		else if (c == '&') {
        			nfaStack.push(mkNFAOfChar('&'));
        		}
        		else if (c == '*') {
        			nfaStack.push(mkNFAOfChar('*'));
        		}
        		else if (c == '?') {
        			nfaStack.push(mkNFAOfChar('?'));
        		}
        		else if (c == '+') {
        			nfaStack.push(mkNFAOfChar('+'));
        		}
        		else if (c == '.') {
        			nfaStack.push(mkNFAOfChar('.'));
        		}
        		else if (c == '\\') {
        			nfaStack.push(mkNFAOfChar('\\'));
        		}
        	}
        	else if (c == '.') {
        		nfaStack.push(mkNFAOfAnyChar());
        	}
        	else if (c == '&') {
        		AbstractNFA nfa = nfaStack.pop();
        		nfaStack.push(concatOf(nfaStack.pop(), nfa));
        	}
        	else if (c == '|') {
        		AbstractNFA nfa = nfaStack.pop();
        		nfaStack.push(unionOf(nfaStack.pop(), nfa));
        	}
        	else if (c == '*') {
        		nfaStack.push(starOf(nfaStack.pop()));
        	}
        	else if (c == '+') {
        		nfaStack.push(plusOf(nfaStack.pop()));
        	}
        	else if (c == '?') {
        		nfaStack.push(maxOnceOf(nfaStack.pop()));
        	}
        	else {
        		nfaStack.push(mkNFAOfChar(c));
        	}
        	
        	pos += 1;
        	
        }
        
        if (nfaStack.size() != 1)
            throw new IllegalArgumentException("illegal regular expression: "
                    + "the number of operators do not match"
                    + " the number of tokens.");

        return nfaStack.pop();
    }
    
    @Override
    protected AbstractNFA mkNFAOfDigit() {
    	AbstractNFA nfa = new NFA();
    	int start = StateNumberKeeper.getNewStateNumber();
    	int finish = StateNumberKeeper.getNewStateNumber();
        	
    	nfa.setStartStates(mkStates(start));
    	nfa.setFinalStates(mkStates(finish));
    	
    	Labels labels = new Labels();
    	labels.addFromTo('0', '9');
    	
    	Edge edge = new Edge(start, finish, labels);
    	
    	Set<Edge> edges = new HashSet<Edge>();
    	edges.add(edge);
    	
    	nfa.setEdges(edges);
        return nfa;
    }

    @Override
    protected AbstractNFA mkNFAOfAlphaNum() {
    	AbstractNFA nfa = new NFA();
    	int start = StateNumberKeeper.getNewStateNumber();
    	int finish = StateNumberKeeper.getNewStateNumber();
        	
    	nfa.setStartStates(mkStates(start));
    	nfa.setFinalStates(mkStates(finish));
    	
    	Labels labels = new Labels();
    	labels.addFromTo('0', '9');
    	labels.addFromTo('A', 'Z');
    	labels.addFromTo('a', 'z');
    	
    	Edge edge = new Edge(start, finish, labels);
    	
    	Set<Edge> edges = new HashSet<Edge>();
    	edges.add(edge);
    	
    	nfa.setEdges(edges);
        return nfa;
    }
    
    @Override
    protected AbstractNFA mkNFAOfWhite() {
    	AbstractNFA nfa = new NFA();
    	int start = StateNumberKeeper.getNewStateNumber();
    	int finish = StateNumberKeeper.getNewStateNumber();
        	
    	nfa.setStartStates(mkStates(start));
    	nfa.setFinalStates(mkStates(finish));
    	
    	Labels labels = new Labels();
    	labels.add('\t');
    	labels.add('\r');
    	labels.add('\n');
    	labels.add('\f');
    	labels.add(' ');
    	
    	Edge edge = new Edge(start, finish, labels);
    	
    	Set<Edge> edges = new HashSet<Edge>();
    	edges.add(edge);
    	
    	nfa.setEdges(edges);
        return nfa;

    }

    @Override
    protected AbstractNFA mkNFAOfAnyChar() {
    	AbstractNFA nfa = new NFA();
    	int start = StateNumberKeeper.getNewStateNumber();
    	int finish = StateNumberKeeper.getNewStateNumber();
        	
    	nfa.setStartStates(mkStates(start));
    	nfa.setFinalStates(mkStates(finish));
    	
    	Labels labels = new Labels(-1);
    	
    	Edge edge = new Edge(start, finish, labels);
    	
    	Set<Edge> edges = new HashSet<Edge>();
    	edges.add(edge);
    	
    	nfa.setEdges(edges);
        return nfa;
    }

    @Override
    protected AbstractNFA mkNFAOfChar(char c) {
    	AbstractNFA nfa = new NFA();
    	int start = StateNumberKeeper.getNewStateNumber();
    	int finish = StateNumberKeeper.getNewStateNumber();
        	
    	nfa.setStartStates(mkStates(start));
    	nfa.setFinalStates(mkStates(finish));
    	
    	Labels labels = new Labels(c);
    	
    	Edge edge = new Edge(start, finish, labels);
    	
    	Set<Edge> edges = new HashSet<Edge>();
    	edges.add(edge);
    	
    	nfa.setEdges(edges);
        return nfa;
    }

    @Override
    protected AbstractNFA unionOf(AbstractNFA nfa1, AbstractNFA nfa2) {
    	AbstractNFA unionedNFA = new NFA();
    	
    	// Create new start state, and create epsilon transitions to old start states
    	int start = StateNumberKeeper.getNewStateNumber();
    	unionedNFA.setStartStates(mkStates(start));

    	Labels labels = new Labels(0);
    	int nfa1StartState = nfa1.getStartStates().iterator().next();
    	int nfa2StartState = nfa2.getStartStates().iterator().next();

    	// Create new edges from new start state to the old start states
    	Edge edge1 = new Edge(start, nfa1StartState, labels);
    	Edge edge2 = new Edge(start, nfa2StartState, labels);
    	
    	// New final states = union of old final states
    	Set<Integer> finalStates = new HashSet<Integer>(nfa1.getFinalStates());
    	finalStates.addAll(nfa2.getFinalStates());
    	unionedNFA.setFinalStates(finalStates);
    	
    	Set<Edge> newEdges = new HashSet<Edge>(nfa1.getEdges());
    	newEdges.addAll(nfa2.getEdges());
    	newEdges.add(edge1);
    	newEdges.add(edge2);
    	
    	unionedNFA.setEdges(newEdges);
    	
        return unionedNFA;
    }

    @Override
    protected AbstractNFA concatOf(AbstractNFA nfa1, AbstractNFA nfa2) {
    	AbstractNFA concat = new NFA();
    	
    	//Make new edges
    	Set<Integer> nfaOneFinal = nfa1.getFinalStates();	
    	int nfaTwoStart = nfa2.getStartStates().iterator().next();
    	Set<Edge> newEdges = new HashSet<Edge>();
    	for (int x : nfaOneFinal) {
    		newEdges.add(new Edge(x, nfaTwoStart, new Labels(0)));
    	}
    	
    	//Add Edges from two NFAs
    	newEdges.addAll(nfa1.getEdges());
    	newEdges.addAll(nfa2.getEdges());
    	
    	concat.setStartStates(nfa1.getStartStates());    	
    	concat.setEdges(newEdges);
    	concat.setFinalStates(nfa2.getFinalStates());
    	
        return concat;
    }

    @Override
    protected AbstractNFA starOf(AbstractNFA nfa) {
    	AbstractNFA starred = new NFA();
 
    	Set<Integer> oldFinal = nfa.getFinalStates();
    	int oldStart =  nfa.getStartStates().iterator().next();
    	Set<Edge> oldEdges = nfa.getEdges();

    	Set<Integer> newFinal = new HashSet<Integer>(oldFinal);
    	Set<Edge> newEdges = new HashSet<Edge>(oldEdges);
     	int starredStart = StateNumberKeeper.getNewStateNumber();
    	
    	    	
    	//Edges from old final, to old start
    	for (int x : oldFinal) {
    		newEdges.add(new Edge(x, starredStart, new Labels(0)));
    	}
    	newEdges.add(new Edge(starredStart, oldStart, new Labels(0)));

    	newFinal.add(starredStart);
    	
    	starred.setStartStates(mkStates(starredStart));
    	starred.setFinalStates(newFinal);
    	starred.setEdges(newEdges);
    	
    	
        return starred;
    }

    @Override
    protected AbstractNFA plusOf(AbstractNFA nfa) {
        return null;
    }

    @Override
    protected AbstractNFA maxOnceOf(AbstractNFA nfa) {
        return null;
    }

    private boolean isMetaChar(char c) {
        return c == '\\' || c == '.' || c == '&' || c == '|' || c == '*'
                || c == '+' || c == '?';
    }

    public Set<Integer> mkStates(int... indices) {
        Set<Integer> states = new HashSet<Integer>();
        for (int i = 0; i < indices.length; i++) {
            states.add(indices[i]);
        }
        return states;
    }

    public Set<Edge> mkEdges(Edge... edges) {
        Set<Edge> edgeSet = new HashSet<Edge>();
        for (int i = 0; i < edges.length; i++) {
            edgeSet.add(edges[i]);
        }
        return edgeSet;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out
            .println("usage: NFA arg1 arg2\n"
                    + "arg1: the pattern to match\n"
                    + "arg2: the input string");
        } else {
            NFA nfa = new NFA(args[0]);
            System.out.println("arguments have been validated");
            System.out.println(nfa.accept(args[1]));
        }
        return;
    }
}