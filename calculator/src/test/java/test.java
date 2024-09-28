import Calculator.Main;
import Calculator.core.Calculate;
import Calculator.core.Core;
import Calculator.util.Examine;
import org.junit.Test;

import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class test {
    //测试四则运算，设置不同输入，如果和理论的输出一致则通过。
    @Test
    public void calculate() {
        //加法
        assertThat(Calculate.calculate("1","2",1),equalTo("3"));
        assertThat(Calculate.calculate("1","1/2",1),equalTo("1'1/2"));
        //减法
        assertThat(Calculate.calculate("1","2",2),equalTo("-1"));
        assertThat(Calculate.calculate("1/2","1",2),equalTo("-1/2"));
        assertThat(Calculate.calculate("3/4","4/3",2),equalTo("-7/12"));
        //乘法
        assertThat(Calculate.calculate("1/2","4",3),equalTo("2"));
        assertThat(Calculate.calculate("4","2",3),equalTo("8"));
        assertThat(Calculate.calculate("3/4","5/6",3),equalTo("5/8"));
        assertThat(Calculate.calculate("1'1/2","1'1/2",3),equalTo("2'1/4"));
        //除法
        assertThat(Calculate.calculate("1/2","4",4),equalTo("1/8"));
        assertThat(Calculate.calculate("4","2",4),equalTo("2"));
        assertThat(Calculate.calculate("1'1/2","6",4),equalTo("1/4"));
        assertThat(Calculate.calculate("1'1/2","1'1/2",4),equalTo("1"));
    }

    //测试分数转换成int型分子分母，分别测试带分数真分数自然数
    @Test
    public void conversion() {
        Calculate cal = new Calculate();
        assertThat(cal.conversion("1'1/2"),equalTo(new int[]{3,2}));
        assertThat(cal.conversion("3"),equalTo(new int[]{3,1}));
        assertThat(cal.conversion("3/4"),equalTo(new int[]{3,4}));
    }

    //测试分子分母化简
    @Test
    public void reduction() {
        Calculate cal = new Calculate();
        assertThat(cal.reduction(8,7),equalTo("1'1/7"));
        assertThat(cal.reduction(3,7),equalTo("3/7"));
        assertThat(cal.reduction(8,6),equalTo("1'1/3"));
        assertThat(cal.reduction(4,2),equalTo("2"));
    }

    //测试两个表达式是否相同
    @Test
    public void sameExpression() {
        String a1="8 ÷ 4";
        String b1="4 - 8";
        Examine examine = new Examine();
        assertFalse(examine.sameExpression(a1, b1));
        String a2="8 + 4";
        String b2="4 + 8";
        assertTrue(examine.sameExpression(a2, b2));
        String a3="(8 ÷ 2) × 3";
        String b3="(2 ÷ 8) × 3";
        assertFalse(examine.sameExpression(a3, b3));
    }

    //测试生成中缀表达式。
    @Test
    public void infixExpression(){
        char[] ope={'+','-','×'};
        String[] num={"32","5","9","3/4"};

        Core core = new Core();
        assertThat(core.infixExpression(ope,num),equalTo("(32 + 5 - 9) × 3/4"));
        char[] ope1={'+','×'};
        String[] num1={"3","6","21","3/7"};
        assertThat(core.infixExpression(ope1,num1),equalTo("(3 + 6) × 21"));
        char[] ope2={'×'};
        String[] num2={"32","5","9","3/4"};
        assertThat(core.infixExpression(ope2,num2),equalTo("32 × 5"));
    }

    //测试将中缀表达式转换成后缀表达式
    @Test
    public void postfixExpression(){
        String string="9 + (3 - 1) × 3 + 10 ÷ 2";
        Core core = new Core();
        String[] strings= core.postfixExpression(string);
        assertThat(strings[0],equalTo("9"));
        assertThat(strings[1],equalTo("3"));
        assertThat(strings[2],equalTo("1"));
        assertThat(strings[3],equalTo("-"));
        assertThat(strings[4],equalTo("3"));
        assertThat(strings[5],equalTo("×"));
        assertThat(strings[6],equalTo("+"));
        assertThat(strings[7],equalTo("10"));
        assertThat(strings[8],equalTo("2"));
        assertThat(strings[9],equalTo("÷"));
        assertThat(strings[10],equalTo("+"));

        String string1="a + b × c + (d × e + f) × g";
        String[] strings1= core.postfixExpression(string1);
        assertThat(strings1[0],equalTo("a"));
        assertThat(strings1[1],equalTo("b"));
        assertThat(strings1[2],equalTo("c"));
        assertThat(strings1[3],equalTo("×"));
        assertThat(strings1[4],equalTo("+"));
        assertThat(strings1[5],equalTo("d"));
        assertThat(strings1[6],equalTo("e"));
        assertThat(strings1[7],equalTo("×"));
        assertThat(strings1[8],equalTo("f"));
        assertThat(strings1[9],equalTo("+"));
        assertThat(strings1[10],equalTo("g"));
        assertThat(strings1[11],equalTo("×"));
        assertThat(strings1[12],equalTo("+"));
    }

    //测试计算后缀表达式
    @Test
    public void generateAnswer() {
        String[] strings={"9","3","1","-","3","×","+","10","2","÷","+"};
        Core core = new Core();
        assertThat(core.generateAnswer(strings),equalTo("20"));
    }

    //测试利用栈计算
    @Test
    public void identifyOperator() {
        Stack<String> stack=new Stack<>();
        Core core = new Core();
        stack.push("1");
        stack.push("2");
        core.identifyOperator(stack,"+");
        assertThat(stack.pop(),equalTo("3"));
        stack.push("1");
        stack.push("2");
        core.identifyOperator(stack,"-");
        assertThat(stack.pop(),equalTo("-1"));
        stack.push("1");
        stack.push("2");
        core.identifyOperator(stack,"×");
        assertThat(stack.pop(),equalTo("2"));
        stack.push("1");
        stack.push("2");
        core.identifyOperator(stack,"÷");
        assertThat(stack.pop(),equalTo("1/2"));
        core.identifyOperator(stack,"1'1/2");
        assertThat(stack.pop(),equalTo("1'1/2"));
    }

    //测试主方法
    @Test
    public void main() {
        String[] strings={"-n","300","-r","120"};
        Main.main(strings);
        String[] strings1={"-e","exercise.txt","-a","answers.txt"};
        Main.main(strings1);
    }
}
