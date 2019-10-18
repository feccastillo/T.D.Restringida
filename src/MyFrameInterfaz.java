import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

//import Delaunay.ListaFifo;
//import Delaunay.NodoLista;
//import Delaunay.Vertice;

//import Delaunay.ListaFifo;
//import Delaunay.Vertice;

import java.util.*;
import java.lang.Object.*;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.awt.image.BufferStrategy;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.util.ArrayList;
public class MyFrameInterfaz extends JFrame { 
   ListaFifo    	 	lista_vertices,lista_a_tratar,listCH, listBorde;
   ListaFifo  		lista_convexHull,lista_ficticios,lista_poligonos,list_pol;
   ListaFifo  		lista_voronoiEdges, lista_Hollow ,lista_edge,lista_aux,lista_pologonosT;
   ListaFifo[]		listas_hollows;
   DelaunayTStrategy	delaunayt;
   TriMesh			trimesh;
   Image 			puntoAzul;
   //JButton  		butReset      	= new JButton("Reset");
   //JButton  		butExit       	= new JButton("Exit");
   JButton  		butTriangular   = new JButton("Triangulacion Delaunay");
   //JButton  		butMostrar    	= new JButton("Mostrar Circulo");
   //JButton  		butCircle      	= new JButton("Circulos");
   JButton			butFiltro		= new JButton("Filtrar");
   JButton			butVertices		= new JButton("Dibujar Vertices");
   JButton			butRestringir	= new JButton("Restringir");
   JButton			butGenerar		= new JButton("Generar Vertices");
   JLabel			labeln;
   Panel  			panelRGB;
   Panel  			panel;
   PopupMenu 		popup;
   boolean 			triangulacion = true;
   PoligonoConvexo 	p_c, pol_circulo;
   Vertice 			primero, segundo;
   DataFile			iodata;
   Color			bottomColor;
   private float xmin, xmax, ymin, ymax;

   final	int		width = 1350;
   final	int		height = 720;
   Image			img=null;
   DelaunayTStrategy 	dt;
   String			nameImage= null;
   Criterion		criterio = null;
   boolean			referencep = false;
   Vertice			refPoint=null;
   boolean			refined = false;
   boolean 	circulo 	= false;
   String			globalFileName=null;
   boolean robot 		= false;
   JFrame			jf;
   public static myPanel			mc;// revisar el static
   private BufferStrategy strategy;
   int 		nrobots;
   private int id;
   private int Contador=0;
   private boolean FlagHollow=false;

//  private Graphics 		g;
   
   //canny
// edgeDetector
	public static CannyAlgoritmo edgeDetector;
	// Kernel Gauss
	public static JComboBox<String> gaussKerBox;
	
	public static JTextField highthresh;
	public static JTextField lowthresh;
	public static JTextField sigma;
//	public static JFrame frame;

	//listas locas
	static ListaFifo lista_Vertices;
	static ArrayList<Point> milista = new ArrayList<>();
	static ArrayList<Point> banlista = new ArrayList<>();
	static ArrayList<Point> hollowlista = new ArrayList<>();
	static ArrayList<Point> hollowFinal = new ArrayList<>();
	//movimientos locos
	static Point[] movimientos= { /*primer anillo*/new Point(0,1), new Point(1,1), new Point(1,0), new Point(1,-1), new Point(0,-1),
			/*segundo anillo*/new Point(-1,-1), new Point(-1, 0), new Point(-1,1), new Point(0,2), new Point(1,2), new Point(2,2), 
			new Point(2,1), new Point(2,0), new Point(2,-1), new Point(2,-2), new Point(1,-2), new Point(0,-2), 
			new Point(-1,-2), new Point(-2,-2), new Point(-2,-1), new Point(-2,0), new Point(-2,1), new Point(-2,2), new Point(-1,2),
			/*tercer anillo*/new Point(0,3),new Point(1,3),new Point(2,3),new Point(3,3),new Point(3,2),new Point(3,1),new Point(3,0),
			new Point(3,-1),new Point(3,-2),new Point(3,-3),new Point(2,-3),new Point(1,-3),new Point(0,-3),new Point(-1,-3),new Point(-2,-3),
			new Point(-3,-3),new Point(-3,-2),new Point(-3,-1),new Point(-3,0),new Point(-3,1),new Point(-3,2),new Point(-3,3),new Point(-3,-2),new Point(-3,-1)};
	
