abstract class Lista {
  NodoLista 	first;
  NodoLista 	last;
  int       	cantidad_elementos;
  private 		Iterador 	it;

  Lista() {  //Constructor
     first = null;
     last  = null;
     cantidad_elementos = 0;
   }

  Lista(Lista lista) { //Constructor de copia
     ElementoGeometrico eg;
     ElementoGeometrico n;
     NodoLista nodo;

     it = new Iterador(lista);
     this.first = null;
     this.last  = null;
     this.cantidad_elementos = 0;

     while ( !it.IsDone() ) {
	  n = it.Current();
	  nodo = new NodoLista(n);
	  n.setNodo(nodo);
        this.insertar_elemento(nodo);
	  it.Next();
	}
   }
  
//  public Lista Buscar_lista_id(){
//	  Lista listaAux = new Lista();
//	return null;
//  }
   
  public NodoLista getHead() {
  	return first;
  }
  
  public NodoLista getLast() {
  	return last;
  }

  public boolean buscar_elemento_repetido(ElementoGeometrico eg) {
	NodoLista n;

	n = this.first;
    	if ( n != null ) {
         while ( (n != null) && !n.eg.ElementosIguales(eg) )  
            n = n.next;
      }

      return (n != null);
   }
  public int buscar_id(ElementoGeometrico eg) {
		NodoLista n;

		n = this.first;
	    	if ( n != null ) {
	         while ( (n != null) && !n.eg.ElementosIguales(eg) )  
	            n = n.next;
	      }

	      return n.id_hollow;
	   }
  
//  public boolean buscar_repetido(Vertice p) {
//	NodoLista n;
//
//	n = this.first;
//    	if ( n != null ) {
//         while ( (n != null) && !n.ElementosRepetido(p) )  
//            n = n.next;
//      }
//
//      return (n != null);
//   }

   public void clear() {
	first = last = null;
	cantidad_elementos = 0;
   }
   
   public void removeOne(Triangulo t) {
   	NodoLista nodo = t.getNodo();
  	NodoLista back = nodo.back;
  	NodoLista next = nodo.next;
  		
  	if ( back != null ) back.next = next;
  	if ( next != null ) next.back = back;	
  	if ( first == nodo ) {
  		first = next;
  		next.back = null;
  	}
  	if ( last == nodo ) {
  		last = back;
  		last.next = null;
  	}
  	//nodo.next = null;
  	//nodo.back = null;
  	cantidad_elementos--;
  }
   
  abstract public void insertar_elemento(NodoLista nodo);

  public void insertar_elemento(NodoLista nodo, Vertice p) {
      NodoLista nuevo_nodo;

	nuevo_nodo = new NodoLista((Vertice)p);
	nuevo_nodo.next = nodo.next;
	nodo.next = nuevo_nodo;
	if ( nodo == last ) last = nuevo_nodo;
	//System.out.println("Vertice insertado en poligono convexo");
	cantidad_elementos++;
  }

  public void eliminar_elemento(ElementoGeometrico elem) {
	NodoLista back, curr;
	
	curr = back = this.first;
	while ( curr != null ) {
	   if ( curr.eg == elem	 ) {
		if ( curr == this.first ) 
			this.first = this.first.next;
		 else 
			back.next = curr.next;
		if ( curr == this.last ) this.last = back;
		curr.next = null;
		cantidad_elementos--;
            if ( this.first == null ) this.last = null;
		break;
	    }	  
         else {
	   	back = curr;
	   	curr = curr.next;
	    }
	 }
    }

   public void eliminar_elemento(NodoLista back, NodoLista current) {
	if ( current == this.first ) 
		this.first = this.first.next;
	else 
		back.next = current.next;
	if ( current == this.last ) this.last = back;
	current.next = null;
	cantidad_elementos--;
      if ( this.first == null ) this.last = null;
    }

  public int NumeroElementos() {
	return cantidad_elementos;
  }
  
  protected int size() {
	return cantidad_elementos;
  }
  
  protected boolean isEmpty() {
  	return this.NumeroElementos() == 0;
  }

  public void imprime_lista_elementos() {
    int i=1;
    
    it = new Iterador(this);
    while ( !it.IsDone() ) {
	  it.Current().Imprime_Elemento(i);
	  i++;
	  it.Next();
     }
  }

} //end Lista class



