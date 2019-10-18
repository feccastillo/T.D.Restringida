      
class RectangAreaCriterion extends Criterion
{      
    	private double	_x0, _y0, _x1, _y1, _x2, _y2, _x3, _y3;
      private Vertice	 v0, v1, v2, v3;	
      boolean  		_biggest;
        
      RectangAreaCriterion(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3)
      {
		super();
		
		_x0 = x0;
		_y0 = y0;
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
		_x3 = x3;
		_y3 = y3;
		
		v0 = new Vertice((float)x0, (float)y0);
		v1 = new Vertice((float)x1, (float)y1);
		v2 = new Vertice((float)x2, (float)y2);
		v3 = new Vertice((float)x3, (float)y3);
	}
	  
	int Area2(Vertice p, Vertice q, Vertice r)
	{
		return ((int)p.CordX()*(int)q.CordY() - (int)p.CordY()*(int)q.CordX() + (int)p.CordY()*(int)r.CordX() - (int)p.CordX()*(int)r.CordY() + (int)q.CordX()*(int)r.CordY() - (int)r.CordX()*(int)q.CordY());
   	}

  	boolean lefton(Vertice p, Vertice q, Vertice r)
  	{
    		return Area2(p,q,r) >= 0.0;
   	}
   	  
   	boolean contiene_punto(Vertice q)
   	{
    		return ( lefton(v0,v1,q) && lefton(v1,v2,q) && lefton(v2,v3,q) && lefton(v3,v0,q) ); 
    	}
	  
	boolean test(Triangulo triangle)
      {
            triangle.setCentroid();
            Vertice c = triangle.centroid();
            	
            return contiene_punto(c);
      }
}

