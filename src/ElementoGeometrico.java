import java.lang.*;
import java.awt.*;

abstract class ElementoGeometrico {
	final int   ENNUBE   	= 0;
     	final int   NOENNUBE   	= 1;
     	final int   COCIRCULAR 	= 2;
     	final int   OUTCIRCULO 	= 3;
     	final int   INCIRCULO  	= 4;
     	final int   CONCAVO 	= 5;
     	final int   NOCONCAVO 	= 6;
     	final float ERROR		= (float)1.0; 
	boolean	igual;
	protected   ElementoGeometrico egclone;
	protected   Color	color;
	protected	NodoLista	nodo = null;

   	ElementoGeometrico() {}
	abstract protected void Dibujar(Graphics g);
   	abstract protected void Imprime_Elemento();
   	abstract protected void Imprime_Elemento(int i);
	protected void SetColor(Color _color) {color = _color;}
	protected boolean ElementosIguales(ElementoGeometrico eg) {return igual;}
	abstract protected ElementoGeometrico Clone();
	protected void setNodo(NodoLista n) {nodo = n;}
	protected NodoLista getNodo() {return nodo;}
 }//fin ElementoGeometrico class

