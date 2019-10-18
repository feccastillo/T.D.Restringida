import java.util.*;
import java.io.*;
import java.lang.Object.*;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;
import java.awt.*;

public class M2dFormatIO {
 ListaFifo  		lista_vertices;
 ListaFifo 			lista_triangulos;
 TriMesh			trimesh;
 DataInputStream  	inputData;
 DataOutputStream 	outputData;
 String 			filename;

M2dFormatIO(String filename, TriMesh trimesh) {

     lista_vertices 	= new ListaFifo();
     lista_triangulos 	= new ListaFifo();
     this.filename 	= filename;
     this.trimesh		= trimesh;
}

 public void load()
 {
      HashMap<Integer, Triangulo> triangles 	= new HashMap<Integer, Triangulo>();
      HashMap<Integer, Vertice> vertices 		= new HashMap<Integer, Vertice>();
      
      double xmin=0, xmax=0, ymin=0, ymax=0;
      File 		pathfile 	 = new File(filename);
      String	pathabsolute = pathfile.getAbsolutePath();

      try {
      	//File file 			= new File(pathabsolute);
      	//FileReader filereader 	= new FileReader(file);
      	//BufferedReader buffer 	= new BufferedReader(filereader);
      	BufferedReader buffer = new BufferedReader(new FileReader(filename));

      	String line;
      	while( (line = buffer.readLine()) != null )
      	{
           		if ( line.length() == 0 ) continue;

           		// Now let's process the line as a stream.
           		StringTokenizer tin = new StringTokenizer(line);
           		char type = tin.nextToken().charAt(0);
           		if ( type == ' ' ) continue;
           		if ( type == '#' ) continue;
           		
           		int  id   = Integer.parseInt(tin.nextToken());
           		
           		// Creating the specified type.
           		if (type == 'v' || type == 'V') // Vertex.
           		{
                		double x, y;
                		
                		x = Double.parseDouble(tin.nextToken());
                		y = Double.parseDouble(tin.nextToken());
                		
                		if ( id == 1 ) {
                  		xmin = x; xmax = x;
                    		ymin = y; ymax = y;
                		}
                    
                		if ( x < xmin ) xmin = x;
                		if ( x > xmax ) xmax = x;
                		if ( y < ymin ) ymin = y;
                		if ( y > ymax ) ymax = y;
                    
                		Vertice v = new Vertice((int)x, (int)y);
                		v.setIdv(id);
                		trimesh.addVertex(v);
                		vertices.put(id, v);
            	}
              	else if (type == 't' || type == 'T') // Triangle.
              	{
                    	int v1, v2, v3;

                		v1 = Integer.parseInt(tin.nextToken());
                		v2 = Integer.parseInt(tin.nextToken());
                		v3 = Integer.parseInt(tin.nextToken());
                		
                    	Triangulo t = new Triangulo(vertices.get(v1), vertices.get(v2), vertices.get(v3));

                    	t.setIdt(id);
                    	trimesh.addTriangle(t);
                    	triangles.put(id, t);
              	}
                	else if (type == 'n' || type == 'N') // Neighbor.
                	{
                    	int n1, n2, n3;

                		n1 = Integer.parseInt(tin.nextToken());
                		n2 = Integer.parseInt(tin.nextToken());
                		n3 = Integer.parseInt(tin.nextToken());

                    	if ( 0 != n1 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n1));
                    	}
                    	if ( 0 != n2 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n2));
                    	}
                    	if ( 0 != n3 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n3));
                    	}
                	}
                	else
                	{
                    	System.out.println("Warning, line not recognized:" + line + "'");
                	}
            }
            this.trimesh.setMinMax((float)xmin, (float)xmax, (float)ymin, (float)ymax);
      } catch (IOException e) { 
      	System.out.println("# Excepcion en load(): con nombre de archivo ");
            e.printStackTrace(); 
      };  
 }
 
 public void loadTriangulatedImage()
 {
      HashMap<Integer, Triangulo> triangles 	= new HashMap<Integer, Triangulo>();
      HashMap<Integer, Vertice> vertices 		= new HashMap<Integer, Vertice>();
      
      double xmin=0, xmax=0, ymin=0, ymax=0;
      File 		pathfile 	 = new File(filename);
      String	pathabsolute = pathfile.getAbsolutePath();

      try {
      	//File file 			= new File(pathabsolute);
      	//FileReader filereader 	= new FileReader(file);
      	//BufferedReader buffer 	= new BufferedReader(filereader);
      	BufferedReader buffer = new BufferedReader(new FileReader(filename));

      	String line;
      	while( (line = buffer.readLine()) != null )
      	{
           		if ( line.length() == 0 ) continue;

           		// Now let's process the line as a stream.
           		StringTokenizer tin = new StringTokenizer(line);
           		char type = tin.nextToken().charAt(0);
           		if ( type == ' ' ) continue;
           		if ( type == '#' ) continue;
           		
           		int  id   = Integer.parseInt(tin.nextToken());
           		
           		// Creating the specified type.
           		if (type == 'v' || type == 'V') // Vertex.
           		{
                		double x, y;
                		
                		x = Double.parseDouble(tin.nextToken());
                		y = Double.parseDouble(tin.nextToken());
                		
                		if ( id == 1 ) {
                  		xmin = x; xmax = x;
                    		ymin = y; ymax = y;
                		}
                    
                		if ( x < xmin ) xmin = x;
                		if ( x > xmax ) xmax = x;
                		if ( y < ymin ) ymin = y;
                		if ( y > ymax ) ymax = y;
                    
                		Vertice v = new Vertice((int)x, (int)y);
                		v.setIdv(id);
                		trimesh.addVertex(v);
                		vertices.put(id, v);
            	}
              	else if (type == 't' || type == 'T') // Triangle.
              	{
                    	int v1, v2, v3, red, green, blue;

                		v1 = Integer.parseInt(tin.nextToken());
                		v2 = Integer.parseInt(tin.nextToken());
                		v3 = Integer.parseInt(tin.nextToken());
                		red = Integer.parseInt(tin.nextToken());
                		green = Integer.parseInt(tin.nextToken());
                		blue = Integer.parseInt(tin.nextToken());
                		
                    	Triangulo t = new Triangulo(vertices.get(v1), vertices.get(v2), vertices.get(v3));

                    	t.setIdt(id);
                    	t.setRedRGB(red);
                    	t.setGreenRGB(green);
                    	t.setBlueRGB(blue);
                    	Color color = new Color(red, green, blue);
                    	t.setNewColor(color);
                    	trimesh.addTriangle(t);
                    	triangles.put(id, t);
              	}
                	else if (type == 'n' || type == 'N') // Neighbor.
                	{
                    	int n1, n2, n3;

                		n1 = Integer.parseInt(tin.nextToken());
                		n2 = Integer.parseInt(tin.nextToken());
                		n3 = Integer.parseInt(tin.nextToken());

                    	if ( 0 != n1 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n1));
                    	}
                    	if ( 0 != n2 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n2));
                    	}
                    	if ( 0 != n3 ) {
                    	    	triangles.get(id).addNeighbor(triangles.get(n3));
                    	}
                	}
                	else
                	{
                    	System.out.println("Warning, line not recognized:" + line + "'");
                	}
            }
            this.trimesh.setMinMax((float)xmin, (float)xmax, (float)ymin, (float)ymax);
      } catch (IOException e) { 
      	System.out.println("# Excepcion en load(): con nombre de archivo ");
            e.printStackTrace(); 
      };  
 }

 public ListaFifo getListaVertices()
 {
	return trimesh.vertices();
 }

 public ListaFifo getListaTriangulos()
 {
	return trimesh.triangles();
 }

} //Fin de DataFile class



