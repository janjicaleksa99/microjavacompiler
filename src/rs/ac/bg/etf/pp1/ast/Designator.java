// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private DesignName DesignName;
    private ArrayAccess ArrayAccess;

    public Designator (DesignName DesignName, ArrayAccess ArrayAccess) {
        this.DesignName=DesignName;
        if(DesignName!=null) DesignName.setParent(this);
        this.ArrayAccess=ArrayAccess;
        if(ArrayAccess!=null) ArrayAccess.setParent(this);
    }

    public DesignName getDesignName() {
        return DesignName;
    }

    public void setDesignName(DesignName DesignName) {
        this.DesignName=DesignName;
    }

    public ArrayAccess getArrayAccess() {
        return ArrayAccess;
    }

    public void setArrayAccess(ArrayAccess ArrayAccess) {
        this.ArrayAccess=ArrayAccess;
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
        if(DesignName!=null) DesignName.accept(visitor);
        if(ArrayAccess!=null) ArrayAccess.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignName!=null) DesignName.traverseTopDown(visitor);
        if(ArrayAccess!=null) ArrayAccess.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignName!=null) DesignName.traverseBottomUp(visitor);
        if(ArrayAccess!=null) ArrayAccess.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        if(DesignName!=null)
            buffer.append(DesignName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArrayAccess!=null)
            buffer.append(ArrayAccess.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
