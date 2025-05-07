package proposition;

class SimpleProposition {
    static public boolean calcLogicalNot(boolean p){
        return !p;
    }

    static public boolean calcLogicalAnd(boolean p, boolean q){
        return p && q;
    }
    static public boolean calcLogicalOr(boolean p, boolean q){
        return p || q;
    }
    static public boolean calcLogicalImplies(boolean p, boolean q){
        return !p || q;
    }
    static boolean calcLogicalEquivalent(boolean p, boolean q){
        return p == q;
    }
}
