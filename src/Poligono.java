import java.lang.*;
import java.util.*;
import java.awt.*;

abstract class  Poligono extends ElementoGeometrico {
    protected ListaFifo		listPto;
    protected ListaFifo		listSeg;
    protected Vertice		op1,op2,pto_eleg;
    protected Vertice		p1, p2, p3;
    protected NodoLista		nodo_op1, nodo_op2;
    protected ListaFifo		segAdy;
    protected Edge		seg;
    protected boolean		in;
    protected Geometricas	geom;
    protected Iterador 		it;
    protected int			numpoints;
    protected int			red, green, blue;

    Poligono () {
	  op1		= null;
	  op2		= null;
	  pto_eleg 	= null;
	  nodo_op1 	= null;
	  nodo_op2 	= null;
	  seg		= null;
	  listSeg	= null;
	  listPto	= null;
	  geom 	= new Geometricas();
	  color	= new  Color(1.0f,1.0f,1.0f,0.0f);
	  numpoints = 0;
	  red=green=blue = 1;
  }

  Poligono(ListaFifo listaPto) {
	listSeg	= null;
	listPto	= null;
	geom 	= new Geometricas();
	color	= new  Color(1.0f,1.0f,1.0f,0.0f);

      listPto = new ListaFifo((ListaFifo)listaPto);
	construir_lista_Edges();
	numpoints = 0;
	red=green=blue = 1;
  }

  Poligono(Lista listaSeg) {
	listSeg	= null;
	listPto	= null;
	geom 	= new Geometricas();
	color	= new  Color(1.0f,1.0f,1.0f,0.0f);

      listSeg = new ListaFifo((ListaFifo)listaSeg);
	construir_lista_puntos();
	numpoints = 0;
	red=green=blue = 1;
  }

  Poligono(Vertice p1_, Vertice p2_, Vertice p3_) {
	NodoLista nodo;
	Edge  seg;
	
	p1 = p1_;
	p2 = p2_;
	p3 = p3_;

	listSeg	= null;
	listPto	= null;
	geom 	= new Geometricas();
	color	= new Color(1.0f,1.0f,1.0f,0.0f);

	listPto = new ListaFifo();
	listPto.insertar_elemento(new NodoLista((Vertice)p1));
	listPto.insertar_elemento(new NodoLista((Vertice)p2));
	listPto.insertar_elemento(new NodoLista((Vertice)p3));
	construir_lista_Edges();
	numpoints = 3;
	red=green=blue = 1;
  }

  protected void construir_lista_Edges() {
	Vertice     prim, ptoa, ptob;
	Edge  seg;

	listSeg = new ListaFifo();
 	it = new Iterador(listPto);
	this.cerrar_lista();
	prim = (Vertice)it.Current();
	do {
		ptoa = (Vertice)it.Current();
		it.Next();
		ptob = (Vertice)it.Current();
		seg = new Edge(ptoa,ptob);
		listSeg.insertar_elemento(new NodoLista((Edge)seg));
	  } while ( (Vertice)it.Current() != prim );
	this.abrir_lista();

    }

   protected void construir_lista_puntos() {
	Edge seg;
	numpoints = 0;

	listPto = new ListaFifo();
	it = new Iterador((ListaFifo)listSeg);
	while ( !it.IsDone() ) {
		seg = (Edge)it.Current();
		listPto.insertar_elemento(new NodoLista((Vertice)seg.a));
		numpoints++;
		it.Next();
	  }
   }
    
   protected void construir_lista_puntos(Vertice p1, Vertice p2, Vertice p3) {
	Edge seg;
	numpoints = 0;

	listPto = new ListaFifo();
	listPto.insertar_elemento(new NodoLista(p1));
	listPto.insertar_elemento(new NodoLista(p2));
	listPto.insertar_elemento(new NodoLista(p3));
	numpoints+=3;
   }

   public void reconstruir_Edges() {
	construir_lista_Edges();
   }
   
