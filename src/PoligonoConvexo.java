import java.lang.Object.*;
import java.util.*;
import java.awt.*;

class  PoligonoConvexo extends Poligono {
   private float	sumaTotal = 0;

   PoligonoConvexo (ListaFifo listaPto) {
	super(listaPto);
   }
	
   PoligonoConvexo(Vertice p1, Vertice p2, Vertice p3) {
	super(p1,p2,p3);
    }

   PoligonoConvexo(Lista listaSeg) {
	super(listaSeg);
    }

   protected boolean contiene_punto(Vertice q) {
  	Vertice p1, p2, prim;
	Geometricas geom = new Geometricas();

	it 	 = new Iterador(listPto);
	this.cerrar_lista();
  	prim 	 = (Vertice)it.Current();
  	p1 	 = (Vertice)it.Current();
  	it.Next();
  	p2 	 = (Vertice)it.Current();
  	do {
    		if ( !geom.lefton(p1,p2,q) ) {
			this.abrir_lista();
			return false;
		  }
		p1 	 = p2;
  		it.Next();
  		p2 	 = (Vertice)it.Current();
	  } while ( p1 != prim );
	this.abrir_lista();

    	return true;
   }
 
  protected ElementoGeometrico Clone() {
	return new PoligonoConvexo((ListaFifo)this.listPto);
   }

} //fin PoligonoConvexo class

