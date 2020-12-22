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
   for Java programs. That is, this class allows us to write Java programs
   that use our renderer instead of the renderer built into Java's GUI library.
   Of course, our renderer will be much slower than the one built into Java
   (which uses the computer's GPU).
<p>
   This {@link JFrame} displays our renderer's {@link FrameBuffer} in a
   {@link FrameBufferPanel}.
<p>
   This class should be instantiated by an application that uses
   our renderer. The application should initialize a
   {@link renderer.scene.Scene} object with appropriate models
   and geometry. The application should render the
   {@link renderer.scene.Scene} into the {@link FrameBuffer}
   object contained in this object's {@link FrameBufferPanel}.
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
public class FrameBufferFrame extends JFrame
{
   public FrameBufferPanel fbp;

   /**
      Create a {@link JFrame} window with a {@link BorderLayout} and place a
      {@link FrameBufferPanel} (containing a {@link FrameBuffer}) in the center
      of the layout.

      @param title     title for the {@link JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link JFrame}
   */
   public FrameBufferFrame(String title, int fbWidth, int fbHeight)
   {
      super(title);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationByPlatform(true);

      // Create the initial FrameBuffer for the FrameBufferPanel.
      FrameBuffer fb = new FrameBuffer(fbWidth, fbHeight);

      // Create a FrameBufferPanel (a JPanel) for this FrameBufferFrame.
      fbp = new FrameBufferPanel(fb);

      // Place the FrameBufferPanel in this FrameBufferFrame.
      this.getContentPane().add(fbp, BorderLayout.CENTER);
      this.pack();
      this.setVisible(true);
   }
}
