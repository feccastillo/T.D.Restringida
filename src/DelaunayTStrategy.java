// DelaunayTStrategy: clase que implementa el algoritmo de la triangulación de Delaunay

class DelaunayTStrategy {
     	protected  TriMesh _trimesh;

      DelaunayTStrategy(TriMesh trimesh) {
      	_trimesh = trimesh;
      	System.out.println("TriMesh tiene " + trimesh.vertices().size() + " Vertices");
      	System.out.println("Imprimiendo MinMax en el constructor de DelaunayT");
		_trimesh.printMinMax();
      }

 	void createAuxiliarElements() {
 	      Vertice 	p;
    		float		x_min, x_max, y_min, y_max;

    		x_min = _trimesh.retXmin() - 5000000.0f;
    		y_max = _trimesh.retYmax() + 10000000.0f;
    		x_max = _trimesh.retXmax() + 5000000.0f;
    		y_min = _trimesh.retYmin() - 10000000.0f;
    		
    		
    		/* insertar el Vertice "arriba izquierda" */
    		//Vertice   v1 = new PuntoFicticio(x_min, y_max);
    		Vertice   v1 = new Vertice(x_min, y_max);
    		v1.setVirtual(true);
    		//_trimesh.addVertex(v1);
    		
    		/* insertar el Vertice "arriba derecha" */
    		Vertice   v2 = new PuntoFicticio(x_max, y_max);
    		v2.setVirtual(true);
    		//_trimesh.addVertex(v2);
    		
    		/* insertar el Vertice "abajo izquierda" */
    		Vertice   v3 = new PuntoFicticio(x_min, y_min);
    		v3.setVirtual(true);
    		//_trimesh.addVertex(v3);

    		/* insertar el Vertice "abajo derecha" */
    		Vertice   v4 = new PuntoFicticio(x_max, y_min);
    		v4.setVirtual(true);
    		//_trimesh.addVertex(v4);

    		// Ahora crea los dos primeros triángulos (auxiliares)
    		Triangulo t = new Triangulo(v1, v3, v2);
    		_trimesh.addTriangle(t);
    		t.setFicticio(true);

    		Triangulo n = new Triangulo(v2, v3, v4);
    		_trimesh.addTriangle(n);
    		n.setFicticio(true);
    		
    		t.addNeighbor(n);
    		n.addNeighbor(t);

    		System.out.println("Máximos y mínimos");
    		System.out.println("(" + _trimesh.retXmin() + ", " + _trimesh.retXmax() + ")");
    		System.out.println("(" + _trimesh.retYmin() + ", " + _trimesh.retYmax() + ")");
    		System.out.println(" ");
    		
    		System.out.println("Vertices ficticios");
    		System.out.println("(" + v1.CordX() + ", " + v1.CordY() + ")");
    		System.out.println("(" + v2.CordX() + ", " + v2.CordY() + ")");
    		System.out.println("(" + v3.CordX() + ", " + v3.CordY() + ")");
    		System.out.println("(" + v4.CordX() + ", " + v4.CordY() + ")");

	  }
	  
	  Triangulo containerTriangle(Vertice v){
	  		Iterador j = new Iterador(_trimesh.triangles());
                	while ( !j.IsDone() ) {
                		Triangulo t = (Triangulo)j.Current();
                		if ( t.TestVerticeInTriangle(v) ) {
                			return t;
                		}
                		j.Next();
                	}
                	
                	return null;
	  }
	  
	  void setNeighbors(Triangulo triangle, Triangulo neighbor)
     	  {
     	  		if ( neighbor != null ) {
            		triangle.addNeighbor(neighbor);
            		neighbor.addNeighbor(triangle);
            	}
     	  }
     	  