  MyFrameInterfaz(String name) {      
  	super("Triangulacion Delaunay Restringida");
  	
  	this.enableEvents(AWTEvent.MOUSE_EVENT_MASK
      //  | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK
        );
  	edgeDetector = new CannyAlgoritmo();
  	Vertice.setLastIdv(0);
  	trimesh = new TriMesh();
  	id = 1;
  	jf = this;
  	
	// kernel de selecci�n
	gaussKerBox = new JComboBox<String>();
	gaussKerBox.addItem("3x3");
	gaussKerBox.addItem("4x4");
	gaussKerBox.addItem("5X5");
	gaussKerBox.addItem("6x6");
	gaussKerBox.addItem("7x7");
	gaussKerBox.addItem("8x8");
	gaussKerBox.addItem("9x9");
	gaussKerBox.addItem("10x10");
	gaussKerBox.addItem("12x12");
	gaussKerBox.addItem("15x15");
	gaussKerBox.addItem("20x20");

	// input umbral superior
	highthresh = new JTextField("10", 5);

	// input umbral inferior
	lowthresh = new JTextField("1", 5);

	// input sigma o desviacion standar
	sigma = new JTextField("1.0", 5);

  	//Adhiere el menu al jmenu bar
     	JMenuBar mbar = new JMenuBar();
     	setJMenuBar(mbar);
     	JMenu file 	= new JMenu("File");
     	//JMenu bottom = new JMenu("Window");
     	JMenu utilidad = new JMenu("Utilidades");
    	mbar.add(file);
    	//mbar.add(bottom);
    	mbar.add(utilidad);

	lista_vertices 	= null;
	lista_a_tratar	= null;
	lista_poligonos	= null;//contiene los pologonos generados en la TDD
	lista_pologonosT= null;//copia de lista de poligonos
	lista_Hollow	= null;// para concerbar las lista de los hollows
	lista_edge		= null;// para concerbar los bordes
	lista_aux		= null;
	list_pol		= null;
      listCH		= null;
	lista_convexHull	= null;
	lista_voronoiEdges= null;
	p_c 			= null;	
	pol_circulo    	= null;
	
	if ( name != null ) {
		img = getToolkit().getImage(name);
	}

	// Crea menu items para el primer menu de MenuBar.
    	JMenuItem[] menuitem = new JMenuItem[6];
    	menuitem[0] = new JMenuItem("Cargar Puntos...");
    	menuitem[1] = new JMenuItem("Load M2d File...");
    	menuitem[2] = new JMenuItem("Cargar Imagen");
    	menuitem[3] = new JMenuItem("Guardar Puntos");
    	menuitem[4] = new JMenuItem("Save M2d format...");
//    	menuitem[5] = new JMenuItem("Print Points");
//    	menuitem[6] = new JMenuItem("Print Polygons");
    	menuitem[5] = new JMenuItem("Exit");
    	
    	file.add(menuitem[0]);
    	file.add(menuitem[1]);
    	file.add(menuitem[2]);
    	file.addSeparator();
    	file.add(menuitem[3]);
    	file.add(menuitem[4]);
//    	file.add(menuitem[5]);
//    	file.add(menuitem[6]);
    	file.add(menuitem[5]);

    	menuitem[0].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { loadTextPoints(); }
    	});
    	menuitem[1].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { loadM2d(); }
    	});
    	menuitem[2].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { loadImage(); }
    	});
    	menuitem[3].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { SaveTextPoints(); }
    	});
    	menuitem[4].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { SaveM2DFormat(); }
    	});
//    	menuitem[5].addActionListener(new ActionListener() {
//    	  public void actionPerformed(ActionEvent e) { PrintPoints(); }
//    	});
//    	menuitem[6].addActionListener(new ActionListener() {
//    	  public void actionPerformed(ActionEvent e) { PrintPolygons(); }
//    	});
    	menuitem[5].addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) { System.exit(0); }
    	});

