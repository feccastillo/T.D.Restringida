import java.lang.Object.*;
import java.util.*;
import java.awt.*;

class Triangulo extends Poligono {
     	private Vertice		_centroid;
     	private Circulo	c = null;      //Circulo que circunscribe al triangulo
     	private Triangulo[] 	vecinos;
     	private boolean[] voronoi_neighbor;
     	private Vertice[]		vertices;
     	private int[]			neighbors;
     	private Edge[]		edges;
     	protected		static int	lastidt;
   	protected		int	idt;
   	private boolean		ficticio, selected;
   	private Vertice		_circumcenter;
   	private double		_radius;

     	Triangulo(Vertice p1, Vertice p2, Vertice p3) {
	 	//super(p1, p2, p3);
	 	vecinos   	= new Triangulo[3];
	 	neighbors 	= new int[3];
	 	vertices 	= new Vertice[3];
	 	voronoi_neighbor = new boolean[3];
	 	
	 	vertices[0] = p1;
	 	vertices[1] = p2;
	 	vertices[2] = p3;
	 	ficticio = false;

	 	resetNeighbors();
     		edges	= new Edge[3];
     		fixEdges();
     		idt=0;
     		selected = false;
     	}
     	
     	public void setVoronoiNeighbor(int i, boolean vn) {
     		 voronoi_neighbor[i] = vn;
     	}
     	
     	public boolean getVoronoiNeighborState(int i) {
     		 return voronoi_neighbor[i];
     	}
     	
     	Vertice circumcenter() {
     		return _circumcenter;
     	}
     	
     	public void setCirculo() {
     		if ( c == null ) {
     			c = new Circulo(this);
     		}
     	}
     	
     	public Circulo getCirculo() {
     		return c;
     	}
     	
     	public void setFicticio(boolean fict) {
     		ficticio = fict;
     	}
     	
     	public boolean isFicticio() {
     		return ficticio;
     	}

     	public void setSelected(boolean sel) {
     		selected = sel;
     	}
     	
     	public boolean isSelected() {
     		return selected;
     	}
     	
     	public void setCentroid() {
		Vertice p;
		float x, y;

      	x = (vertices[0].CordX() + vertices[1].CordX() + vertices[2].CordX())/3.0f;
            y = (vertices[0].CordY() + vertices[1].CordY() + vertices[2].CordY())/3.0f;
            _centroid = new Vertice(x, y);
      }
      
      public Vertice centroid()
      {
            return _centroid;
      }
     	
     	public static void setLastIdt(int lidt) {lastidt=lidt;}
  	public static int getLastIdt() {
  		return lastidt;
  	}
  	public void setIdt(int id) { idt = id; }
  	public int getIdt() { return idt; }
  	
  	public Vertice vertex(int i) {
  		return vertices[i];
  	}
  	
  	protected void Dibujar(Graphics g, Color c) {
		construir_lista_puntos(vertices[0], vertices[0], vertices[0]);
		construir_lista_Edges();
		setEdges();
		
		int[] x = new int[3];
		int[] y = new int[3];
		x[0] = (int)vertex(0).CordX();
		y[0] = (int)vertex(0).CordY();

		x[1] = (int)vertex(1).CordX();
		y[1] = (int)vertex(1).CordY();
		
		x[2] = (int)vertex(2).CordX();
		y[2] = (int)vertex(2).CordY();
		
		g.setColor(Color.blue);
		g.drawLine((int)vertex(0).CordX(),(int)vertex(0).CordY(),(int)vertex(1).CordX(),(int)vertex(1).CordY());
		g.drawLine((int)vertex(1).CordX(),(int)vertex(1).CordY(),(int)vertex(2).CordX(),(int)vertex(2).CordY());
		g.drawLine((int)vertex(2).CordX(),(int)vertex(2).CordY(),(int)vertex(0).CordX(),(int)vertex(0).CordY());

	 	g.setColor(c);
	 	g.fillPolygon(x, y, 3);
	 	//System.out.println("Pintando triángulos");
	 
	 	//if ( !trimage ) 
	 	this.Dibujar(g);
   	}
  	    	
     	void setEdges() {
     		int i=0;
     		Iterador it = new Iterador(listSeg);
     		while ( !it.IsDone() ) {
			Edge seg = (Edge)it.Current();
			edges[i] = seg;
			i++;
			it.Next();
	  	}
     	}
     	//comprueba que ningun vertice sea continui con otro
     	
