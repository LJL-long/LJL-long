package Calculator;

import Calculator.IO.IO;
import Calculator.core.Core;
import Calculator.pojo.Expression;
import Calculator.pojo.Query;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        List<Expression> expressionList = new LinkedList<>();
        List<Query> queryList = new LinkedList<>();
        Core topic = new Core();

        int max = 0;
        int NumEx = 10;
        String option = null;
        String value = null;
        try {
            if (args.length % 2 == 0 && args.length != 0) {
                for (int i = 0; i < args.length; i += 2) {
                    option = args[i];
                    value = args[i + 1];

                    switch (option) {
                        case "-a" -> {
                        }
                        case "-r" -> max = parseInt(value);
                        case "-n" -> NumEx = parseInt(value);
                        case "-e" -> {
                            if (i + 2 < args.length && args[i + 2].equals("-a")) {
                                String answerFilePath = args[i + 3];
                                if (new File(value).exists() && new File(answerFilePath).exists()) {
                                    IO.readAndGrade(value, answerFilePath, "grade.txt");
                                } else {
                                    System.out.println("错误：exercise和answer文件不存在！");
                                    showOption();
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("错误: Missing -a option");
                                showOption();
                                System.exit(1);
                            }
                        }
                        default -> {
                            System.out.println("错误: Invalid option - " + option);
                            showOption();
                            System.exit(1);
                        }
                    }
                }
            } else {
                System.out.println("错误: Invalid number of arguments");
                showOption();
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("错误: wrong parameter");
            showOption();
            System.out.println("错误: Invalid value for option - " + option + " (" + value + ")");
            System.exit(1);
        }
        if (max != 0) {
            //生成题目和答案
            topic.getTopicAndAnswer(NumEx, max, expressionList, queryList);
            //写入文件地址在此定义
            IO.writeToTxt(expressionList, "./exercise.txt", "./answers.txt");
            IO.readAndGrade("./exercise.txt", "./answers.txt", "./grade.txt");
            System.out.print("生成成功");
        }
    }

    public static void showOption(){
        System.out.println("option: ");
        System.out.println("\t\t"+"[-r]: -r max number");
        System.out.println("\t\t"+"[-r][-n]: -r max number, -n the number of expression");
        System.out.println("\t\t"+"[-e][-a]: -e url of standard exercise.txt, -a url of standard answer.txt");
        System.out.println("tip: Details in my blog");
    }
}