//	JMenuItem[] menubottom  = new JMenuItem[4];
//	menubottom[0] = new JMenuItem("New Bottom Color");
//	menubottom[1] = new JMenuItem("Generar Vertice");
//	menubottom[2] = new JMenuItem("Refresh");
//	menubottom[3] = new JMenuItem("asdsad");
//    	bottom.add(menubottom[0]);
//    	bottom.add(menubottom[1]);
//    	bottom.add(menubottom[2]);
//    	bottom.add(menubottom[3]);
//    	
//    	menubottom[0].addActionListener(new ActionListener() {
//    	  public void actionPerformed(ActionEvent e) {CirculosX();}
//    	});
//    	menubottom[1].addActionListener(new ActionListener() {
//    	  public void actionPerformed(ActionEvent e) {SearchBorde();}
//    	});
//    	menubottom[2].addActionListener(new ActionListener() {
//    	  public void actionPerformed(ActionEvent e) { refresh();}
//    	});
//    	menubottom[3].addActionListener(new ActionListener() {
//      	  public void actionPerformed(ActionEvent e) { }
//      	});
    	
    	
    	
    	JMenuItem[] menubottomutil  = new JMenuItem[3];
    	menubottomutil[0] = new JMenuItem("Mostrar Circulos");
    	menubottomutil[1] = new JMenuItem("Imprimir Vertices");
    	menubottomutil[2] = new JMenuItem("Cantidad Poligonos");
        	utilidad.add(menubottomutil[0]);
        	utilidad.add(menubottomutil[1]);
        	utilidad.add(menubottomutil[2]);
        	
        	menubottomutil[0].addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) {CirculosX();}
        	});
        	menubottomutil[1].addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) {PrintPoints(); }
        	});
        	menubottomutil[2].addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) { PrintPolygons(); }
        	});

    	
    	

	bottomColor=Color.lightGray;
	triangulacion 		= true;
	circulo 		= false;	
	//butReset.setEnabled(true);
	//butExit.setEnabled(true);
	butTriangular.setEnabled(false);
	//butMostrar.setEnabled(false);
	//butCircle.setEnabled(true);
	butFiltro.setEnabled(false);
	butVertices.setEnabled(false);
	butRestringir.setEnabled(false);
	butGenerar.setEnabled(false);
	
	panel = new Panel();
  	panel.setLayout(new GridLayout(1,8));
  	panel.add(butFiltro);
  	panel.add(butGenerar);
  	panel.add(butVertices);
	panel.add(butTriangular);
	panel.add(butRestringir);
    add(panel, BorderLayout.SOUTH);
	lista_vertices	= new ListaFifo();
	lista_Hollow	= new ListaFifo();
	lista_edge		= new ListaFifo();
	lista_pologonosT = new ListaFifo();
	lista_aux		= new ListaFifo();
	listas_hollows  = new ListaFifo[100];

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setPreferredSize(new Dimension(width,height));
    	setBackground(bottomColor);
    	pack();
    	setResizable(false);
    	setVisible(true);

  	mc = new myPanel(width,height);
  	add(mc, BorderLayout.CENTER); 	
  	actionButtonEvent();
  }

  public void loadPoints() {
	//Ejecuta Load Text Points
	iodata = new DataFile(true);
	String fname = iodata.getFileName();
	System.out.println("Leyendo Archivo " + fname);
	trimesh.clearMesh();
	TriMesh trim = iodata.loadPoints(fname, trimesh);
	System.out.println("Vertices leidos");
	lista_vertices = trim.vertices();
	trimesh = trim;
	xmin = trimesh.retXmin();
	xmax = trimesh.retXmax();
	ymin = trimesh.retYmin();
	ymax = trimesh.retYmax();
	mc.setTrimesh(trimesh);
	id++;
	globalFileName = fname;
	System.out.println("Imprimiendo MinMax después de leer vertices");
	trimesh.printMinMax();
	setTitle("Movimiento de robots en el plano y con obstáculos::: " + "File: " + fname + ":::" + "Points: " + trimesh.vertices().size());
  }
  
  public void loadTextPoints() {
	//Ejecuta Load Text Points
	String name=null;
	
	JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      int result = chooser.showOpenDialog(null);
      
      if ( result == JFileChooser.APPROVE_OPTION ) {
      	name = chooser.getSelectedFile().getPath();
      }
	iodata = new DataFile(name);
	String fname = iodata.getFileName();
	System.out.println("Leyendo Archivo " + fname);

	trimesh.clearMesh();
	TriMesh trim = iodata.loadPoints(fname, trimesh);
	System.out.println("Vertices leidos");

	lista_vertices = trim.vertices();
	trimesh = trim;
	xmin = trimesh.retXmin();
	xmax = trimesh.retXmax();
	ymin = trimesh.retYmin();
	ymax = trimesh.retYmax();
	id++;
	mc.setTrimesh(trimesh);
	globalFileName = chooser.getSelectedFile().getName();
	System.out.println("Imprimiendo MinMax después de leer vertices");
	trimesh.printMinMax();
	setTitle("Movimiento de robots en el plano y con obstáculos::: " + "File: " + chooser.getSelectedFile().getName() + ":::" + "Points: " + trimesh.vertices().size());
	butTriangular.setEnabled(true);
	mc.repaint();
  }
  
    public void loadImage() {
  	//Ejecuta Load Image (JPG, PNG)
	String name=null;
	Graphics g = getGraphics();
	
	JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      Toolkit tk = Toolkit.getDefaultToolkit(); 
      
      int result = chooser.showOpenDialog(null);
      
      if ( result == JFileChooser.APPROVE_OPTION ) {
      	name = chooser.getSelectedFile().getPath();
      	img = getToolkit().getImage(name);
      }
      globalFileName = chooser.getSelectedFile().getName();
      nameImage = chooser.getSelectedFile().getName();
	setTitle("Generador de Mallas Geometricas::: " + "File: " + chooser.getSelectedFile().getName() + ":::" + "Points: " + trimesh.vertices().size());
	mc.setImage(img);
	butFiltro.setEnabled(true);
  }
  public void loadM2d() {
	//Ejecuta Load M2d format file
	String name=null;
	
	JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      int result = chooser.showOpenDialog(null);
      
      if ( result == JFileChooser.APPROVE_OPTION ) {
      	name = chooser.getSelectedFile().getPath();
      }
      trimesh.clearMesh();
	M2dFormatIO inputm2d = new M2dFormatIO(chooser.getSelectedFile().getName(), trimesh);
	System.out.println("Leyendo Archivo M2d: " + chooser.getSelectedFile().getName());
	inputm2d.load();
	
	lista_vertices 	= trimesh.vertices();
	lista_poligonos 	= trimesh.triangles();
	xmin = trimesh.retXmin();
	xmax = trimesh.retXmax();
	ymin = trimesh.retYmin();
	ymax = trimesh.retYmax();
	id++;
	globalFileName = chooser.getSelectedFile().getName();
	System.out.println("Imprimiendo MinMax después de leer vertices");
	trimesh.printMinMax();
	setTitle("Movimiento de robots en el plano y con obstáculos::: " + "File: " + chooser.getSelectedFile().getName() + ":::" + "Points: " + trimesh.vertices().size() + " Triangles: " + trimesh.triangles().size());
  }

  public void SavePoints() {
	//Ejecuta Save Points
	iodata = new DataFile(false);
	iodata.savePoints(trimesh.vertices());
  }
  
  public void SaveTextPoints() {
	//Ejecuta Save Text Points
	iodata = new DataFile(false);
	String fname = iodata.getFileName();
	iodata.savePoints(fname, trimesh.vertices());
  }
  
  public void SaveM2DFormat() {
	//Ejecuta Save M2D Format
	iodata = new DataFile(false);
	String fname = iodata.getFileName();
	iodata.saveM2d(fname, trimesh.vertices(), lista_poligonos);
  }
  
  public void PrintPoints() {
	//Ejecuta Imprime lista de VerticesSystem.out.println("Punto de salida: " + "(" + expunto.CordX() + ", " + expunto.CordY() + ")");
	System.out.println("Lista Vertices de entrada:");
      //lista_vertices.imprime_lista_elementos();
      trimesh.vertices().imprime_lista_elementos();
	System.out.println(lista_vertices.NumeroElementos()+ "  vertices");
  }
  
  public void CirculosX(){
	  circulo = true;
		triangulacion   = true;
		mc.setParameters(circulo, triangulacion);
		System.out.println("Muestra circunsferencias");
		mc.repaint();
  }
  
