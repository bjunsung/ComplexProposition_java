import proposition.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ComplexProposition cp = Factory.createComplexProposition();
        cp.printTruthTable();
    }
}
