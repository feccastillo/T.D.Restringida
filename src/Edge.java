import java.lang.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.*;

class Edge extends ElementoGeometrico {
    	protected Vertice a;
    	protected Vertice b;
    	protected int num_seg;
    	protected float length;

    	Edge() {
    	   	a = null;
    	   	b = null;
    	}

    	Edge(Vertice _prim, Vertice _ult) {
    	   	a = _prim;
    	   	b = _ult;
    	   	length = (float)distance();
    	 }

   	Vertice initial() {
   		return a;
   	}
   	
   	Vertice terminal() {
   		return b;
   	}
   	
   	public double distance()
      {
		float dis = (b.CordX()-a.CordX())*(b.CordX()-a.CordX()) + (b.CordY()-a.CordY())*(b.CordY()-a.CordY());
		
            return Math.sqrt((double)dis);
      }

      // biseccion: entrega el punto medio de este lado.
  	public Vertice biseccion() {
    		return new Vertice( (a.CordX()+b.CordX())/2, (a.CordY()+b.CordY())/2 );
  	}
  	
  	public void Dibujar(Graphics g) {
		Vertice ini = this.initial();
  		Vertice ter = this.terminal();
  		int x0 = (int)ini.CordX();
  		int y0 = (int)ini.CordY();
  		int x1 = (int)ter.CordX();
  		int y1 = (int)ter.CordY();
		
		g.setColor(Color.red);
		g.drawLine(x0, y0, x1, y1);
  	}
  	
  	protected void Imprime_Elemento() {
  		System.out.print("[(");
  		a.Imprime_Elemento();
  		System.out.print("), (");
  		b.Imprime_Elemento();
  		System.out.println(")]");
  	}
   	protected void Imprime_Elemento(int i) {}
   	
   	public Edge Clone() {return this;}
   	
} //fin Edge class
   
