class ListaFifo extends Lista {
	int Cantidad_Hollow;
  ListaFifo() {   
      super();
   }

  ListaFifo(Lista lista) {
       super(lista);
  }

  public void insertar_elemento(NodoLista nodo) {  
	if ( first == null ) {
		first	= nodo;
		last 	= nodo;
	} 
	else {
  		last.next = nodo;
  		nodo.back = last;
  		last = nodo;
	}
     	this.cantidad_elementos++;
  }
  
  public void insertar_elemento_hollow(NodoLista nodo, int id) {  
	if ( first == null ) {
		first	= nodo;
		last 	= nodo;
	} 
	else {
  		last.next = nodo;
  		nodo.back = last;
  		last = nodo;
	}
     	this.cantidad_elementos++;
     	last.id_hollow=id;
  }
  
  public boolean pertenece_triangulo(Triangulo t){
	  
	  if(!this.buscar_elemento_repetido(t.vertex(0))){
		  return false;
	  }
	  if(!this.buscar_elemento_repetido(t.vertex(1))){
		  return false;
	  }
	  if(!this.buscar_elemento_repetido(t.vertex(2))){
		  return false;
	  }
	  
	  return true;
  }
  
  public ListaFifo Lista_por_id(int id){
	  ListaFifo ListaAux = new ListaFifo();
	  NodoLista n;

		n = this.first;
	    	if ( n != null ) {
	         while ( (n != null)) { 
	        	 if(n.id_hollow==id){
	        		 ListaAux.insertar_elemento(n);
	        	 }
	            n = n.next;
	         }
	      }
	return ListaAux;
	  
  }
  
  public void push_elemento(NodoLista nodo) {  
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
  protected boolean contiene_punto(Vertice q) {
	    float   cx;
	    int     nintersec = 0;
	    Vect    v1, v2, dir;
	    Vertice     head, sig, current;

	    Iterador it;

	    it = new Iterador(this);
	    head     = (Vertice)it.Current();
	    current    = (Vertice)it.Current();
	    it.SetPostCurrent();
	    sig     = (Vertice)it.PostCurrent();
	    while ( current != null ) {
	    v1 = new Vect((float)(sig.CordX()-q.CordX()),(float)(sig.CordY()-q.CordY()));
	    v2 = new Vect((float)(current.CordX()-q.CordX()),(float)(current.CordY()-q.CordY()));
	    
	    if ( v1.CordY()*v2.CordY()<=0 ) {
	        if ( (v1.CordY() != 0)  || ( v2.CordY() != 0) ) {
	        dir = new
	Vect((float)(v2.CordX()-v1.CordX()),(float)(v2.CordY()-v1.CordY()));
	        cx = v1.CordX()*dir.CordY() - v1.CordY()*dir.CordX();
	          if (dir.CordY() < 0) {cx = cx * (-1.0f);}
	          if(cx == 0 ) { nintersec=1; break;}
	          if (cx > 0) {
	           nintersec++ ;
	             if( ( (v1.CordY()<0) && (v2.CordY() ==0) ) || (
	(v1.CordY()==0) && (v2.CordY()<0) )) nintersec--;
	            }
	         }
	       }

	      it.Next();
	    current     = (Vertice)it.Current();
	    if ( current != null ) {
	       it.SetPostCurrent();
	       sig     = (Vertice)it.PostCurrent();
	     }
	      if ( sig == null ) sig = head;

	   } //end while

	    return ((nintersec % 2) == 1);
	 }
} //end Lista class



