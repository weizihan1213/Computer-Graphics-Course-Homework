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

/**

*/
public class Hw3 implements MouseListener,MouseMotionListener,KeyListener
{
   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */

   public static int x_p;
   public static int y_p;
   public static int x_d;
   public static int y_d;
   public static boolean hit[] = new boolean[5];
   private FrameBufferFrame fbf; // The event handlers need
   private Scene scene;          // access to these two fileds.

   double x0circle = -5;                                      // Gets the current X value of the center of the cirlce
   double y0circle = 5;										 // Gets the current Y value of the center of the cirlce

   private double getX0circle() { return x0circle; }
   private double getY0circle() { return y0circle; }




   public Hw3()
   {
      // Define initial dimensions for a FrameBuffer.
      final int fbWidth  = 1024;
      final int fbHeight = 1024;


      // Create a FrameBufferFrame holding a FrameBufferPanel.
      fbf = new FrameBufferFrame("Renderer 2", fbWidth, fbHeight);
      fbf.setResizable(false);


      fbf.addMouseListener(this);
      fbf.addMouseMotionListener(this);
      fbf.addKeyListener(this);



      // Create the Scene object that we shall render
      scene = new Scene();

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

      // Render.
      FrameBuffer fb = fbf.fbp.getFrameBuffer();
      fb.clearFB(Color.black);
      Pipeline.render(scene, fb.vp);
      fbf.fbp.update();
      fbf.repaint();
   }

   // implement the MouseListener interfaces.
   @Override public void mouseEntered(MouseEvent e)
   {
	  System.out.println("Mouse Entered:" + e.getX() + "," + e.getY());
   }
   @Override public void mouseClicked(MouseEvent e)
   {
	  // transform the pixel coordinates into camera space coordinates.
	  double X = (((e.getX() - .5)*(2.0/1024.0))-1.0)*10.0;
	  double Y = (((e.getY() - .5)*(2.0/1024.0))-1.0)*-10.0;
	  System.out.println("Camera Space Coordinates:" + String.format("%.2f",X)+","+String.format("%.2f",Y));
   }
   @Override public void mousePressed(MouseEvent e)
   {
	 //transform the pixel coordinates into the camera space coordiantes.
	  double X = (((e.getX() - .5)*(2.0/1024.0))-1.0)*10.0;
	  double Y = (((e.getY() - .5)*(2.0/1024.0))-1.0)*-10.0;


	  // Square_1
	  double s1x1 = scene.modelList.get(0).vertexList.get(0).x;
	  double s1y1 = scene.modelList.get(0).vertexList.get(0).y;
	  double s1x2 = scene.modelList.get(0).vertexList.get(2).x;
	  double s1y2 = scene.modelList.get(0).vertexList.get(2).y;

	  // Square_2
	  double s2x1 = scene.modelList.get(1).vertexList.get(0).x;
	  double s2y1 = scene.modelList.get(1).vertexList.get(0).y;
	  double s2x2 = scene.modelList.get(1).vertexList.get(2).x;
	  double s2y2 = scene.modelList.get(1).vertexList.get(2).y;

	  // Square_3
	  double s3x1 = scene.modelList.get(2).vertexList.get(0).x;
	  double s3y1 = scene.modelList.get(2).vertexList.get(0).y;
	  double s3x2 = scene.modelList.get(2).vertexList.get(2).x;
	  double s3y2 = scene.modelList.get(2).vertexList.get(2).y;

	  // diamond
	  double s4x1 = scene.modelList.get(3).vertexList.get(2).x;
	  double s4y1 = scene.modelList.get(3).vertexList.get(3).y;
	  double s4x2 = scene.modelList.get(3).vertexList.get(0).x;
	  double s4y2 = scene.modelList.get(3).vertexList.get(1).y;



	  double dis_x = (X - getX0circle()) * (X - getX0circle());
	  double dis_y = (Y - getY0circle()) * (Y - getY0circle());
	  double Euc_dis =dis_x + dis_y;



	  x_p = e.getX();
	  y_p = e.getY();

	  System.out.println("Mouse Pressed:" + x_p + "," + y_p);


	  // Dtermine if the mouse is inside the pattern.
	  if(X>=s1x1 && X<=s1x2 && Y>=s1y1 && Y<=s1y2)
	  {
	  	  System.out.println("Square_1");
	  	  hit[0]=true;
	  }
	  if(X>=s2x1 &&X<=s2x2 &&Y>=s2y1 &&Y<=s2y2)
	  {
	  	  System.out.println("Square_2");
	  	  hit[1]=true;
	  }
	  if(X>=s3x1 && X<=s3x2 && Y>=s3y1 && Y<=s3y2)
	  {
	  	  System.out.println("Square_3");
	  	  hit[2]=true;
	  }
	  if(X>=s4x1 && X<=s4x2 && Y>=s4y1 && Y<=s4y2)
	  {
	      System.out.println("Diamond");
	      hit[3]=true;
	  }
	  if(Euc_dis<=9)
	  {
		  System.out.println("Circle");
		  hit[4]=true;
      }

	// Render.
    FrameBuffer fb = fbf.fbp.getFrameBuffer();
    fb.clearFB(Color.black);
    Pipeline.render(scene, fb.vp);
    fbf.fbp.update();
    fbf.repaint();
   }
   @Override public void mouseReleased(MouseEvent e)
   {

	  System.out.println("Mouse Released:" + e.getX() + "," + e.getY());
	  hit[0]=false;
	  hit[1]=false;
	  hit[2]=false;
	  hit[3]=false;
	  hit[4]=false;

	  // Render.
	  FrameBuffer fb = fbf.fbp.getFrameBuffer();
	  fb.clearFB(Color.black);
	  Pipeline.render(scene, fb.vp);
	  fbf.fbp.update();
      fbf.repaint();

   }
   @Override public void mouseExited(MouseEvent e)
   {
	   System.out.println("Mouse Exited:" +e.getX() + "," + e.getY());
	   hit[0]=false;
	   hit[1]=false;
	   hit[2]=false;
	   hit[3]=false;
	   hit[4]=false;
   }

