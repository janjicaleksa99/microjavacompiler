// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class SingleStmt extends Statement {

    private LabelDecl LabelDecl;
    private SingleStatement SingleStatement;

    public SingleStmt (LabelDecl LabelDecl, SingleStatement SingleStatement) {
        this.LabelDecl=LabelDecl;
        if(LabelDecl!=null) LabelDecl.setParent(this);
        this.SingleStatement=SingleStatement;
        if(SingleStatement!=null) SingleStatement.setParent(this);
    }

    public LabelDecl getLabelDecl() {
        return LabelDecl;
    }

    public void setLabelDecl(LabelDecl LabelDecl) {
        this.LabelDecl=LabelDecl;
    }

    public SingleStatement getSingleStatement() {
        return SingleStatement;
    }

    public void setSingleStatement(SingleStatement SingleStatement) {
        this.SingleStatement=SingleStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LabelDecl!=null) LabelDecl.accept(visitor);
        if(SingleStatement!=null) SingleStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LabelDecl!=null) LabelDecl.traverseTopDown(visitor);
        if(SingleStatement!=null) SingleStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LabelDecl!=null) LabelDecl.traverseBottomUp(visitor);
        if(SingleStatement!=null) SingleStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStmt(\n");

        if(LabelDecl!=null)
            buffer.append(LabelDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleStatement!=null)
            buffer.append(SingleStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStmt]");
        return buffer.toString();
    }
}
