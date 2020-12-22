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
public class InteractiveModelsAll_R2 extends InteractiveFrame
{
   private Scene scene;
   private ArrayList<Model> modelArray = new ArrayList<>();
   private int currentModel = 0;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public InteractiveModelsAll_R2(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create the Scene object that we shall render
      scene = new Scene();
/*
      // Create several Model objects, using default constructors.
      modelArray.add(new Square());
      modelArray.add(new SquareGrid());
      modelArray.add(new Circle());
      modelArray.add(new Disk());
      modelArray.add(new DiskSector());
      modelArray.add(new Ring());
      modelArray.add(new RingSector());
      modelArray.add(new Box());
      modelArray.add(new Cube());
      modelArray.add(new Cube2());
      modelArray.add(new Cube3());
      modelArray.add(new Cube4());
      modelArray.add(new Tetrahedron());
      modelArray.add(new Octahedron());
      modelArray.add(new Icosahedron());
      modelArray.add(new Dodecahedron());
      modelArray.add(new Pyramid());
      modelArray.add(new PyramidFrustum());
      modelArray.add(new TriangularPyramid());
      modelArray.add(new TriangularPrism());
      modelArray.add(new ViewFrustum());
      modelArray.add(new Cone());
      modelArray.add(new ConeSector());
      modelArray.add(new ConeFrustum());
      modelArray.add(new Cylinder());
      modelArray.add(new CylinderSector());
      modelArray.add(new Sphere());
      modelArray.add(new SphereSector());
      modelArray.add(new SphereSubdivided());
      modelArray.add(new Torus());
      modelArray.add(new TorusSector());
      modelArray.add(new ParametricCurve());
      modelArray.add(new ParametricSurface());
      modelArray.add(new SurfaceOfRevolution());
      modelArray.add(new Axes3D());
      modelArray.add(new Axes2D());
      modelArray.add(new PanelXY());
*/
      // Instantiate at least one of every Model class.
      // 2D models
      modelArray.add(new Square(1.0));
      modelArray.add(new SquareGrid(1.0, 11, 15));
      modelArray.add(new Circle(1.0, 16));
      modelArray.add(new Disk(1.0, 4, 16));
      modelArray.add(new DiskSector(1.0, Math.PI/2, 3*Math.PI/2, 4, 8));
      modelArray.add(new Ring(1.0, 0.25, 3, 16));
      modelArray.add(new RingSector(1.0, 0.25, Math.PI/2, 3*Math.PI/2, 3, 8));
      // cubes
      modelArray.add(new Box(1.0, 1.0, 1.0));
      modelArray.add(new Cube( ));
      modelArray.add(new Cube2(4, 5, 6));
      modelArray.add(new Cube3(12, 14, 15));
      modelArray.add(new Cube4(12, 14, 15));
      // polyhedra
      modelArray.add(new Tetrahedron());
      modelArray.add(new Tetrahedron(true));
      modelArray.add(new Octahedron());
      modelArray.add(new Icosahedron());
      modelArray.add(new Dodecahedron());
      // pyramids
      modelArray.add(new Pyramid(2.0, 1.0, 5, 6));
      modelArray.add(new Pyramid(2.0, 1.0, 5, 6, true));
      modelArray.add(new PyramidFrustum(2.0, 1.0, 0.5, 2, 5));
      modelArray.add(new PyramidFrustum(1.0, 2.0, 0.5, 2, 5));
      modelArray.add(new TriangularPyramid(Math.sqrt(3)/Math.sqrt(2)));
      modelArray.add(new TriangularPyramid(1.0, 1.0, 7, 7));
      modelArray.add(new TriangularPrism(0.6, 0.5, 0.5, 3, true));
      modelArray.add(new ViewFrustumModel());
      // cones
      modelArray.add(new Cone(1.0, 1.0, 10, 16));
      modelArray.add(new ConeSector(1.0, 1.0, 0.5, 0, 2*Math.PI, 5, 16));
      modelArray.add(new ConeSector(1.0, 1.0, 0.5, Math.PI/2, 3*Math.PI/2, 5, 8));
      modelArray.add(new ConeFrustum(1.0, 0.5, 0.5, 6, 16));
      modelArray.add(new ConeFrustum(0.5, 1.0, 0.5, 6, 16));
      // cylinders
      modelArray.add(new Cylinder(0.5, 1.0, 11, 12));
      modelArray.add(new CylinderSector(0.5, 1.0, Math.PI/2, 3*Math.PI/2, 11, 6));
      // spheres
      modelArray.add(new Sphere(1.0, 15, 12));
      modelArray.add(new SphereSector(1.0, Math.PI/2, 3*Math.PI/2,
                                           Math.PI/4, 3*Math.PI/4, 7, 6));
      modelArray.add(new SphereSubdivided(5));
      modelArray.add(new SphereSubdivided(6, true, false));
      modelArray.add(new SphereSubdivided(7, false, true));
      // torus
      modelArray.add(new Torus(0.75, 0.25, 12, 16));
      modelArray.add(new TorusSector(0.75, 0.25, Math.PI/2, 3*Math.PI/2, 12, 8));
      modelArray.add(new TorusSector(0.75, 0.25, 0, 2*Math.PI,
                                                 Math.PI, 2*Math.PI, 6, 16));
      modelArray.add(new TorusSector(0.75, 0.25, 0, 2*Math.PI,
                                                -Math.PI/2, Math.PI/2, 6, 16));
      modelArray.add(new TorusSector(0.75, 0.25, Math.PI/2, 3*Math.PI/2,
                                                -Math.PI/2, Math.PI/2, 6, 8));
      // model files
      //modelArray.add(new GRSModel(new File("../assets/grs/bronto.grs")));
      //modelArray.add(new ObjSimpleModel(new File("../assets/cow.obj")));
      // parametric curves and surfaces
      modelArray.add(new ParametricCurve());
      modelArray.add(new ParametricSurface());
      modelArray.add(new ParametricSurface((s,t)->s*Math.cos(t*Math.PI),
                                           (s,t)->t,
                                           (s,t)->s*Math.sin(t*Math.PI),
                                           -1, 1, -1, 1, 49, 49));
      modelArray.add(new ParametricSurface(
                (u,v)->0.3*(1-u)*(3+Math.cos(v))*Math.sin(4*Math.PI*u),
                (u,v)->0.3*(3*u+(1-u)*Math.sin(v)),
                (u,v)->0.3*(1-u)*(3+Math.cos(v))*Math.cos(4*Math.PI*u),
                0, 1, 0, 2*Math.PI, 49, 49));
      modelArray.add(new SurfaceOfRevolution());
      modelArray.add(new SurfaceOfRevolution(t->0.5*(1+t*t), -1, 1, 30, 30));
      modelArray.add(new SurfaceOfRevolution(t->t, t->4*t*(1-t), 0, 1, 30, 30));
      // coordinate axes
      modelArray.add(new Axes3D(1, 1, 1));
      modelArray.add(new Axes2D(-1, 1, -1, 1, 8, 8));
      modelArray.add(new PanelXY(-4, 4, -5, 5, -5));
      modelArray.add(new PanelXZ(-1, 1, -6, 1, -0.5));
      modelArray.add(new PanelYZ(-1, 1, -6, 1, -0.5));

      // Add a model to the Scene.
      scene.addModel(modelArray.get(currentModel));

      // Push the models away from where the camera is.
      for (Model m : modelArray)
      {
         for (Vertex v : m.vertexList)
         {
            v.z -= 2;
         }
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
      else if ('/' == c)
      {
         currentModel = (currentModel + 1) % modelArray.size();
         scene.modelList.set( 0, modelArray.get(currentModel) );
      }
      else if ('?' == c)
      {
         currentModel = (currentModel - 1);
         if (currentModel < 0) currentModel = modelArray.size() - 1;
         scene.modelList.set( 0, modelArray.get(currentModel) );
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
         String p = scene.camera.perspective ? "perspective" : "orthographic";
         System.out.println("Using " + p + " projection");
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.x /= 1.1;  // This does NOT work with
               v.y /= 1.1;  // perspective projection.
               v.z /= 1.1;
            }
      }
      else if ('S' == c) // Scale the model 10% larger.
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.x *= 1.1;  // This does NOT work with
               v.y *= 1.1;  // perspective projection.
               v.z *= 1.1;
            }
      }
      else if ('x' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.x -= 0.1;
            }
      }
      else if ('X' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.x += 0.1;
            }
      }
      else if ('y' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.y -= 0.1;
            }
      }
      else if ('Y' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.y += 0.1;
            }
      }
      else if ('z' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
            {
               v.z -= 0.1;
            }
      }
      else if ('Z' == c)
      {
         for (Model m : modelArray)
            for (Vertex v : m.vertexList)
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
      // InteractiveModelsAll_R2 constructor in the Java GUI Event
      // Dispatch Thread, otherwise we get a race condition
      // between the constructor (running in the main() thread)
      // and the very first ComponentEvent (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() // an anonymous inner class constructor
         {
            public void run() // implement the Runnable interface
            {
               // call the constructor that builds the gui
               new InteractiveModelsAll_R2("Renderer 2", width, height);
            }
         }
      );
   }//main()


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the '/' key to cycle through the models.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the models along the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the models.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
