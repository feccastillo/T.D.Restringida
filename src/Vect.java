/************ Vect.java **************/
import java.lang.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.*;

class Vect extends ElementoGeometrico {
   float x,y;

   Vect() {
	x = 0;
	y = 0;
    }

   Vect(float _x, float _y) {
	x = _x;
	y = _y;
    }

   public float CordX() {
       return x;
     }

   public float CordY() {
       return y;
     }

   public void Imprime_Elemento() {
	System.out.println(CordX() + ":" + CordY());
    }

   public float Modulo() {
	return (float)Math.sqrt((double)(CordX()*CordX() + CordY()*CordY()));
    }

   public float ProductoVertice(Vect v) {
	return (this.CordX()*v.CordX() + this.CordY()*v.CordY());
    }

   public float ProductoCruz(Vect v) {
	return (this.CordX()*v.CordY() - this.CordY()*v.CordX());
    }

   public ElementoGeometrico Clone() {
	return new Vect((float)this.CordX(),(float)this.CordY());
   }
   
   public void Dibujar(Graphics g) {}

   protected void Imprime_Elemento(int i) {}
 } // fin Vector class

