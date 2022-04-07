package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.NoVarDecl;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class RuleVisitor extends VisitorAdaptor {
	@Override
	public void visit(Program Program) {
		System.out.println("Prepoznata (Program) produkcija");
	}
	
	@Override
	public void visit(NoVarDecl NoVarDecl) {
		System.out.println("Prepoznata (NoVarDecl) produkcija");
	}
}
