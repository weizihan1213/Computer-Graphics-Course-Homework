/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

import java.lang.Math.*;
/**

*/
public class Pt2Hw3
{
   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public Pt2Hw3()
   {
      // Define initial dimensions for a FrameBuffer.
      final int fbWidth  = 1024;
      final int fbHeight = 1024;

      // Create a FrameBufferFrame holding a FrameBufferPanel.
      FrameBufferFrame fbf = new FrameBufferFrame("Renderer 2", fbWidth, fbHeight);
      fbf.setResizable(false);

      // Create the Scene object that we shall render
      Scene scene = new Scene();

      // Create several Model objects.
      scene.addModel(new Square(1));
      scene.addModel(new Square(2));
      scene.addModel(new Square(3));
      scene.addModel(new Circle(3, 4));
      scene.addModel(new Circle(3, 64));

      // Give each model a useful name.
      scene.modelList.get(0).name = "Square_1";
      scene.modelList.get(1).name = "Square_2";
      scene.modelList.get(2).name = "Square_3";
      scene.modelList.get(3).name = "Diamond";
      scene.modelList.get(4).name = "Circle";

      // Push the models away from where the camera is.
      for (Model m : scene.modelList)
      {
         for (Vertex v : m.vertexList)
         {
            v.z -= 10;
         }
      }

      // Give each model an initial position in the scene.
      for (Vertex v : scene.modelList.get(0).vertexList)
      {
         v.x += 0;
         v.y += 0;
      }
      for (Vertex v : scene.modelList.get(1).vertexList)
      {
         v.x -= 5;
         v.y -= 5;
      }
      for (Vertex v : scene.modelList.get(2).vertexList)
      {
         v.x += 5;
         v.y += 5;
      }
      for (Vertex v : scene.modelList.get(3).vertexList)
      {
         v.x += 5;
         v.y -= 5;
      }
      for (Vertex v : scene.modelList.get(4).vertexList)
      {
         v.x -= 5;
         v.y += 5;
      }
      
      // Set the instance variables equal to the center of the circle
      x0c = -5;
      y0c = 5;
      
      // Render.
      FrameBuffer fb = fbf.fbp.getFrameBuffer();
      fb.clearFB(Color.black);
      Pipeline.render(scene, fb.vp);
      fbf.fbp.update();
      fbf.repaint();
      

      
      fbf.addMouseListener(
         new MouseListener(){
          @Override public void mouseClicked  (MouseEvent e) {}
          @Override public void mouseEntered  (MouseEvent e) {}
          @Override public void mouseExited   (MouseEvent e) {}
          @Override public void mousePressed  (MouseEvent e)
          {
          
            x_pressed = e.getX();               // initailizing instance variables
            y_pressed = e.getY();
            
            double x_pix = getXP( e.getX() );   // subtracting the offset of the window
            double y_pix = getYP( e.getY() );
            
            double x_cam = getXC( x_pix );      // geting the camera coordinates from the pixel coordinates    
            double y_cam = getYC( y_pix );
            System.out.println(x_cam+ ", " + y_cam);
            wasHit(x_cam, y_cam, scene);        // determing the objects that were 'hit'
            
            // Render.
            FrameBuffer fb = fbf.fbp.getFrameBuffer();
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbf.fbp.update();
            fbf.repaint();
          }
          @Override public void mouseReleased (MouseEvent e)
          {
            if( hit[4] == true )
            {
               x0c = getXC( e.getX() );   // update the center of the circle (FIX)
               y0c = getYC( e.getY() );
            }
            
            for(int i = 0; i < 5; i++) { hit[i] = false; }  // Once user releases click, all hit array is reset to false
            
            // Render.
            FrameBuffer fb = fbf.fbp.getFrameBuffer();
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbf.fbp.update();
            fbf.repaint();
          }          
       });
         
      fbf.addMouseMotionListener(
         new MouseMotionListener(){
         public void mouseMoved     (MouseEvent e) {}
         public void mouseDragged   (MouseEvent e)
         {
            double x_dragged = e.getX();
            double y_dragged = e.getY();
            
            double x_pixD = x_dragged - x_pressed;
            double y_pixD = y_dragged - y_pressed;
            
            double x_pressedC = getXC( x_pressed );   // Obtaining x and y camera coordinates for the pressed instance variables
            double y_pressedC = getYC( y_pressed );
            
            double x_draggedC = getXC( x_dragged );   // Obtaining x and y camera coordinates for the dragged variables
            double y_draggedC = getYC( y_dragged );
            
            double x_camD = x_draggedC - x_pressedC;  // Obtaining x and y camera distance
            double y_camD = y_draggedC - y_pressedC;
            
            x_pressed += x_pixD;
            y_pressed += y_pixD;
            
            for(int i = 0; i <= 3; i++)
            {
               if( hit[i] == true )
               {
                  for(Vertex v: scene.modelList.get(i).vertexList)
		            {
			            v.x += x_camD;
			            v.y += y_camD;
	               }
               }
            }
            
            if( hit[4] == true )
            {
               for(Vertex v: scene.modelList.get(4).vertexList)
               {
                  v.x += x_camD;
                  v.y += y_camD;
               }
            }
            // Render.
            FrameBuffer fb = fbf.fbp.getFrameBuffer();
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbf.fbp.update();
            fbf.repaint();
         }
      });
   }
   
