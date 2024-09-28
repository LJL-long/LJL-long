package Calculator.util;

import Calculator.core.Calculate;

import java.util.Random;

public class ItemRandom {
    /**
     * 生成随机的运算符数组，包括加法、减法、乘法和除法。
     *
     * @return 包含随机运算符的字符数组
     */
    public static char[] RanOpe() {
        Random rand = new Random();
        int[] ope_int = new int[3];

        // 循环直到生成至少一个运算符
        while (true) {
            for (int i = 0; i < 3; i++) {
                // 随机生成能代表无、加、减、乘、除的数0、1、2、3、4
                ope_int[i] = rand.nextInt(4 - 0) + 0;
            }
            if (ope_int[0] == 0 && ope_int[1] == 0 && ope_int[2] == 0) continue;
            break;
        }

        int j = 0;
        for (int i = 0; i < ope_int.length; i++) {
            // 计算随机生成多少个符号
            if (ope_int[i] != 0) j++;
        }
        char[] ope = new char[j];
        for (int i = 0, k = 0; i < 3; i++) {
            switch (ope_int[i]) {
                case 1:
                    ope[k] = '+';
                    k++;
                    break;
                case 2:
                    ope[k] = '-';
                    k++;
                    break;
                case 3:
                    ope[k] = '×';
                    k++;
                    break;
                case 4:
                    ope[k] = '÷';
                    k++;
                    break;
                default:
                    break;
            }
        }
        return ope;
    }

    /**
     * 生成随机的数字数组，其中 max 参数用于指定生成的随机数字的范围。如果 max 大于1，将生成整数，否则将生成分数。
     *
     * @param max 生成随机数字的范围
     * @return 包含随机数字的字符串数组
     */
    public static String[] RanNum(int max) {
        String[] num = new String[4];
        Random rand = new Random();
        if (max > 1) {
            for (int i = 0; i < num.length; i++) {
                int tag = rand.nextInt(3 - 0) + 0;
                if (tag == 0 || tag == 1)
                    num[i] = String.valueOf((int) (Math.random() * max));
                else
                    num[i] = RanFra(max);
            }
        } else if (max == 1) {
            for (int i = 0; i < num.length; i++)
                num[i] = RanFra(max);
        }
        return num;
    }

    /**
     * 生成随机的分数字符串，其中 max 参数用于指定生成的随机分数的分母的范围。
     *
     * @param max 生成随机分数的分母的范围
     * @return 随机分数的字符串表示
     */
    public static String RanFra(int max) {
        int fra, zi;
        Random rand = new Random();

        // 分子分母不为零
        do {
            fra = rand.nextInt(20 - 1) + 1;
            zi = rand.nextInt(20 - 1) + 1;
        } while (fra == 0 || zi == 0);

        Calculate cal = new Calculate();
        return cal.reduction(zi, fra);
    }
}
