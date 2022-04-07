package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrayVarIdentDeclList;
import rs.ac.bg.etf.pp1.ast.FormArrIdentPar;
import rs.ac.bg.etf.pp1.ast.FormArrPar;
import rs.ac.bg.etf.pp1.ast.FormIdentPar;
import rs.ac.bg.etf.pp1.ast.FormPar;
import rs.ac.bg.etf.pp1.ast.VarArrayDecl;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarIdentDeclarationsList;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
		
		@Override
		public void visit(FormPar FormPar) {
			count++;
		}
		
		@Override
		public void visit(FormArrPar FormArrPar) {
			count++;
		}
		
		@Override
		public void visit(FormArrIdentPar FormArrIdentPar) {
			count++;
		}
		
		@Override
		public void visit(FormIdentPar FormIdentPar) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		@Override
		public void visit(VarDeclaration VarDeclaration) {
			count++;
		}
		
		@Override
		public void visit(VarArrayDecl VarArrayDecl) {
			count++;
		}
		
		@Override
		public void visit(ArrayVarIdentDeclList ArrayVarIdentDeclList) {
			count++;
		}
		
		@Override
		public void visit(VarIdentDeclarationsList VarIdentDeclarationsList) {
			count++;
		}
	}
}
