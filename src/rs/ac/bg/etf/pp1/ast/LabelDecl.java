// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class LabelDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Label Label;

    public LabelDecl (Label Label) {
        this.Label=Label;
        if(Label!=null) Label.setParent(this);
    }

    public Label getLabel() {
        return Label;
    }

    public void setLabel(Label Label) {
        this.Label=Label;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Label!=null) Label.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Label!=null) Label.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Label!=null) Label.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LabelDecl(\n");

        if(Label!=null)
            buffer.append(Label.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LabelDecl]");
        return buffer.toString();
    }
}