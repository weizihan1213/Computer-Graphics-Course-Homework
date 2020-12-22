/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a flat wireframe checkerboard panel in the xy-plane.
*/
public class PanelXY extends Model
{
   /**
      Create a flat checkerboard panel in the xy-plane that runs
      from -1 to 1 in the x-direction and -1 to 1 in the y-direction.
   */
   public PanelXY( )
   {
      this(-1, 1, -1, 1);
   }


   /**
      Create a flat checkerboard panel in the xy-plane with the given dimensions.

      @param xMin  location of left edge
      @param xMax  location of right edge
      @param yMin  location of bottom edge
      @param yMax  location of top edge
   */
   public PanelXY(int xMin, int xMax, int yMin, int yMax)
   {
      this(xMin, xMax, yMin, yMax, 0.0);
   }


   /**
      Create a flat checkerboard panel parallel to the xy-plane with the given dimensions.

      @param xMin  location of left edge
      @param xMax  location of right edge
      @param yMin  location of bottom edge
      @param yMax  location of top edge
      @param z     z-plane that holds the panel
   */
   public PanelXY(int xMin, int xMax, int yMin, int yMax, double z)
   {
      super("PanelXY");

      // Create the checkerboard panel's geometry.

      // An array of indexes to be used to create line segments.
      int[][] index = new int[(xMax-xMin)+1][(yMax-yMin)+1];

      // Create the checkerboard of vertices.
      int i = 0;
      for (int x = xMin; x <= xMax; ++x)
      {
         for (int y = yMin; y <= yMax; ++y)
         {
            addVertex(new Vertex(x, y, z));
            index[x-xMin][y-yMin] = i++;
         }
      }

      // Create the line segments that run in the y-direction.
      for (int x = 0; x <= xMax-xMin; ++x)
      {
         for (int y = 0; y < yMax-yMin; ++y)
         {
            addLineSegment(new LineSegment(index[x][y], index[x][y+1]));
         }
      }

      // Create the line segments that run in the x-direction.
      for (int y = 0; y <= yMax-yMin; ++y)
      {
         for (int x = 0; x < xMax-xMin; ++x)
         {
            addLineSegment(new LineSegment(index[x][y], index[x+1][y]));
         }
      }
   }
}//PanelXY
