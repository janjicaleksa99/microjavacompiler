package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	
	
	boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	Struct currentType = null;
	Obj currentClassObj = null;
	Struct currentClassStruct = null;
	Obj currentMethod = null;
	Obj currentIdentNode = null;
	List<String> declaredLabels = new ArrayList<String>();
	List<String> gotoLabels = new ArrayList<String>();
	int nVars;
	boolean arrayAccess = false;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	@Override
	public void visit(Program Program) {
		// System.out.println("Prepoznata (Program) produkcija");
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(Program.getProgramName().obj);
		Tab.closeScope();
		for (String gotolabel : gotoLabels) {
			boolean labelDeclared = false;
			for (String label : declaredLabels) {
				if (gotolabel.equals(label))
					labelDeclared = true;
			}
			if (!labelDeclared)
				report_error("Labela " + gotolabel + " nije deklarisana", Program);
		}
	}
	
	private void addBoolType() {
		Struct bool = new Struct(Struct.Bool);
		Tab.insert(Obj.Type, "bool", bool);
	}
	
	@Override
	public void visit(ProgramName ProgramName) {
		addBoolType();
		ProgramName.obj = Tab.insert(Obj.Prog, ProgramName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(VarDeclaration VarDeclaration) {
		//System.out.println("Prepoznata (VarDeclaration) produkcija");
		Obj varNode = Tab.find(VarDeclaration.getVarName());
		if (varNode != Tab.noObj) {
			report_error("Greska na " + VarDeclaration.getLine() + ": (" + VarDeclaration.getVarName() + ") vec deklarisano",
					VarDeclaration);
		}
		else {
			if (currentClassStruct != null) {
				Tab.insert(Obj.Fld, VarDeclaration.getVarName(),
						VarDeclaration.getType().struct);
			}
			else {
				varNode = Tab.insert(Obj.Var, VarDeclaration.getVarName(),
						VarDeclaration.getType().struct);
			}
		}
	}
	
	@Override
	public void visit(VarArrayDecl VarArrayDecl) {
		//System.out.println("Prepoznata (VarArrayDecl) produkcija");
		Obj varNode = Tab.find(VarArrayDecl.getVarName());
		if (varNode != Tab.noObj) {
			report_error("Greska na " + VarArrayDecl.getLine() + ": (" + VarArrayDecl.getVarName() + ") vec deklarisano",
					VarArrayDecl);
		}
		else {
			if (currentClassStruct != null) {
				Struct arrayStruct = new Struct(Struct.Array, VarArrayDecl.getType().struct);
				varNode = Tab.insert(Obj.Fld, VarArrayDecl.getVarName(),
						arrayStruct);
			}
			else {
				Struct arrayStruct = new Struct(Struct.Array, VarArrayDecl.getType().struct);
				varNode = Tab.insert(Obj.Var, VarArrayDecl.getVarName(),
						arrayStruct);
			}
		}
	}
	
	@Override
	public void visit(VarIdentDeclarationsList VarIdentDeclarationsList) {
		//System.out.println("Prepoznata (VarIdentDeclarationsList) produkcija");
		Obj varNode = Tab.find(VarIdentDeclarationsList.getVarName());
		if (varNode != Tab.noObj) {
			report_error("Greska na " + VarIdentDeclarationsList.getLine() + ": (" + VarIdentDeclarationsList.getVarName() + ") vec deklarisano",
					VarIdentDeclarationsList);
		}
		else {
			if (currentClassStruct != null) {
				Tab.insert(Obj.Fld, VarIdentDeclarationsList.getVarName(),
						currentType);
			}
			else {
				Tab.insert(Obj.Var, VarIdentDeclarationsList.getVarName(),
						currentType);
			}
		}
	}
	
	@Override
	public void visit(ArrayVarIdentDeclList ArrayVarIdentDeclList) {
		//System.out.println("Prepoznata (ArrayVarIdentDeclList) produkcija");
		Obj varNode = Tab.find(ArrayVarIdentDeclList.getVarName());
		if (varNode != Tab.noObj) {
			report_error("Greska na " + ArrayVarIdentDeclList.getLine() + ": (" + ArrayVarIdentDeclList.getVarName() + ") vec deklarisano",
					ArrayVarIdentDeclList);
		}
		else {
			if (currentClassStruct != null) {
				Struct arrayStruct = new Struct(Struct.Array, currentType);
				Tab.insert(Obj.Fld, ArrayVarIdentDeclList.getVarName(),
						arrayStruct);
			}
			else {
				Struct arrayStruct = new Struct(Struct.Array, currentType);
				Tab.insert(Obj.Var, ArrayVarIdentDeclList.getVarName(),
						arrayStruct);
			}
		}
	}
	
	@Override
	public void visit(ClassName ClassName) {
		currentClassStruct = new Struct(Struct.Class);
		ClassName.obj = Tab.insert(Obj.Type, ClassName.getClassName(), currentClassStruct);
		Tab.openScope();
		report_info("Obradjuje se klasa " + ClassName.getClassName(), ClassName);
	}
	
	@Override
	public void visit(ClassDecl ClassDecl) {
		Tab.chainLocalSymbols(currentClassStruct);
		Tab.closeScope();	
		currentClassStruct = null;
	}
	
	@Override
	public void visit(MethodTypeName MethodTypeName) {
		currentMethod = Tab.insert(Obj.Meth, MethodTypeName.getMethName(),
				MethodTypeName.getType().struct);
		MethodTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + MethodTypeName.getMethName(), MethodTypeName);
	}
	
	@Override
	public void visit(MethodVoidName MethodVoidName) {
		currentMethod = Tab.insert(Obj.Meth, MethodVoidName.getMethName(),
				new Struct(Struct.None));
		MethodVoidName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + MethodVoidName.getMethName(), MethodVoidName);
	}
	
	@Override
	public void visit(MethDeclWithFormParsAndRetType MethDeclWithFormParsAndRetType) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	@Override
	public void visit(MethodDeclWithRetType MethodDeclWithRetType) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	@Override
	public void visit(MethDeclWithFormParsAndRetVoid MethDeclWithFormParsAndRetVoid) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	@Override
	public void visit(MethodDeclWithRetVoid MethodDeclWithRetVoid) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	@Override
	public void visit(ConstrName ConstrName) {
		ConstrName.obj = Tab.insert(Obj.Meth, ConstrName.getConstrName(),
				new Struct(Struct.None));
		Tab.openScope();
	}
	
	@Override
	public void visit(ConstructorDecl ConstructorDecl) {
		Tab.chainLocalSymbols(ConstructorDecl.getConstrName().obj);
		Tab.closeScope();
	}
	
	@Override
	public void visit(FormPar FormPar) {
		Tab.insert(Obj.Var, FormPar.getParName(), FormPar.getType().struct);
	}
	
	@Override
	public void visit(FormArrPar FormArrPar) {
		Struct arrayStruct = new Struct(Struct.Array, FormArrPar.getType().struct);
		Obj varNode = Tab.insert(Obj.Var, FormArrPar.getParName(),
				arrayStruct);
	}
	
	@Override
	public void visit(FormArrIdentPar FormArrIdentPar) {
		Struct arrayStruct = new Struct(Struct.Array, FormArrIdentPar.getType().struct);
		Tab.insert(Obj.Var, FormArrIdentPar.getParName(), arrayStruct);
	}
	
	@Override
	public void visit(FormIdentPar FormIdentPar) {
		Tab.insert(Obj.Var, FormIdentPar.getParName(), FormIdentPar.getType().struct);
	}
	
	@Override
	public void visit(NumConstDecl NumConstDecl) {
		Obj constNode = Tab.find(NumConstDecl.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + NumConstDecl.getLine() + ": (" + NumConstDecl.getConstName() + ") vec deklarisano",
					NumConstDecl);
		}
		else {
			constNode = Tab.insert(Obj.Con, NumConstDecl.getConstName(), NumConstDecl.getType().struct);
			constNode.setAdr(NumConstDecl.getNumConst());
		}
	}
	
	@Override
	public void visit(CharConstDecl CharConstDecl) {
		Obj constNode = Tab.find(CharConstDecl.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + CharConstDecl.getLine() + ": (" + CharConstDecl.getConstName() + ") vec deklarisano",
					CharConstDecl);
		}
		else {
			constNode = Tab.insert(Obj.Con, CharConstDecl.getConstName(), CharConstDecl.getType().struct);
			constNode.setAdr(CharConstDecl.getCharConst());
		}
	}
	
	@Override
	public void visit(BoolConstDecl BoolConstDecl) {
		Obj constNode = Tab.find(BoolConstDecl.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + BoolConstDecl.getLine() + ": (" + BoolConstDecl.getConstName() + ") vec deklarisano",
					BoolConstDecl);
		}
		else {
			constNode = Tab.insert(Obj.Con, BoolConstDecl.getConstName(), BoolConstDecl.getType().struct);
			constNode.setAdr(BoolConstDecl.getBool_Const() ? 1 : 0);
		}
	}
	
	@Override
	public void visit(NumConstIdentDeclList NumConstIdentDeclList) {
		Obj constNode = Tab.find(NumConstIdentDeclList.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + NumConstIdentDeclList.getLine() + ": (" + NumConstIdentDeclList.getConstName() + ") vec deklarisano",
					NumConstIdentDeclList);
		}
		else {
			constNode = Tab.insert(Obj.Con, NumConstIdentDeclList.getConstName(), currentType);
			constNode.setAdr(NumConstIdentDeclList.getNumConst());
		}
	}
	
	@Override
	public void visit(CharConstIdentDeclList CharConstIdentDeclList) {
		Obj constNode = Tab.find(CharConstIdentDeclList.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + CharConstIdentDeclList.getLine() + ": (" + CharConstIdentDeclList.getConstName() + ") vec deklarisano",
					CharConstIdentDeclList);
		}
		else {
			constNode = Tab.insert(Obj.Con, CharConstIdentDeclList.getConstName(), currentType);
			constNode.setAdr(CharConstIdentDeclList.getCharConst());
		}
	}
	
	@Override
	public void visit(BoolConstIdentDeclList BoolConstIdentDeclList) {
		Obj constNode = Tab.find(BoolConstIdentDeclList.getConstName());
		if (constNode != Tab.noObj) {
			report_error("Greska na " + BoolConstIdentDeclList.getLine() + ": (" + BoolConstIdentDeclList.getConstName() + ") vec deklarisano",
					BoolConstIdentDeclList);
		}
		else {
			constNode = Tab.insert(Obj.Con, BoolConstIdentDeclList.getConstName(), currentType);
			constNode.setAdr(BoolConstIdentDeclList.getBool_Const() ? 1 : 0);
		}
	}
	
	@Override
	public void visit(DesignStmtIncr DesignStmtIncr) {
		Obj designObj = DesignStmtIncr.getDesignator().obj;
		if (designObj.getKind() == Obj.Con)
			report_error("Greska na " + DesignStmtIncr.getLine() +
					" (" + designObj.getName() + ")" + " je konstanta", null);
		if (designObj.getType().getKind() == Struct.Array) {
			if (designObj.getType().getElemType().getKind() != Struct.Int || !arrayAccess)
				report_error("Greska na " + DesignStmtIncr.getLine() +
						" (" + designObj.getName() + ")" + " nije celobrojni tip", null);
			arrayAccess = false;
		}
		else {
			if (designObj.getType().getKind() != Struct.Int)
				report_error("Greska na " + DesignStmtIncr.getLine() +
						" (" + designObj.getName() + ")" + " nije celobrojni tip", null);
		}
	}
	
	@Override
	public void visit(DesignStmtDecr DesignStmtDecr) {
		Obj designObj = DesignStmtDecr.getDesignator().obj;
		if (designObj.getKind() == Obj.Con)
			report_error("Greska na " + DesignStmtDecr.getLine() +
					" (" + designObj.getName() + ")" + " je konstanta", null);
		if (designObj.getType().getKind() == Struct.Array) {
			if (designObj.getType().getElemType().getKind() != Struct.Int || !arrayAccess)
				report_error("Greska na " + DesignStmtDecr.getLine() +
						" (" + designObj.getName() + ")" + " nije celobrojni tip", null);
			arrayAccess = false;
		}
		else {
			if (designObj.getType().getKind() != Struct.Int)
				report_error("Greska na " + DesignStmtDecr.getLine() +
						" (" + designObj.getName() + ")" + " nije celobrojni tip", null);
		}
	}
	
	@Override
	public void visit(DesignAssignStatement DesignAssignStatement) {
		Obj designator = DesignAssignStatement.getDesignator().obj;
		Obj expression = DesignAssignStatement.getExpression().obj;
		if (designator.getKind() == Obj.Con) {
			report_error("Greska na " + DesignAssignStatement.getLine() +
					" (" + designator.getName() + ")" + " je konstanta", null);
		}
		if (designator.getType().getKind() != expression.getType().getKind() && !arrayAccess) {
			report_error("Tip izraza na " + DesignAssignStatement.getLine() +
					" nije jednak tipu designatora", null);
			arrayAccess = false;
		}
	}
	
	@Override
	public void visit(PrintExprNumConstStmt PrintExprNumConstStmt) {
		Obj expression = PrintExprNumConstStmt.getExpression().obj;
		if (expression.getType().getKind() == Struct.Array) {
			if (expression.getType().getElemType().getKind() != Struct.Int &&
					expression.getType().getElemType().getKind() != Struct.Char &&
					expression.getType().getElemType().getKind() != Struct.Bool) {
				report_error("Greska: Izraz u print iskazu mora biti int, char ili bool tipa", PrintExprNumConstStmt);
			}
		}
		else
		if (expression.getType().getKind() != Struct.Int &&
				expression.getType().getKind() != Struct.Char &&
				expression.getType().getKind() != Struct.Bool) {
			report_error("Greska: Izraz u print iskazu mora biti int, char ili bool tipa", PrintExprNumConstStmt);
		}
	}
	
	@Override
	public void visit(PrintExprStmt PrintExprStmt) {
		Obj expression = PrintExprStmt.getExpression().obj;
		if (expression.getType().getKind() == Struct.Array) {
			if (expression.getType().getElemType().getKind() != Struct.Int &&
					expression.getType().getElemType().getKind() != Struct.Char &&
					expression.getType().getElemType().getKind() != Struct.Bool) {
				report_error("Greska: Izraz u print iskazu mora biti int, char ili bool tipa", PrintExprStmt);
			}
		}
		else
		if (expression.getType().getKind() != Struct.Int &&
				expression.getType().getKind() != Struct.Char &&
				expression.getType().getKind() != Struct.Bool) {
			report_error("Greska: Izraz u print iskazu mora biti int, char ili bool tipa", PrintExprStmt);
		}
	}
	
	@Override
	public void visit(ReadStmt ReadStmt) {
		Obj designator = ReadStmt.getDesignator().obj;
		if (designator.getKind() == Obj.Type) {
			if (designator.getType().getKind() == Struct.Array) {
				if (designator.getType().getElemType().getKind() != Struct.Int &&
						designator.getType().getElemType().getKind() != Struct.Char &&
						designator.getType().getElemType().getKind() != Struct.Bool) {
					report_error("Greska: Promenljiva u read iskazu mora biti int, char ili bool tipa", ReadStmt);
				}
			}
			else
			if (designator.getType().getKind() != Struct.Int &&
					designator.getType().getKind() != Struct.Char &&
					designator.getType().getKind() != Struct.Bool) {
				report_error("Greska: Promenljiva u read iskazu mora biti int, char ili bool tipa", ReadStmt);
			}
		}
		else {
			if (designator.getKind() == Obj.Con) {
				report_error("Greska: " + designator.getName() + " je konstanta i ne moze da se procita", ReadStmt);
			}
			if (designator.getType().getKind() == Struct.Array) {
				if (designator.getType().getElemType().getKind() != Struct.Int &&
						designator.getType().getElemType().getKind() != Struct.Char &&
						designator.getType().getElemType().getKind() != Struct.Bool) {
					report_error("Greska: Promenljiva u read iskazu mora biti int, char ili bool tipa", ReadStmt);
				}
			}
			else
			if (designator.getType().getKind() != Struct.Int &&
					designator.getType().getKind() != Struct.Char &&
					designator.getType().getKind() != Struct.Bool) {
				report_error("Greska: Promenljiva u read iskazu mora biti int, char ili bool tipa", ReadStmt);
			}
		}
	}
	
	@Override
	public void visit(LabelDecl LabelDecl) {
		String label = LabelDecl.getLabel().getLabel();
		boolean labelDeclared = false;
		for (String labelDecl : declaredLabels) {
			if (labelDecl.equals(label))
				labelDeclared = true;
		}
		if (labelDeclared)
			report_error("Labela " + label + " je vec deklarisana", null);
		else
			declaredLabels.add(label);
	}
	
	@Override
	public void visit(GotoStmt GotoStmt) {
		String label = GotoStmt.getLabel().getLabel();
		gotoLabels.add(label);
	}
	
	@Override
	public void visit(FactorDesignator FactorDesignator) {
		FactorDesignator.obj = FactorDesignator.getDesignator().obj;
	}
	
	@Override
	public void visit(FactorNumConst FactorNumConst) {
		FactorNumConst.obj = Tab.find("int");
	}
	
	@Override
	public void visit(FactorCharConst FactorCharConst) {
		FactorCharConst.obj = Tab.find("char");
	}
	
	@Override
	public void visit(FactorBoolConst FactorBoolConst) {
		FactorBoolConst.obj = Tab.find("bool");
	}
	
	@Override
	public void visit(FactorNewExpr FactorNewExpr) {
		Obj objNode = Tab.find(FactorNewExpr.getType().getTypeName());
		Struct arrayStruct = new Struct(Struct.Array, objNode.getType());
		Obj arrayNode = new Obj(Obj.Type, "array", arrayStruct);
		FactorNewExpr.obj = arrayNode;
	}
	
	@Override
	public void visit(FactorExpr FactorExpr) {
		FactorExpr.obj = FactorExpr.getExpression().obj;
	}
	
	@Override
	public void visit(MulopFactors MulopFactors) {
		if (MulopFactors.getParent() instanceof Term) {
			Term term = (Term) MulopFactors.getParent();
			if (term.getFactor().obj.getType().getKind() == Struct.Array) {
				if (term.getFactor().obj.getType().getElemType() != Tab.intType) {
					report_error("Tip izraza u indeksu nije celobrojnog tipa", MulopFactors);
				}
			}
			else
			if (term.getFactor().obj.getType() != Tab.intType)
				report_error("Greska: Mnozenje i deljenje je moguce samo sa int tipom", MulopFactors);
		}
		
		if (MulopFactors.getFactor().obj.getType().getKind() == Struct.Array) {
			if (MulopFactors.getFactor().obj.getType().getElemType() != Tab.intType) {
				report_error("Tip izraza u indeksu nije celobrojnog tipa", MulopFactors);
			}
		}
		else
		if (MulopFactors.getFactor().obj.getType() != Tab.intType) {
			report_error("Greska: Mnozenje i deljenje je moguce samo sa int tipom", MulopFactors);
		}
		MulopFactors.obj = MulopFactors.getFactor().obj;
	}
	
	@Override
	public void visit(Term Term) {
		/*if (Term.getMulopFactorList().obj.getType() != Tab.intType) {
			//  Treba ispisati gresku
		}*/
		
		if (Term.getFactor().obj.getType() != Tab.intType) {
			//report_error("Greska: Mnozenje i deljenje je moguce samo sa int tipom", Term);
		}
		Term.obj = Term.getFactor().obj;
	}
	
	@Override
	public void visit(SingleTerm SingleTerm) {
		SingleTerm.obj = SingleTerm.getFirstTerm().obj;
	}
	
	@Override
	public void visit(AddOpExpression AddOpExpression) {
		if (AddOpExpression.getExpression().obj.getType() != Tab.intType) {
			report_error("Greska: Sabiranje i oduzimanje je moguce samo sa int tipom", AddOpExpression);
		}
		if (AddOpExpression.getTerm().obj.getType() != Tab.intType) {
			report_error("Greska: Sabiranje i oduzimanje je moguce samo sa int tipom", AddOpExpression);
		}
		AddOpExpression.obj = AddOpExpression.getTerm().obj;
	}
	
	@Override
	public void visit(TermPositive TermPositive) {
		TermPositive.obj = TermPositive.getTerm().obj;
	}
	
	@Override
	public void visit(TermNegative TermNegative) {
		if (TermNegative.getTerm().obj.getType() != Tab.intType) {
			report_error("Greska: Sabiranje i oduzimanje je moguce samo sa int tipom", TermNegative);
		}
		TermNegative.obj = TermNegative.getTerm().obj;
	}
	
	@Override
	public void visit(Designator Designator) {
		if (Designator.getArrayAccess().obj == null)
			Designator.obj = Designator.getDesignName().obj;
		else
			Designator.obj = Designator.getArrayAccess().obj;
	}
	
	@Override
	public void visit(DesignName DesignName) {
		Obj identNode = Tab.find(DesignName.getDesignName());
		if (identNode.getType().getKind() == Struct.Array)
			currentIdentNode = identNode;
		if (identNode == Tab.noObj) {
			report_error("Greska na " + DesignName.getLine() + " (" + DesignName.getDesignName() + ") nije nadjeno", DesignName);
		}
		DesignName.obj = identNode;
		
		String objKind = null;
		switch (identNode.getKind()) {
		case 0:
			objKind = "Con";
			break;
		case 1:
			objKind = "Var";
			break;
		}
		
		String structKind = "";
		switch (identNode.getType().getKind()) {
		case 1:
			structKind = "int";
			break;
		case 2:
			structKind = "char";
			break;
		case 3:
			structKind = "Arr";
			break;
		case 5:
			structKind = "bool";
			break;
		}
		
		String elemKind = "";
		if (structKind.equals("Arr")) {
			switch (identNode.getType().getElemType().getKind()) {
			case 1:
				elemKind = "int";
				break;
			case 2:
				elemKind = "char";
				break;
			case 3:
				elemKind = "Arr";
				break;
			case 5:
				elemKind = "bool";
				break;
			}
		}
		
		if (identNode != Tab.noObj)
			report_info("Pretraga na " + DesignName.getLine() + "(" + identNode.getName() + ") " +
					"nadjeno " + objKind + " " + identNode.getName() + ": " + structKind +
					(structKind.equals("Arr") ? " of " + elemKind : "") + ", " + identNode.getAdr() +
					", " + identNode.getLevel()
					, null);
	}
	
	@Override
	public void visit(ArrayAcc ArrayAcc) {
		if (ArrayAcc.getExpression().obj.getType().getKind() == Struct.Array) {
			if (ArrayAcc.getExpression().obj.getType().getElemType() != Tab.intType) {
				report_error("Tip izraza u indeksu nije celobrojnog tipa", ArrayAcc);
			}
		}
		else
		if (ArrayAcc.getExpression().obj.getType() != Tab.intType) {
			report_error("Tip izraza u indeksu nije celobrojnog tipa", ArrayAcc);
		}
		Obj typeNode = null;
		if (currentIdentNode.getType().getElemType().getKind() == Struct.Int)
			typeNode = Tab.find("int");
		if (currentIdentNode.getType().getElemType().getKind() == Struct.Char)
			typeNode = Tab.find("char");
		if (currentIdentNode.getType().getElemType().getKind() == Struct.Bool)
			typeNode = Tab.find("bool");
		ArrayAcc.obj = currentIdentNode;
		arrayAccess = true;
	}
	
	@Override
	public void visit(Type Type) {
		Obj typeNode = Tab.find(Type.getTypeName());
		if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip (" + Type.getTypeName() + ") u tabeli simbola! ", Type);
    		Type.struct = Tab.noType;
    	}
		else{
    		currentType = typeNode.getType();
    		if(Obj.Type == typeNode.getKind()){
    			Type.struct = typeNode.getType();
    		}
    		else{
    			report_error("Greska: Ime (" + Type.getTypeName() + ") ne predstavlja tip!", Type);
    			Type.struct = Tab.noType;
    		}
    	}
	}
}
