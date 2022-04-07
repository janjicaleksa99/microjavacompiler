// generated with ast extension for cup
// version 0.8
// 10/1/2022 23:22:22


package rs.ac.bg.etf.pp1.ast;

public class NumConstIdentDeclList extends ConstIdentDeclList {

    private ConstIdentDeclList ConstIdentDeclList;
    private String constName;
    private Integer numConst;

    public NumConstIdentDeclList (ConstIdentDeclList ConstIdentDeclList, String constName, Integer numConst) {
        this.ConstIdentDeclList=ConstIdentDeclList;
        if(ConstIdentDeclList!=null) ConstIdentDeclList.setParent(this);
        this.constName=constName;
        this.numConst=numConst;
    }

    public ConstIdentDeclList getConstIdentDeclList() {
        return ConstIdentDeclList;
    }

    public void setConstIdentDeclList(ConstIdentDeclList ConstIdentDeclList) {
        this.ConstIdentDeclList=ConstIdentDeclList;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstIdentDeclList!=null) ConstIdentDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstIdentDeclList!=null) ConstIdentDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstIdentDeclList!=null) ConstIdentDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstIdentDeclList(\n");

        if(ConstIdentDeclList!=null)
            buffer.append(ConstIdentDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+numConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstIdentDeclList]");
        return buffer.toString();
    }
}
