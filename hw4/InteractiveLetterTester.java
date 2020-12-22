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
   private boolean showMatrix = false;
   private double xTranslation = 0.0;
   private double yTranslation = 0.0;
   private double zTranslation = 0.0;
   private double xRotation = 0.0;
   private double yRotation = 0.0;
   private double zRotation = 0.0;
   private double scale = 1.0;

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
      scene.addPosition(new Position( new P() )); // Use any Model you want here.

      // Push the letter away from where the camera is.
      scene.positionList.get(0).matrix = Matrix.translate(0, 0, -2);

      // Give the letter a random color.
      ModelShading.setRandomColor( scene.positionList.get(0).model );
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
       //Clip.debug = ! Clip.debug;
       //RasterizeAntialias.debug = ! RasterizeAntialias.debug
      }
      else if ('a' == c)
      {
         RasterizeAntialias.doAntialiasing = ! RasterizeAntialias.doAntialiasing;
         System.out.print("Anti-aliasing is turned ");
         System.out.println(RasterizeAntialias.doAntialiasing ? "On" : "Off");
      }
      else if ('g' == c)
      {
         RasterizeAntialias.doGamma = ! RasterizeAntialias.doGamma;
         System.out.print("Gamma correction is turned ");
         System.out.println(RasterizeAntialias.doGamma ? "On" : "Off");
      }
      else if ('p' == c)
      {
         scene.camera.perspective = ! scene.camera.perspective;
         String p = scene.camera.perspective ? "perspective" : "orthographic";
         System.out.println("Using " + p + " projection");
      }
      else if ('c' == c)
      {
         // Change the solid random color of the cube.
         ModelShading.setRandomColor(scene.positionList.get(0).model);
      }
      else if ('C' == c)
      {
         // Change each color in the cube to a random color.
         ModelShading.setRandomColors(scene.positionList.get(0).model);
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the cube.
         ModelShading.setRandomVertexColors(scene.positionList.get(0).model);
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the cube.
         ModelShading.setRandomLineSegmentColors(scene.positionList.get(0).model);
      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the cube.
         ModelShading.setRainbowLineSegmentColors(scene.positionList.get(0).model);
      }
      else if ('m' == c)
      {
         showMatrix = ! showMatrix;
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         scale /= 1.1;
      }
      else if ('S' == c) // Scale the model 10% larger.
      {
         scale *= 1.1;
      }
      else if ('x' == c)
      {
         xTranslation -= 0.1;
      }
      else if ('X' == c)
      {
         xTranslation += 0.1;
      }
      else if ('y' == c)
      {
         yTranslation -= 0.1;
      }
      else if ('Y' == c)
      {
         yTranslation += 0.1;
      }
      else if ('z' == c)
      {
         zTranslation -= 0.1;
      }
      else if ('Z' == c)
      {
         zTranslation += 0.1;
      }
      else if ('u' == c)
      {
         xRotation -= 2.0;
      }
      else if ('U' == c)
      {
         xRotation += 2.0;
      }
      else if ('v' == c)
      {
         yRotation -= 2.0;
      }
      else if ('V' == c)
      {
         yRotation += 2.0;
      }
      else if ('w' == c)
      {
         zRotation -= 2.0;
      }
      else if ('W' == c)
      {
         zRotation += 2.0;
      }

      // Set the model-to-camera transformation matrix.
      // The order of the transformations is very important!
      Position model_p = scene.positionList.get(0);
      // Push the model away from where the camera is.
      model_p.matrix = Matrix.translate(0, 0, -2);
      // Move the model relative to its new position.
      model_p.matrix.mult( Matrix.translate(xTranslation,
                                            yTranslation,
                                            zTranslation) );
      model_p.matrix.mult( Matrix.rotateX(xRotation) );
      model_p.matrix.mult( Matrix.rotateY(yRotation) );
      model_p.matrix.mult( Matrix.rotateZ(zRotation) );
      model_p.matrix.mult( Matrix.scale(scale) );

      if (showMatrix && ('m'==c
           ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
           ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c))
      {
         System.out.println("xRot = " + xRotation
                        + ", yRot = " + yRotation
                        + ", zRot = " + zRotation);
         System.out.print( model_p.matrix );
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
      // with the given dimensions.
      javax.swing.SwingUtilities.invokeLater(
         () -> new InteractiveLetterTester("Renderer 6", width, height)
      );
   }


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the model along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate the model around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the model.");
      System.out.println("Use the 'c' key to change the random solid model color.");
      System.out.println("Use the 'C' key to randomly change model's colors.");
    //System.out.println("Use the 'e' key to change the random vertex colors.");
      System.out.println("Use the 'e' key to change the random solid edge colors.");
      System.out.println("Use the 'E' key to change the random edge colors.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the 'g' key to toggle gamma correction on and off.");
      System.out.println("Use the 'm' key to toggle showing the Model transformation matrix.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
