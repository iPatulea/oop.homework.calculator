package ParseTree;
import Operators.operatori;
/**
 * Clasa care costruieste arborele de parsare.
 * @author iPatulea
 *
 */

public class Tree {
	
	private Node root;
	
	public Node getRoot(){
		return root;
	}
	
	public Tree(){
		root = new Node();
	}
	
	/**
	 * Inserare Numar
	 * @param value
	 * @param n
	 * Se insereaza numarul cat mai in dreapta.
	 */
	public void insertValue(String value,Node n){
		if(root.getValue() == null){
			root.setValue(value);
			return;
		}
		if(n.getRN()!=null){
			insertValue(value,n.getRN());
			return;
		}
		Node nou = new Node(value,n);
		n.setRN(nou);
	}
	
	/**
	 * Inserare "PLUS"
	 * @param bras - numarul de paranteze deschise
	 * @param firstbras - numarul de paranteze deschise de inceput
	 * @param n - nodul curent
	 * Inserez fara nicio prioritate dupa ce ajung la sfasitul parantezelor.
	 */
	public void insertPlus(int bras,int firstbras,Node n){
		if(bras - firstbras == 0 || bras == 0){
			Node nou = new Node(operatori.plus,n.getDad());
			if(n == root){
				root = nou;
				n.setDad(nou);
				nou.setLN(n);
				return;
			}
			n.getDad().setRN(nou);
			n.setDad(nou);
			nou.setLN(n);
			return;
		}
		if(n.getnrB() <= bras){
			if(n.getnrB() == bras){
				insertPlus(0,firstbras,n.getRN());
				return;
			}
			insertPlus(bras,firstbras,n.getRN());
			return;
		}
	}
	
	/**
	 * Inserare "MINUS"
	 * @param bras - numarul de paranteze deschise
	 * @param firstbras - numarul de paranteze deschise de inceput
	 * @param n - nodul curent
	 * Inserez fara nicio prioritate dupa ce ajung la sfasitul parantezelor.
	 */
	public void insertMinus(int bras,int firstbras,Node n){
		if(bras - firstbras == 0 || bras == 0){
			if(n.getValue() == null){
				root.setValue(operatori.minus);
				return;
			}
			Node nou = new Node(operatori.minus,n.getDad());
			if(n == root){
				root = nou;
				n.setDad(nou);
				nou.setLN(n);
				return;
			}
			n.getDad().setRN(nou);
			n.setDad(nou);
			nou.setLN(n);
			return;
		}
		if(n.getnrB() <= bras){
			if(n.getRN() == null){
				Node nou = new Node(operatori.minus,n);
				n.setRN(nou);
				return;
			}
			if( n.getnrB() == bras){
				insertMinus(0,firstbras,n.getRN());
				return;
			}
			insertMinus(bras,firstbras,n.getRN());
			return;
		}
	}
	