   boolean hit[] = new boolean[5];
   private void wasHit(double x, double y, Scene scene)
   {
      // Detect if user 'hit' a square object
      for(int i = 0; i <= 2; i++)
      {
         double x0s = scene.modelList.get(i).vertexList.get(0).x; // x0s and y0s get the x and y value of the upper left corner of the square
         double y0s = scene.modelList.get(i).vertexList.get(0).y;     
         double x1s = scene.modelList.get(i).vertexList.get(2).x; // x1s and y1s get the x and y value of the lower right corner of the square
         double y1s = scene.modelList.get(i).vertexList.get(2).y;  
         
         if( x >= x0s && x <= x1s && 
         y >= y0s && y <= y1s )  { hit[i] = true; }
      }
      
      // Detect if we hit the diamond
      double x0d = scene.modelList.get(3).vertexList.get(2).x;
      double y0d = scene.modelList.get(3).vertexList.get(3).y;
      double x1d = scene.modelList.get(3).vertexList.get(0).x;
      double y1d = scene.modelList.get(3).vertexList.get(1).y;
      
      if( x >= x0d && x <= x1d &&
          y >= y0d && y <= y1d ) { hit[3] = true; }

      // Detect if we hit the circle
      if( distance(x, x0c, y, y0c) <= 3 ) { hit[4] = true; }
   }
   
   private double getXP(double xP) { return xP - 8; }    // Returns the X pixel coordinate minus the offset of the title bar
   private double getYP(double yP) { return yP - 31; }   // Returns the Y pixel coordinate minus the offset of the title bar
   
   private double getXC(double xP) { return (((xP-.5) * (2.0/1024)) - 1) * 10; }    // Returns the X camera (or world) coordinate
   private double getYC(double yP) { return (((yP-.5) * (2.0/1024)) - 1) * -10; }   // Returns the Y camera (or world) coordinate
   
   // Computes the distance between two points
   private double distance (double x1, double x2, double y1, double y2) { return Math.pow(Math.pow(x2-x1, 2) + Math.pow(y2-y1 ,2), 0.5); }
   
   double x_pressed = 0;   // instance variables to keep track of the mouse's position before it's dragged
   double y_pressed = 0;
   
   double x0c;             // Instance variables to keep track of the center of the circle
   double y0c;
   
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
         new Runnable() { // an anonymous inner class constructor
            public void run() { // implement the Runnable interface
               new Pt2Hw3(); // call the constructor that builds the gui
      }});
   }


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
