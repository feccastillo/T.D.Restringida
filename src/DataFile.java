import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.io.File;
import java.lang.Object.*;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;

public class DataFile extends Frame {
 private Lista  			lista_vertices, lista_a_tratar = null;
 private FileDialog  		fd;
 private TextField	 	tfDirectory		= new TextField();
 private TextField	 	tfFile		= new TextField();
 private TextField   		tfFilter       	= new TextField();
 private DataInputStream  	inputData;
 private DataOutputStream 	outputData;
 private Scanner			inputFile;

 DataFile(boolean load) {
     lista_vertices = new ListaFifo();
     Panel panel2 = new Panel();

     panel2.setLayout(new GridBagLayout());
     addRow(panel2, new Label("Directory:", Label.RIGHT), tfDirectory);
     addRow(panel2, new Label("File:", Label.RIGHT), tfFile);
     addRow(panel2, new Label("Filter:", Label.RIGHT), tfFilter);
     add("North",panel2);

     FileDialog fd = new FileDialog(this,null,load ? FileDialog.LOAD:FileDialog.SAVE);
     fd.setDirectory(tfDirectory.getText());
     fd.setFile(tfFile.getText());
     fd.setFilenameFilter(null);
     fd.setVisible(true);
     tfDirectory.setText(fd.getDirectory());
     tfFile.setText(fd.getFile());
}

 DataFile(String fname) {
     lista_vertices = new ListaFifo();
     tfFile.setText(fname);
}

public String getFileName() {
	return tfFile.getText();
}

public void addRow(Container cont, Component c1, Component c2) {
  GridBagLayout gbl = (GridBagLayout)cont.getLayout();
  GridBagConstraints c = new GridBagConstraints();
  Component comp;

  c.fill = GridBagConstraints.BOTH;
  cont.add(c1);
  gbl.setConstraints(c1, c);

  c.gridwidth = GridBagConstraints.REMAINDER;
  c.weightx = 1.0;
  cont.add(c2);
  gbl.setConstraints(c2,c);
 }

public TriMesh loadPoints(TriMesh trimesh) {
  int i=0;
  int count=0;
  Vertice vertice;
  float x=0.0f, y=0.0f;
  NodoLista nodo;
  int 	xmin=0, xmax=0, ymin=0, ymax=0;
  int id = 1;
  
  Vertice.setLastIdv(0);

  try {
    //inputData = new DataInputStream( new FileInputStream(fd.getDirectory() + File.pathSeparator + fd.getFile() ));

	inputData = new DataInputStream(new FileInputStream(new File(tfFile.getText())));

    	i = inputData.available()/4;
    	System.out.println("Bytes : " + inputData.available());
      System.out.println("Numero de Vertices : " + i/2);

    	while ( i > 0 ) {
	  	x = (float)inputData.readFloat();
	  	i--;
	  	y = (float)inputData.readFloat();
        	i--;
	  	vertice = new Vertice((float)x, (float)y);
	  	int lidv = Vertice.getLastIdv() + 1;
   		//System.out.println("lidv: " + lidv);
   		vertice.setIdv(lidv);
   		Vertice.setLastIdv(lidv); 
	  	nodo = new NodoLista((Vertice)vertice);    
        	lista_vertices.insertar_elemento(nodo);
        	trimesh.addVertex(nodo);
        	count++;
	}
    	inputData.close();
  } catch (IOException e) {};
  System.out.println("Se leyeron " + trimesh.vertices().size() + " Vertices desde archivo " + tfFile.getText());
  
  //return (ListaFifo)lista_vertices;
  return trimesh;
}

public TriMesh loadPoints(String fname, TriMesh trimesh) {
  int 	i=0;
  Vertice 	vertice;
  NodoLista nodo;
  int 	xmin=0, xmax=0, ymin=0, ymax=0;
  int 	id = 1;
  
  Vertice.setLastIdv(0);
  
  System.out.println("Ingresa a leer archivo " + fname);
  try {
	BufferedReader reader = new BufferedReader(new FileReader(tfFile.getText()));

	String line;
    	while ( (line = reader.readLine()) != null ) {
    		StringTokenizer st = new StringTokenizer(line);
	  	int x = Integer.parseInt(st.nextToken());
	  	int y = Integer.parseInt(st.nextToken());
	  	
	  	if ( id == 1 ) {
      		xmin = x; xmax = x;
      		ymin = y; ymax = y;
      	}
      	id++;
                    
      	if ( x < xmin ) xmin = x;
      	if ( x > xmax ) xmax = x;
      	if ( y < ymin ) ymin = y;
      	if ( y > ymax ) ymax = y;
	  	
//	  	System.out.println("(" + x + " " + y + ")");
	  	vertice = new Vertice((float)x, (float)y);
	  	int lidv = Vertice.getLastIdv() + 1;
   		//System.out.println("lidv: " + lidv);
   		vertice.setIdv(lidv);
   		Vertice.setLastIdv(lidv);  
	  	nodo = new NodoLista((Vertice)vertice);    
        	lista_vertices.insertar_elemento(nodo);
        	trimesh.addVertex(nodo);
        	i++;
	}
    	reader.close();
  } catch (IOException e) {};
  System.out.println("Se leyeron " + trimesh.vertices().size() + " Vertices desde archivo " + fname);
  System.out.println("Máximos y mínimos");
  trimesh.setMinMax((float)xmin, (float)xmax, (float)ymin, (float)ymax);
  System.out.println("(" + trimesh.retXmin() + ", " + trimesh.retXmax() + ")");
  System.out.println("(" + trimesh.retYmin() + ", " + trimesh.retYmax() + ")");
  System.out.println(" ");

  //return (ListaFifo)lista_vertices;
  return trimesh;
}

public Image loadImage(TriMesh trimesh) {
  int i=0;
  int count=0;
  Vertice Vertice;
  float x=0.0f, y=0.0f;
  NodoLista nodo;
  int 	xmin=0, xmax=0, ymin=0, ymax=0;
  int id = 1;
  File file;
  Image im=null;

  //try {
      //inputData = new DataInputStream( new FileInputStream(fd.getDirectory() + File.pathSeparator + fd.getFile() ));
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      Toolkit tk = Toolkit.getDefaultToolkit(); 
      
      int result = chooser.showOpenDialog(null);
      
      //if ( result == JFileChooser.APPROVE_OPTION ) {
      	String name = chooser.getSelectedFile().getPath();
      	file = new File(name);
      	im = tk.getImage(name);
      //}
  //} catch (IOException e) {};

  return im;
}

public void savePoints(Lista lista) {
  Vertice Vertice=null;
  Iterador it;

 try {
 //  outputData = new DataOutputStream(new FileOutputStream(fd.getDirectory() + File.pathSeparator + fd.getFile() )); 
   	outputData = new DataOutputStream(new FileOutputStream(new  File(tfFile.getText() ))); 

   	lista_vertices = new ListaFifo((Lista)lista);

   	it = new Iterador(lista_vertices);
   	while ( !it.IsDone() ) {
	  	Vertice = (Vertice)it.Current();
	  	outputData.writeFloat(Vertice.CordX());
	  	outputData.writeFloat(Vertice.CordY());
	  	it.Next();
     	}
   	outputData.close();
  } catch (IOException e) {};
 }
 
