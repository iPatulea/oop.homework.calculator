import Operators.operatori;
import ParseTree.Node;
import VisitorPattern.Visitor;
/**
 * Vizitator care parurge arborele de parsare si returneaza rezultatul.
 * @author iPatulea
 *
 */
public class CalculatorVisitor implements Visitor{

	@Override
	public double visit(Node n) throws EvaluatorException{
		/*
		 * Folosesc o metoda recursiva de parcurgere copilStanga:radacina:copilDreapta
		 * Retin rezultatul subarborelui fiecarui nod;
		 */
		if(n.getValue().equals(operatori.plus)){
			double rez;
			rez = visit( n.getLN() ) + visit( n.getRN() );
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.inmultit)){
			double rez;
			rez =  visit( n.getLN() ) * visit( n.getRN() ) ;
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.impartit)){
			double x = visit(n.getRN());
			if(x != 0){
				double rez;
				rez = visit( n.getLN() ) / x ;
				n.setRez(rez);
				return rez;
			}
			else
				throw new EvaluatorException();
		}
		if(n.getValue().equals(operatori.minus)){
			double rez;
			if(n.getLN() == null){
				rez =  - visit(n.getRN());
				n.setRez(rez);
				return rez;
			}
			else{
				rez = visit( n.getLN() ) - visit( n.getRN() ) ;
				n.setRez(rez);
				return rez;
			}
		}
		if(n.getValue().equals(operatori.putere)){
			double rez;
			rez = Math.pow(visit(n.getLN()),visit(n.getRN()));
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.log)){
			
			double x = visit(n.getRN()),rez;
			if(x<=0){
				throw new EvaluatorException();
			}
			rez =  Math.log10(x);
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.sqrt)){
			double x = visit(n.getRN()),rez;
			if(x<0){
				throw new EvaluatorException();
			}
			rez = Math.sqrt(x);
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.sin)){
			double rez;
			rez = Math.sin(visit(n.getRN()));
			n.setRez(rez);
			return rez;
		}
		if(n.getValue().equals(operatori.cos)){
			double rez;
			rez = Math.cos(visit(n.getRN()));
			n.setRez(rez);
			return rez;
		}
		double rez;
		rez = Double.parseDouble(n.getValue());
		n.setRez(rez);
		return rez;
	}
	
}
