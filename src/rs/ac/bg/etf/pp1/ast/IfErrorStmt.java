// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class IfErrorStmt extends SingleStatement {

    public IfErrorStmt () {
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
        buffer.append("IfErrorStmt(\n");

        buffer.append(tab);
        buffer.append(") [IfErrorStmt]");
        return buffer.toString();
    }
}
