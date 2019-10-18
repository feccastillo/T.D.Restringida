class ListaLifo extends Lista {
  ListaLifo() {   
      super();
   }

  ListaLifo(Lista lista) {
       super(lista);
  }

  public void insertar_elemento(NodoLista nodo) {  
	if ( first == null ) {
		first	= nodo;
		last 	= nodo;
	} 
	else {
  		nodo.next = first;
  		nodo.back = null;
  		first = nodo;
	}
     	this.cantidad_elementos++;
  }
  
  public NodoLista pop() {  
	NodoLista nl=null;
	
	if ( first != null ) {
		nl    = first;
		first = first.Next();
		if ( first != null )
			first.setBack(null);
		nl.setNext(null);
		this.cantidad_elementos--;
	}

     	return nl;
  }
} //end Lista class



