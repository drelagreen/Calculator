import java.util.LinkedList;
import java.util.Stack;

public class Calc {
    /**
     * by Drelagreen
     * 22.06.2019
     * Калькулятор на
     * обратной польской записb
     * **********приоритеты
     * ^ - high
     * * / - medium
     * + - low
     * ( ) - lowest
     * **********
     * *Первым шагом идет проверка строки на возможность вычисления
     * *Если обнаружится несостыковка - алгоритм прекращает работу и выводит ошибку
     * <p>
     * **Вторым шагом идет сортировочная станция, преобразующая строку в обратную польскую запись
     * <p>
     * ***Третий шаг - Стэковой машиной вычисляем значение - в процессе разраб
     * <p>
     * //**Метод nextNum() выделяет число из нескольких символов, если в нем что-то пойдет не так - алгоритм выведет ошибку
     * //***isOperation определяет операционный ли символ или нет
     */
    static StringBuilder rezult = new StringBuilder();
    static LinkedList<String> ll = new LinkedList<>();
    static StringBuilder sb = new StringBuilder();
    private static Stack<Character> stack = new Stack<>();

    public static void main(String[] args) {

        String str = "(1+2)*4+3";
        char[] c = str.toCharArray();
        int otkrSkobki = 0;
        int zakrSkobki = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != '.' && c[i] != ',' && !isOperation(c[i]) && !Character.isDigit(c[i])) {
                System.out.println("1error " + c[i] + " " + i);
                return;
            } else if (c[i] == '(') otkrSkobki++;
            else if (c[i] == ')') zakrSkobki++;
            else if ((i == 0 || i == c.length - 1) && (isOperation(c[i]) || c[i] == '.' || c[i] == ',')) {
                System.out.println("2error " + c[i] + " " + i);
                return;
            } else if (i != 0 && isOperation(c[i]) && isOperation(c[i - 1])) {
                if (!(c[i - 1] == '(' && c[i] == '-') && !(c[i - 1] == ')')) {
                    System.out.println("3error " + c[i] + " " + i);
                    return;
                }
            } else if ((c[i] == '.' || c[i] == ',') && !Character.isDigit(c[i - 1]) && !Character.isDigit(c[i + 1])) {
                System.out.println("4error " + c[i] + " " + i);
                return;
            }
        }
        if (otkrSkobki != zakrSkobki) {
            System.out.println("5error skobki");
            return;
        }


        for (int i = 0; i < c.length; i++) {

            if (Character.isDigit(c[i])) {
                sb.append(c[i]);
            } else if (c[i] == '.' || c[i] == ',') {
                sb.append(c[i]);
            } else if (isOperation(c[i])) {
                nextNum();
                int p = getPriorityLevel(c[i]);
                if (c[i] == '-' && c[i - 1] == '(') {
                    rezult.append(" " + 0 + " ");
                }
                if (!stack.empty() && c[i] != '(') {
                    if (c[i] == ')') {
                        char temp = stack.pop();
                        while (temp != '(') {
                            rezult.append(" ").append(temp).append(" ");
                            temp = stack.pop();
                        }
                    } else if (getPriorityLevel(c[i]) > getPriorityLevel(stack.peek()))
                        stack.push(c[i]);
                    else {
                        while (!stack.empty() && getPriorityLevel(c[i]) <= getPriorityLevel(stack.peek()))
                            rezult.append(" ").append(stack.pop()).append(" ");
                        stack.push(c[i]);
                    }
                } else
                    stack.push(c[i]);
            }
        }
        nextNum();

        while (!stack.empty()) {
            rezult.append(" ").append(stack.pop()).append(" ");
        }

        System.out.println(rezult.toString());
    }


    static boolean isOperation(char c) {
        char[] operands = {'+', '-', '*', '/', '^', '(', ')'};
        for (char c1 : operands) {
            if (String.valueOf(c1).equals(String.valueOf(c))) return true;
        }
        return false;
    }

    static void nextNum() {
        if (sb.length() != 0) {
            try {
                double e = Double.parseDouble(sb.toString());
                rezult.append(" ");
                rezult.append(e);
                rezult.append(" ");
                sb = new StringBuilder();
            } catch (Exception e) {
                rezult.append("error nextnum");
                System.out.println("error nexnum");
            }
        }
    }

    static int getPriorityLevel(char c) {
        switch (c) {
            case '*':
            case '/':
                return 3;
            case '^':
                return 4;
            case '+':
            case '-':
                return 2;
            case '(':
            case ')':
                return 1;
        }
        return 0;
    }
}



