package proposition;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

class ComplexPropEvaluatorImpl implements ComplexPropEvaluator{
    private List<Character> postfix;
    TruthMap truthMap;
    boolean evalSimpleProposition(boolean p, char op){
        return SimpleProposition.calcLogicalNot(p);
    }
    private boolean evalSimpleProposition(boolean p, boolean q, char op){
        return switch(op){
            case '·' -> SimpleProposition.calcLogicalAnd(p, q);
            case '∨' -> SimpleProposition.calcLogicalOr(p, q);
            case '→' -> SimpleProposition.calcLogicalImplies(p, q);
            case '↔' -> SimpleProposition.calcLogicalEquivalent(p, q);
            default -> throw new InvalidInputExeption("Invalid Logical Connective : " + op);
        };
    }

    public ComplexPropEvaluatorImpl(List<Character> postfix, TruthMap truthmap){
        this.postfix = postfix;
        this.truthMap = truthmap;
    }

    public boolean evalPostfix(){
        Stack<String> evalstack = null;
        for(char token : postfix){
            if(('A' <= token && token <= 'Z') || ('a' <= token && token <= 'z')){
                evalstack.push(Character.toString(token));
            }
            else if(token == '~'){
                String p = Objects.requireNonNull(evalstack).pop();
                boolean truthValue = truthMap.getTruthValue(p);
                boolean evaluatedTruthValue = evalSimpleProposition(truthValue, '~');
                evalstack.push(evaluatedTruthValue ? "TRUE" : "FALSE");
            }
            else {
                String q = evalstack.pop();
                String p = evalstack.pop();
                boolean pValue = truthMap.getTruthValue(p);
                boolean qValue = truthMap.getTruthValue(q);
                boolean evaluatedTruthValue = evalSimpleProposition(pValue, qValue, token);
                evalstack.push(evaluatedTruthValue ? "TRUE" : "FALSE");
            }
        }
        if(evalstack.size() != 1)
            throw new InvalidInputExeption("Invalid Complex Proposition");
        String result = evalstack.lastElement();
        return result.equals("TRUE");
    }

    public char getMajorLogicalConnective(){
        return postfix.getLast();

    }

}