     	  public void tieneTriangulosVirtuales() {
     	  		Iterador i = new Iterador(_trimesh.triangles());
                	while ( ! i.IsDone() ) {
                		Triangulo t = (Triangulo)i.Current();
                		if ( (t.vertex(0).isVirtual()) || (t.vertex(1).isVirtual()) || (t.vertex(2).isVirtual()) ) {
                			_trimesh.triangles().removeOne(t);
                			System.out.println("Este triángulo es virtual");
                		}
                		i.Next();
                	}
     	  }
     	  
/*     	  void removeTriangleAux()
     	  {
     	  		Iterador i = new Iterador(_trimesh.triangles());
                	while ( ! i.IsDone() ) {
                		Triangulo t = (Triangulo)i.Current();
                		if ( (t.vertex(0) instanceof VerticeFicticio) || (t.vertex(1) instanceof VerticeFicticio) || 
                		     (t.vertex(2) instanceof VerticeFicticio) ) {
                			_trimesh.triangles().removeOne(t);
                			//System.out.println("Se removió un triángulo con Vertices ficticios");
                		}
                		i.Next();
                	}
     	  }
*/     	  
     	  public void resetNeighbors(Triangulo t) {
     	  	for ( int i=0; i<3; i++ ) {
     	  		Triangulo n = t.neighbor(i);
     	  		if ( n != null ) {
     	  			int idx = n.getNeighborIndex(t);
     	  			t.resetNeighbors(i);
     	  			n.resetNeighbors(idx);
     	  		}
     	  	}
     	  }
     	  
     	  void removeTriangleAux()
     	  {
     	  		Iterador i = new Iterador(_trimesh.triangles());
                	while ( ! i.IsDone() ) {
                		Triangulo t = (Triangulo)i.Current();
                		if ( (t.vertex(0).isVirtual()) || (t.vertex(1).isVirtual()) || (t.vertex(2).isVirtual()) ) {
                			resetNeighbors(t);
                			_trimesh.triangles().removeOne(t);
                			t.setFicticio(true);
                			//System.out.println("Se removió un triángulo con Vertices ficticios");
                		} else t.setFicticio(false);
                		i.Next();
                	}
     	  }
     	  
        //Podra recibir la lista de Vertices y la de Vertices ficticios.
        TriMesh triangulate()
        {
        	int edgecollinear;
        	//_trimesh = this.trimesh();
        	_trimesh.triangles().clear();
        	
        	createAuxiliarElements();

		//System.out.println("Ingresó a triangulate y creo los elementos auxiliares");
		//Se prepara para recorrer la lista de vértices
        	Iterador i = new Iterador(_trimesh.vertices());
            while ( !i.IsDone() ) {
                	//System.out.println("Ingresó a while de i.IsDone() iterador vertices");
                	Vertice v = (Vertice)i.Current();
                	if ( !(v instanceof PuntoFicticio) )
                	{
                		Triangulo t = containerTriangle(v);
                		//System.out.println("Encontró triángulo contenedor");
                				
                		int indexedge = 0;
                		int indexn = 0;
				indexedge = t.longestEdgeIndex();
						
				edgecollinear = t.coLlinear(v);
						
				if ( edgecollinear != -1 ) {
					indexedge = edgecollinear;
					//System.out.println("Vertice cae en una arista");
				}
                		Triangulo n0 = t.neighbor(indexedge);
                		Triangulo n1 = t.neighbor((indexedge+1)%3);
                		Triangulo n2 = t.neighbor((indexedge+2)%3);
                				
                		Vertice v2 = t.vertex(indexedge); //vértice opuesto a arista 0       
            		Vertice v0 = t.vertex((indexedge+1)%3); //vértice opuesto a arista 1
            		Vertice v1 = t.vertex((indexedge+2)%3); //vértice opuesto a arista 2

                		if ( edgecollinear != -1 ) { //El Vertice cae en una arista de un triángulo			       
            			Triangulo newTriangle1 = t.bisect(v, edgecollinear);
            			_trimesh.addTriangle(newTriangle1);
            			//System.out.println("Se van a crear 4 nuevos triángulos");

            			if ( n0 != null ) {
            				if ( n0.neighbor(indexn) ==  t ) indexn = 0;
            				else if ( n0.neighbor(1) ==  t ) indexn = 1;
            				else if ( n0.neighbor(2) ==  t ) indexn = 2;
            						
                				Triangulo newTriangle2 = n0.bisect(v, indexn);
                				_trimesh.addTriangle(newTriangle2);
                				
                				setNeighbors(t, newTriangle2);
                				setNeighbors(n0, newTriangle1);

                				Triangulo nn1 = n0.neighbor((indexn+1)%3);
                				Triangulo nn2 = n0.neighbor((indexn+2)%3);
            				LegalizeEdge(v,newTriangle2,nn1);
            				LegalizeEdge(v,n0,nn2);
            			}
            			LegalizeEdge(v,t,n2);
            			LegalizeEdge(v,newTriangle1,n1);
                		}
                		else { //El Vertice cae en el interior de un triángulo
                			//System.out.println("Se van a crear 3 nuevos triángulos");
                			Triangulo t0 = new Triangulo(v2,v,v1);
                			_trimesh.addTriangle(t0);
                			Triangulo t1 = new Triangulo(v2,v0,v);
                			_trimesh.addTriangle(t1);
                			t.setVertex(indexedge, v); //Modifica triángulo para que se transforme en uno nuevo
                			
                			setNeighbors(t0, n1);
                			setNeighbors(t1, n2);
                			setNeighbors(t, t0);
                			setNeighbors(t, t1);
                			setNeighbors(t0, t1);

     					LegalizeEdge(v,t,n0);
     					LegalizeEdge(v,t0, n1);
            			LegalizeEdge(v,t1, n2);
                		}
            	}
            	i.Next();
            	//System.out.println("Hizo i.Next();");
            }
            //System.out.println("Antes de eliminar triángulo aux, hay " + _trimesh.triangles().cantidad_elementos + " elementos y " + _trimesh.vertices().cantidad_elementos + " Vertices");
            removeTriangleAux();
            System.out.println("Después de eliminar triángulo aux, hay " + _trimesh.triangles().cantidad_elementos + " elementos y " + _trimesh.vertices().cantidad_elementos + " Vertices");
            //System.out.println("Terminó y removió triángulos auxiliares");
            
            return _trimesh;
     	  }

