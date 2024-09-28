package Calculator.core;

public class Calculate {

    /**
     * 计算表达式的值。
     *
     * @param number1 第一个数字（字符串形式）
     * @param number2 第二个数字（字符串形式）
     * @param signal  运算信号：1表示加法，2表示减法，3表示乘法，4表示除法
     * @return 计算结果（字符串形式）
     */
    public static String calculate(String number1, String number2, int signal) {
        Calculate cal = new Calculate();

        // 将字符串转换为int型分子分母
        int[] array1 = cal.conversion(number1);
        int[] array2 = cal.conversion(number2);

        int mol1 = array1[0], den1 = array1[1], mol2 = array2[0], den2 = array2[1];
        int mol, den;

        switch (signal) {
            case 1: // 加法
                if (den1 == den2 && den1 == 1)
                    return String.valueOf(mol1 + mol2);
                else {
                    den = cal.lcm(den1, den2);
                    mol = mol1 * den / den1 + mol2 * den / den2;
                    return cal.reduction(mol, den);
                }

            case 2: // 减法
                if (den1 == den2 && den1 == 1)
                    return String.valueOf(mol1 - mol2);
                else {
                    int symbol = 0; // 符号位，1表示有负号
                    den = cal.lcm(den1, den2);
                    mol = mol1 * den / den1 - mol2 * den / den2;

                    if (mol < 0) {
                        mol = -mol;
                        symbol = 1;
                    } else if (mol == 0) {
                        return "0";
                    }

                    if (symbol == 1)
                        return "-" + cal.reduction(mol, den);
                    else
                        return cal.reduction(mol, den);
                }

            case 3: // 乘法
                if (den1 == den2 && den1 == 1)
                    return String.valueOf(mol1 * mol2);
                else {
                    mol = mol1 * mol2;
                    den = den1 * den2;
                    return cal.reduction(mol, den);
                }

            case 4: // 除法
                if (den1 == den2 && den1 == 1)
                    return cal.reduction(mol1, mol2);
                else {
                    mol = mol1 * den2;
                    den = den1 * mol2;
                    return cal.reduction(mol, den);
                }
        }
        return null;
    }

    /**
     * 将字符串（无论自然数还是分数）转换为int型分子分母。
     *
     * @param number 输入的字符串
     * @return 包含分子和分母的整数数组
     */
    public int[] conversion(String number) {
        int num, mol, den;

        if (number.matches(".*/.*")) { // 如果是分数
            String string;

            if ((string = number.split("/")[0]).matches(".*'.*")) { // 如果是带分数
                num = Integer.parseInt(string.split("'")[0]);
                mol = Integer.parseInt(string.split("'")[1]);
            } else {
                num = 0;
                mol = Integer.parseInt(number.split("/")[0]);
            }

            den = Integer.parseInt(number.split("/")[1]);
            mol = mol + den * num;
        } else { // 如果是自然数
            mol = Integer.parseInt(number);
            den = 1;
        }

        return new int[]{mol, den};
    }

    /**
     * 计算最大公因数。
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 最大公因数
     */
    int gcd(int a, int b) {
        int n = Math.min(a, b);
        int i;

        for (i = n; i > 0; --i) {
            if (a % i == 0 && b % i == 0)
                break;
        }
        return i;
    }

    /**
     * 计算最小公倍数。
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 最小公倍数
     */
    int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * 分子分母化简。
     *
     * @param mol 分子
     * @param den 分母
     * @return 化简后的字符串表示
     */
    public String reduction(int mol, int den) {
        Calculate cal = new Calculate();

        int k = cal.gcd(mol, den);

        if (k == 0) {
            return "0";
        } else if (k != 1) {
            mol = mol / k;
            den = den / k;
        }

        if (den == 1)
            return String.valueOf(mol);

        if (den > mol)
            return mol + "/" + den;
        else
            return mol / den + "'" + mol % den + "/" + den;
    }
}