   protected void setNewColor(Color col) {
   	color = col;
   }
   
   protected void setRedRGB(int r) {
   	red = r;
   }
   
   protected void setGreenRGB(int g) {
   	green = g;
   }
   
   protected void setBlueRGB(int b) {
   	blue = b;
   }
   
   protected int getRedRGB() {
   	return red;
   }
   
   protected int getGreenRGB() {
   	return green;
   }
   
   protected int getBlueRGB() {
   	return blue;
   }
   
   protected Color getColor() {
   	return color;
   }
   
   public ListaFifo getListaVertices() {
   		return listPto;
   }

   protected void Dibujar(Graphics g) {
	Edge  curSg;
	Vertice     a,b;

	it = new Iterador(listSeg);
	do {
 	   	g.setColor(Color.blue);
	   	curSg = (Edge)it.Current();
	   	a = (Vertice)curSg.a;
	   	b = (Vertice)curSg.b;
	   	g.drawLine((int)a.CordX(),(int)a.CordY(), (int)b.CordX(),(int)b.CordY());
	   	it.Next();
	 } while ( !it.IsDone() );
	 
	 //this.Dibujar(g, color);
   }
     
   protected void Dibujar(Graphics g, Color c, boolean trimage) {
	Edge  	curSg;
	Vertice     	a,b;
	int		i=0;

	it = new Iterador(listSeg);
	//System.out.println("Creó el iterador de Edges");
	int nump = listSeg.cantidad_elementos;
	//System.out.println("Cantidad de Edges " + nump);
	int[] x = new int[nump];
	int[] y = new int[nump];
	do {
	   	curSg = (Edge)it.Current();
	   	//System.out.println("Tomó un Edge");
	   	a = (Vertice)curSg.a;
	   	b = (Vertice)curSg.b;
		x[i] = (int)a.CordX();
		y[i] = (int)a.CordY();
		//g.drawLine((int)a.CordX(),(int)a.CordY(),(int)b.CordX(),(int)b.CordY());
	   	it.Next();
	   	i++;
	 } while ( !it.IsDone() );
	 g.setColor(c);
	 g.fillPolygon(x, y, nump);
	 //System.out.println("Salió dibujar los Edges");
	 
	 this.Dibujar(g);
   }

   protected void Imprime_Elemento() {
        Vertice 	n;

	  it = new Iterador(listPto);
        System.out.println("--. Vertices del Poligono: ");

        while ( !it.IsDone() ) {
		n = (Vertice)it.Current();
		System.out.println("--. Id vertice poligono: " + n.getIdv());
	 	if ( n instanceof PuntoSteiner )
		   System.out.println("Es un Vertice de Steiner...");
		n.Imprime_Elemento();
		System.out.println(" ");
           	it.Next();
         }
      }
    
    protected void Imprime_Elemento(int i) {
        Vertice 	n;

	  it = new Iterador(listPto);
        System.out.println("--. Vertices del Poligono: " + i);

        while ( !it.IsDone() ) {
		n = (Vertice)it.Current();
		System.out.println("--. Id vertice poligono: " + n.getIdv());
	 	if ( n instanceof PuntoSteiner )
		   System.out.println("Es un Vertice de Steiner...");
		n.Imprime_Elemento();
		System.out.println(" ");
           	it.Next();
         }
      }

    public void abrir_lista() {
	 listPto.last.next = null;
	 if ( listSeg != null && listSeg.first != null ) 
		listSeg.last.next = null;
     }

    public void cerrar_lista() {
	 listPto.last.next = listPto.first;
	 if ( listSeg != null && listSeg.first != null ) 
            listSeg.last.next = listSeg.first;
     }

    protected boolean contiene_punto(Vertice q) {return in;}
 
    public boolean ElementosIguales(ElementoGeometrico eg) {
	igual = false;
	System.out.println("Poligonos son distintos");

	return igual;
    }
 } //fin Poligono class



