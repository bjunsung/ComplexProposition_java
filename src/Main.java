import java.util.Scanner;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char test = sc.next().charAt(0);
        if (test == '·')
            System.out.println("연언");
        if (test == '→')
            System.out.println("단순 함축");
        if (test == '↔')
            System.out.println("단순 동치");
        if(test == '∨')
            System.out.println("선언");
    }
}

//'·', '→', '↔'