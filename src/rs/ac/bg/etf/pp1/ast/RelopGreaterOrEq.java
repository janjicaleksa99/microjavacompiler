// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class RelopGreaterOrEq extends Relop {

    public RelopGreaterOrEq () {
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
        buffer.append("RelopGreaterOrEq(\n");

        buffer.append(tab);
        buffer.append(") [RelopGreaterOrEq]");
        return buffer.toString();
    }
}