	/**
	 * Inserare "Impartit"
	 * @param bras - numarul de paranteze deschise
	 * @param endbra - numarul de paranteze inchise recent
	 * @param firstbras - numarul de paranteze deschise de inceput
	 * @param n - nodul curent
	 * endbra = 0 - inserarea se face dupa prioritate 
	 * endbra > 0 - se inchid parantezele si se verifica cazurile posibile
	 */
	public void insertImpartit(int bras,int endbra,int firstbras,Node n){
		if(endbra > 0){
			//Calculez daca se inchide vreo paranteza de inceput.
			int aux = firstbras + endbra - bras;
			//Inchid parantezele. Intoarce nodul care a inchis ultimul ultima paranteza.
			Node fin = closeBra(bras, endbra,firstbras, n);
			//Daca se inchide o paranteza de inceput atunci nodul nou va rupe tot arborele si va deveni radacina.
			if(aux > 0){
				Node nou = new Node(operatori.impartit,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}
			//Daca nodul este root si nu mai deschide alta paranteza.
			if(n == fin && fin.getownB() == 0){
				//Daca este plus sau minul atunci nu rupe tot arborele din cauza ca are prioritate mai mare
				if(n.getValue().equals(operatori.plus) || n.getValue().equals(operatori.minus)){
					Node nou = new Node(operatori.impartit,fin);
					nou.setLN(fin.getRN());
					fin.getRN().setDad(nou);
					fin.setRN(nou);
					return;
				}
				//Daca nu atunci devine root si tot arborele ii devine fiu stang.
				Node nou = new Node(operatori.impartit,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}	
			if(fin.getownB() > 0){
				Node nou = new Node(operatori.impartit,fin);
				nou.setLN(fin.getRN());
				fin.getRN().setDad(nou);
				fin.setRN(nou);
				return;
			}
			if(fin.getValue().equals(operatori.plus) || fin.getValue().equals(operatori.minus)){
				Node nou = new Node(operatori.impartit,fin);
				nou.setLN(fin.getRN());
				fin.getRN().setDad(nou);
				fin.setRN(nou);
				return;
			}
			//Am inchis parantezele deci acum pot insera normal.
			insertImpartit(bras - endbra,0,firstbras,n);
			return;
		}
		//Parantezele de inceput conteaza doar daca sunt inchise.
		if(bras - firstbras == 0 || bras == 0){
			if(n.getValue().equals(operatori.plus) == true){
				insertImpartit(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.minus) == true && n.getLN() != null){
				insertImpartit(bras,endbra,firstbras,n.getRN());
				return;
			}
			Node nou = new Node(operatori.impartit,n.getDad());
			if(n == root){
				root = nou;
				n.setDad(nou);
				nou.setLN(n);
				return;
			}
			n.getDad().setRN(nou);
			n.setDad(nou);
			nou.setLN(n);
			return;
		}
		if(n.getnrB() <= bras){
			if( n.getnrB() == bras){
				insertImpartit(0,endbra,firstbras,n.getRN());
				return;
			}
			insertImpartit(bras,endbra,firstbras,n.getRN());
			return;
		}
	}
	
	/**
	 * Inserare "Inmultit"
	 * @param bras - numarul de paranteze deschise
	 * @param endbra - numarul de paranteze inchise recent
	 * @param firstbras - numarul de paranteze deschise de inceput
	 * @param n - nodul curent
	 * endbra = 0 - inserarea se face dupa prioritate 
	 * endbra > 0 - se inchid parantezele si se verifica cazurile posibile
	 */
	public void insertInmultit(int bras,int endbra,int firstbras,Node n){
		//Identic cu Inserarea pentru "Impartit".
		if(endbra > 0){
			int aux = firstbras + endbra - bras;
			Node fin = closeBra(bras, endbra, firstbras,n);
			if(aux > 0){
				Node nou = new Node(operatori.inmultit,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}
			if(n == fin && fin.getownB() == 0 ){
				if(n.getValue().equals(operatori.plus) || n.getValue().equals(operatori.minus)){
					Node nou = new Node(operatori.inmultit,fin);
					nou.setLN(fin.getRN());
					fin.getRN().setDad(nou);
					fin.setRN(nou);
					return;
				}
				Node nou = new Node(operatori.inmultit,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}
			if(fin.getownB() > 0){
				Node nou = new Node(operatori.inmultit,fin);
				nou.setLN(fin.getRN());
				fin.getRN().setDad(nou);
				fin.setRN(nou);
				return;
			}
			if(fin.getValue().equals(operatori.plus) || fin.getValue().equals(operatori.minus)){
				Node nou = new Node(operatori.inmultit,fin);
				nou.setLN(fin.getRN());
				fin.getRN().setDad(nou);
				fin.setRN(nou);
				return;
			}
			insertInmultit(bras-endbra,0,firstbras,n);
			return;
		}
		if(bras - firstbras == 0 || bras == 0){
			if(n.getValue().equals(operatori.plus) == true){
				insertInmultit(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.minus) == true && n.getLN() != null){
				insertInmultit(bras,endbra,firstbras,n.getRN());
				return;
			}
			Node nou = new Node(operatori.inmultit,n.getDad());
			if(n == root){
				root = nou;
				n.setDad(nou);
				nou.setLN(n);
				return;
			}
			n.getDad().setRN(nou);
			n.setDad(nou);
			nou.setLN(n);
			return;
		}
		if(n.getnrB() <= bras){
			if( n.getnrB() == bras){
				insertInmultit(0,endbra,firstbras,n.getRN());
				return;
			}	
			insertInmultit(bras,endbra,firstbras,n.getRN());
			return;
		}
	}
	
	/**
	 * Inserare "Putere"
	 * @param bras - numarul de paranteze deschise
	 * @param endbra - numarul de paranteze inchise recent
	 * @param firstbras - numarul de paranteze deschise de inceput
	 * @param n - nodul curent
	 * endbra = 0 - inserarea se face dupa prioritate 
	 * endbra > 0 - se inchid parantezele si se verifica cazurile posibile
	 */
	public void insertPutere(int bras,int endbra,int firstbras,Node n){
		//Identic cu Inserarea "Impartit" si "Inmultit" dar are prioritate mai mare.
		if(endbra > 0){
			int aux = firstbras + endbra - bras;
			Node fin = closeBra(bras, endbra, firstbras,n);
			if(aux > 0){
				Node nou = new Node(operatori.putere,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}
			if(n == fin && fin.getownB() == 0){
				if(n.getValue().equals(operatori.plus) || n.getValue().equals(operatori.minus) || n.getValue().equals(operatori.inmultit) 
						|| n.getValue().equals(operatori.impartit) || n.getValue().equals(operatori.putere)){
					Node nou = new Node(operatori.putere,fin);
					nou.setLN(fin.getRN());
					fin.getRN().setDad(nou);
					fin.setRN(nou);
					return;
				}
				Node nou = new Node(operatori.putere,null);
				n.setDad(nou);
				nou.setLN(n);
				root = nou;
				return;
			}
			if(fin.getownB() > 0){
				Node nou = new Node(operatori.putere,fin);
				nou.setLN(fin.getRN());
				fin.getRN().setDad(nou);
				fin.setRN(nou);
				return;
			}
			insertPutere(bras-endbra,0,firstbras,n);
			return;
		}
		if(bras - firstbras == 0 || bras == 0){
			if(n.getValue().equals(operatori.plus) == true){
				insertPutere(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.minus) == true && n.getLN() != null){
				insertPutere(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.inmultit) == true){
				insertPutere(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.impartit) == true){
				insertPutere(bras,endbra,firstbras,n.getRN());
				return;
			}
			if(n.getValue().equals(operatori.putere) == true){
				insertPutere(bras,endbra,firstbras,n.getRN());
				return;
			}
			Node nou = new Node(operatori.putere,n.getDad());
			if(n == root){
				root = nou;
				n.setDad(nou);
				nou.setLN(n);
				return;
			}
			n.getDad().setRN(nou);
			n.setDad(nou);
			nou.setLN(n);
			return;
		}
		if(n.getnrB() <= bras){
			if( n.getnrB() == bras){
				insertPutere(0,endbra,firstbras,n.getRN());
				return;
			}		
			insertPutere(bras,endbra,firstbras,n.getRN());
			return;
		}
	}
	
	/**
	 * Inserare "Functii"
	 * @param func - functia respectiva
	 * @param n - nodul curent
	 * Se insereaza cat mia in dreapta jos a arborelui.
	 */
	public void insertFunc(String func,Node n){
		if(root.getValue() == null){
			root.setValue(func);
			return;
		}
		if(n.getRN()!=null){
			insertFunc(func,n.getRN());
			return;
		}
		Node nou = new Node(func,n);
		n.setRN(nou);
	}
	
	/**
	 * Inchide Paranteze
	 * @param bras - paranteze deschise
	 * @param wasBraL - paranteze ce trebuie inchise
	 * @param firstbras - numarul parantezelor de inceput deschise 
	 * @param n - nodul curent
	 * @return - returneaza nodul la care s-a inchis ultima paranteza;
	 * Se inchid "wasBraL" paranteze de pe partea dreapta a arborelui de jos in sus
	 */
	public Node closeBra(int bras,int wasBraL,int firstbras,Node n){
		if(n.getRN() == null){
			int aux = wasBraL;
			while(true){
				if(n.getnrB() > 0 && n.getownB() > aux){
					n.setB(n.getnrB() - aux);
					n.setownB(n.getownB() - aux);
					break;
				}
				if(n.getnrB() > 0 && n.getownB() <= aux ){
					aux -= n.getownB();
					n.setB(0);
					n.setownB(0);
				}
				if(aux == 0)
					break;
				if(n == root){
					break;
				}
				n = n.getDad();
			}
			return n;
		}
		return closeBra(bras,wasBraL,firstbras,n.getRN());
	}
	
	
	public void openBra(int bras,Node n){
		
		if(n.getRN() == null){
			n.setB(bras);
			n.setownB(n.getownB()+1);
			return;
		}
		openBra(bras,n.getRN());
	}
	
	
	public void display(Node n,int pos){
		if(n.getLN() != null)
			display(n.getLN(),pos+1);
		for(int i=1;i<=pos*5;i++)
			System.out.print(" ");
		System.out.println(n.getValue());
		if(n.getRN() != null)
			display(n.getRN(),pos+1);
	}
}
