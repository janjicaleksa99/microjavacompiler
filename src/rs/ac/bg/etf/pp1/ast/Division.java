// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class Division extends Mulop {

    private String slash;

    public Division (String slash) {
        this.slash=slash;
    }

    public String getSlash() {
        return slash;
    }

    public void setSlash(String slash) {
        this.slash=slash;
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
        buffer.append("Division(\n");

        buffer.append(" "+tab+slash);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Division]");
        return buffer.toString();
    }
}
