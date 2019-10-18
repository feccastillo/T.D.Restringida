//************ Circulo.java **************
import java.lang.*;
import java.awt.*;

class Circulo extends ElementoGeometrico {
   private Vertice centro;
   private float radio, _radius;

   Circulo(Vertice _p1, Vertice _p2, Vertice _p3) {
 	centro = calcularCentro(_p1, _p2, _p3);
	color = Color.red;
	System.out.println("Imprimiendo desde el constructor");
	this.Imprime_Elemento();
   }
   
   Circulo(Triangulo t) {
 	Vertice p1 = t.vertex(0);
 	Vertice p2 = t.vertex(1);
 	Vertice p3 = t.vertex(2);
 	centro = calcularCentro(p1, p2, p3);
	color = Color.red;
   }

   public void Dibujar(Graphics g) {
	g.setColor(color);
      g.drawArc((int)(centro.CordX()-this.radio), (int)(centro.CordY()-this.radio), (int)(2*this.radio),(int)(2*this.radio),0,360);
      drawCircle(g, centro.CordX(), centro.CordY(), 2.0f, Color.blue);
   }
   
   public void drawCircle(Graphics g, float x, float y, float radius, Color c) {
  	int diameter = (int)(radius * 2);

  	g.setColor(c);
  	g.fillOval((int)(x - radius), (int)(y - radius), diameter, diameter); 
  }
  
    public Vertice calcularCentro(Vertice x1, Vertice x2, Vertice x3) {
    	double a, b, c, d, e, f, h, k, r;
    	
    	a = x1.CordX();
    	b = x1.CordY();
    	c = x2.CordX();
    	d = x2.CordY();
    	e = x3.CordX();
    	f = x3.CordY();
    	
    	h = (0.5) * ((Math.pow(a, 2) + Math.pow(b, 2)) * (f - d) + (Math.pow(c, 2) + Math.pow(d, 2)) * (b - f) + (Math.pow(e, 2) + Math.pow(f, 2)) * (d - b)) / (a * (f - d) + c * (b - f) + e * (d - b));
    	k = (0.5) * ((Math.pow(a, 2) + Math.pow(b, 2)) * (e - c) + (Math.pow(c, 2) + Math.pow(d, 2)) * (a - e) + (Math.pow(e, 2) + Math.pow(f, 2)) * (c - a)) / (b * (e - c) + d * (a - e) + f * (c - a));
    	radio = (float)Math.sqrt(Math.pow((h - a), 2) + Math.pow((k - b), 2));

	Vertice center = new Vertice((int)h, (int)k);
   	
    	return center;
  }
  
  public void Calcular_Centro_Circulo(Vertice p1, Vertice p2, Vertice p3) {
      float a,a1,a2,x,y;

	a = (p2.CordY()-p3.CordY())*(p2.CordX()-p1.CordX()) - (p2.CordY()-p1.CordY())*(p2.CordX()-p3.CordX());
	a1= (p1.CordX()+p2.CordX())*(p2.CordX()-p1.CordX()) + (p2.CordY()-p1.CordY())*(p1.CordY()+p2.CordY());
	a2= (p2.CordX()+p3.CordX())*(p2.CordX()-p3.CordX()) + (p2.CordY()-p3.CordY())*(p2.CordY()+p3.CordY());

	x = (a1*(p2.CordY()-p3.CordY()) - a2*(p2.CordY()-p1.CordY()))/a/2;
	y = (a2*(p2.CordX()-p1.CordX()) - a1*(p2.CordX()-p3.CordX()))/a/2;
	
	centro = new Vertice((float)x,(float)y);
  }
    
    public Vertice circumcenter()
    {
    		return centro;
    }
    
    public Vertice circumcenter(Triangulo t)
    {
    		double  denom1, denom2, denom3, num1, num2, num3, num4;
      	double  exp1, exp2, pp2x, pp2y;
       	double  coordX, coordY;
       	Vertice _circumcenter;
       	int sumaTotal=0;
       	
      	Vertice p1 = t.vertex(0);
      	Vertice p2 = t.vertex(1);
      	Vertice p3 = t.vertex(2);

       	if (( p2.CordX() == p1.CordX() ) || ( p3.CordY() == p2.CordY() ))
       	     denom1 = 0.0;
       	else    denom1 = (p2.CordX() - p1.CordX())*(p3.CordY() - p2.CordY());

       	if (( p1.CordY() == p2.CordY() ) || ( p2.CordX() == p3.CordX() ))
       	     denom2 = 0.0;
       	else    denom2 = (p1.CordY() - p2.CordY())*(p2.CordX() - p3.CordX());

       	denom3 = 2*(denom1 - denom2);

       	//Calculo del centro de la circulo

       	pp2x = p2.CordX()*p2.CordX();
       	pp2y = p2.CordY()*p2.CordY();

       	exp1 = -p1.CordX()*p1.CordX() + pp2x - p1.CordY()*p1.CordY() + pp2y;
       	exp2 = -pp2x + p3.CordX()*p3.CordX() - pp2y + p3.CordY()*p3.CordY();

       	if ( p3.CordY() == p2.CordY() )
       	     num1 = 0.0f;
       	else
       	     num1 = (p3.CordY() - p2.CordY())*exp1;

       	if ( p1.CordY() == p2.CordY() )
       	     num2 = 0.0f;
       	else
       	     num2 = (p1.CordY() - p2.CordY())*exp2;

       	// Coordenada X
       	coordX = (num1 + num2)/denom3;

       	if ( p2.CordX() == p1.CordX() )
       	     num3 = 0.0f;
       	else
       	     num3 = (p2.CordX() - p1.CordX())*exp2;

       	if ( p2.CordX() == p3.CordX())
       	     num4 = 0.0f;
       	else
       	     num4 = (p2.CordX() - p3.CordX())*exp1;

       	// Coordenada Y

       	coordY = (num3 + num4)/denom3;

       	_circumcenter = new Vertice((int)coordX, (int)coordY);
       	centro = _circumcenter;
       	
       	//radio = Calcula_Radio(p1);
       	
       	sumaTotal += Calcula_Radio(t.vertex(0));
		sumaTotal += Calcula_Radio(t.vertex(1));
		sumaTotal += Calcula_Radio(t.vertex(2));
		radio = sumaTotal/3.0f;
       	
       	//_radius = (float)Math.sqrt( (double)((_circumcenter.CordX()-p1.CordX())*(_circumcenter.CordX()-p1.CordX()) +	
		//		     (_circumcenter.CordY()-p1.CordY())*(_circumcenter.CordY()-p1.CordY())) );

    		return _circumcenter;
    }

     public float Calcula_Radio(Vertice p) {
       return (float)Math.sqrt((double)((centro.CordX() - p.CordX())*(centro.CordX() -p.CordX())+(centro.CordY() - p.CordY())*(centro.CordY() - p.CordY())));
     }
    
    public float radius() {
    		return (float)radio;
    }
    
    public void setRadius(float r) {
    		radio = r;
    }
    
    protected void Imprime_Elemento() {
    		System.out.println("Imprimiendo circunsferencia ");
    		System.out.println("Centro: ");
    		System.out.println("radio: " + radio);
    }
    protected void Imprime_Elemento(int i) {}
    
    public ElementoGeometrico Clone() {return this;}

 } //Fin Circulo class




