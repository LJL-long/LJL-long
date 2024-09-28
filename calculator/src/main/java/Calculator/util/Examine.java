package Calculator.util;

import Calculator.core.Calculate;
import Calculator.core.Core;
import Calculator.pojo.Expression;
import Calculator.pojo.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Examine {
    /**
     * 检查表达式的答案，并处理重复的表达式。
     *
     * @param infixExpression 输入的中缀表达式
     * @param expressionList 包含表达式的列表
     * @param queryList 包含查询的列表
     */
    public void answerChecking(String infixExpression, List<Expression> expressionList, List<Query> queryList) {
        Core topic = new Core();
        Calculate cal = new Calculate();
        String[] postfix = topic.postfixExpression(infixExpression);
        String answer = topic.generateAnswer(postfix);

        // 如果答案不是"-1"，表示表达式符合要求
        if (!answer.equals("-1")) {
            int[] scores = cal.conversion(answer);
            float score = (float) scores[0] / scores[1];

            // 如果查询列表为空，将表达式和答案添加到列表中
            if (queryList.isEmpty()) {
                expressionList.add(new Expression(infixExpression, answer, score));
                queryList.add(new Query(expressionList.size() - 1, score));
            } else {
                for (int i = 0; i < queryList.size(); i++) {
                    if (queryList.get(i).getAnswer() == score) {
                        // 获取已有题目表达式
                        String contrast = expressionList.get(queryList.get(i).getNumber()).getString();

                        // 如果长度相等且表达式重复，退出查重循环
                        if (contrast.length() == infixExpression.length() && sameExpression(contrast, infixExpression)) {
                            break;
                        }
                        // 如果存在下一个题目，且下一个题目的答案大于新生成表达式答案，加入该表达式
                        else if (i + 1 < queryList.size() && queryList.get(i + 1).getAnswer() > score) {
                            expressionList.add(new Expression(infixExpression, answer, score));
                            queryList.add(i, new Query(expressionList.size() - 1, score));
                            break;
                        }
                        // 没有下一个题目，在末尾添加
                        else if (i + 1 == queryList.size()) {
                            expressionList.add(new Expression(infixExpression, answer, score));
                            queryList.add(i, new Query(expressionList.size() - 1, score));
                            break;
                        }
                    }
                    // 已有表达式答案大于新表达式答案，加入
                    else if (queryList.get(i).getAnswer() > score) {
                        expressionList.add(new Expression(infixExpression, answer, score));
                        queryList.add(i, new Query(expressionList.size() - 1, score));
                        break;
                    }
                }
            }
        }
    }

    /**
     * 比较两个表达式是否相同。
     *
     * @param exp1 第一个表达式
     * @param exp2 第二个表达式
     * @return 如果两个表达式相同返回true，否则返回false
     */
    public boolean sameExpression(String exp1, String exp2) {
        if ((exp1.charAt(0) == '(' && exp2.charAt(0) != '(') || (exp2.charAt(0) == '(' && exp1.charAt(0) != '('))
            return false;

        exp1 = exp1.replace("(", "");
        exp1 = exp1.replace(")", "");
        exp2 = exp2.replace("(", "");
        exp2 = exp2.replace(")", "");

        // 不同长度的表达式不相同
        if (exp1.length() != exp2.length())
            return false;

        ArrayList<String> e1 = new ArrayList<>();
        ArrayList<String> e2 = new ArrayList<>();
        for (String s : exp1.split(" ")) {
            e1.add(s);
        }
        for (String s : exp2.split(" ")) {
            e2.add(s);
        }

        if (!Objects.equals(e1.get(1), e2.get(1)))
            return false;

        if ((e1.get(0).equals(e2.get(0)) && e1.get(2).equals(e2.get(2))) ||
                (e1.get(0).equals(e2.get(2)) && e1.get(2).equals(e2.get(0)) && (e1.get(1).equals("+") || e1.get(1).equals("×")))) {
            for (int i = 3; i < e1.size(); i++) {
                if (!Objects.equals(e1.get(i), e2.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