//  public void ReducirGeneral(){
//		Point cero;
//		Point tres;
//		while(milista.size()>0){
//			cero=new Point(milista.remove(0));
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			tres=new Point(milista.remove(0));
//			mc.addListaVertices(cero);
//			mc.addListaVertices(tres);
//		}
//  }
  
  public void ReducirListaY(){//cuando la X es invariable elimino las y
	  for(int i=0;i<milista.size()-1;i++){//cuando y es invariable elimino X
		  if(milista.get(i).x == milista.get(i+1).x){
			  boolean flag=true;		  
			  while(flag && milista.size()>i+2){
				  if(milista.get(i+1).x == milista.get(i+2).x){
					  milista.remove(i+1);
				  }
				  else{flag=false;}
				  
			  }
		  }
	  }
  }
  public void ReducirListaX(){
	  for(int i=0;i<milista.size()-1;i++){//cuando y es invariable elimino X
		  if(milista.get(i).y == milista.get(i+1).y){
			  boolean flag=true;		  
			  while(flag && milista.size()>i+2){
				  if(milista.get(i+1).y == milista.get(i+2).y){
					  milista.remove(i+1);
				  }
				  else{flag=false;}
				  
			  }
		  }
	  }
  }
  public void ReducirAcendente(){
	  for(int i=0;i<milista.size()-2;i++){//cuando y es invariable elimino X
		  System.out.println("entro al for y");
		  if(milista.get(i).y == milista.get(i+1).y+1 && milista.get(i).x == milista.get(i+1).x+1){
			  boolean flag=true;		  
			  while(flag){
				  if(milista.get(i+1).y == milista.get(i+2).y+1 && milista.get(i+1).x == milista.get(i+2).x+1 && milista.size()>i+2){
					  milista.remove(i+1);
				  }
				  else{flag=false;}
				  
			  }
		  }
	  }
  }
  public void RestringirHollows_simple(){
//		System.out.print("Cantidad de poligonos :");
//		System.out.println(lista_poligonos.NumeroElementos());
//		//System.out.println(trimesh.lt.NumeroElementos());
//		System.out.print("Cantidad de vertices :");
//		System.out.println(lista_vertices.NumeroElementos());
//		System.out.print("Cantidad lista hollow final :");
//		System.out.println(hollowFinal.size());
//		System.out.print("lista_hollow:");
//		System.out.println(lista_Hollow.NumeroElementos());
		
		Iterador j = new Iterador(lista_poligonos);
	
  	while ( !j.IsDone() ) {
  		
  		Triangulo t = (Triangulo)j.Current();
  		Iterador Hollow = new Iterador(lista_Hollow);
  		while(!Hollow.IsDone()){
  			if(lista_Hollow.buscar_elemento_repetido(t.vertex(0))){
  				//flag1=true;
//  				System.out.println("entro a vertice 1");
  				Iterador Hollow2 = new Iterador(lista_Hollow);
  				while(!Hollow2.IsDone()){
  					if(lista_Hollow.buscar_elemento_repetido(t.vertex(1))){
//  						System.out.println("entro a vertice 2");
  						Iterador Hollow3 = new Iterador(lista_Hollow);
  						while(!Hollow3.IsDone()){
  							if(lista_Hollow.buscar_elemento_repetido(t.vertex(2))){
//  								System.out.println("entro a vertice 3");
//  								System.out.println("encontro candidato a eliminacion--->");
  								lista_poligonos.removeOne(t);
  								break;
  							}
  							Hollow3.Next();
  						}
  						break;
  					}
  					Hollow2.Next();
  				}
  				break;
  			}
////  			Hollow.Next();
////  			Vertice aux = new Vertice();
////  			int i=1;
//  			(t.vertex(1)).Imprime_Elemento();
//  			//System.out.println(aux.toString());
//  			//lista_Hollow.imprime_lista_elementos();
  			Hollow.Next();
  		}
  		j.Next();
  	}
  	mc.trimesh.lt=lista_poligonos;
  	mc.repaint();
	}


  
	public static boolean searchBorde(int i, int j, BufferedImage img) {
		boolean valor;
		Color c = new Color(img.getRGB(j, i));

		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		if (r == 255 & g == 255 & b == 255) {
			valor = true;
		} else {
			valor = false;
		}
		return valor;
	}
	public void SearchHollow(BufferedImage img,PrintStream ps){//para buscar los huecos
		Point aux = null;
		Point auxprint=null;
		Point auxCabeza= null;
		Point iterador=null;;
		boolean flag=true;
		boolean flag2=false;
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				flag=false;
				flag2=false;
				aux = new Point(i,j);
				auxCabeza = new Point(i,j);
				if (!milista.contains(aux) && !banlista.contains(aux)&&!hollowlista.contains(aux) && searchBorde(aux.x,aux.y,img)) {
					do {
						for (int p = 0; p < 48 ; p++) {
							iterador=new Point(aux.x+movimientos[p].x, aux.y+movimientos[p].y);
							if(aux.equals(auxCabeza)&& flag){
								flag2=true;
								break;
							}
							if (addVerticeHollow(iterador, img)) {
								aux = new Point(aux.x+movimientos[p].x, aux.y+movimientos[p].y);
								flag=true;
								break;
							}
							if(p==47){
								flag2=true;
							}
							
						}
						if(flag2){
							break;
						}
					} while (true);
					if(!aux.equals(auxCabeza) && flag ){
						ps.println(hollowlista.size());
						while(hollowlista.size()>0){
							banlista.add(hollowlista.remove(0));
						}
						banlista.add(auxCabeza);
						ps.println("elimino lista");
						break;
					}
					if(aux.equals(auxCabeza) && !flag ){
						banlista.add(aux);
						break;
					}
					if(hollowlista.size()<5){
						while(hollowlista.size()>0){
						banlista.add(auxCabeza);
						banlista.add(hollowlista.remove(0));
						}
					}
					
					//System.out.println(hollowlista.size());
					listas_hollows[Contador] = new ListaFifo();
					while(hollowlista.size()>0){
						auxprint=hollowlista.remove(0);
						ps.println(auxprint);
						hollowFinal.add(auxprint);//para mantener la lista de hollows por separado
						Vertice  punto = new Vertice(auxprint.y, auxprint.x);
						lista_Hollow.insertar_elemento_hollow(new NodoLista((Vertice)punto),Contador);
						listas_hollows[Contador].insertar_elemento(new NodoLista((Vertice)punto));
						FlagHollow = true;
						addVertice(auxprint,img);
				}
					if(FlagHollow){
						Contador++;
						lista_Hollow.Cantidad_Hollow++;
						FlagHollow=false;
					}
					ps.println("termina estrella");
			}
		}
		
		}
