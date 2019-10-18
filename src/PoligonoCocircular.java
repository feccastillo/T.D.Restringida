import java.lang.Object.*;
import java.util.*;

class  PoligonoCocircular extends PoligonoConvexo {
   protected Circulo  c;
   private   int sumaTotal=0;
   protected Vertice puntoa, puntob, puntoc;

   PoligonoCocircular(ListaFifo listaPto) {
	super(listaPto);
	iniciar_puntos();
	c = new Circulo(puntoa,puntob,puntoc);
	calcularRadioInicial();
    }

   PoligonoCocircular(Vertice p1, Vertice p2, Vertice p3) {
	super(p1,p2,p3);
	iniciar_puntos();
	puntoa = p1;
	puntob = p2;
	puntoc = p3;
    	c = new Circulo(puntoa, puntob, puntoc);
	calcularRadioInicial();
    }

   PoligonoCocircular (Lista listaSeg) {
	super(listaSeg);
	iniciar_puntos();
	c = new Circulo(puntoa,puntob,puntoc);
	calcularRadioInicial();
    }

   private void iniciar_puntos() {
	it = new Iterador(listPto);
	puntoa = (Vertice)it.Current(); 
	it.Next();
	puntob = (Vertice)it.Current();
	it.Next();
	puntoc = (Vertice)it.Current();
	it.Set();
    }

  private void calcularRadioInicial() {
	sumaTotal += c.Calcula_Radio((Vertice)puntoa);
	sumaTotal += c.Calcula_Radio((Vertice)puntob);
	sumaTotal += c.Calcula_Radio((Vertice)puntoc);
	c.setRadius(sumaTotal/3.0f);
    }

  protected ElementoGeometrico Clone() {
	return new PoligonoCocircular((ListaFifo)this.listPto);
  }
  
  public Circulo Circle() {
  	return c;
  }

} //fin PoligonoCocircular class

