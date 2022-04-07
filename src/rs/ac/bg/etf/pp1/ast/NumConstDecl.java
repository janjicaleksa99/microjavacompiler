// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class NumConstDecl extends ConstDecl {

    private Type Type;
    private String constName;
    private Integer numConst;
    private ConstIdentDeclList ConstIdentDeclList;

    public NumConstDecl (Type Type, String constName, Integer numConst, ConstIdentDeclList ConstIdentDeclList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.constName=constName;
        this.numConst=numConst;
        this.ConstIdentDeclList=ConstIdentDeclList;
        if(ConstIdentDeclList!=null) ConstIdentDeclList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Integer getNumConst() {
        return numConst;
    }

    public void setNumConst(Integer numConst) {
        this.numConst=numConst;
    }

    public ConstIdentDeclList getConstIdentDeclList() {
        return ConstIdentDeclList;
    }

    public void setConstIdentDeclList(ConstIdentDeclList ConstIdentDeclList) {
        this.ConstIdentDeclList=ConstIdentDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstIdentDeclList!=null) ConstIdentDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstIdentDeclList!=null) ConstIdentDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstIdentDeclList!=null) ConstIdentDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+numConst);
        buffer.append("\n");

        if(ConstIdentDeclList!=null)
            buffer.append(ConstIdentDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstDecl]");
        return buffer.toString();
    }
}
