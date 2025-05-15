package proposition;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

class TruthMap {
    private int propSize;
    private int fixedSize;
    private final String[] proposition;
    private final boolean[] truthValue;
    private final boolean[] fixed;

    TruthMap(){
        proposition = new String[52];
        truthValue = new boolean[52];
        fixed = new boolean[52];
        propSize = 0;
        fixedSize = 0;
    }

    void setKey(String key, boolean value){
        for (int i = 0; i < propSize; ++i){
            if(proposition[i].equals(key)){
                truthValue[i] = value;
                return;
            }
        }
        proposition[propSize] = key;
        truthValue[propSize] = value;
        propSize++;
    }

    int findIndex(String key){
        for (int i = 0; i < propSize; ++i)
            if (proposition[i].equals(key))
                return i;
        throw new IndexNotFoundException("can not find key : " + key);
    }

    void addProposition(char key){addProposition(Character.toString(key));}
    void addProposition(String key){
        try{
            findIndex(key);
            throw new InvalidInputExeption("duplicate key detected");
        } catch(IndexNotFoundException e){
            proposition[propSize] = key;
            truthValue[propSize] = false;
            propSize++;
        }
    }

    public void setFixedProposition(List<List<String>> mappings){
        for (List<String> mapping : mappings){
            try{
                int idx = findIndex(mapping.get(0));
                if (mapping.get(1).equalsIgnoreCase("TRUE"))
                    truthValue[idx] = true;
                else if (mapping.get(1).equalsIgnoreCase("FALSE"))
                    truthValue[idx] = false;
                else
                    throw new InvalidInputExeption("Invalid truth value detected in setting Fixed value");
                fixed[idx] = true;
                fixedSize++;
            }catch(IndexNotFoundException e){System.out.println(e.getMessage());}
        }
    }


    boolean getTruthValue(String key){
        if (key.equals("TRUE")) return true;
        else if (key.equals("FALSE")) return false;
        int idx = findIndex(key);
        return truthValue[idx];
    }

    void setPropValueStepSequence(int num){
        for(int i = 0, idx = 0; i < this.propSize; ++i, ++idx){
            if (fixed[i]) {
                idx--;
                continue;
            }
            truthValue[i] = num / (int) Math.pow(2.0, (double) this.getMutablePropSize() - (idx+1)) % 2 == 0;
        }
    }

    private boolean checkDuplicate(List<String> arr){
        Set<String> seen = new HashSet<>();
        for(String key : arr){
            if(!seen.add(key)) return true;
        }
        return false;
    }

    void setPropOrder(List<String> keys){
        if (checkDuplicate(keys)) throw new InvalidInputExeption("duplicate proposition key detected, check your pre-ordered proposition.");
        for (int i = 0; i < keys.size(); ++i){
            int idx = findIndex(keys.get(i));
            if (idx == i) continue;
            String tempKey = keys.get(i);
            boolean tempValue = truthValue[idx];
            boolean tempFixed = fixed[idx];
            for(int j = idx; j > i; --j){
                proposition[j] = proposition[j - 1];
                truthValue[j] = truthValue[j - 1];
                fixed[j] = fixed[j-1];
            }
            proposition[i] = tempKey;
            truthValue[i] = tempValue;
            fixed[i] = tempFixed;
        }
    }

    void printPropositions(){
        for (int i=  0; i < propSize; ++i){
            String key = proposition[i];
            if(key.equals(proposition[0])) System.out.print("idx__" + key);
            else System.out.print("___" + key);
        }
    }

    void printTruthValue(){
        for (int i= 0; i < propSize;  ++i){
            System.out.print(truthValue[i] ? "T" : "F");
            System.out.print(" | ");
        }
    }

    int getMutablePropSize(){
        return propSize - fixedSize;
    }
}
