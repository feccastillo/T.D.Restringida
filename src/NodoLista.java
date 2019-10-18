class NodoLista {
   ElementoGeometrico eg;
   NodoLista next;
   NodoLista back;
   int id_hollow;

   NodoLista(ElementoGeometrico _eg) {
	eg   = _eg;
      back = null;
      next = null;
   }
   NodoLista(ElementoGeometrico _eg, int id) {
		eg   = _eg;
	      back = null;
	      next = null;
	      id_hollow=id;
	   }
   
   public ElementoGeometrico getElementoG() {
   	return eg;
   }
   
   public NodoLista Next() {
   	return next;
   }
   
   public NodoLista Back() {
   	return back;
   }
   
   public void setNext(NodoLista n) {
   	next = n;
   }
   
   public void setBack(NodoLista b) {
   	back = b;
   }
   public int get_id(){
	   return this.id_hollow;
   }
 } //fin NodoLista class

