package proposition;
import java.util.DuplicateFormatFlagsException;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

class ComplexPropositionImpl {
    private String inputLine;
    private List<Character> postfix;
    private Stack<Character> connctiveStack;
    private TruthMap truthMap;
    private ComplexPropEvaluator evaluator;

    public final static char[] validConnective = { '~', '·', '∨', '→', '↔', '[', '{', '(', ')', '}', ']' };
    public final static String[] ExampleComplexProposition = new String[]{"(P·Q)VR", "~[CV(AV~D)]·(A->~C)", "P<->Q"};

    private boolean isValidConnective(char op){
        for (char sample : validConnective)
            if(op == sample)
                return true;
        return false;
    }

    private void popStackTilLeftBracket(char bracketRight){
        char bracketLeft = switch (bracketRight) {
            case ')' -> '(';
            case '}' -> '{';
            case ']' -> '[';
            default -> ' ';
        };
        while (true) {
            if (connctiveStack.empty()) {
                throw new RuntimeException("Bracket Mismatch Error : matching '" + bracketLeft + "' not found for '" + bracketRight + "'");
            }
            char op = connctiveStack.pop();
            if (op == bracketLeft)
                break;
            else
                postfix.add(op);
        }
        if (!connctiveStack.empty() && connctiveStack.peek() == '~') {
            postfix.add('~');
            connctiveStack.pop();
        }
    }

    private void popStackTilEmpty(){
        while(!connctiveStack.empty()){
            postfix.add(connctiveStack.pop());
        }
    }

    private boolean getTruthValueForEachCase(int i){
        truthMap.setPropValueStepSequence(i);
        return evaluator.evalPostfix();
    }

    private void infixToPostfix(String inputLine){
        for (int i = 0; i < inputLine.length(); ++i){
            char token = inputLine.charAt(i);
            if (token == ' ') continue;
            else if (('A' <= token && token <= 'Z') || ('a' <= token && token <= 'z')){
                try{
                    truthMap.addProposition(token);
                }catch(InvalidInputExeption ignored){}
            }
            else if(! isValidConnective(token))
                throw new InvalidInputExeption("Invalid Connective detected : " + token);
            else if (token == ']'  || token == '}' || token == ')')
                popStackTilLeftBracket(token);
            else connctiveStack.push(token);
        }
        popStackTilEmpty();
        evaluator = new ComplexPropEvaluatorImpl(postfix, truthMap);
    }

    ComplexPropositionImpl(String inputLine){
        this.inputLine = inputLine;
        infixToPostfix(inputLine);
    }

    public static void printValidConnective(){
        System.out.print("Logical Connectives : ");
        for(char op : validConnective){
            if(op == ']') System.out.print(op);
            else System.out.print(op + ", ");
        }
        System.out.println();
    }

    public void printTruthTable(){
         System.out.println("*  *  *  *  *\n<TRUTH TABLE>\n*  *  *  *  *");
         truthMap.printPropositions();
         System.out.println("___" + inputLine);
         for(int i = 0; i < Math.pow(2.0, (double) truthMap.getMutablePropSize()); ++i){
             boolean truthValueInThisCase = getTruthValueForEachCase(i);
             System.out.printf("%2d | ", i+1);
             truthMap.printTruthValue();
             System.out.println(" " + (truthValueInThisCase ? "T" : "F") + " ");
         }
         System.out.println("Major Logical Connective : " + evaluator.getMajorLogicalConnective());
         System.out.println("*  *  *  *  *");
    }
}
