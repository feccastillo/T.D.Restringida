import javax.swing.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	static MyFrameInterfaz Interfaz;

	static public void main(String[] args) {
		if ( args.length > 0 )
			Interfaz = new MyFrameInterfaz(args[0]);
		else Interfaz = new MyFrameInterfaz(null);
	}
 } //Fin de la Main class

