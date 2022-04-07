package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;

import java.util.HashMap;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	private HashMap<String, Integer> labelAddress = new HashMap<String, Integer>();
	private HashMap<String, Integer> gotoStmAddress = new HashMap<String, Integer>();
	private Obj currentDesignator;
	private boolean newarray = false;
	
	public int getMainPc() {
		return mainPc;
	}

	public void setMainPc(int mainPc) {
		this.mainPc = mainPc;
	}
	
	@Override
	public void visit(Program Program) {
		gotoStmAddress.forEach((gotolabel, gotoaddress) -> {
			
			int labeladdr = labelAddress.get(gotolabel);
			Code.put2(gotoaddress + 1, labeladdr - gotoaddress);
 		});
	}
	
	@Override
	public void visit(MethodVoidName MethodVoidName) {
		if ("main".equalsIgnoreCase(MethodVoidName.getMethName())) {
			mainPc = Code.pc;
		}
		MethodVoidName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = MethodVoidName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}
	
	@Override
	public void visit(MethDeclWithFormParsAndRetType MethDeclWithFormParsAndRetType) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(MethodDeclWithRetType MethodDeclWithRetType) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(MethDeclWithFormParsAndRetVoid MethDeclWithFormParsAndRetVoid) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(MethodDeclWithRetVoid MethodDeclWithRetVoid) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(DesignAssignStatement DesignAssignStatement) {
		Obj designator = DesignAssignStatement.getDesignator().obj;
		if (designator.getType().getKind() == Struct.Array && !newarray) {
			if (designator.getType().getElemType().getKind() == Struct.Char)
				Code.put(Code.bastore);
			else
				Code.put(Code.astore);
		}
		else
			Code.store(DesignAssignStatement.getDesignator().obj);
		newarray = false;
	}
	
	@Override
	public void visit(DesignStmtIncr DesignStmtIncr) {
		//Code.load(DesignStmtIncr.getDesignator().obj);
		
		Obj designator = DesignStmtIncr.getDesignator().getDesignName().obj;
		if (designator.getType().getKind() == Struct.Array) {
			Code.put(Code.dup2);
			if (designator.getType().getElemType().getKind() == Struct.Char)
				Code.put(Code.baload);
			else
				Code.put(Code.aload);
		}
		
		Obj incNode = new Obj(Obj.Con, "1", new Struct(Struct.Int));
		incNode.setLevel(0);
		incNode.setAdr(1);
		Code.load(incNode);
		Code.put(Code.add);
		// Designator + 1;
		
		
		if (designator.getType().getKind() == Struct.Array) {
			if (designator.getType().getElemType().getKind() == Struct.Char)
				Code.put(Code.bastore);
			else
				Code.put(Code.astore);
		}
		else
			Code.store(DesignStmtIncr.getDesignator().obj);
		// Designator = Designator + 1;
	}
	
	@Override
	public void visit(DesignStmtDecr DesignStmtDecr) {
		//Code.load(DesignStmtDecr.getDesignator().obj);
		
		Obj designator = DesignStmtDecr.getDesignator().getDesignName().obj;
		if (designator.getType().getKind() == Struct.Array) {
			Code.put(Code.dup2);
			if (designator.getType().getElemType().getKind() == Struct.Char)
				Code.put(Code.baload);
			else
				Code.put(Code.aload);
		}
		
		Obj incNode = new Obj(Obj.Con, "-1", new Struct(Struct.Int));
		incNode.setLevel(0);
		incNode.setAdr(-1);
		Code.load(incNode);
		Code.put(Code.add);
		// Designator + 1;
		
		
		if (designator.getType().getKind() == Struct.Array) {
			if (designator.getType().getElemType().getKind() == Struct.Char)
				Code.put(Code.bastore);
			else
				Code.put(Code.astore);
		}
		else
			Code.store(DesignStmtDecr.getDesignator().obj);
		// Designator = Designator + 1;
	}
	
	@Override
	public void visit(FactorDesignator FactorDesignator) {
		//Code.load(FactorDesignator.obj);
		/*if (currentOperator != '0') {
			switch (currentOperator) {
			case '+':
				Code.put(Code.add);
				break;
			case '-':
				Code.put(Code.sub);
				break;
			case '*':
				Code.put(Code.mul);
				break;
			case '/':
				Code.put(Code.div);
				break;
			case '%':
				Code.put(Code.rem);
				break;
			}
		}*/
	}
	
	@Override
	public void visit(FactorNumConst FactorNumConst) {
		Obj numNode = new Obj(Obj.Con, "const", new Struct(Struct.Int));
		numNode.setLevel(0);
		numNode.setAdr(FactorNumConst.getNumConst());
		Code.load(numNode);
	}
	
	@Override
	public void visit(FactorCharConst FactorCharConst) {
		Obj charNode = new Obj(Obj.Con, "const", new Struct(Struct.Char));
		charNode.setLevel(0);
		charNode.setAdr(FactorCharConst.getCharConst());
		Code.load(charNode);
	}
	
	@Override
	public void visit(FactorBoolConst FactorBoolConst) {
		Obj boolNode = new Obj(Obj.Con, "const", new Struct(Struct.Bool));
		boolNode.setLevel(0);
		boolNode.setAdr(FactorBoolConst.getBool_Const() ? 1 : 0);
		Code.load(boolNode);
	}
	
	@Override
	public void visit(FactorNewExpr FactorNewExpr) {
		this.newarray = true;
		Code.put(Code.newarray);
		Struct type = FactorNewExpr.getType().struct;
		if (type.getKind() == Struct.Char) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}
	
	@Override
	public void visit(FactorExpr FactorExpr) {
		
	}
	
	@Override
	public void visit(LabelDecl LabelDecl) {
		labelAddress.put(LabelDecl.getLabel().getLabel(), Code.pc);
	}
	
	@Override
	public void visit(GotoStmt GotoStmt) {
		gotoStmAddress.put(GotoStmt.getLabel().getLabel(), Code.pc);
		Code.putJump(0);
	}
	
	@Override
	public void visit(ReadStmt ReadStmt) {
		if (ReadStmt.getDesignator().obj.getType().getKind() == Struct.Array) {
			if (ReadStmt.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) {
				Code.put(Code.bread);
				Code.put(Code.bastore);
			}
			else {
				Code.put(Code.read);
				Code.put(Code.astore);
			}
		}
		else
		if (ReadStmt.getDesignator().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
			Code.store(ReadStmt.getDesignator().obj);
		}
		else {
			Code.put(Code.read);
			Code.store(ReadStmt.getDesignator().obj);
		}
	}
	
	@Override
	public void visit(PrintExprNumConstStmt PrintExprNumConstStmt) {
		Obj numConst = new Obj(Obj.Con, "const", new Struct(Struct.Int));
		numConst.setLevel(0);
		numConst.setAdr(PrintExprNumConstStmt.getNumConst());
		Code.load(numConst);
		if (PrintExprNumConstStmt.getExpression().obj.getType().getKind() == Struct.Array) {
			if (PrintExprNumConstStmt.getExpression().obj.getType().getElemType().getKind()
					== Struct.Char) {
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.print);
			}
		}
		else
		if (PrintExprNumConstStmt.getExpression().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(PrintExprStmt PrintExprStmt) {
		Obj numConst = new Obj(Obj.Con, "const", new Struct(Struct.Int));
		numConst.setLevel(0);
		if (PrintExprStmt.getExpression().obj.getType().getKind() == Struct.Array) {
			if (PrintExprStmt.getExpression().obj.getType().getElemType().getKind() == Struct.Char) {
				numConst.setAdr(2);
				Code.load(numConst);
				Code.put(Code.bprint);
			}
			else {
				numConst.setAdr(5);
				Code.load(numConst);
				Code.put(Code.print);
			}
		}
		else
		if (PrintExprStmt.getExpression().obj.getType().getKind() == Struct.Char) {
			numConst.setAdr(2);
			Code.load(numConst);
			Code.put(Code.bprint);
		}
		else {
			numConst.setAdr(5);
			Code.load(numConst);
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(DesignName DesignName) {
		SyntaxNode parent = DesignName.getParent();
		currentDesignator = DesignName.obj;
		if (DesignAssignStatement.class != parent.getParent().getClass()
				|| DesignName.obj.getType().getKind() == Struct.Array) {
			Code.load(currentDesignator);
		}
	}
	
	@Override
	public void visit(ArrayAcc ArrayAcc) {
		//Code.load(currentDesignator);
		//Struct struct = ArrayAcc.obj.getType();
		//Obj elemNode = new Obj(Obj.Elem, "elem", struct);
		//Code.load(elemNode);
		if (ArrayAcc.getParent().getParent().getClass() != DesignAssignStatement.class
				&& ArrayAcc.getParent().getParent().getClass() != DesignStmtIncr.class
				&&  ArrayAcc.getParent().getParent().getClass() != DesignStmtDecr.class
				&& ArrayAcc.getParent().getParent().getClass() != ReadStmt.class) {
			if (ArrayAcc.obj.getType().getKind() == Struct.Array) {
				if (ArrayAcc.obj.getType().getElemType().getKind() == Struct.Char)
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
			}
			else
			if (ArrayAcc.obj.getType().getKind() == Struct.Char)
				Code.put(Code.baload);
			else
				Code.put(Code.aload);
		}
	}
	
	@Override
    public void visit(TermNegative TermNegative) {
        Code.put(Code.neg);
    }
	
	@Override
	public void visit(AddOpExpression AddOpExpression) {
		Addop addop = AddOpExpression.getAddop();
        if (addop instanceof Additon)
            Code.put(Code.add);
        else if (addop instanceof Substraction)
            Code.put(Code.sub);
	}
	
	@Override
	public void visit(MulopFactors MulopFactors) {
		Mulop mulop = MulopFactors.getMulop();
		if (mulop instanceof Multiplication)
			Code.put(Code.mul);
		else if (mulop instanceof Division)
			Code.put(Code.div);
		else if (mulop instanceof Mod)
			Code.put(Code.rem);
	}
}