 public void savePoints(String fname, Lista lista) {
  Vertice Vertice=null;
  Iterador it;

 try {
   	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(tfFile.getText())));
   	lista_vertices = new ListaFifo((Lista)lista);

   	it = new Iterador(lista_vertices);
   	while ( !it.IsDone() ) {
		Vertice = (Vertice)it.Current();
		int x = (int)Vertice.CordX();
		int y = (int)Vertice.CordY();
		String line = x + " " + y + "\n";
		writer.write(line);
		writer.flush();
		it.Next();
     	}
   	writer.close();
  } catch (IOException e) {};
 }
 
 public void saveM2d(String fname, Lista lista, ListaFifo poligonos) {
  	Vertice Vertice=null;
  	Iterador it;
  	int i = 1;
  	Triangulo.setLastIdt(0);

 	try {
   		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(tfFile.getText())));
   		lista_vertices = lista;
   		
   		writer.write("# Vertices \n");
   		
   		it = new Iterador(lista_vertices);
   		while ( !it.IsDone() ) {
			Vertice = (Vertice)it.Current();
			int x = (int)Vertice.CordX();
			int y = (int)Vertice.CordY();
			String line = "v " + Vertice.getIdv() + " " + x + " " + y + "\n";
			writer.write(line);
			writer.flush();
			it.Next();
			//i++;
     		}
     		
     		writer.write("\n");
     		writer.write("# Triangles \n");
     		ListaFifo lista_triangulos = new ListaFifo();
     		
     		i=1;
     		Iterador ip = new Iterador(poligonos);
   		while ( !ip.IsDone() ) {			
			Triangulo t = (Triangulo)ip.Current();
			Vertice a = t.vertex(0);
			Vertice b = t.vertex(1);
			Vertice c = t.vertex(2);
			int idt = Triangulo.getLastIdt() + 1;
			t.setIdt(idt);
   			Triangulo.setLastIdt(idt);
			NodoLista nodo = new NodoLista(t);
			lista_triangulos.insertar_elemento(nodo);
			
			int id0 = (int)a.getIdv();
			int id1 = (int)b.getIdv();
			int id2 = (int)c.getIdv();
			String line = "t " + i + " " + id0 + " " + id1 + " " + id2 + "\n";
			writer.write(line);
			writer.flush();
			ip.Next();
			i++;
     		}

		writer.write("\n");
     		writer.write("# Neighbors \n");
		
		it = new Iterador(lista_triangulos);
   		while ( !it.IsDone() ) {
			Triangulo t = (Triangulo)it.Current();
			Triangulo t0 = t.neighbor(0);
			int n0 = t0.getIdt();
			Triangulo t1 = t.neighbor(1);
			int n1 = t1.getIdt();
			Triangulo t2 = t.neighbor(2);
			int n2 = t2.getIdt();
			String line = "n " + t.getIdt() + " " + n0 + " " + n1 + " " + n2 + "\n";
			writer.write(line);
			writer.flush();
			it.Next();
     		}
     		
   		writer.close();
  	} catch (IOException e) {};
 }
 
 public void saveM2dTriangulatedImage(String fname, Lista lista, ListaFifo poligonos) {
  	Vertice Vertice=null;
  	Iterador it;
  	int i = 1;
  	Triangulo.setLastIdt(0);

 	try {
   		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(tfFile.getText())));
   		lista_vertices = lista;
   		
   		writer.write("# Vertices \n");
   		
   		it = new Iterador(lista_vertices);
   		while ( !it.IsDone() ) {
			Vertice = (Vertice)it.Current();
			int x = (int)Vertice.CordX();
			int y = (int)Vertice.CordY();
			String line = "v " + Vertice.getIdv() + " " + x + " " + y + "\n";
			writer.write(line);
			writer.flush();
			it.Next();
			//i++;
     		}
     		
     		writer.write("\n");
     		writer.write("# Triangles \n");
     		writer.write("# t N° v0 v1 v2 r g b \n");
     		ListaFifo lista_triangulos = new ListaFifo();
     		
     		i=1;
     		Iterador ip = new Iterador(poligonos);
   		while ( !ip.IsDone() ) {			
			Triangulo t = (Triangulo)ip.Current();
			Vertice a = t.vertex(0);
			Vertice b = t.vertex(1);
			Vertice c = t.vertex(2);
			int idt = Triangulo.getLastIdt() + 1;
			int red   = t.getRedRGB();
			int green = t.getGreenRGB();
			int blue  = t.getBlueRGB();
			t.setIdt(idt);
   			Triangulo.setLastIdt(idt);
			NodoLista nodo = new NodoLista(t);
			lista_triangulos.insertar_elemento(nodo);
			
			int id0 = (int)a.getIdv();
			int id1 = (int)b.getIdv();
			int id2 = (int)c.getIdv();
			String line = "t " + i + " " + id0 + " " + id1 + " " + id2 + " " + red + " " + green + " " + blue + "\n";
			writer.write(line);
			writer.flush();
			ip.Next();
			i++;
     		}

		writer.write("\n");
     		writer.write("# Neighbors \n");
		
		it = new Iterador(lista_triangulos);
   		while ( !it.IsDone() ) {
			Triangulo t = (Triangulo)it.Current();
			Triangulo t0 = t.neighbor(0);
			int n0 = t0.getIdt();
			Triangulo t1 = t.neighbor(1);
			int n1 = t1.getIdt();
			Triangulo t2 = t.neighbor(2);
			int n2 = t2.getIdt();
			String line = "n " + t.getIdt() + " " + n0 + " " + n1 + " " + n2 + "\n";
			writer.write(line);
			writer.flush();
			it.Next();
     		}
     		
   		writer.close();
  	} catch (IOException e) {};
 }

} //Fin de DataFile class



