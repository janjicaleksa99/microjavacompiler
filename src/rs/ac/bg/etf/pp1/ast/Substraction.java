// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class Substraction extends Addop {

    private String minus;

    public Substraction (String minus) {
        this.minus=minus;
    }

    public String getMinus() {
        return minus;
    }

    public void setMinus(String minus) {
        this.minus=minus;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Substraction(\n");

        buffer.append(" "+tab+minus);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Substraction]");
        return buffer.toString();
    }
}
