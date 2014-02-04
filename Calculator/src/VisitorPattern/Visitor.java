package VisitorPattern;
import ParseTree.Node;


public interface Visitor {
	public double visit(Node n);
}
