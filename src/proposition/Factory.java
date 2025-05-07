package proposition;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Factory {
    static Scanner scanner = new Scanner(System.in);

    static private String getCP(){
        int exIdx;
        String line = "";
        while (true) {
            try {
                System.out.print("Input Your Complex Proposition here(you can type a number 1, 2, or 3 to use the examples above)>> ");
                exIdx = scanner.nextInt();
                scanner.nextLine();
                line = ComplexPropositionImpl.ExampleComplexProposition[exIdx - 1];
                break;
            } catch (InputMismatchException e){
                try {
                    Double d = scanner.nextDouble();
                    System.out.println("Example number must be int type");
                    scanner.nextLine();
                } catch (InputMismatchException e2) {
                    line = scanner.nextLine();
                    break;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Example number must be 1, 2, or 3");
            }
        }
        return line;
    }

    static void setOrder(ComplexProposition cp){
        try {
            System.out.print("Would you like to specify the order of propositions? (y/n)");
            String answer = scanner.next();
            scanner.nextLine();
            if (!(answer.equals("Y") || answer.equals("y")))
                throw new InputMismatchException();
            System.out.println("Complex Proposition ex) ~[(~P ∨ ~Q) → A]  for example) A P Q, then this will set the proposition in the order you provided.");
            System.out.print("input propositions order here >> ");
            String orderLine = scanner.nextLine();
            List<String> order = new ArrayList<>();
            for (int i = 0; i < orderLine.length(); ++i){
                if ('A' <= orderLine.charAt(i) && orderLine.charAt(i) <= 'Z' || 'a' <= orderLine.charAt(i) && orderLine.charAt(i) <= 'z')
                    order.add(String.valueOf(orderLine.charAt(i)));
            }
            cp.setPropOrder(order);
        }catch(InputMismatchException ignore){}
        catch(IndexNotFoundException e){
            System.out.println("Invalid input detected. check your proposition order set");
        }
    }

    public static ComplexProposition createComplexProposition(){
        ComplexPropositionImpl.printValidConnective();
        System.out.println("Example Input : 1. (P · Q) ∨ R\t2. ~[C ∨ (A ∨ ~D)] · (A → ~C)\t3. P ↔ Q");
        ComplexProposition cp = new ComplexPropositionImpl(getCP());
        setOrder(cp);
        return cp;
    }
}