	  void LegalizeEdge(Vertice vt1, Triangulo triangle, Triangulo neighbor)
        {
            int indext = 0;
            int indexn = 0;
            
            if ( neighbor != null ) {
        		if ( !neighbor.incircle(vt1) ) {
        			if ( neighbor.neighbor(indexn) ==  triangle ) indexn = 0;
            		if ( neighbor.neighbor(1) ==  triangle ) indexn = 1;
            		if ( neighbor.neighbor(2) ==  triangle ) indexn = 2;
            
            		if ( triangle.neighbor(indext) ==  neighbor ) indext = 0;
            		if ( triangle.neighbor(1) ==  neighbor ) indext = 1;
            		if ( triangle.neighbor(2) ==  neighbor ) indext = 2;
        
            		Vertice vn1 = neighbor.vertex(indexn); //vértice opuesto a arista
            		Triangulo neighbort1 = triangle.neighbor((indext+1)%3);
            		Triangulo neighborn1 = neighbor.neighbor((indexn+1)%3);
            
            		Triangulo neighbort2 = triangle.neighbor((indext+2)%3);
            		Triangulo neighborn2 = neighbor.neighbor((indexn+2)%3);

            		triangle.setVertex((indext+2)%3, vn1); //Modifica triángulo para que se transforme en uno nuevo
            		neighbor.setVertex((indexn+2)%3, vt1); //Modifica triángulo para que se transforme en uno nuevo
            
            		triangle.resetNeighbors();
            		neighbor.resetNeighbors();
            		
            		setNeighbors(triangle, neighborn1);
            		setNeighbors(neighbor, neighbort1);
            		setNeighbors(triangle, neighbort2);
            		setNeighbors(neighbor, neighborn2);
            		setNeighbors(triangle, neighbor);

           			LegalizeEdge(vt1, triangle, neighborn1);
            		LegalizeEdge(vt1, neighbor, neighborn2);
            		//System.out.println("Hizo un flip de arista");
            	}
            }
     }
}

