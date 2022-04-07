package rs.ac.bg.etf.pp1;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {
	
	private static Program program;
	private static Logger log;
	public static boolean parsingSuccesful = false;
	
	public static void tsdump() {
		Tab.dump();
	}
	
	public static void setRoot(Program prog) {
		program = prog;
	}
	
	public static void setLogger(Logger log) {
		Compiler.log = log;
	}
	
	public static void main(String[] args) {
		try {
			MJParserTest.main(args);
			
			Tab.init();
			// ispis sintaksnog stabla
			log.info(program.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			//RuleVisitor v = new RuleVisitor();
			SemanticPass sp = new SemanticPass();
			program.traverseBottomUp(sp); 
			
			//log.info("Print count calls = " + v.printCallCount);
			
			//log.info("Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			tsdump();
			
			if (parsingSuccesful && !sp.errorDetected) {
				log.info("Sintaksna i semanticka analiza je uspesno zavrsena!");
				
				File objFile = new File(args[1]);
				if (objFile.exists())
					objFile.delete();
				
				CodeGenerator cg = new CodeGenerator();
				program.traverseBottomUp(cg);
				Code.dataSize = sp.nVars;
				Code.mainPc = cg.getMainPc();
				Code.write(new FileOutputStream(objFile));
			}
			else {
				log.info("Sintaksna i semanticka analiza nije uspesno zavrsena!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
