/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;

/**

*/
public class PNWBuilder_COPY
{
   private FrameBufferFrame fbf; // The event handlers need
   private Scene scene;          // access to these fields.

   private double fovy = 90.0;

   private boolean letterbox = false;
   private double aspectRatio = 1.78; //  1920 / 1080
   private double near   =  1.0;
   private double left   = -1.78;
   private double right  =  1.78;
   private double bottom = -1.0;
   private double top    =  1.0;
   private boolean showCamera = false;
   private boolean showFBaspectRatio = false;

   private boolean showMatrix = false;
   private double distanceToCamera = -3;

   // Change this so that you can remember the state of each model.
   private double[] xTranslation = new double[] {0,-1,0,1};
   private double[] yTranslation = new double[] {0,0,0,0};
   private double[] zTranslation = new double[] {distanceToCamera+1.5,distanceToCamera+1.5,distanceToCamera+1.5,distanceToCamera+1.5};
   private double[] xRotation = new double[] {0,0,0,0};
   private double[] yRotation = new double[] {0,0,0,0};
   private double[] zRotation = new double[] {0,0,0,0};
   private double[] scale = new double[] {1,1,1,1};

   private int modelIndex = 0; // 0==PNW, 1==P, 2==N, 3==W
   private Position currentPosition;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public PNWBuilder_COPY()
   {
      // Define initial dimensions for a FrameBuffer.
      int width  = 960;
      int height = 540;

      // Create a FrameBufferFrame holding a FrameBufferPanel.
      fbf = new FrameBufferFrame("Renderer 8", width, height);

      // Create (inner) event handler objects
      // for events from the FrameBufferFrame.
      fbf.addKeyListener(new KeyHandler());
      fbf.addComponentListener(new ComponentHandler());


      // Create the Scene object that we shall render.
      scene = new Scene();

      // Set up the camera's view volume.
      scene.camera.projPerspective(left, right, bottom, top, near);

       Position p = new Position(new P());
       Position n = new Position(new N());
       Position w = new Position(new W());
       Position pnw = new Position();
       pnw.addNestedPosition(p, n, w);
       scene.addPosition(pnw);
       //scene.addPosition(p);
       //scene.addPosition(n);
       //scene.addPosition(w);

      // Add a model of the xy-plane.
      Model wall  = new PanelXY(-6, 6, -3, 3);
      ModelShading.setColor(wall, Color.darkGray.darker());
      scene.addPosition(new Position(wall));
      scene.getPosition(1).matrix = Matrix.translate(0, 0, distanceToCamera);


      // Change this code to build the appropriate scene graph.


      ModelShading.setRandomColor(scene.getPosition(0).getNestedPosition(0).model);
      ModelShading.setRandomColor(scene.getPosition(0).getNestedPosition(1).model);
      ModelShading.setRandomColor(scene.getPosition(0).getNestedPosition(2).model);

       scene.getPosition(0).matrix = Matrix.translate(
               xTranslation[modelIndex],
               yTranslation[modelIndex],
               zTranslation[modelIndex]);

      scene.getPosition(0).getNestedPosition(0).matrix = Matrix.translate(
                                               xTranslation[1],
                                               yTranslation[1],
                                               zTranslation[1]);
      scene.getPosition(0).getNestedPosition(1).matrix = Matrix.translate(
                                               xTranslation[2],
                                               yTranslation[2],
                                               zTranslation[2]);
      scene.getPosition(0).getNestedPosition(2).matrix = Matrix.translate(
                                               xTranslation[3],
                                               yTranslation[3],
                                               zTranslation[3]);



      currentPosition = scene.getPosition(0);

   }


