import java.awt.Color;
import java.awt.Graphics;

class TriMesh {
	ListaFifo 	lt, laux, selectedTriangles;
	ListaFifo 	lp;
	int  countsel;
	int _np;
	float xmin, xmax, ymin, ymax;
	
	TriMesh()
	{
		lt  = new ListaFifo();
		lp  = new ListaFifo();
		selectedTriangles  = new ListaFifo();
		xmin = xmax = ymin = ymax = -1;
	}
	
	public void addTriangle(Triangulo triangle)
      {
            assert triangle != null;

            NodoLista nodo = new NodoLista((ElementoGeometrico)triangle);
            triangle.setNodo(nodo);
            lt.insertar_elemento(nodo);
      }
      
      public void addTriangle(NodoLista nodo)
      {
            assert nodo != null;

            Triangulo eg = (Triangulo)nodo.eg;
            eg.setNodo(nodo);
            lt.insertar_elemento(nodo);
      }
      
      public void addSelectedTriangle(Triangulo triangle)
      {
            assert triangle != null;

            NodoLista nodo = new NodoLista((ElementoGeometrico)triangle);
            triangle.setNodo(nodo);
            selectedTriangles.insertar_elemento(nodo);
      }
      
      public void addSelectedTriangle(NodoLista nodo)
      {
            assert nodo != null;

            Triangulo eg = (Triangulo)nodo.eg;
            eg.setNodo(nodo);
            selectedTriangles.insertar_elemento(nodo);
      }
      
      public void addVertex(Vertice p)
      {
            assert p != null;

            NodoLista nodo = new NodoLista(p);
            p.setNodo(nodo);
            lp.insertar_elemento(nodo);
      }
      
      public void addVertex(NodoLista nodo)
      {
            assert nodo != null;

            Vertice eg = (Vertice)nodo.eg;
            eg.setNodo(nodo);
            lp.insertar_elemento(nodo);
      }
	      
      public ListaFifo triangles()
      {
            return lt;
      }
      
      public ListaFifo striangles()
      {
            return selectedTriangles;
      }
      
      public ListaFifo vertices()
      {
            return lp;
      }
      
      public void clearMesh() {
      	lt.clear();
      	lp.clear();
      	selectedTriangles.clear();
      }
      
      public void printMinMax()
      {
      	System.out.println("TriMesh::Máximos y mínimos");
      	System.out.println("(" + this.retXmin() + ", " + this.retXmax() + ")");
  		System.out.println("(" + this.retYmin() + ", " + this.retYmax() + ")");
      }
            
      void setMinMax(float xmi, float xma, float ymi, float yma)
      {
        	xmin = xmi;
        	xmax = xma;
        	ymin = ymi;
        	ymax = yma;
      }
      
      float retXmin()
      {
        	return xmin;
      }
        
      float retXmax()
      {
        	return xmax;
      }
        
      float retYmin()
      {
        	return ymin;
      }
        
      float retYmax()
      {
        	return ymax;
      }
      
      int countSelected()
	{
		return countsel;
	}
	
	public void select(Criterion criterion)
      {
            countsel = 0;
            	
            selectedTriangles.clear();
            Iterador i = new Iterador(triangles());
   		while ( !i.IsDone() ) {
                	Triangulo t = (Triangulo)i.Current();
                	
                	t.setSelected(criterion.test(t));
                	if ( t.isSelected() ) {
                 		addSelectedTriangle(t);
                 		t.setNewColor(Color.white);
                 		countsel++;
                  }
                  i.Next();	
             }
      }
}
