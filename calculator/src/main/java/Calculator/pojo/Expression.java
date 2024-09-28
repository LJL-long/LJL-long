package Calculator.pojo;

//表达式类
public class Expression {
    String string;
    String values;
    float score;
    public Expression(String string,String values,float score){
        this.string=string;
        this.values=values;
        this.score=score;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public float getScore(){
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}

