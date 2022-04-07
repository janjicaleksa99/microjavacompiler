// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class SingleTerm extends Expression {

    private FirstTerm FirstTerm;

    public SingleTerm (FirstTerm FirstTerm) {
        this.FirstTerm=FirstTerm;
        if(FirstTerm!=null) FirstTerm.setParent(this);
    }

    public FirstTerm getFirstTerm() {
        return FirstTerm;
    }

    public void setFirstTerm(FirstTerm FirstTerm) {
        this.FirstTerm=FirstTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FirstTerm!=null) FirstTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FirstTerm!=null) FirstTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FirstTerm!=null) FirstTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleTerm(\n");

        if(FirstTerm!=null)
            buffer.append(FirstTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleTerm]");
        return buffer.toString();
    }
}
