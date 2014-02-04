import java.util.StringTokenizer;

import Operators.operatori;
import ParseTree.Tree;
import VisitorPattern.Visitor;
/**
 * Singura metoda a clasei "eval" imparte expresia primita ca parametru
 * si insereaza intr-un arbore de parsare operatorii functiile si numerele
 * in functie de paranteze.
 * @author iPatulea
 *
 */


public class ExpressionParser {

	public static float eval(String expression) throws SyntacticException, EvaluatorException{
		// do the magic
		Tree tree = new Tree();
		/*
		 * bras - numarul de paranteze deschise
		 * wasbraL - numarul de paranteze inchise recent
		 * wasFunc - elementul trecut a fost o functie
		 * wasOp - elementul tercut a fost un operator
		 * isFirst - este primul element si ajuta la parantezele de inceput
		 * fisrtbras - numarul de paranteze de inceput
		 */
		int bras=0,wasBraL=0,wasFunc=0,wasOp=0,isFirst=1,firstbras = 0;
		StringTokenizer i = new StringTokenizer(expression);
		while(i.hasMoreElements()){
			
			String actual = i.nextToken();
			
			if(actual.equals(operatori.plus) == true){
				if(wasFunc == 1 || wasOp == 1 || isFirst == 1)
					throw new SyntacticException();
				//Daca inaintea lui s-a inchis o paranteza, o inchid si in arbore fara vreun efect deoarece "plus" are prioritate mica.
				if(wasBraL > 0){
					tree.closeBra(bras+wasBraL,wasBraL,firstbras,tree.getRoot());
					//Calculez daca a fost inchisa o paranteza de inceput.
					if(firstbras > 0 && firstbras - bras > 0)
						firstbras -= (firstbras - bras);
				}
				//Inserez "plus" dupa "bras" paranteze avand "firstbras" paranteze de inceput.
				tree.insertPlus(bras,firstbras,tree.getRoot());
				isFirst=0;
				wasOp=1;
				wasBraL=0;
				wasFunc=0;
				continue;
			}
			
			if(actual.equals(operatori.minus) == true){
				if(wasFunc == 1 || wasOp == 1)
					throw new SyntacticException();
				if(wasBraL > 0){
					tree.closeBra(bras+wasBraL,wasBraL,firstbras,tree.getRoot());
					if(firstbras > 0 && firstbras - bras > 0)
						firstbras -= (firstbras - bras);
				}
				tree.insertMinus(bras,firstbras,tree.getRoot());
				isFirst=0;
				wasOp=1;
				wasBraL=0;
				wasFunc=0;
				continue;
			}
			
			if(actual.equals(operatori.impartit) == true){
				if(wasFunc == 1 || wasOp == 1 || isFirst == 1)
					throw new SyntacticException();
				//Inserez "impartit" dupa "bras" paranteze, avand "wasBraL" paranteze inchise inainte si "firstbras" paranteze de inceput.
				tree.insertImpartit(bras+wasBraL,wasBraL,firstbras,tree.getRoot());
				//Calculez daca a fost inchisa o paranteza de inceput.
				if(firstbras > 0 && firstbras - bras > 0)
					firstbras -= (firstbras- bras);
				isFirst=0;
				wasOp=1;
				wasBraL=0;
				wasFunc=0;
				continue;
			}
			
			if(actual.equals(operatori.inmultit) == true){
				if(wasFunc == 1 || wasOp == 1 || isFirst == 1)
					throw new SyntacticException();
				tree.insertInmultit(bras+wasBraL,wasBraL,firstbras,tree.getRoot());
				if(firstbras > 0 && firstbras - bras > 0)	
					firstbras -= (firstbras - bras);
				isFirst=0;
				wasOp=1;
				wasBraL=0;
				wasFunc=0;
				continue;
			}
			
			if(actual.equals(operatori.putere) == true){
				if(wasFunc == 1 || wasOp == 1 || isFirst == 1)
					throw new SyntacticException();
				tree.insertPutere(bras+wasBraL,wasBraL,firstbras,tree.getRoot());
				if(firstbras > 0 && firstbras - bras > 0)
					firstbras -= (firstbras - bras);
				isFirst=0;
				wasOp=1;
				wasBraL=0;
				wasFunc=0;
				continue;
			}
			
			if(actual.equals(operatori.log) == true){
				tree.insertFunc(operatori.log,tree.getRoot());
				isFirst=0;
				wasOp=0;
				wasFunc=1;
				wasBraL=0;
				continue;
			}
			
			if(actual.equals(operatori.sqrt) == true){
				tree.insertFunc(operatori.sqrt,tree.getRoot());
				isFirst=0;
				wasOp=0;
				wasFunc=1;
				wasBraL=0;
				continue;
			}
			
			if(actual.equals(operatori.cos) == true){
				tree.insertFunc(operatori.cos,tree.getRoot());
				isFirst=0;
				wasOp=0;
				wasFunc=1;
				wasBraL=0;
				continue;
			}
			
			if(actual.equals(operatori.sin) == true){
				tree.insertFunc(operatori.sin,tree.getRoot());
				isFirst=0;
				wasOp=0;
				wasFunc=1;
				wasBraL=0;
				continue;
			}
			
			if(actual.equals(operatori.braR) == true){
				if(isFirst == 1)
					firstbras++;
				bras++;
				if(isFirst != 1)
					tree.openBra(bras ,tree.getRoot());
				wasOp=0;
				wasFunc=0;
				wasBraL=0;
				continue;
			}
			
			if(actual.equals(operatori.braL) == true){
				wasBraL++;
				isFirst=0;
				wasOp=0;
				wasFunc=0;
				bras--;
				if(bras < 0)
					throw new SyntacticException();
				continue;
			}
			//Daca nu este parsabil stringul atunci arunc exceptie altfel inseamna ca e numar si il inserez.
			try{
				Double.parseDouble(actual);
			}
			catch(Exception e){
				throw new SyntacticException();
			}
			
			tree.insertValue(actual,tree.getRoot());
			isFirst=0;
			wasOp=0;
			wasBraL=0;
			wasFunc=0;
		}
		
		if (bras > 0)
			throw new SyntacticException();
		
		
		Visitor v = new CalculatorVisitor();
		
		try{
		tree.getRoot().accept(v);
		}
		catch(Exception e){
			throw new EvaluatorException();
		}
		
		return (float)tree.getRoot().getRez();
	}
	
}
