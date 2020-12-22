/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.io.File;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

/**

*/
@SuppressWarnings("serial")
public class InteractiveLetterTester extends InteractiveFrame
{
   private Scene scene;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public InteractiveLetterTester(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create the Scene object that we shall render
      scene = new Scene();

      // Add a letter to the Scene.
      scene.addModel( new P() );   // Use any Model you want here.

      // Push the letter away from where the camera is.
      for (Vertex v : scene.modelList.get(0).vertexList)
      {
         v.z -= 2;
      }
   }


   // Implement part of the KeyListener interface.
   @Override public void keyTyped(KeyEvent e)
   {
      //System.out.println( e );

      char c = e.getKeyChar();
      if ('h' == c)
      {
         print_help_message();
         return;
      }
      else if ('d' == c)
      {
         Pipeline.debug = ! Pipeline.debug;
       //Rasterize_Clip.debug = ! Rasterize_Clip.debug;
      }
      else if ('c' == c)
      {
         Pipeline.doClipping = ! Pipeline.doClipping;
         System.out.print("Clipping is turned ");
         System.out.println(Pipeline.doClipping ? "On" : "Off");
      }
      else if ('p' == c)
      {
         scene.camera.perspective = ! scene.camera.perspective;
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.x /= 1.1;  // This does NOT work with
            v.y /= 1.1;  // perspective projection.
            v.z /= 1.1;
         }
      }
      else if ('S' == c) // Scale the model 10% larger.
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.x *= 1.1;  // This does NOT work with
            v.y *= 1.1;  // perspective projection.
            v.z *= 1.1;
         }
      }
      else if ('x' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.x -= 0.1;
         }
      }
      else if ('X' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.x += 0.1;
         }
      }
      else if ('y' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.y -= 0.1;
         }
      }
      else if ('Y' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.y += 0.1;
         }
      }
      else if ('z' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.z -= 0.1;
         }
      }
      else if ('Z' == c)
      {
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.z += 0.1;
         }
      }

      // Render again.
      FrameBuffer fb = this.fbp.getFrameBuffer();
      fb.clearFB(Color.black);
      Pipeline.render(scene, fb.vp);
      fbp.update();
      repaint();
   }


   // Implement part of the ComponentListener interface.
   @Override public void componentResized(ComponentEvent e)
   {
      //System.out.println( e );

      // Get the new size of the FrameBufferPanel.
      int w = this.fbp.getWidth();
      int h = this.fbp.getHeight();

      // Create a new FrameBuffer that fits the new window size.
      FrameBuffer fb = new FrameBuffer(w, h);
      this.fbp.setFrameBuffer(fb);
      fb.clearFB(Color.black);
      Pipeline.render(scene, fb.vp);
      fbp.update();
      repaint();
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // Define initial dimensions for a FrameBuffer.
      int width  = 1024;
      int height = 1024;
      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions. NOTE: We need to call the
      // InteractiveLetterTester constructor in the Java GUI Event
      // Dispatch Thread, otherwise we get a race condition
      // between the constructor (running in the main() thread)
      // and the very first ComponentEvent (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() // an anonymous inner class constructor
         {
            public void run() // implement the Runnable interface
            {
               // call the constructor that builds the gui
               new InteractiveLetterTester("Renderer 5", width, height);
            }
         }
      );
   }//main()


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the model along the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the model.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
