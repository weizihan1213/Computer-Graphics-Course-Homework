/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a positive x, y, and z axis in 3-dimensional space.
*/
public class Axes3D extends Model
{
   /**
      Create a positive x, y, and z axis
      with one unit length for each axis.
   */
   public Axes3D( )
   {
      this(1.0, 1.0, 1.0);
   }


   /**
      Create a positive x, y, and z axis
      with the given length for each axis.

      @param xMax  length of the x-axis
      @param yMax  length of the y-axis
      @param zMax  length of the z-axis
   */
   public Axes3D(double xMax, double yMax, double zMax)
   {
      this(0.0, xMax, 0.0, yMax, 0.0, zMax);
   }


   /**
      Create an x, y, and z axis with the
      given endpoints for each axis.

      @param xMin  left endpoint of the x-axis
      @param xMax  right endpoint of the x-axis
      @param yMin  bottom endpoint of the y-axis
      @param yMax  top endpoint of the y-axis
      @param zMin  back endpoint of the z-axis
      @param zMax  front endpoint of the z-axis
   */
   public Axes3D(double xMin, double xMax,
                 double yMin, double yMax,
                 double zMin, double zMax)
   {
      super("Axes 3D");

      Vertex x0 = new Vertex(xMin, 0,    0);
      Vertex x1 = new Vertex(xMax, 0,    0);
      Vertex y0 = new Vertex( 0,  yMin,  0);
      Vertex y1 = new Vertex( 0,  yMax,  0);
      Vertex z0 = new Vertex( 0,   0,   zMin);
      Vertex z1 = new Vertex( 0,   0,   zMax);

      // Add the vertices to the model.
      addVertex(x0, x1, y0, y1, z0, z1);

      // Create 3 line segments.
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(2, 3),
                     new LineSegment(4, 5));
   }
}//Axes3D
