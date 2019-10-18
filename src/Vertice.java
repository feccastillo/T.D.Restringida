/************ Vertice.java **************/
import java.lang.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.*;

class Vertice extends ElementoGeometrico {
   protected 	float x;
   protected 	float y;
   protected	static int	lastid;
   protected	int	idv;
   Vertice		next;
   boolean		ficticio;

  Vertice() {
    x    = (float)0.0;
    y    = (float)0.0;
    idv   = 0;
    ficticio = false;
   }

  Vertice(float _x, float _y) {
    x    =  _x;
    y    =  _y;
    idv   = 0;
    ficticio = false;
  }
  
  Vertice(double _x, double _y) {
    x    =  (float)_x;
    y    =  (float)_y;
    idv   = 0;
    ficticio = false;
  }

  Vertice(int _x, int _y) {
     x    = (float)_x;
     y    = (float)_y;
     idv   = 0;
     ficticio = false;
   }

  public static void setLastIdv(int lid) {lastid=lid;}
  public static int getLastIdv() {
  	return lastid;
  }
  public float CordX() { return x; } 
  public float CordY() { return y; }
  public void setIdv(int id) { idv = id; }
  public int getIdv() { return idv; }
  
  public void setVirtual(boolean fict) {
     	ficticio = fict;
  }
     	
  public boolean isVirtual() {
     	return ficticio;
  }

  public float Distancia(Vertice p) {
    return  (float)Math.sqrt( (double)((this.CordX() - p.CordX())* (this.CordX() - p.CordX()) + (this.CordY() - p.CordY())*(this.CordY() - p.CordY())));

   }

  public void Imprime_Elemento() {
      System.out.print(CordX() + ":" + CordY());
   }
  
  public void Imprime_Elemento(int i) {
      System.out.println(CordX() + ":" + CordY() + ":: " + i);
   }

  public boolean ElementosIguales(ElementoGeometrico eg) {
      Vertice p;

	p = (Vertice)eg;
	
     	return ((p.CordX() == this.CordX()) && (p.CordY() == this.CordY()));
   }
  
  public boolean ElementosRepetido(Vertice p) {
     	return ((p.CordX() == this.CordX()) && (p.CordY() == this.CordY()));
   }
  
  
  public void drawCircle(Graphics g, int x, int y, int radius, Color c) {
  	int diameter = radius * 2;

  	g.setColor(c);
  	g.fillOval(x - radius, y - radius, diameter, diameter); 
  }
  
  public void Dibujar(Graphics g) {
      int x = (int)this.CordX();
  	int y = (int)this.CordY();

      drawCircle(g, x, y, 2, Color.red);
  }
  
  public ElementoGeometrico Clone() {
	return new Vertice((float)this.CordX(),(float)this.CordY());
   }
} //fin Vertice class
