public class Test {

    public static void main(String[] args){
        int idx = 0;
        for (int num = 0; num < 4; ++num) {
            for (int i = 0; i < 2; ++i) {
                if(idx++ == 1) continue;
                System.out.print(num / (int) Math.pow(2.0, (double) (2) - (idx + 1)) % 2 == 0);
            }
            System.out.println();
        }
    }
}