//		ps.println("banlista-------------->>>");
//		while(banlista.size()>0){
//			auxprint=banlista.remove(0);
//			ps.println(auxprint);
//		}
//		System.out.println(banlista.size());
//		System.out.println(hollowlista.size());
//		System.out.println(milista.size());
	}

  public void SearchBorde() {
		BufferedImage img = null;
		Vertice inicial = new Vertice(0, 0);
		boolean salir = false;
		try {
			img = ImageIO.read(edgeDetector.getEdgeImageFile());
			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					if (searchBorde(i, j,img)) {
						inicial = new Vertice(i, j);
						salir = true;
					}
					if (salir)
						break;
				}
				if (salir)
					break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Point aux = new Point((int) inicial.CordX(), (int) inicial.CordY());
		Point iterador;
		FileOutputStream os = null;
		try {
			os = new FileOutputStream("Informe_Pixel.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream ps = new PrintStream(os);
		
		//ps.println("Primero en: " + inicial.CordX() + "," + inicial.CordY());
		ps.println("Informe");

		do {
			for (int i = 0; i < 48 ; i++) {	
				iterador=new Point(aux.x+movimientos[i].x, aux.y+movimientos[i].y);
				if (addVertice(iterador, img)) {
					Vertice  punto = new Vertice(aux.y, aux.x);
					lista_edge.insertar_elemento(new NodoLista((Vertice)punto));
					aux = new Point(aux.x+movimientos[i].x, aux.y+movimientos[i].y);
					ps.println(aux.toString());
					break;
				}
				
			}
			
			
			if(aux.x == inicial.CordX() && aux.y == inicial.CordY()){
				break;
			}
		} while (true);
		System.out.println("Se a�aden " + milista.size() + " elementos");
		ps.println("Fin de lista exterior");
		
		
		SearchHollow(img,ps);
  	  //ReducirListaY();
  	  //ReducirListaX();
  	  //ReducirAcendente();
	}
	public void ConvertirVertice(){
		Point cero;
		Point tres;
////		for(int i=0;i>=milista.size();i=i+4){
////			mc.addListaVertices(milista.remove(i));
////			
////		}
		while(milista.size()>0){
			cero=new Point(milista.remove(0));
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
//			milista.remove(0);
			tres=new Point(milista.remove(0));
			mc.addListaVertices(cero);
			mc.addListaVertices(tres);
		}
//		while(milista.size()>0){
//			mc.addListaVertices(milista.remove(0));
//			}
	}



public void ConstrainedDelaunayBorde(){	
	Iterador Triangle = new Iterador(lista_poligonos);
	Vertice vert;
	
	while(!Triangle.IsDone()){
		((Triangulo)Triangle.Current()).setCentroid();
		vert=((Triangulo)Triangle.Current()).centroid();
		//vert.Imprime_Elemento();
		if(!lista_edge.contiene_punto(vert)){
			//System.out.println("entro al if de eliminar");
			//((Triangulo)Triangle.Current()).Imprime_Elemento(1);
			((Triangulo)Triangle.Current()).SetnullVecinos();
			((Triangulo)Triangle.Current()).resetNeighbors();
			lista_poligonos.removeOne(((Triangulo)Triangle.Current()));
		}
		Triangle.Next();
	}
}

public void ConstrainedDelaunayHollow(){	
	Iterador Triangle = new Iterador(lista_poligonos);
	int v0,v1,v2;
	Vertice vert;
	ElementoGeometrico n;
    NodoLista nodo;
//	while(!Triangle.IsDone()){
//		if(lista_Hollow.pertenece_triangulo(((Triangulo)Triangle.Current()))){
//			v0=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(0));
//			v1=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(1));
//			v2=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(2));
//			if(v0==v1 && v1==v2){
//				System.out.println(v0);
//				System.out.println(v1);
//				System.out.println(v2);
//				Iterador it = new Iterador(lista_Hollow);
//				int asd=0;
//				while(!it.IsDone()){
////					n=it.Current();
////					nodo = new NodoLista(n);
////					n.setNodo(nodo);
//					nodo=it.ValueCurrent();
//					System.out.println("hasta aca llega ------------>");
//					System.out.println(nodo.get_id());
//					System.out.println(v0);
//					if(nodo.get_id()==v0){
////					if(it.current.get_id()==v0){
//						System.out.println("entraaa--->");
//						lista_aux.insertar_elemento(nodo);
//						System.out.println(lista_Hollow.Cantidad_Hollow);
//						System.out.println(lista_aux.cantidad_elementos);
//						System.out.println(lista_Hollow.cantidad_elementos);
//					}
//					System.out.println("sale del if");
//					System.out.println(asd);
//					asd++;
//					if(it. ValuePostCurrent() == null){System.out.println("LLega a nullxxXXXXXXXXXXXXXXX ");}
//					it.Next();
//				}
//				System.out.println("sale del while");
//				((Triangulo)Triangle.Current()).setCentroid();
//				vert=((Triangulo)Triangle.Current()).centroid();
//				if(lista_aux.contiene_punto(vert)){
//					System.out.println("elimina triangle");
//					((Triangulo)Triangle.Current()).SetnullVecinos();
//					((Triangulo)Triangle.Current()).resetNeighbors();
//					lista_poligonos.removeOne(((Triangulo)Triangle.Current()));
//					lista_aux.clear();
//				}
//			}
//		}
//		Triangle.Next();
//	}
	
	
//	while(!Triangle.IsDone()){
//	if(lista_Hollow.pertenece_triangulo(((Triangulo)Triangle.Current()))){
//		v0=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(0));
//		v1=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(1));
//		v2=lista_Hollow.buscar_id(((Triangulo)Triangle.Current()).vertex(2));
//		if(v0==v1 && v1==v2){
//			System.out.println(v0);
//			System.out.println(v1);
//			System.out.println(v2);
//			Iterador it = new Iterador(lista_Hollow);
//			int asd=0;
//			for(int i=0; i<Contador;i++){
//				if(listas_hollows[Contador].pertenece_triangulo(((Triangulo)Triangle.Current()))){
//					System.out.println("Eliminaaa");
//					((Triangulo)Triangle.Current()).setCentroid();
//					vert=((Triangulo)Triangle.Current()).centroid();
//					if(lista_aux.contiene_punto(vert)){
//						System.out.println("elimina triangle");
//						((Triangulo)Triangle.Current()).SetnullVecinos();
//						((Triangulo)Triangle.Current()).resetNeighbors();
//						lista_poligonos.removeOne(((Triangulo)Triangle.Current()));
//						lista_aux.clear();
//					}
//				}
//				
//			}
//			System.out.println("sale del while");
//			((Triangulo)Triangle.Current()).setCentroid();
//			vert=((Triangulo)Triangle.Current()).centroid();
//			if(lista_aux.contiene_punto(vert)){
//				System.out.println("elimina triangle");
//				((Triangulo)Triangle.Current()).SetnullVecinos();
//				((Triangulo)Triangle.Current()).resetNeighbors();
//				lista_poligonos.removeOne(((Triangulo)Triangle.Current()));
//				lista_aux.clear();
//			}
    System.out.println(Contador);
    while(!Triangle.IsDone()){
    	for(int i=0; i<Contador;i++){
    		System.out.println(listas_hollows[i].cantidad_elementos);
	    	if(listas_hollows[i].pertenece_triangulo(((Triangulo)Triangle.Current()))){
	    		System.out.println("Eliminaaa");
				((Triangulo)Triangle.Current()).setCentroid();
				vert=((Triangulo)Triangle.Current()).centroid();
				if(listas_hollows[i].contiene_punto(vert)){
					System.out.println("elimina triangle");
					((Triangulo)Triangle.Current()).SetnullVecinos();
					((Triangulo)Triangle.Current()).resetNeighbors();
					lista_poligonos.removeOne(((Triangulo)Triangle.Current()));
					//lista_aux.clear();
				}
	    	}
    	}
	Triangle.Next();
}
}


	public boolean addVertice(Point iterador, BufferedImage img) {
		if (!milista.contains(iterador) && searchBorde(iterador.x, iterador.y, img)) {
			milista.add(iterador);
			return true;
		} else
			return false;
	}
	public boolean addVerticeHollow(Point iterador, BufferedImage img) {
		if (!milista.contains(iterador) && !banlista.contains(iterador) && !hollowlista.contains(iterador) && searchBorde(iterador.x, iterador.y, img)) {
			hollowlista.add(iterador);
			return true;
		} else
			return false;
	}
//  public void NewColor() {
//	Graphics g 	= getGraphics();
//	Random rand = new Random();
//	float red 	= rand.nextFloat();
//	float green = rand.nextFloat();
//	float blue 	= rand.nextFloat();
//	bottomColor = new Color(red, green, blue);
//	g.setColor(bottomColor);
//      g.fillRect(0,0,width,height);
//      refresh();
//  }

  public void actionButtonEvent() {
	  
	  //ker de gauss
//		gaussKerBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				kerSize();
//			}
//		});
		
//  	butExit.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            System.exit(0);
//         }
//      });
      
//      butReset.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            reset();
//         }
//      });
//  	
	  
    butRestringir.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
       //System.out.println(mc.getId());

    	ConstrainedDelaunayBorde();
    	ConstrainedDelaunayHollow();
    	mc.trimesh.lt=lista_poligonos;
    	mc.repaint();
    }
 });
  	  butVertices.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  mc.setImage(null);
        	  mc.repaint();
        	  ConvertirVertice();
        	  butTriangular.setEnabled(true);
        	  butVertices.setEnabled(false);
        	  
           }
        });
    	
      
      butTriangular.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         	//Graphics g = getGraphics();

		circulo = false;
		triangulacion = true;
		mc.setParameters(circulo, triangulacion);
		System.out.println("Triangulacion de Delaunay de una nube de vertices");
		trimesh = mc.getTrimesh();
		id = mc.getId();
		if ( id > 1 ) {
			mc.setMinMax();
			trimesh.printMinMax();
		}

		dt = new DelaunayTStrategy(trimesh);
		TriMesh trim = dt.triangulate();
		lista_poligonos 	= trim.triangles();
		lista_vertices	= trim.vertices();
		System.out.println("TriMesh retornó lista de poligonos con " + lista_poligonos.cantidad_elementos + " triangulos y " + trimesh.vertices().size() + " vertices");
		trimesh = trim;
		
		if ( globalFileName != null )
			setTitle("Movimiento de robots en el plano y con obstáculos::: " + "File: " + globalFileName + ":::" + "Points: " + trimesh.vertices().size() + " Triangles: " + trimesh.triangles().size());
		else setTitle("Movimiento de robots en el plano y con obstáculos::: " + "Points: " + trimesh.vertices().size() + " Triangles: " + trimesh.triangles().size());
		mc.repaint();
		butRestringir.setEnabled(true);
		butTriangular.setEnabled(false);
         }
      });

