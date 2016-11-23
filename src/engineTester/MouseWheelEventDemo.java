package engineTester;

/*
* MouseWheelEventDemo.java
*/
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
 
import javax.swing.*;
 
public class MouseWheelEventDemo extends JPanel
        implements MouseWheelListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
    JScrollPane scrollPane;
    static final String NEWLINE = System.getProperty("line.separator");
     
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MouseWheelEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        JComponent newContentPane = new MouseWheelEventDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
     
    public MouseWheelEventDemo() {
        super(new BorderLayout());
         
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        add(scrollPane, BorderLayout.CENTER);
        textArea.append("This text area displays information "
                + "about its mouse wheel events."
                + NEWLINE);
         
        //Register for mouse-wheel events on the text area.
        textArea.addMouseWheelListener(this);
         
        setPreferredSize(new Dimension(450, 350));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
     
    //Append to the text area and make sure the new text is visible.
    void eventOutput(String eventDescription, MouseWheelEvent e) {
        textArea.append(e.getComponent().getClass().getName()
        + ": "
                + eventDescription);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
     
    public void mouseWheelMoved(MouseWheelEvent e) {
        String message;
        int notches = e.getWheelRotation();
        if (notches < 0) {
            message = "Mouse wheel moved UP "
                    + -notches + " notch(es)" + NEWLINE;
        } else {
            message = "Mouse wheel moved DOWN "        
                    + notches + " notch(es)" + NEWLINE;
        }
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            message += "    Scroll type: WHEEL_UNIT_SCROLL" + NEWLINE;
            message += "    Scroll amount: " + e.getScrollAmount()
            + " unit increments per notch" + NEWLINE;
            message += "    Units to scroll: " + e.getUnitsToScroll()
            + " unit increments" + NEWLINE;
            message += "    Vertical unit increment: "
                    + scrollPane.getVerticalScrollBar().getUnitIncrement(1)
                    + " pixels" + NEWLINE;
        } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
            message += "    Scroll type: WHEEL_BLOCK_SCROLL" + NEWLINE;
            message += "    Vertical block increment: "
                    + scrollPane.getVerticalScrollBar().getBlockIncrement(1)
                    + " pixels" + NEWLINE;
        }
        eventOutput(message, e);
    }
}
