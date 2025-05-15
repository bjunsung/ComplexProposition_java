package proposition;

import java.util.List;

public interface ComplexProposition {
    void setPropOrder(List<String> keys);
    public void setFixedProposition(List<List<String>> fixedMap);
    void printTruthTable();
    void printPostfix();
}
