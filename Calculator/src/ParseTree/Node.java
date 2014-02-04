package ParseTree;

import VisitorPattern.Visitable;
import VisitorPattern.Visitor;
/**
 * Clasa ce descrie un nod al arborelui de parsare.
 * @author iPatulea
 * Clasa implementeaza interfata Visitable.
 */
public class Node implements Visitable{
/*
 * value - valoarea din nod
 * rn - fiul drept
 * ln - fiul stang
 * Dad - tatal
 * nrB - numarul de paranteze deschise pana la el
 * ownB - numarul de paranteze pe care le deschide
 */
	private double rez;
	private String value;
	private Node rn;
	private Node ln;
	private Node Dad;
	private int nrB;
	private int ownB;
	
	public Node(){
		
		this.ownB = 0;
		rn = null;
		ln = null;
		Dad = null;
		this.nrB = 0;
		this.value = null;
	}
	public Node(String value,Node n){
		
		this.value = value;
		this.rn = null;
		this.ln = null;
		this.Dad = n;
		this.nrB = 0;
		this.ownB = 0;
	}
	
	public void setownB(int b){
		this.ownB = b;
	}
	
	public int getownB(){ 
		return ownB;
	}
	
	public Node getDad(){
		return this.Dad;
	}
	
	public void setDad(Node n){
		this.Dad = n;
	}
	
	public void setB(int nr){
		this.nrB = nr;
	}
	
	public int getnrB(){
		return this.nrB; 
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public void setRN(Node n) {
		this.rn = n;
	}
	
	public void setLN(Node n) {
		this.ln = n;
	}
	
	public Node getRN(){
		return rn;
	}
	
	public Node getLN(){
		return ln;
	}
	public double getRez(){
		return rez;
	}
	public void setRez(double rez){
		this.rez = rez;
	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