//      butCircle.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//         	//Graphics g = getGraphics();
//		circulo = true;
//		triangulacion   = true;
//		mc.setParameters(circulo, triangulacion);
//		System.out.println("Muestra circunsferencias");
//		mc.repaint();
//         }
//      });
      butFiltro.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    		  edgeDetector.setHighThreshold(Integer.valueOf(highthresh.getText()).intValue());
    			edgeDetector.setLowThreshold(Integer.valueOf(lowthresh.getText()).intValue());
    			edgeDetector.setSigma(Float.valueOf(sigma.getText()).floatValue());

    			// processa l'immagine
    				try {
						edgeDetector.process(img);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			Image edgeImage = edgeDetector.getEdgeImage();

    			mc.setImage(edgeImage);
    			mc.repaint();
    			butGenerar.setEnabled(true);
    			butFiltro.setEnabled(false);
    	  }
      });
      butGenerar.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    		  SearchBorde();
    		  butVertices.setEnabled(true);
    		  butGenerar.setEnabled(false);
    	  }
      });
  }
  
  @Override
  public void update(Graphics g) {
    	paint(g);
  }
  
  private void ExitPoint() {
  	mc.setExitPoint(true);
  }

  //vnode_cero
  private void StartPoint() {
	mc.setFlagPoints(true, 0);
  }

  public void reset() {
	  lista_vertices		= null;
	  lista_poligonos		= null;
	  circulo 			= false;
	  triangulacion 		= true;
	  p_c 			= null;
	  pol_circulo    		= null;
        Graphics g 		= getGraphics();
        g.setColor(bottomColor);
        g.fillRect(0,0,width,height);
        Vertice.setLastIdv(0);
        trimesh = new TriMesh();
        System.out.println("reset() ");
  }

  public void reconstituir() {
	  Graphics g = getGraphics();

        g.setColor(bottomColor);
        g.fillRect(0,0,width,height);
  }
   
  public void refresh() {
   	circulo = false;
	pol_circulo = null;
	triangulacion = true;
	reconstituir();
	repaint();
  }
    
  public Vertice rotateVector(double x, double y, double cx, double cy, double angle) {
	double s = Math.sin(angle);
      double c = Math.cos(angle);
            
      x -= cx;
	y -= cy;
 
	double nx = c * x - s * y;
	double ny = s * x + c * y;
		
	nx += cx;
	ny += cy;

	return new Vertice((float)nx, (float)ny);
  }

  public void selectRectangularArea() {
	RectangAreaDialog dialog = new RectangAreaDialog(this, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
	
	System.out.println("MyFrame: " + dialog.getCoord()[0] + " " + dialog.getCoord()[1] + " " + dialog.getCoord()[2] + " " + dialog.getCoord()[3]);
	
	double coordX0 = (double)dialog.getCoord()[0];
      double coordY0 = (double)dialog.getCoord()[1];
      double coordX2 = (double)dialog.getCoord()[2];
      double coordY2 = (double)dialog.getCoord()[3];
	
	float angle = (float)dialog.getAngle();
      double coordX1 = coordX2;
      double coordY1 = coordY0;
            
      double coordX3 = coordX0;
      double coordY3 = coordY2;
            
      double cx = (coordX0 + coordX1 + coordX2 + coordX3)/4.0;
      double cy = (coordY0 + coordY1 + coordY2 + coordY3)/4.0;
            
      double rotation = 360.0 - angle;

      if ( (angle > 0.0) && (rotation > 0.0) ) {
           	double alfa = (2*3.14159*rotation)/360.0;
            
           	Vertice v0 = rotateVector(coordX0, coordY0, cx, cy, alfa);
          	Vertice v1 = rotateVector(coordX1, coordY1, cx, cy, alfa);
           	Vertice v2 = rotateVector(coordX2, coordY2, cx, cy, alfa);
           	Vertice v3 = rotateVector(coordX3, coordY3, cx, cy, alfa);

           	coordX0 = v0.CordX();
           	coordY0 = v0.CordY();
           	coordX1 = v1.CordX();
           	coordY1 = v1.CordY();
           	coordX2 = v2.CordX();
           	coordY2 = v2.CordY();
           	coordX3 = v3.CordX();
           	coordY3 = v3.CordY();
      }

      //_meshViewer->setRectangleArea(coordX0, coordY0, coordX1, coordY1, coordX2, coordY2, coordX3, coordY3);
	criterio = new RectangAreaCriterion(coordX0, coordY0, coordX3, coordY3, coordX2, coordY2, coordX1, coordY1);
	trimesh.select(criterio);
  }
  
  public void PrintPolygons() {
	lista_poligonos.imprime_lista_elementos();
	System.out.println("Cantidad de Poligonos: " + lista_poligonos.NumeroElementos());
  }
} //Fin MyFrameInterfaz class

