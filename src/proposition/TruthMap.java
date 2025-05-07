package proposition;

import java.util.HashSet;
import java.util.Set;

class TruthMap {
    private int propSize;
    private int fixedSize;
    private String[] proposition;
    private boolean[] truthValue;
    private boolean[] fixed;

    TruthMap(){
        proposition = new String[52];
        truthValue = new boolean[52];
        fixed = new boolean[52];
    }

    void setKey(String key, boolean value){
        for (int i = 0; i < propSize; ++i){
            if(proposition[i] == key){
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
            if (proposition[i] == key)
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
        }
    }

    void setFixedProposition(String key, boolean value) {
        int idx = findIndex(key);
        fixed[idx] = true;
        fixedSize++;
    }

    boolean getTruthValue(String key){
        int idx = findIndex(key);
        return truthValue[idx];
    }

    void setPropValueStepSequence(int num){
        for(int i = 0; i < this.propSize; ++i){
            if (fixed[i]) continue;
            truthValue[i] = num / (int) Math.pow(2.0, (double) this.propSize - (i+1)) % 2 == 0;
        }
    }

    private boolean checkDuplicate(String[] arr){
        Set<String> seen = new HashSet<>();
        for(String key : arr){
            if(!seen.add(key)) return true;
        }
        return false;
    }

    void setPropOrder(String[] keys){
        if (checkDuplicate(keys)) throw new InvalidInputExeption("duplicate proposition key detected, check your pre-ordered proposition.");
        for (int i = 0; i < keys.length; ++i){
            int idx = findIndex(keys[i]);
            if (idx == i) continue;
            String tempKey = keys[idx];
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
        for (String key : proposition){
            if(key.equals(proposition[0])) System.out.print("idx__" + key);
            else System.out.print("___" + key);
        }
    }

    void printTruthValue(){
        for (int i= 0; i < propSize;  ++i){
            System.out.print(truthValue[i] ? "T" : "F");
            System.out.print("  |");
        }
    }

    int getMutablePropSize(){
        return propSize - fixedSize;
    }
}