     	public boolean Test_No_Continuidad(ListaFifo lista){
	     		Iterador j = new Iterador(lista);

	       	 while(!j.IsDone()){
	       		 if(this.vertex(0).ElementosRepetido((Vertice)j.Current())){
	       			 //System.out.println("pregunta por 0");
	       			 if(j.BackCurrent()!=null){
	       				if(this.vertex(1).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
		       			 if(this.vertex(2).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
	       			 }
	       			 j.Next();
	       			if(this.vertex(1).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 if(this.vertex(2).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 j.Back();
	       		 }
	       		if(this.vertex(1).ElementosRepetido((Vertice)j.Current())){
	       			//System.out.println("pregunta por 1");
	       			 if(j.BackCurrent()!=null){
	       				if(this.vertex(0).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
		       			 if(this.vertex(2).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
	       			 }
	       			j.Next();
	       			if(this.vertex(0).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 if(this.vertex(2).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 j.Back();
	       			 
	       		 }
	       		if(this.vertex(2).ElementosRepetido((Vertice)j.Current())){
	       			//System.out.println("pregunta por 2");
	       			 if(j.BackCurrent()!=null){
	       				if(this.vertex(1).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
		       			 if(this.vertex(0).ElementosRepetido((Vertice)j.BackCurrent())){
		       				 return false;
		       			 }
	       			 }
	       			j.Next();
	       			if(this.vertex(1).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 if(this.vertex(0).ElementosRepetido((Vertice)j.Current())){
	       				 return false;
	       			 }
	       			 j.Back();
	       			 
	       		 }
	       		 j.Next();
	       	 }
	       	 System.out.println("si pella uno csm");
     		return true;
     	}
     	//para comprobar si alguna arista apunta a null
     	public boolean test_arista_null(){
     		if(this.neighbor(0)==null){
     			return true;
     		}
     		if(this.neighbor(1)==null){
     			return true;
     		}
     		if(this.neighbor(2)==null){
     			return true;
     		}
     		
     		
			return false;
     	}
     	public int test_arista_null_index(){
     		if(this.neighbor(0)==null){
     			return 0;
     		}
     		if(this.neighbor(1)==null){
     			return 1;
     		}
     		if(this.neighbor(2)==null){
     			return 2;
     		}
			return -1;
     	}
     	
//     public boolean Test_continuidad_cero(ListaFifo lista){
//    	 if(this == null){return false;}
//    	 Iterador j = new Iterador(lista);
//    	 boolean flag1=true;
//    	 boolean flag2=true;
//    	 while(!j.IsDone() && (flag1 || flag2)){
//    		 if(lista.buscar_elemento_repetido(this.vertex(0))){
//    			 if(j.BackCurrent()!= null){
//    				 j.Back();
//        			 if(this.vertex(1).ElementosRepetido((Vertice)j.Current()) || this.vertex(2).ElementosRepetido((Vertice)j.Current())){
//        				 return true;
//        			 }
//        			 else{j.Next();}
//    			 }
//    			 j.Next();
//    			 if(this.vertex(1).ElementosIguales(j.Current()) || this.vertex(2).ElementosIguales(j.Current())){
//    				 return true;
//    			 }
//    			 
//    			 flag2=false;
//    		 }
//    		 if(lista.buscar_elemento_repetido(this.vertex(2))){
//    			 if(j.BackCurrent()!=null){
//    				 j.Back();
//        			 if(this.vertex(0).ElementosIguales(j.Current()) || this.vertex(2).ElementosIguales(j.Current())){
//        				 return true;
//        			 }
//    			 }
//    			 else{j.Next();}
//    			 j.Next();
//    			 if(this.vertex(0).ElementosIguales(j.Current()) || this.vertex(2).ElementosIguales(j.Current())){
//    				 return true;
//    			 }
//    			 
//    			 flag1=false;
//    		 }
//    		 j.Next();
//    	 }
//    	 System.out.println("triangulo cero continuo");
//    	 return false;
//     }	
     	
     	//para ver sis los vertices del triangulos son continuos totales en la lista
//     public boolean Test_continuidad_Vertice_total(ListaFifo lista){
//    	
//    	 if(this == null){
//    		 return false;
//    	 }
//    	 Iterador j = new Iterador(lista);
//    	 boolean flag2=false;
//    	 boolean flag3=false;
//    	 boolean flagAux=false;
//    	 while(!j.IsDone()){
//    		 if(lista.buscar_elemento_repetido(this.vertex(0))){
//    			 if(j.BackCurrent()!=null){
//    				 j.Back();
//            		 if(this.vertex(1).ElementosIguales(j.Current())){
//            			 flag2=true;
//            			 j.Back();
//                		 if(this.vertex(2).ElementosIguales(j.Current())){
//                			 return true;
//                		 }
//            		 } 
//    			 }
//        		 
//    			 if(flag2){
//    				 //para volver al primero
//    				 j.Next();
//    				 j.Next();
//    				 //al siguiente
//    				 j.Next();
//    				 if(this.vertex(2).ElementosIguales(j.Current())){
//    					 return true;
//    				 }
//    				 else{j.Back();}
//    			 }
//    			 
//    			 j.Back();
//    			 if(this.vertex(2).ElementosIguales(j.Current())){
//        			 flag3=true;
//        			 j.Back();
//            		 if(this.vertex(1).ElementosIguales(j.Current())){
//            			 return true;
//            		 }
//        		 }
//    			 
//    			 
//    			 if(flag3){
//    				 //para volver al primero
//    				 j.Next();
//    				 j.Next();
//    				 //al siguiente
//    				 j.Next();
//    				 if(this.vertex(1).ElementosIguales(j.Current())){
//    					 return true;
//    				 }
//    				 else{j.Back();}
//    			 }
//    			 //como no esta hacia atras hay que volver solo 1
//    			 j.Next();// estoy en 1
//    			 if(this.vertex(1).ElementosIguales(j.Current())){
//    				 flagAux=true;
//        			 j.Next();
//            		 if(this.vertex(2).ElementosIguales(j.Current())){
//            			 return true;
//            		 }
//        		 }
//    			 if(flagAux){return false;}
//        		 if(this.vertex(2).ElementosIguales(j.Current())){
//        			 j.Next();
//            		 if(this.vertex(1).ElementosIguales(j.Current())){
//            			 return true;
//            		 }
//        		 }
//    		 }
//    		
//    	 }
//    	 return false;
//     }

//----- Calcula test del circulo entre dos triangulos -----

	public boolean Test_del_Circulo(Vertice Vertice)
  	{
           	float distancia;

           	distancia = c.Calcula_Radio(Vertice);

           	if (distancia <= c.radius()) return false;
            else return true;
   	}
   	
   	public boolean incircle(Vertice vd)
	{
  	 	double adx, bdx, cdx, ady, bdy, cdy;
  		double bdxcdy, cdxbdy, cdxady, adxcdy, adxbdy, bdxady;
  		double alift, blift, clift;
  		double det;

  		//Point<3,double>	pa, pb, pc, pd;
  		double[] pa = new double[2];
  		double[] pb = new double[2];
  		double[] pc = new double[2];
  		double[] pd = new double[2];

  		Vertice va = this.vertex(0);
        	Vertice vb = this.vertex(1);
        	Vertice vc = this.vertex(2);
        	
        	pa[0] = va.CordX();
            pa[1] = va.CordY();

            pb[0] = vb.CordX();
            pb[1] = vb.CordY();

            pc[0] = vc.CordX();
            pc[1] = vc.CordY();

            pd[0] = vd.CordX();
            pd[1] = vd.CordY();

  		adx = pa[0] - pd[0];
  		bdx = pb[0] - pd[0];
  		cdx = pc[0] - pd[0];
  		ady = pa[1] - pd[1];
  		bdy = pb[1] - pd[1];
  		cdy = pc[1] - pd[1];

  		bdxcdy = bdx * cdy;
  		cdxbdy = cdx * bdy;
  		alift = adx * adx + ady * ady;

  		cdxady = cdx * ady;
  		adxcdy = adx * cdy;
  		blift = bdx * bdx + bdy * bdy;

  		adxbdy = adx * bdy;
  		bdxady = bdx * ady;
  		clift = cdx * cdx + cdy * cdy;

  		det =   alift * (bdxcdy - cdxbdy)
      		+ blift * (cdxady - adxcdy)
      		+ clift * (adxbdy - bdxady);

    		//if ( det  <= 0.0  ) return true;
    		if ( det  <= 0.0  ) return true; // Si cumple el test de Delaunay
    		
    		return false; // Si no cumple el test
	}

	/*--------- vertices_iguales-----------------------
	Retorna TRUE si las coordenadas x e y del vertice v1 son
	iguales a las coordenadas x e y del Vertice p
	--------------------------------------------------*/
	public boolean vertices_iguales(Vertice v1, Vertice v2) {

		if ((v1.x == v2.x) && (v1.y == v2.y)) 
			return true;
		return false;
	}
	
	Edge edge(int i) {
		return edges[i];
	}

	void fixEdges()
      {
      	for (int i=0; i<3; i++) {
                	edges[i] = new Edge(vertices[(i+1)%3], vertices[(i+2)%3]);
            }
      }
	
	boolean faceToface(int edgei, int edgej, Triangulo t) 
      {
            if ( ((edges[edgei].initial().ElementosIguales(t.edge(edgej).initial()) && 
                  (edges[edgei].terminal().ElementosIguales(t.edge(edgej).terminal()))))  ||
                 ((edges[edgei].initial().ElementosIguales(t.edge(edgej).terminal())) && 
                  (edges[edgei].terminal().ElementosIguales(t.edge(edgej).initial()))) )
                  	return true;
            
            
            return false;
      }
      
      int GetNeighborIndex(Triangulo triangle)
      {
            assert triangle != null;
            
            int index = -1;
            for ( int i=0; i<3; i++ )  //Caras
            {
                for ( int j=0; j<3; j++ )
                {
                    //Aquí comparar las caras de los dos triángulos.
                    //Now, we compare the faces of triangles.
                    //Compare face i of 'this' triangle and face j of 'Triangle'
                    
                    if ( faceToface(i, j, triangle) )
                    { 
                    	index = i;

                        return index;
                    }
                }
            }

            return -1;
      }
      
      int getNeighborIndex(Triangulo triangle) {
      	return neighborIndex(triangle);
      }
      
      //This method determines if 'triangle' is a 'this' t's neighbor triangle
      int neighborIndex(Triangulo triangle)
      {
            assert triangle != null;
            
            int index = -1;
            for (int i=0; i<3; i++)
            {
                boolean shared = false;
                for (int j=0; j<3; j++)
                {
                    //if (_vertices[i] == triangle._vertices[j])
                    if ( (this.vertices[i].CordX() == triangle.vertices[j].CordX()) &&
                    	 (this.vertices[i].CordY() == triangle.vertices[j].CordY()) )
                    {
                        shared = true;
                        break;
                    }
//                	if(this.vertex(i).ElementosIguales(triangle.vertex(j))){
//                		shared = true;
//                        break;
//                	}
                }

                assert !shared && (index == -1) || shared;

                if (!shared) index = i;
            }

            assert (index != -1) && (0 <= index) && (index < 3);

            return index;
      }

	boolean addNeighbor(Triangulo neighbor)
      {
             //int index = getNeighborIndex(neighbor);
             int index = neighborIndex(neighbor);
             if ( index != -1 ) {
            	vecinos[index] = neighbor;
            	//neighbors[index] = neighbor.getIdt();
            	
            	return true;
             }
             
             return false;
      }
      
      public Triangulo neighbor(int i) {
  		return vecinos[i];
  	}

      
      int vecinoidx(int idx) {
      	return neighbors[idx];
      }
      
      void setVertex(int i, Vertice Vertice)
      {
            vertices[i] = Vertice;
            fixEdges();
      }
      
      float Orient2D(Vertice v0, Vertice v1, Vertice v2)
	{
	  	return (v0.CordX()-v2.CordX())*(v1.CordY()-v2.CordY())-(v0.CordY()-v2.CordY())*(v1.CordX()-v2.CordX());
	}
	
	public void resetNeighbors()
      {
      	for ( int i=0; i<3; i++ ) {
                	vecinos[i] 	 = null;
     			neighbors[i] = -1;
     			voronoi_neighbor[i] = false;
            }
      }
      
      public void resetNeighbors(int i) {
    	  //if(vecinos[i]!= null){
  		this.vecinos[i]   = null;
  		this.neighbors[i] = -1;
  	}
      public void SetnullVecinos(){
    	Triangulo taux1;
  		Triangulo taux2;
  		Triangulo taux3;
    	taux1=this.neighbor(0);
  		taux2=this.neighbor(1);
  		taux3=this.neighbor(2);
  		if(taux1!=null){
  			int aux1 = taux1.neighborIndex(this);
  			taux1.resetNeighbors(aux1);
  		}
  		if(taux2!=null){
  			int aux2 = taux2.neighborIndex(this);
  			taux2.resetNeighbors(aux2);
  		}
  		if(taux3!=null){
  			int aux3 = taux3.neighborIndex(this);
  			taux3.resetNeighbors(aux3);
  		}
      }
      int longestEdgeIndex()
      {
            double max = -1.0;
            int index  = -1;

            for ( int i=0; i<3; i++ )
            {
                Edge e  = this.edge(i);
                double dist = e.distance();
                if ( max < dist ) {
                    max   = dist;
                    index = i;
                }
            }

            assert -1 != index;

            return index;
      }
      
      Edge longestEdge()
      {
            return edges[longestEdgeIndex()];
      }
      
      public Triangulo longestEdgeNeighbor()
      {
            int index = this.longestEdgeIndex();
            
            return this.neighbor(index);
      }
      
      public boolean isTerminal()
      {
            Triangulo neighbor = this.longestEdgeNeighbor();
            if ( neighbor == null ) {
                return true;
            }

            neighbor = neighbor.longestEdgeNeighbor();

            return ( this == neighbor );
      }
        
      public boolean isTerminal(Triangulo neighbor)
      {
            if ( neighbor == null ) {
                return true;
            }

            Triangulo newNeighbor = neighbor.longestEdgeNeighbor();

            return ( this == newNeighbor );
      }
      
      boolean TestVerticeInTriangle(Vertice q) {
		if ( Orient2D(vertices[0], vertices[1], q) < 0 ) {
			return false;
		}
		if ( Orient2D(vertices[1], vertices[2], q) < 0 ) {
			return false;
		}
		if ( Orient2D(vertices[2], vertices[0], q) < 0 ) {
			return false;
		}
		
		return true;
	}
	
	// Return the index of edge into a 'this' triangle where q is collinear
	int coLlinear(Vertice q)
	{
	  	if ( Orient2D(edges[0].a, edges[0].b, q ) == 0.0 ) {
	  		return 0;
	  	}
	  	if ( Orient2D(edges[1].a, edges[1].b, q ) == 0.0 ) {
	  		return 1;
	  	}
	  	if ( Orient2D(edges[2].a, edges[2].b, q ) == 0.0 ) {
	  		return 2;
	  	}
	  	
	  	return -1;
	}
	
	Triangulo bisect(Vertice newVertice)
      {
            int index = this.longestEdgeIndex();
            
            Vertice v1 = this.vertex(index); //vértice opuesto a arista
            Vertice v2 = newVertice; //El nuevo vértice
            Vertice v3 = this.vertex((index+2)%3); 

            Triangulo newTriangle = new Triangulo(v1, v2, v3);
            
            this.setVertex((index+2)%3, newVertice); //Modifica triángulo para que se transforme en nuevo
		Triangulo neighbor = this.neighbor((index+1)%3);
		
            if ( neighbor != null ) { //¿Tiene vecino?
                newTriangle.addNeighbor(neighbor); //cuidado!, que el vecino neighbor podría estar en manos
                neighbor.addNeighbor(newTriangle); //de otro thread!!.
            }

            newTriangle.addNeighbor(this);
            this.addNeighbor(newTriangle);

            return newTriangle;
      }
	
	Triangulo bisect(Vertice newVertice, int index)
      {
            Vertice v1 = this.vertex(index); //vértice opuesto a arista
            Vertice v2 = newVertice; //El nuevo vértice
            Vertice v3 = this.vertex((index+2)%3); 

            Triangulo newTriangle = new Triangulo(v1, v2, v3);
            
            this.setVertex((index+2)%3, newVertice); //Modifica triángulo para que se transforme en nuevo
		Triangulo neighbor = this.neighbor((index+1)%3);
		
            if ( neighbor != null ) { //¿Tiene vecino?
                newTriangle.addNeighbor(neighbor); //cuidado!, que el vecino neighbor podría estar en manos
                neighbor.addNeighbor(newTriangle); //de otro thread!!.
            }

            newTriangle.addNeighbor(this);
            this.addNeighbor(newTriangle);

            return newTriangle;
      }
      
      protected void Imprime_Elemento(int i) {
      	System.out.println("--. Vertices del Triangulo: " + i);
      	
      	System.out.println("[(" + vertices[0].CordX() + ":" + vertices[0].CordY() + ")::(" +  vertices[1].CordX() + ":" + vertices[1].CordY() + ")::(" + vertices[2].CordX() + ":" + vertices[2].CordY() + ")]");
      }
      
      public Triangulo Clone() {return this;}
}
