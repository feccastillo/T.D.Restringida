import java.awt.*;
import java.lang.Object.*;

class ListaOrdenada extends Lista {
   ListaOrdenada() {  //Constructor
       super();
    }

   ListaOrdenada(Lista lista) { //Constructor de copia
       super(lista);
    }

   public void insertar_elemento(NodoLista nodo){
    NodoLista n1, n2;
    Vertice     p,q;

    n1 = n2 = this.first;
    if (n1 == null) {
	  this.first = nodo;

	  cantidad_elementos++;
      }
     else {
      p = (Vertice)n2.eg;
	q = (Vertice)nodo.eg;
      if (p.CordX() > q.CordX()) {
	    nodo.next = this.first;
          this.first = nodo;
	    cantidad_elementos++;
        }
       else {
        if ( !(p.ElementosIguales((Vertice)q)) ) {
            n2 = n2.next;
	      while (n2 != null) {
	          p = (Vertice)n2.eg;
                if (p.CordX() < q.CordX()) {
                   n1 = n2;
                   n2 = n2.next;
                 }
                else
		        break;
		  }
		 if ( !(p.ElementosIguales((Vertice)q)) ) {
			nodo.next = n1.next;
              	n1.next = nodo;
	        	cantidad_elementos++;
	         }
		  else System.out.println("Vertice Repetido");
	    }
	   else System.out.println("Vertice Repetido");
       }
     }
   }

 } //Fin ListaOrdenada class


