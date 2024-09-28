package Calculator.pojo;

//查询对象，用于比较是否重复
public class Query {
    int number;
    float answer;
    public Query(int number,float answer){
        this.number=number;
        this.answer=answer;
    }

    public float getAnswer() {
        return answer;
    }

    public int getNumber() {
        return number;
    }

    public void setAnswer(float answer) {
        this.answer = answer;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
