// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class ArrayVarIdentDeclList extends VarIdentDeclList {

    private VarIdentDeclList VarIdentDeclList;
    private String varName;

    public ArrayVarIdentDeclList (VarIdentDeclList VarIdentDeclList, String varName) {
        this.VarIdentDeclList=VarIdentDeclList;
        if(VarIdentDeclList!=null) VarIdentDeclList.setParent(this);
        this.varName=varName;
    }

    public VarIdentDeclList getVarIdentDeclList() {
        return VarIdentDeclList;
    }

    public void setVarIdentDeclList(VarIdentDeclList VarIdentDeclList) {
        this.VarIdentDeclList=VarIdentDeclList;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarIdentDeclList!=null) VarIdentDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarIdentDeclList!=null) VarIdentDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarIdentDeclList!=null) VarIdentDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayVarIdentDeclList(\n");

        if(VarIdentDeclList!=null)
            buffer.append(VarIdentDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVarIdentDeclList]");
        return buffer.toString();
    }
}
