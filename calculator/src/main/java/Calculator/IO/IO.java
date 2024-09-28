package Calculator.IO;

import Calculator.core.Core;
import Calculator.pojo.Expression;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IO {
    /**
     * 将表达式列表写入文本文件。
     *
     * @param exp      表达式列表
     * @param exercise 文件路径，用于保存练习题
     * @param answers  文件路径，用于保存答案
     */
    public static void writeToTxt(List<Expression> exp, String exercise, String answers) {
        try {
            File exerciseFile = new File(exercise);
            File answersFile = new File(answers);

            // 检查文件是否存在，如果不存在则创建文件
            if (!exerciseFile.exists()) {
                exerciseFile.createNewFile();
            }
            if (!answersFile.exists()) {
                answersFile.createNewFile();
            }

            try (
                    OutputStreamWriter exerciseWriter = new OutputStreamWriter(new FileOutputStream(exerciseFile), StandardCharsets.UTF_8);
                    OutputStreamWriter answersWriter = new OutputStreamWriter(new FileOutputStream(answersFile), StandardCharsets.UTF_8)
            ) {
                for (int i = 0; i < exp.size(); i++) {
                    exerciseWriter.write(i + 1 + "-->" + exp.get(i).getString() + " = " + "\r\n");
                    answersWriter.write(i + 1 + "-->" + exp.get(i).getValues() + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取练习题和答案，然后进行评分，将评分结果写入文本文件。
     *
     * @param exerciseFile 练习题文件路径
     * @param answersFile  答案文件路径
     * @param grade        评分文件路径
     */
    public static void readAndGrade(String exerciseFile, String answersFile, String grade) {
        try {
            FileReader exerciseReader = new FileReader(exerciseFile);
            FileReader answersReader = new FileReader(answersFile);
            BufferedReader exerciseBufferedReader = new BufferedReader(exerciseReader);
            BufferedReader answersBufferedReader = new BufferedReader(answersReader);

            Core t = new Core();
            ArrayList<Integer> correct = new ArrayList<>();
            ArrayList<Integer> wrong = new ArrayList<>();
            String line1;
            String line2;
            int i = 1;

            while ((line2 = answersBufferedReader.readLine()) != null && (line1 = exerciseBufferedReader.readLine()) != null) {
                String a1 = line2.split(">")[1];
                String l1 = line1.split(">")[1];
                String l2 = l1.split(" = ")[0];
                String[] s = t.postfixExpression(l2);
                String strAns = t.generateAnswer(s);
                if (strAns.equals(a1)) correct.add(i++);
                else wrong.add(i++);
            }

            File gradeFile = new File(grade);
            if (!gradeFile.exists()) gradeFile.createNewFile();

            try (OutputStreamWriter gradeWriter = new OutputStreamWriter(new FileOutputStream(gradeFile), StandardCharsets.UTF_8)) {
                if (!correct.isEmpty()) {
                    gradeWriter.write("Correct: " + correct.size() + "(" + correct.get(0));
                    for (int j = 1; j < correct.size(); j++) {
                        gradeWriter.write("," + correct.get(j));
                    }
                    gradeWriter.write(")" + "\r\n");
                } else gradeWriter.write("Correct: 0" + "\r\n");

                if (!wrong.isEmpty()) {
                    gradeWriter.write("Wrong: " + wrong.size() + "(" + wrong.get(0));
                    for (int j = 1; j < wrong.size(); j++) {
                        gradeWriter.write("," + wrong.get(j));
                    }
                    gradeWriter.write(")" + "\r\n");
                } else gradeWriter.write("Wrong: 0" + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
