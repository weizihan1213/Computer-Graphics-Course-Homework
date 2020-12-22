/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
   This class allows our rendering code to be used as the primary renderer
   for interactive Java programs. That is, this class allows us to write
   interactive Java GUI programs that use our renderer instead of the
   renderer built into Java's GUI library. Of course, our renderer will be
   much slower than the one built into Java (which uses the computer's GPU).
<p>
   This {@link JFrame} displays our renderer's {@link FrameBuffer} in a
   {@link FrameBufferPanel} and this class acts as an interface between the
   Java GUI event system and our renderer.
<p>
   This class acts as an "adapter class" for several kinds of event
   listeners, so applications only need to worry about the events that
   are relevant to them.
<p>
   This class is meant to be sub classed by an application that uses our
   renderer. The sub class constructor should initialize a
   {@link renderer.scene.Scene} object with appropriate models and geometry.
   The sub class should also provide implementations of any desired event
   handlers. The event handlers provide interactivity by updating the
   {@link renderer.scene.Scene} object in response to user actions. After
   an event handler updates the {@link renderer.scene.Scene} object, it will
   render the {@link renderer.scene.Scene} into the {@link FrameBuffer} object
   contained in this object's {@link FrameBufferPanel}.
<p>
   Each instance of {@code InteractiveFrame} has a reference to a
   {@link FrameBufferPanel} object, which has a reference to a
   {@link FrameBuffer} object. When a GUI event happens, one of the
   implemented event listeners will update this {@link JFrame} window by
   modifying a {@link renderer.scene.Scene} object appropriately and then
   having our renderer render the {@link renderer.scene.Scene} object into
   the {@link FrameBuffer}. When the renderer is done updating the
   {@link FrameBuffer}, the event listener will call the
   {@link FrameBufferPanel}'s update() method, which will pass the
   {@link FrameBuffer}'s pixel data to the {@link java.awt.Image} being drawn
   on the {@link java.awt.Graphics} context of the {@link FrameBufferPanel}
   (which is a {@link javax.swing.JPanel}). Then the event listener will call this
   object's repaint() method which will trigger the {@link FrameBufferPanel}'s
   paintComponent() method. This will display the {@link java.awt.Image} (that
   holds the {@link FrameBuffer}'s contents) in the {@link javax.swing.JPanel}
   within this {@link JFrame}'s window.
<p>
   This window may be resizeable. When this window resizes, its FrameBufferPanel
   will also resize, and the FrameBufferPanel's FrameBuffer object will also need
   to resize. But FrameBuffer objects cannot be resized. So each time this window
   resizes, a new FrameBuffer object needs to be created for the FrameBufferPanel.
   The componentResized() method from the ComponentListener interface should call
   the setFrameBuffer() method in this object's FrameBufferPanel and pass it a
   reference to a new FrameBuffer object with the new dimensions of the resized
   window
*/
@SuppressWarnings("serial")
public class InteractiveFrame extends JFrame implements
  ActionListener, ItemListener,  AdjustmentListener,  TextListener,
  KeyListener,    MouseListener, MouseMotionListener, ComponentListener,
  WindowListener, FocusListener
{
   protected FrameBufferPanel fbp;

   /**
      Create a {@link JFrame} window with a {@link BorderLayout} and place a
      {@link FrameBufferPanel} (containing a {@link FrameBuffer}) in the center
      of the layout.

      @param title     title for the {@link JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link JFrame}
   */
   public InteractiveFrame(String title, int fbWidth, int fbHeight)
   {
      super(title);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationByPlatform(true);

      // Create the initial FrameBuffer for the FrameBufferPanel.
      FrameBuffer fb = new FrameBuffer(fbWidth, fbHeight);

      // Create a FrameBufferPanel (a JPanel) for this JFrame.
      fbp = new FrameBufferPanel(fb);

      // Place the JPanel in this JFrame.
      this.getContentPane().add(fbp, BorderLayout.CENTER);
      this.pack();
      this.setVisible(true);

      // Make this object the event handler for the following
      // events, which are events created by this window itself.
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addComponentListener(this);
      this.addWindowListener(this);
      this.addFocusListener(this);
   }


   /**
      Accessor method for the {@link FrameBuffer} currently being used
      as the source for the {@link java.awt.Image} painted on the FrameBufferPanel.

      @return a reference to the FrameBuffer used for this JFrame
   */
   public FrameBuffer getFrameBuffer()
   {
      return fbp.getFrameBuffer();
   }


   /**
      Change the FrameBuffer being used as the source
      for the Image painted on the FrameBufferPanel.
   <p>
      This will usually be in response to a call to the
      componentResized() event handler.

      @param fb  new FrameBuffer object for this JFrame
   */
   public void setFrameBuffer(FrameBuffer fb)
   {
      fbp.setFrameBuffer(fb);
   }


   // Implement the ActionListener interface.
   @Override public void actionPerformed(ActionEvent e){}
   // Implement the ItemListener interface.
   @Override public void itemStateChanged(ItemEvent e){}
   // Implement the AdjustmentListener interface.
   @Override public void adjustmentValueChanged(AdjustmentEvent e){}
   // Implement the TextListener interface.
   @Override public void textValueChanged(TextEvent e){}
   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e){}
   // Implement the MouseListener interface.
   @Override public void mouseClicked(MouseEvent e){}
   @Override public void mousePressed(MouseEvent e){}
   @Override public void mouseReleased(MouseEvent e){}
   @Override public void mouseEntered(MouseEvent e){}
   @Override public void mouseExited(MouseEvent e){}
   // Implement the MouseMotionListener interface.
   @Override public void mouseDragged(MouseEvent e){}
   @Override public void mouseMoved(MouseEvent e){}
   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e){}
   // Implement the WindowListener interface.
   @Override public void windowOpened(WindowEvent e){}
   @Override public void windowClosing(WindowEvent e){}
   @Override public void windowClosed(WindowEvent e){}
   @Override public void windowActivated(WindowEvent e){}
   @Override public void windowDeactivated(WindowEvent e){}
   @Override public void windowIconified(WindowEvent e){}
   @Override public void windowDeiconified(WindowEvent e){}
   // Implement the FocusListener interface.
   @Override public void focusGained(FocusEvent e){}
   @Override public void focusLost(FocusEvent e){}
}
