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

    @Override
    protected AbstractNFA mkNFAFromRegEx(String regex) {
        Stack<AbstractNFA> nfaStack = new Stack<AbstractNFA>();
        char c;
        int pos = 0;
        while (pos < regex.length()) {
            //implement provide pseudo-code
        }

        if (nfaStack.size() != 1)
            throw new IllegalArgumentException("illegal regular expression: "
                    + "the number of operators do not match"
                    + " the number of tokens.");

        return nfaStack.pop();
    }

    @Override
    protected AbstractNFA mkNFAOfDigit() {
        return null;
    }

    @Override
    protected AbstractNFA mkNFAOfAlphaNum() {
        return null;
    }
    
    @Override
    protected AbstractNFA mkNFAOfWhite() {
        return null;
    }

    @Override
    protected AbstractNFA mkNFAOfAnyChar() {
        return null;
    }

    @Override
    protected AbstractNFA mkNFAOfChar(char c) {
        return null;
    }

    @Override
    protected AbstractNFA unionOf(AbstractNFA nfa1, AbstractNFA nfa2) {
        return null;
    }

    @Override
    protected AbstractNFA concatOf(AbstractNFA nfa1, AbstractNFA nfa2) {
        return null;
    }

    @Override
    protected AbstractNFA starOf(AbstractNFA nfa) {
        return null;
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

    private Set<Integer> mkStates(int... indices) {
        Set<Integer> states = new HashSet<Integer>();
        for (int i = 0; i < indices.length; i++) {
            states.add(indices[i]);
        }
        return states;
    }

    private Set<Edge> mkEdges(Edge... edges) {
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