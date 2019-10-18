import java.awt.*;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.*;
import java.lang.Object.*;
import java.lang.reflect.Field;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class myPanel extends JPanel /*implements MouseListener*/ {
	private ListaFifo lista_vertices,lista_a_tratar,listCH, listBorde;
	private ListaFifo lista_voronoiEdges;
	private ListaFifo lista_convexHull,lista_ficticios,lista_poligonos,list_pol;
	private int PointX, PointY;
	private Image offscreen = null;
	private Graphics offgc=null;
	private Dimension dimAux;
	private Dimension dimCanvas;
	private Image dibujoAux;
	private Graphics gAux;
	private final boolean showBackground=true;
	private Color 	backcolor;
	private float 	xmin, xmax, ymin, ymax;
	private int 	id;
	TriMesh 	trimesh;
	private int 	nrobots;
   	private boolean	exitpoint;
   	private int		startpoints;
   	private boolean	flagstpuntos;
	private boolean 	circulo 	= false;
   	private boolean 	triangulacion = true;
   	private final	int	width;
   	private final	int	height;
   	private Color	bottomColor;
   	private BufferStrategy strategy;
   	private boolean 	image = false;
   	private Image	img=null;
   	
   	int imgWidth;
  	int imgHeight;
  	int frameWidth;
  	int frameHeight;
	
 	myPanel(int width, int height) {
 		//addMouseListener(this);
		startpoints = 0;
		trimesh = new TriMesh();
		id = 1;
		this.width  = width;
		this.height = height;
		this.setSize(new Dimension(width, height));
		dimCanvas = this.getSize();
		backcolor = Color.white;
		setBackground(backcolor);
 	}
 	
 	public int getId() {
 		return id;
 	}
 	
 	public TriMesh getTrimesh() {
 		return trimesh;
 	}
 	
 	public void setTrimesh(TriMesh trim) {
 		trimesh = trim;
 	}
 	
 	public void setBackGroundColor(Color c) {
 		backcolor = c;
 	}
	
 	public void setExitPoint(boolean exp) {
 		exitpoint = exp;
 	}
 	
 	public void setFlagPoints(boolean flagp, int stp) {
 		flagstpuntos = flagp;
		startpoints  = stp;
 	}

 	public void reconstituir() {
		Graphics g = getGraphics();

      	g.setColor(bottomColor);
      	g.fillRect(0,0,width,height);
  	}
  	
  	@Override
	public void update(Graphics g) {
  	  	paint(g);
  	}
  	
  	public Graphics getAuxG() {
  		return gAux;
  	}
  	
  	public Image getImageAux() {
  		return dibujoAux;
  	}
  	
  	public void setImage(Image imagen) {
  		img = imagen;
  		repaint();
  	}
  	
  	public void showBackGroundPanel() {
  		offscreen = createImage(getWidth(), getHeight());
  		if ( showBackground ) {
     			gAux.drawImage(offscreen, 0, 0, null);
     			setBackground(backcolor);
     		}
     	}
     	
     	public void setParameters(boolean circ, boolean tri) {
 		circulo 		= circ;
 		triangulacion 	= tri;
 	}

 	@Override
 	public void paint(Graphics g) {
		Vertice           curPt = null, pto = null, bpto = null;
     		Triangulo 	 	t;
     		Iterador 		itPto, itPol;
     		lista_poligonos 	= trimesh.triangles();
		lista_vertices	= trimesh.vertices();

     		if ( gAux==null || dimAux==null || dimCanvas.width != dimAux.width || dimCanvas.height != dimAux.height ) {
     			dimAux    = dimCanvas;
     			dibujoAux = createImage(dimAux.width, dimAux.height);
     			gAux      = dibujoAux.getGraphics();
     		}
     		
     		this.showBackGroundPanel();

     		if ( trimesh.vertices().size() > 0 ) {
     			Iterador itv = new Iterador(trimesh.vertices());
		   	while ( !itv.IsDone() ) {
				Vertice v = (Vertice)itv.Current();
				Color col = Color.red;
				drawPoint(gAux, (int)v.CordX(), (int)v.CordY(), 3, col);
				itv.Next();
		    	}
     		}
     		
     		if ( trimesh.triangles().size() > 0 ) {
		   	if ( triangulacion ) {
		   		itPol = new Iterador(trimesh.triangles());
		   		while ( !itPol.IsDone() ) {
					t = (Triangulo)itPol.Current();
					if ( t.isFicticio() ) {
						System.out.println("Dibujando un triángulo ficticio");
					}
					Color c = t.getColor();			
					DrawTriangle(gAux, t, c);
					
					itPol.Next();
		    		}
		    	}
		    	
		    	if ( circulo ) {
		    		//System.out.println("Mostrando circulos");
		    		Iterador it = new Iterador(lista_poligonos);
		   		while ( !it.IsDone() ) {
					t = (Triangulo)it.Current();
 		   		 	this.drawCircle(gAux, t);
					it.Next();
		    		}
		    	}	
		}
		g.drawImage(dibujoAux, 0, 0, this);
		
		if ( img != null ) {
     			drawImage(img, g);
     		}
     		
    }
	
    public void drawImage(Image ima, Graphics g) {
  	  imgWidth  = ima.getWidth(null);
        imgHeight = ima.getHeight(null);     
        Dimension height = getSize();
         
        double imgAspect = (double) imgHeight / imgWidth;
 
        frameWidth  = this.getWidth();
        frameHeight = this.getHeight();  //-50;
         
        double frameAspect = (double) frameHeight / frameWidth;
 
        int x1 = 0; // Posición X arriba izquierda
        int y1 = 0; // Posición Y arriba izquierda
        int x2 = 0; // Posición X derecho abajo
        int y2 = 0; // Posición Y derecho abajo
         
        if ( (imgWidth < frameWidth) && (imgHeight < frameHeight) ) {
            // Si la imagen es más pequeña que el canvas
            x1 = (frameWidth - imgWidth)  / 2;
            y1 = (frameHeight - imgHeight) / 2;
            x2 = imgWidth + x1;
            y2 = imgHeight + y1;
             
        } else {
            if (frameAspect > imgAspect) {
                y1 = frameHeight;
                // Mantener la relación de aspecto de la imagen
                frameHeight = (int) (frameWidth * imgAspect);
                y1 = (y1 - frameHeight) / 2;
            } else {
                x1 = frameWidth;
                // Mantener la relación de aspecto de la imagen
                frameWidth = (int) (frameHeight / imgAspect);
                x1 = (x1 - frameWidth) / 2;
            }
            x2 = frameWidth + x1;
            y2 = frameHeight + y1;
        }

    	  boolean bool = g.drawImage(ima, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
    	  //g.drawImage(ima, 0, 0, height.width, height.height, null);
    	  //boolean bool = g.drawImage(ima, 0, 0, this);
	  repaint();
	  setOpaque(false);
	  super.paintComponent(g);
    }
    
    	public void addListaVertices(Point aux){

       		String type = null;
       		int cx = aux.y;
       		int cy = aux.x;
       		Vertice  punto = new Vertice((float)cx, (float)cy);
       		
       		Vertice 	curPt;        
       		Iterador 	it;
       		int		numpoints = 0;

       		int lidv = Vertice.getLastIdv() + 1;
       		punto.setIdv(lidv);
       		Vertice.setLastIdv(lidv);
       		
       		if ( ! exitpoint && !flagstpuntos ) {
       			trimesh.addVertex(punto);
       			
       			Color col = Color.red;
    			drawPoint(gAux, (int)punto.CordX(), (int)punto.CordY(), 8, col);
       		
       			if ((lista_vertices != null) && !lista_vertices.buscar_elemento_repetido(punto)) {
    			  	lista_vertices.insertar_elemento(new NodoLista((Vertice)punto));
      			}
      		}
      			   	
       		if ( id == 1 ) {
          		xmin = cx; xmax = cx;
          		ymin = cy; ymax = cy;
          	}
          	id++;
                        
          	if ( cx < xmin ) xmin = cx;
          	if ( cx > xmax ) xmax = cx;
          	if ( cy < ymin ) ymin = cy;
          	if ( cy > ymax ) ymax = cy;

       		repaint();
     	
    	}

//    	@Override
//    	public void mouseClicked(MouseEvent evt) {}
//
//    	@Override
//    	public void mouseExited(MouseEvent e) {}
//
//     	@Override
//    	public void mouseReleased(MouseEvent e) {}
//
//    	@Override
//    	public void mouseEntered(MouseEvent e) {}
//    
//    	@Override
//    	public void mousePressed(MouseEvent evt) {
//   		String type = null;
//   		int cx = evt.getX();
//   		int cy = evt.getY();
//   		Vertice  punto = new Vertice((float)cx, (float)cy);
//   		
//   		Vertice 	curPt;        
//   		Iterador 	it;
//   		int		numpoints = 0;
//
//   		int lidv = Vertice.getLastIdv() + 1;
//   		punto.setIdv(lidv);
//   		Vertice.setLastIdv(lidv);
//   		
//   		if ( ! exitpoint && !flagstpuntos ) {
//   			trimesh.addVertex(punto);
//   			
//   			Color col = Color.red;
//			drawPoint(gAux, (int)punto.CordX(), (int)punto.CordY(), 8, col);
//   		
//   			if ((lista_vertices != null) && !lista_vertices.buscar_elemento_repetido(punto)) {
//			  	lista_vertices.insertar_elemento(new NodoLista((Vertice)punto));
//  			}
//  		}
//  			   	
//   		if ( id == 1 ) {
//      		xmin = cx; xmax = cx;
//      		ymin = cy; ymax = cy;
//      	}
//      	id++;
//                    
//      	if ( cx < xmin ) xmin = cx;
//      	if ( cx > xmax ) xmax = cx;
//      	if ( cy < ymin ) ymin = cy;
//      	if ( cy > ymax ) ymax = cy;
//
//   		repaint();
// 	}
 	
 	public void setMinMax() {
 		trimesh.setMinMax(xmin, xmax, ymin, ymax);
 	}
  		
	protected void DrawTriangle(Graphics g, Triangulo t, Color c) {
		t.Dibujar(g,c);
		repaint();
   	}

  	public void drawCircle(Graphics g, Triangulo tr) {
  		tr.setCirculo();
   		Circulo circuns = tr.getCirculo();
		circuns.Dibujar(g);
		repaint();
  	}
  	
  	public void drawCircle(Graphics g, int x, int y, float radius, Color c) {
  		int diameter = (int)(radius * 2);

  		g.setColor(c);
  		g.fillOval((int)(x - radius), (int)(y - radius), diameter, diameter); 
  		repaint();
  	}
  	
  	public Graphics getG() {
  		return gAux;
  	}
  
  	public void drawPoint(int x, int y, int r, Color c) {
      	Graphics g = getGraphics();

      	int radius = r;
      	drawCircle(g, x, y, radius, c);
  	}
  	
  	public void drawPoint(Graphics g, int x, int y, int r, Color c) {
      	int radius = r;
      	drawCircle(g, x, y, radius, c);
  	}
  	
  	public void drawEdge(Graphics g, Edge ve, Color color) {
		Vertice ini = ve.initial();
  		Vertice ter = ve.terminal();
  		int x0 = (int)ini.CordX();
  		int y0 = (int)ini.CordY();
  		int x1 = (int)ter.CordX();
  		int y1 = (int)ter.CordY();
		
		g.setColor(color);
		g.drawLine(x0, y0, x1, y1);
  	}
 } // Fin class myPanel