   // implement the MouseMotionListener interfaces.
   @Override public void mouseDragged(MouseEvent e)
   {
	  int x_d = e.getX();
	  int y_d = e.getY();

	  // calculate the pixel distance between the start point and end point.
	  int pix_dis_x = x_d - x_p;
	  int pix_dis_y = y_d - y_p;

	  // transform the pixel coordinates into camera space coordinates.
	  double Cam_x_p = (((x_p - .5)*(2.0/1024.0))-1.0)*10.0;
	  double Cam_y_p = (((y_p - .5)*(2.0/1024.0))-1.0)*-10.0;
	  double Cam_x_d = (((x_d - .5)*(2.0/1024.0))-1.0)*10.0;
	  double Cam_y_d = (((y_d - .5)*(2.0/1024.0))-1.0)*-10.0;

	  // calculate the distance in camera space.
	  double cam_dis_x = Cam_x_d - Cam_x_p;
	  double cam_dis_y = Cam_y_d - Cam_y_p;

	  System.out.println(e.getX()+ "," + e.getY());
	  System.out.println("Pix_Dis_X:" + pix_dis_x + "," + "Pix_Dis_Y:" + pix_dis_y);
	  System.out.println("Cam_Dis_X:" + String.format("%.2f",cam_dis_x) + "," + "Cam_Dis_Y:" + String.format("%.2f",cam_dis_y));

	  x_p = x_p + pix_dis_x;
	  y_p = y_p + pix_dis_y;


	  if(hit[0]==true)
	  {
		  for(Vertex v : scene.modelList.get(0).vertexList)
		  {
			  v.x += cam_dis_x;
			  v.y += cam_dis_y;
	      }
      }
      if(hit[1]==true)
      {
		  for(Vertex v : scene.modelList.get(1).vertexList)
		  {
			  v.x += cam_dis_x;
			  v.y += cam_dis_y;
		  }
	  }
	  if(hit[2]==true)
	  {
		 for(Vertex v : scene.modelList.get(2).vertexList)
		 {
			v.x += cam_dis_x;
			v.y += cam_dis_y;
		 }
	  }
	  if(hit[3]==true)
	  {
		  for(Vertex v : scene.modelList.get(3).vertexList)
		  {
			  v.x += cam_dis_x;
			  v.y += cam_dis_y;
		  }
	  }
	  if(hit[4]==true)
	  {
		  for (Vertex v : scene.modelList.get(4).vertexList)
		  {
			  v.x += cam_dis_x;
              v.y += cam_dis_y;
		  }

		  x0circle += cam_dis_x;
          y0circle += cam_dis_y;


	  }

  // Render.
  FrameBuffer fb = fbf.fbp.getFrameBuffer();
  fb.clearFB(Color.black);
  Pipeline.render(scene, fb.vp);
  fbf.fbp.update();
  fbf.repaint();
  }


   @Override public void mouseMoved(MouseEvent e){}

   // implement the KeyListener interfaces.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e)
   {
	   char c = e.getKeyChar();
	   if('d'==c)
	   {
		   Pipeline.debug = ! Pipeline.debug;
	   }
	   else if ('h' == c)
	   {
	       print_help_message();
	       return;
      }


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
         new Runnable() { // an anonymous inner class constructor
            public void run() { // implement the Runnable interface
               new Hw3(); // call the constructor that builds the gui
      }});
   }


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
