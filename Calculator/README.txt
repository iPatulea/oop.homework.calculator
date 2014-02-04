TEMA 4

1. Impartirea sirului / Construirea arborelui.
2. Parcurgerea arborelui (design pattern Visitor).
3. Probleme intampinate.
4. Feedback.

1. Algoritmul de impartire este creat 100% de mine, NU am folosit niciun fel de algoritm deja stiut sau implementat.
Am o clasa "Node" ceea ce evident reprezinta nodul arborelui si o clasa "Tree" unde in mare parte inserez operatori
sau operanzi pornind de fiecare data de la nodul "radacina" al arborelui. Nodurile care sunt operatori sau functi
retin cate paranteze sunt deschise pana la ei in arbore dar si cate paranteze deschid ei. IDEEA programului este ca
operatorii si functiile sunt vazuti ca deschizatori de paranteze. 

   Imediat ce impart stringul expresie si incep sa iau la rand parantezele, functiile, operatorii si numerele inserez 
in arbore in felul urmator:

	paranteza stanga "(" (braR): Cresc contorul "bras" pentru cate paranteze am deschise si contorul "firstbras" 
				     pentru cate paranteze de inceput am (expresia incepe cu paranteze).
				     Daca nu este o paranteza de inceput notez ca operatorul precedent deschide o paranteza.

	paranteza dreapta ")" (braL): Cresc contor "wasBraL" pentru cate paranteze la rand se inchid si scad 
				      contorul "bras"(bras - contorul pentru cate paranteze am deschise).
				      La urmatorul operator urmeaza sa inchid paranteza in dreptul cui a deschis-o.

	functii (log;sqrt;sin;cos): Inserez in arbore functia respectiva cat mai in dreapta arborelui.

	operatorii plus si minus: Daca inaintea lor s-a inchis o paranteza apelez "tree" si inchid ultima paranteza 
				  deschisa si notez daca a fost o paranteza de inceput inchisa sau nu.
				  Inserez in arbore operatorul imediat dupa ce parcurg parantezele deschise.
				  In acest mod operatorul UNAR se rezolva foarte usor deoarece orice minus este practic 
				  tratat ca fiind unul.

	operatorii inmultit, impartit, putere: 	Apelez direct arborele fara sa vad daca inaintea lor s-au inchis
						paranteze pentru ca fac acest lucru din arbore.
						In arbore sunt multe cazuri posibile si se trateaza fosrte diferit daca 
						s-au inchis paranteze sau nu. Daca nu s-au inchis paranteze in functie 
						de prioritati sunt pusi in arbore iar daca da le inchid si parcurg niste 
						posibilitati descrise in cod.

	numere: Sunt cel mai simplu de asezat in arbore. II inserez cat mai in dreapta ca si functiile.

   Inchiderea prantezelor se face parcurcand partea dreapta a arborelui(parctic cea care conteaza) de jos in sus pana se epuizeaza 
numarul parantezelor ce trebuiesc inchise.
   Desi testele nu verifica prea mult posibilitatea ca multe paranteze sa fie deschise la inceput, am implementat si acest fapt pentru
toate conditiile posibile.
   Exceptiile au fost in general usor de identificat. Excepitiile de sintaxa le-am identificat prin neparsarea unor elemente si prin
introducerea de contori ce retineau ce element precedent a fost si daca acesta era compatibil cu cel curent.


2. Parcurgerea am facut-o in felul (arboreStang, radacina, arboreDrept) in clasa CalculatorVisitor prin recursivitate. Identific 
operatorul sau functia si returnez operatia respectiva pe "copii". Unde e cazul vad daca este exceptie sau nu. Ex:
	Valoarea nodului: "/" daca copilDreapt este 0 atunci Exceptie altfel returneaza copilStang / copilDrept s.a.m.d.
   Clasa "Node" implementeaza interfata Visitable pentru ca o apelez pe root, iar metoda "accept" apeleaza "visit" din "CalculatorVisitor".
CalculatorVisitor arunca exceptia "EvaluatorException" sau intoarce rezultatul care sa salveaza in fiecare nod. Rezultatul final se afla
evident in "root".


3. Prima problema a fost cea a descoperiri unei solutii ce merita implementata. Insa cea mai MARE problema a fost gestionarea parantezelor.
Am descoperit ca am atat de multe cazuri de acoperit incat uneori am fost coplesit. Mereu credeam ca am rezolvat dar defapt descopeream
un caz care imi dadea peste cap modul de abordare. In cele din urma am reusit sa acopar cazurile (macar cele necesare :) ). Am fost nevoit
sa apelez de multe ori la niste improvizatii sa rezolv problemele. In rest probeme dificile nu am mai intampinat.

4. Ma bucur sa vad ca in cele din urma am primit o tema care a necesitat mai mult de 2 ore de munca.
