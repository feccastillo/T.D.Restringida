import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class RectangAreaDialog extends JDialog {
	private int[] coord;
	private double angle;
	private JTextField	cordx1, cordy1, cordx2, cordy2, angulo;

    	public RectangAreaDialog(JFrame frame, boolean mod) {
        	super(frame, mod);
        	// evita cambio de tamaño
        	setResizable(false);
        	// título del diáolog
        	setTitle("Area Rectangular");
        	// dimensiones que ocupa en la pantalla1
        	setBounds(100, 200, 450, 130);
        	// capa que contendrá todo
        	getContentPane().setLayout(new BorderLayout());
		coord = new int[4];
        	{
        		JPanel textfPane = new JPanel();
        		textfPane.setLayout(new GridLayout(1,1));
        		getContentPane().add(textfPane, BorderLayout.NORTH);
        		{
            		JPanel textfPaneN = new JPanel();
            		textfPane.setLayout(new FlowLayout());
            		JPanel textfPaneS = new JPanel();
            		textfPaneS.setLayout(new FlowLayout());
            		textfPane.add(textfPaneN); //, BorderLayout.NORTH);
            		//textfPane.add(textfPaneS); //, BorderLayout.SOUTH);
            		getContentPane().add(textfPaneS, BorderLayout.CENTER);
            		JLabel	izqar		= new JLabel("Coord. Izq. Arriba: ");
            		cordx1	= new JTextField("0",3);
 				cordy1	= new JTextField("0",3);
 				JLabel	derab		= new JLabel("Coord. Der. Abajo: ");
				cordx2      = new JTextField("0",3);
				cordy2      = new JTextField("0",3);
				JLabel	ang	= new JLabel("Angulo inclinacion: ");
				angulo      = new JTextField("0",3);
				textfPaneN.add(izqar);
				textfPaneN.add(cordx1);
				textfPaneN.add(cordy1);
				textfPaneN.add(derab);
				textfPaneN.add(cordx2);
				textfPaneN.add(cordy2);
				textfPaneS.add(ang);
				textfPaneS.add(angulo);
				
				izqar.setVisible(true);
				cordx1.setVisible(true);
				cordy1.setVisible(true);
				derab.setVisible(true);
				cordx2.setVisible(true);
				cordy2.setVisible(true);
				ang.setVisible(true);
				angulo.setVisible(true);
				
				textfPaneN.setVisible(true);
				textfPaneS.setVisible(true);
				textfPane.setVisible(true);
        		}
        	}
        	{
            	// a continuación tenemos los botones clásicos 'Vale' y 'Cancela'
            	// éste código lo ha generado Eclipse...
            	JPanel buttonPane = new JPanel();
            	buttonPane.setLayout(new FlowLayout());
            	getContentPane().add(buttonPane, BorderLayout.SOUTH);
            	{
            		JButton okButton = new JButton("OK");
                		okButton.addActionListener(new ActionListener() {
                    		public void actionPerformed(ActionEvent e) {
                        		coord[0] = Integer.parseInt(cordx1.getText());
                    			coord[1] = Integer.parseInt(cordy1.getText());
                    			coord[2] = Integer.parseInt(cordx2.getText());
                    			coord[3] = Integer.parseInt(cordy2.getText());
                    			angle = Double.parseDouble(angulo.getText());
                        		dispose();
                    		}
                		});
                		okButton.setActionCommand("OK");
                		buttonPane.add(okButton);
                		getRootPane().setDefaultButton(okButton);
            	}
            	{
                		JButton cancelButton = new JButton("Cancelar");
                		cancelButton.addActionListener(new ActionListener() {
                    	public void actionPerformed(ActionEvent arg0) {
                        		dispose();
                    		}
                		});
                		cancelButton.setActionCommand("Cancelar");
                		buttonPane.add(cancelButton);
            	}
        	}
    	}
    	
    	public int[] getCoord() {
    		return coord;
    	}
    	
    	public double getAngle() {
    		return angle;
    	}
}

