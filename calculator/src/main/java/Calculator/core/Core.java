package Calculator.core;

import Calculator.pojo.Expression;
import Calculator.pojo.Query;
import Calculator.util.Examine;
import Calculator.util.ItemRandom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Core {
    /**
     * 生成题目和答案，通过调用ItemRandom类生成随机运算符和数字组合，然后检查并添加到题目列表和查询列表中。
     *
     * @param number           题目数量
     * @param maxNum           生成的随机数字的范围
     * @param expressionList   题目列表
     * @param queryList        查询列表
     */
    public void getTopicAndAnswer(int number, int maxNum, List<Expression> expressionList, List<Query> queryList) {
        Examine check = new Examine();
        while (expressionList.size() < number) {
            String infix = infixExpression(ItemRandom.RanOpe(), ItemRandom.RanNum(maxNum));
            check.answerChecking(infix, expressionList, queryList);
        }
    }

    /**
     * 生成中缀表达式，根据随机生成的运算符和数字数组生成中缀表达式字符串。
     *
     * @param ope 随机生成的运算符数组
     * @param num 随机生成的数字数组
     * @return 中缀表达式字符串
     */
    public String infixExpression(char[] ope, String[] num) {
        int i = 0;
        ArrayList<String> inf = new ArrayList<>();
        for (; i < ope.length; i++) {
            inf.add(num[i]);
            inf.add(String.valueOf(ope[i]));
        }
        inf.add(num[i]);
        switch (ope.length) {
            case 3 -> {
                if ((ope[0] == '+' || ope[0] == '-') && (ope[1] == '+' || ope[1] == '-') && (ope[2] == '×' || ope[2] == '÷')) {
                    inf.add(0, "(");
                    inf.add(6, ")");
                }
                if ((ope[0] == '+' || ope[0] == '-') && (ope[1] == '×' || ope[1] == '÷')) {
                    inf.add(0, "(");
                    inf.add(4, ")");
                }
            }
            case 2 -> {
                if ((ope[0] == '+' || ope[0] == '-') && (ope[1] == '×' || ope[1] == '÷')) {
                    inf.add(0, "(");
                    inf.add(4, ")");
                }
            }
            default -> {
            }
        }
        for (i = 0; i < inf.size(); i++) {
            if (inf.get(i).equals("+") || inf.get(i).equals("-") || inf.get(i).equals("×") || inf.get(i).equals("÷"))
                inf.set(i, " " + inf.get(i) + " ");
        }
        StringBuilder infix = new StringBuilder(inf.get(0));
        for (i = 1; i < inf.size(); i++) {
            infix.append(inf.get(i));
        }
        return infix.toString();
    }

    /**
     * 中缀表达式转换成后缀表达式。
     *
     * @param string 中缀表达式字符串
     * @return 后缀表达式的字符串数组
     */
    public String[] postfixExpression(String string) {
        // 符号栈
        Stack<String> stack = new Stack<>();
        // 后缀表达式
        List<String> list = new LinkedList<>();
        // 将中缀表达式按空格分开
        String[] splitString = string.split(" ");
        for (String str : splitString) {
            // 如果是左括号就入栈
            if (str.matches("\\(.*")) {
                list.add(str.split("\\(")[1]);
                stack.push("(");
            }
            // 如果是右括号就把栈顶元素依次加入到列表，直到读取到左括号，将其出栈。
            else if (str.matches(".*\\)")) {
                list.add(str.split("\\)")[0]);
                while (!stack.peek().equals("(")) {
                    list.add(stack.pop());
                }
                stack.pop();
            } else if (str.matches("[+\\-×÷]")) {
                // 栈为空将运算符入栈
                if (stack.isEmpty()) stack.push(str);
                    // 如果运算符是加减，优先级最低，将栈顶元素加入到列表，如果读取到左括号或栈为空将运算符入栈
                else if (str.matches("[+\\-]")) {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        list.add(stack.pop());
                    }
                    stack.push(str);
                }
                // 如果运算符是乘除
                else {
                    // 如果栈不为空且栈顶元素是乘除，将其出栈加入到列表。
                    while (!stack.isEmpty() && stack.peek().matches("[×÷]")) {
                        list.add(stack.pop());
                    }
                    // 栈顶元素是加减或为空，将运算符入栈。
                    stack.push(str);
                }
            }
            // 其余符号都是表示数字，将其入栈。
            else {
                list.add(str);
            }
        }
        // 最后把栈内元素全部加入到列表
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        String[] postfixString = new String[list.size()];
        // 将列表元素转变为字符串数组
        for (int i = list.size() - 1; i >= 0; i--) {
            postfixString[i] = list.remove(i);
        }
        return postfixString;
    }

    /**
     * 由后缀表达式生成题目答案。
     *
     * @param strings 后缀表达式的字符串数组
     * @return 表达式计算结果的字符串
     */
    public String generateAnswer(String[] strings) {
        Stack<String> stack = new Stack<>();
        for (String string : strings) {
            // 返回true表示计算过程有负号或除数为0
            if (identifyOperator(stack, string))
                return "-1";
        }
        return stack.pop();
    }

    /**
     * 识别运算符，返回true表示计算过程有负号。
     *
     * @param stack  运算符栈
     * @param string 当前运算符或数字
     * @return 如果计算过程中出现负号，返回true，否则返回false
     */
    public boolean identifyOperator(Stack<String> stack, String string) {
        String num, num1, num2;
        switch (string) {
            case "+" -> {
                num2 = stack.pop();
                num1 = stack.pop();
                num = Calculate.calculate(num1, num2, 1);
                stack.push(num);
            }
            case "-" -> {
                num2 = stack.pop();
                num1 = stack.pop();
                num = Calculate.calculate(num1, num2, 2);
                stack.push(num);
                // 如果计算过程中出现负号，返回true
                if (num != null && num.matches("-.*")) return true;
            }
            case "×" -> {
                num2 = stack.pop();
                num1 = stack.pop();
                num = Calculate.calculate(num1, num2, 3);
                stack.push(num);
            }
            case "÷" -> {
                num2 = stack.pop();
                num1 = stack.pop();
                // 如果除数为0
                if (num2.equals("0")) return true;
                num = Calculate.calculate(num1, num2, 4);
                stack.push(num);
            }
            default -> stack.push(string);
        }
        return false;
    }
}