   // Define an inner KeyListener class.
   class KeyHandler extends KeyAdapter {
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
       //Rasterize.debug = ! Rasterize.debug;
      }
      else if ('/' == c)
      {
          modelIndex = (modelIndex + 1) % 4;
          if (0 == modelIndex)
          {
              currentPosition = scene.getPosition(0);
              System.out.println("Working on PNW.");

          }
          else if (1 == modelIndex)
          {
              scene.getPosition(0).getNestedPosition(0).matrix = Matrix.translate(
                      xTranslation[modelIndex],
                      yTranslation[modelIndex],
                      zTranslation[modelIndex]);
              currentPosition = scene.getPosition(1).getNestedPosition(0);
              System.out.println("Working on P.");

          }
          else if (2 == modelIndex)
          {
              currentPosition = scene.getPosition(1).getNestedPosition(1);
              System.out.println("Working on N.");

          }
          else if (3 == modelIndex)
          {
              currentPosition = scene.getPosition(1).getNestedPosition(2);
              System.out.println("Working on W.");

          }
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
         String pers = scene.camera.perspective ? "perspective" : "orthographic";
         System.out.println("Using " + pers + " projection");
      }
      else if ('l' == c)
      {
         letterbox = ! letterbox;
         System.out.print("Letter boxing is turned ");
         System.out.println(letterbox ? "On" : "Off");
      }
      else if ('n' == c || 'N' == c)
      {
         // Move the camera's near plane.
         if ('n' == c)
         {
            near -= 0.01;
         }
         else
         {
            near += 0.01;
         }
      }
      else if ('r' == c || 'R' == c)
      {
         // Change the aspect ratio of the camera's view rectangle.
         if ('r' == c)
         {
            aspectRatio -= 0.1;
         }
         else
         {
            aspectRatio += 0.1;
         }

         // Adjust right and left.
         // (Keep the vertical field-of-view fixed.)
         right =  top * aspectRatio;
         left  = -right;
         System.out.printf("Aspect ratio (of camera's image rectangle) = %.2f\n", aspectRatio);
      }
      else if ('o' == c || 'O' == c)
      {
         // Change left, right, bottom, and top.
         // (Keep the aspect ratio fixed.)
         if ('o' == c)
         {
            left   += 0.1 * aspectRatio;
            right  -= 0.1 * aspectRatio;
            bottom += 0.1;
            top    -= 0.1;
         }
         else
         {
            left   -= 0.1 * aspectRatio;
            right  += 0.1 * aspectRatio;
            bottom -= 0.1;
            top    += 0.1;
         }
      }
      else if ('c' == c)
      {
         // Change the solid random color of the current model.
         if (null != currentPosition.model)
         {
            ModelShading.setRandomColor(currentPosition.model);
         }
      }
      else if ('C' == c)
      {
         // Change each color in the current model to a random color.
         if (null != currentPosition.model)
         {
            ModelShading.setRandomColors(currentPosition.model);
         }
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the current model.
         if (null != currentPosition.model)
         {
            ModelShading.setRandomVertexColors(currentPosition.model);
         }
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the current model.
         if (null != currentPosition.model)
         {
            ModelShading.setRandomLineSegmentColors(currentPosition.model);
         }
      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the current model.
         if (null != currentPosition.model)
         {
            ModelShading.setRainbowLineSegmentColors(currentPosition.model);
         }
      }
      else if ('f' == c)
      {
         showFBaspectRatio = ! showFBaspectRatio;
         if (showFBaspectRatio)
         {
            // Get the new size of the FrameBufferPanel.
            int w = fbf.fbp.getWidth();
            int h = fbf.fbp.getHeight();
            System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);
         }
      }
      else if ('m' == c)
      {
         showMatrix = ! showMatrix;
      }
      else if ('M' == c)
      {
         showCamera = ! showCamera;
      }
      else if ('=' == c)
      {
         xTranslation[modelIndex] = 0.0;
         yTranslation[modelIndex] = 0.0;
         zTranslation[modelIndex] = distanceToCamera;
         xRotation[modelIndex] = 0.0;
         yRotation[modelIndex] = 0.0;
         zRotation[modelIndex] = 0.0;
         scale[modelIndex] = 1.0;
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         scale[modelIndex] /= 1.1;
      }
      else if ('S' == c) // Scale the model 10% larger.
      {
         scale[modelIndex] *= 1.1;
      }
      else if ('x' == c)
      {
         xTranslation[modelIndex] -= 0.1;
      }
      else if ('X' == c)
      {
         xTranslation[modelIndex] += 0.1;
      }
      else if ('y' == c)
      {
         yTranslation[modelIndex] -= 0.1;
      }
      else if ('Y' == c)
      {
         yTranslation[modelIndex] += 0.1;
      }
      else if ('z' == c)
      {
         zTranslation[modelIndex] -= 0.1;
      }
      else if ('Z' == c)
      {
         zTranslation[modelIndex] += 0.1;
      }
      else if ('u' == c)
      {
         xRotation[modelIndex] -= 2.0;
      }
      else if ('U' == c)
      {
         xRotation[modelIndex] += 2.0;
      }
      else if ('v' == c)
      {
         yRotation[modelIndex] -= 2.0;
      }
      else if ('V' == c)
      {
         yRotation[modelIndex] += 2.0;
      }
      else if ('w' == c)
      {
         zRotation[modelIndex] -= 2.0;
      }
      else if ('W' == c)
      {
         zRotation[modelIndex] += 2.0;
      }


      currentPosition.matrix = Matrix.identity();
      currentPosition.matrix.mult(Matrix.translate(
                                         xTranslation[modelIndex],
                                         yTranslation[modelIndex],
                                         zTranslation[modelIndex]));
      currentPosition.matrix.mult(Matrix.rotateX(xRotation[modelIndex]));
      currentPosition.matrix.mult(Matrix.rotateY(yRotation[modelIndex]));
      currentPosition.matrix.mult(Matrix.rotateZ(zRotation[modelIndex]));
      currentPosition.matrix.mult(Matrix.scale(scale[modelIndex]));


      // Set up the camera's view volume.
      if (scene.camera.perspective)
      {
         scene.camera.projPerspective(left, right, bottom, top, near);
      }
      else
      {
         scene.camera.projOrtho(left, right, bottom, top);
      }

      if (showCamera && ('M'==c
           ||'n'==c||'N'==c||'o'==c||'O'==c||'r'==c||'R'==c||'p'==c))
      {
         System.out.print( scene.camera );
      }

      if (showMatrix && ('m'==c||'/'==c||'?'==c||'='==c
           ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
           ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c))
      {
         System.out.println("xRot = " + xRotation
                        + ", yRot = " + yRotation
                        + ", zRot = " + zRotation);
         System.out.print( currentPosition.matrix );
      }

      // Render again.
      setupViewport();
   }}


   // Define an inner ComponentListener class.
   class ComponentHandler extends ComponentAdapter {
   @Override public void componentResized(ComponentEvent e)
   {
      //System.out.println( e );

      // Get the new size of the FrameBufferPanel.
      int w = fbf.fbp.getWidth();
      int h = fbf.fbp.getHeight();

      // Create a new FrameBuffer that fits the new window size.
      FrameBuffer fb = new FrameBuffer(w, h);
      fbf.fbp.setFrameBuffer(fb);

      if (showFBaspectRatio)
         System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);

      // Render again.
      setupViewport();
   }}


   // Get in one place the code to set up the viewport.
   private void setupViewport()
   {
      // Render again.
      // Get the size of the FrameBuffer.
      FrameBuffer fb = fbf.fbp.getFrameBuffer();
      int w = fb.width;
      int h = fb.height;
      // Create a viewport with the correct aspect ratio.
      if ( letterbox )
      {
         if ( aspectRatio <= w/(double)h )
         {
            int width = (int)(h*aspectRatio);
            int xOffset = (w - width)/2;
            fb.setViewport(xOffset, 0, width, h);
         }
         else
         {
            int height = (int)(w/aspectRatio);
            int yOffset = (h - height)/2;
            fb.setViewport(0, yOffset, w, height);
         }
         fb.clearFB(Color.darkGray);
         fb.vp.clearVP(Color.black);
      }
      else // the viewport is the whole framebuffer
      {
         fb.setViewport();
         fb.vp.clearVP(Color.black);
      }
      Pipeline.render(scene, fb.vp);
      fbf.fbp.update();
      fbf.repaint();
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> {new PNWBuilder();}
      );
   }//main()


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the '/' key to cycle between PNW, P, N, W.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate a model along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate a model around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of a model.");
      System.out.println("Use the 'c' key to change a model's random solid color.");
      System.out.println("Use the 'C' key to randomly change a model's colors.");
    //System.out.println("Use the 'e' key to change the random vertex colors.");
      System.out.println("Use the 'e' key to change the random solid edge colors.");
      System.out.println("Use the 'E' key to change the random edge colors.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the 'g' key to toggle gamma correction on and off.");
      System.out.println("Use the n/N keys to move the camera's near plane.");
      System.out.println("Use the o/O keys to change the size of the camera's view rectangle.");
      System.out.println("Use the r/R keys to change the aspect ratio of the camera's view rectangle.");
      System.out.println("Use the 'f' key to toggle showing framebufer aspect ratio.");
      System.out.println("Use the 'l' key to toggle letterboxing on and off.");
      System.out.println("Use the 'm' key to toggle showing the Model transformation matrix.");
      System.out.println("Use the 'M' key to toggle showing the Camera matrix.");
      System.out.println("Use the '=' key to reset the current transformation matrix to the identity.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
