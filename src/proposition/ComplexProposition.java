package proposition;

import java.util.List;

public interface ComplexProposition {
    void setPropOrder(List<String> keys);
    void printTruthTable();
    void printPostfix();
}
