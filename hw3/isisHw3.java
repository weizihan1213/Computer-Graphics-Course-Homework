/*
Name:Isis Curiel
CS 455 HW3
10/19/19
Description:
You have to write this program in several (many) stages. First, give the
program very basic event handlers that just print the event objects to stdout.
Then, use the MouseEvent api to print out just the information that is relevant
(like the x and y coordinates of a mouse click). When you know the pixel
coordinates of a mouse click, you need to transform the pixel coordinates
into the corresponding (x, y, z) coordinates in camera space. The geometric
objects are all in the z = -10 plane, so you need to transform the
2-dimensional pixel coordinates into the appropriate (x, y) coordinates of
the z = -10 plane. Print this information to stdout and then click on several
obvious points in the window and make sure your coordinate transformation is
correct. When you can click on a point and get its correct camera space
coordinates, then you are ready to determine if you are clicking on a
geometric shape. You know the location and size of each shape in the
z = -10 plane. So you should be able to tell if a mouse click is inside
a shape. Write a boolean hit() method that determines if a mouse click
"hits" a shape. Iterate through the scene.modelList and look for a shape
that is hit and print the model's name to stdout (this code should could
be in the mousePressed() method). Here is an important hint. You probably
don't want to work directly with the 3D models themselves since they are
meant for 3d rendering (all the vertices and line segments in the Circle
object are not going to be of much use when you want to see if you clicked
within the circle). It would be a good idea to create another representation
of each shape that would be easier to work with when finding hits. For example,
for the circle, all you need to know are its center and radius to determine
a hit.

When you can determine if a mouse click is within a shape, you are ready to
start working with the mouseDragged() method. A user will press down on the
mouse button, drag the mouse, then release the mouse button. You get a call
to mousePressed() when the user presses down on the mouse button and you get
a call to mouseReleased() when the user releases the mouse button (or a call
to mouseExited() if the dragged mouse leaves the window). Between the calls
to mousePressed() and mouseReleased(), while the mouse is being dragged, you
will get calls to mouseDragged(). Each call you receive to mouseDragged()
represents some amount of movement of the mouse, sometimes its just one pixel
worth of movement, sometimes it is dozens of pixels worth of movement.
You need to begin with writing a simple combination of mousePressed(),
mouseDragged(), and mouseReleased() methods that just keeps track of where
the mouse is when its pressed, where the mouse is currently at each call to
mouseDragged(), and where the mouse is when the mouse is released. Print all
this information to stdout and get a feel for how mouse dragging works. Compute
the "distance traveled" by the mouse between calls to mouseDragged() and print
this to stdout (in both pixel coordinates and camera coordinates and in both
the x-direction and the y-direction). The distance traveled by the mouse in
camera coordinates is vital for being able to move a shape by the appropriate
amount.

Now you know when a mouse press lands within a shape and how far a mouse drag
moved. So now you can take the x and y distances traveled by the mouse (between
calls to the mouseDragged() method) and use them to update the location of the
shape that was hit. This part is similar to the last assignment. You need to
update the x and y coordinates of each vertex in the model by the distance
(in camera space) that the mouse moved. (Be sure to also update your
alternative representation of each model.) After updating each model, the
scene needs to be rendered. Your listener method should end with a block of
code like this.

    // Render again.
    fb.clearFB(Color.black);
    Pipeline.render(scene, fb.vp);
    fbf.fbp.update();
    fbf.repaint();
Here is one little detail that you will need to deal with. When you click on
the mouse, the pixel coordinates that Java gives you are relative to the whole
Java window, not to the framebuffer panel within the window. Another way to put
this is that pixel (0, 0) is not the upper left hand corner of the framebuffer,
it is the upper left hand corner of the title bar of the Java window. You will
need to subtract a small offset from the coordinates that Java gives you in
order to compensate for the title bar at the top of the window and the small
border on the left edge of the window.

Here is another useful idea. The steps above tell you to print out a lot of
information to stdout. All of that output is useful, so you probably don't want
to delete the code that produces it. But you don't always want to see all of
that output. You can create a keyboard command (or several keyboard commands)
to turn on and off the "debugging" output
*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.Math;
/**
.
*/
@SuppressWarnings("serial")
public class isisHw3
{
   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
  public static int xPoint;
  public static int yPoint;
  public static int xDis;
  public static int yDis;
  public static boolean hit[] = new boolean[5];
   public isisHw3()
   {
      // Define initial dimensions for a FrameBuffer.
      final int fbWidth  = 1024;
      final int fbHeight = 1024;

      // Create a FrameBufferFrame holding a FrameBufferPanel.
      FrameBufferFrame fbf = new FrameBufferFrame("Renderer 2", fbWidth, fbHeight);
      fbf.setResizable(false);

      // Create the Scene object that we shall render
      Scene scene = new Scene();

      //add Listeners
      fbf.addMouseListener(
          new MouseListener(){
             @Override public void mouseClicked(MouseEvent e){
              double  xCamPt = (((e.getX()-.5)*(2.0/1024.0))-1.0)*10;
              double yCamPt = (((e.getY()-.5)*(2.0/1024.0))-1.0)*10;
              System.out.println("mouseClicked: X="+e.getX()+", Y="+e.getY());
              System.out.println("Cam X="+xCamPt+",Cam Y="+yCamPt);
              //prints out cam space coords of mouse clicked
             }
             @Override public void mouseReleased(MouseEvent e){
               System.out.println("mouseReleased: X="+e.getX()+", Y="+e.getY());
               hit[0]=false;
               hit[1]=false;
               hit[2]=false;
               hit[3]=false;
               hit[4]=false;
               //render update
               FrameBuffer fb = fbf.fbp.getFrameBuffer();
               fb.clearFB(Color.black);
               Pipeline.render(scene, fb.vp);
               fbf.fbp.update();
               fbf.repaint();
             } //ME
             @Override public void mouseEntered(MouseEvent e){
                System.out.println("mouseEntered: X=" + e.getX() + ", Y=" + e.getY());
                //prints out x/y vals of when cursor entered screen
             }
             @Override public void mouseExited(MouseEvent e) {
               System.out.println("mouseExited: X="+e.getX()+", Y="+e.getY());
               hit[0]=false;
               hit[1]=false;
               hit[2]=false;
               hit[3]=false;
               hit[4]=false;
               //prints out x/y vals of when cursor exited screen
             }//ME
             @Override public void mousePressed(MouseEvent e){
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

               double disX = (X - getIntialXcircle()) * (X - getIntialXcircle());
               double disY = (Y - getIntialYcircle()) * (Y - getIntialYcircle());
               double disFromCenter =disX + disY;

               xPoint = e.getX();
               yPoint = e.getY();
               System.out.println("mousePressed: X="+xPoint+", Y="+yPoint);


               //Figure out if mouse inside shape
               if(X>=s1x1 && X<=s1x2 && Y>=s1y1 && Y<=s1y2)
                {System.out.println("Square_1");
                 hit[0] =true;}
               else if(X>=s2x1 &&X<=s2x2 &&Y>=s2y1 &&Y<=s2y2)
                {System.out.println("Square_2");
                 hit[1] =true;}
               else if(X>=s3x1 && X<=s3x2 && Y>=s3y1 && Y<=s3y2)
                {System.out.println("Square_3");
                 hit[2] =true;}
               else if(X>=s4x1 && X<=s4x2 && Y>=s4y1 && Y<=s4y2)
               {System.out.println("Diamond");
                hit[3] =true;}
               if(disFromCenter<=9)
               {System.out.println("Circle");
                hit[4]=true;}

             // Render updates
             FrameBuffer fb = fbf.fbp.getFrameBuffer();
             fb.clearFB(Color.black);
             Pipeline.render(scene, fb.vp);
             fbf.fbp.update();
             fbf.repaint();
      }
      }
      );

      fbf.addMouseMotionListener(
          new MouseMotionListener(){
             @Override public void mouseDragged(MouseEvent e){
               xDis = e.getX();
               yDis = e.getY();
               int pixelDisX = xDis - xPoint;
               int pixelDisY = yDis - yPoint;

                // transform the pix coor into cam space coordinates.
               double camXPt = (((xPoint - .5)*(2.0/1024.0))-1.0)*10.0;
               double camYPt = (((yPoint - .5)*(2.0/1024.0))-1.0)*-10.0;
               double camXDis = (((xDis - .5)*(2.0/1024.0))-1.0)*10.0;
               double camYDis = (((yDis - .5)*(2.0/1024.0))-1.0)*-10.0;

               // calculate the dis in cam space.
               double camDisX = camXDis - camXPt;
               double camDisY = camYDis - camYPt;

               System.out.println(xDis+ "," + yDis);
               System.out.println("Pix_Dis_X:" + pixelDisX + "," + "Pix_Dis_Y:" + pixelDisY);
               System.out.println("Cam_Dis_X:" + camDisX + "," + "Cam_Dis_Y:" + camDisY);

               xPoint = xPoint + pixelDisX;
               yPoint = yPoint + pixelDisY;

               if(hit[0]==true)
               {
                 for(Vertex v : scene.modelList.get(0).vertexList)
                 {
                   v.x += camXDis;
                   v.y += camYDis;
                 }
               }
               if(hit[1]==true)
               {
                 for(Vertex v : scene.modelList.get(1).vertexList)
                 {
                   v.x += camXDis;
                   v.y += camYDis;
                 }
               }
               if(hit[2]==true)
               {
                 for(Vertex v : scene.modelList.get(2).vertexList)
                 {
                   v.x += camXDis;
                   v.y += camYDis;
                 }
               }
               if(hit[3]==true)
               {
                 for(Vertex v : scene.modelList.get(3).vertexList)
                 {
                   v.x += camXDis;
                   v.y += camYDis;
                 }
               }
               if(hit[4]==true)
               {
                 for (Vertex v : scene.modelList.get(4).vertexList)
                 {
                   v.x += camXDis;
                   v.y += camYDis;
                 }
                 intialXCircle += camXDis;
                 intialYCircle += camYDis;
             }
               FrameBuffer fb = fbf.fbp.getFrameBuffer();
               fb.clearFB(Color.black);
               Pipeline.render(scene, fb.vp);
               fbf.fbp.update();
               fbf.repaint();
             }
              @Override public void mouseMoved(MouseEvent e){}
             }
      );

      fbf.addKeyListener(
         new KeyListener(){ // An anonymous local inner class constructor.
            // Implement the three methods of the KeyListener interface.
            @Override public void keyPressed (KeyEvent e){}
            @Override public void keyReleased(KeyEvent e){}
            @Override public void keyTyped   (KeyEvent e){
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
               else if ('c'==c){
                 Pipeline.doClipping = ! Pipeline.doClipping;
                 System.out.print("Clipping is turned ");
                 System.out.println(Pipeline.doClipping ? "On" : "Off");
                return;
               }
             }
         }
      );

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
   double intialXCircle = -5;   // current val of current circ x pos
   double intialYCircle = 5;    // current val of current circ y pos

   private double getIntialXcircle() { return  intialXCircle; }
   private double getIntialYcircle() { return  intialYCircle; }

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
