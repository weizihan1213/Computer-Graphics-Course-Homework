/*

*/

import renderer.scene.*;

/**


*/
public class N extends Model
{
   /**
      The letter N.
   */
   public N()
   {
      super("N");

      Vertex[] v = new Vertex[20];

      int i = 0;
      v[i++] = new Vertex(0.00, 0.00, 0.0);  // front face
      v[i++] = new Vertex(0.00, 1.00, 0.0);
      v[i++] = new Vertex(0.25, 1.00, 0.0);
      v[i++] = new Vertex(0.75, 0.5,  0.0);
      v[i++] = new Vertex(0.75, 1.0,  0.0);
      v[i++] = new Vertex(1.00, 1.0,  0.0);
      v[i++] = new Vertex(1.00, 0.0,  0.0);
      v[i++] = new Vertex(0.75, 0.0,  0.0);
      v[i++] = new Vertex(0.25, 0.5,  0.0);
      v[i++] = new Vertex(0.25, 0.0,  0.0);

      v[i++] = new Vertex(0.00, 0.00, -0.25);  // back face
      v[i++] = new Vertex(0.00, 1.00, -0.25);
      v[i++] = new Vertex(0.25, 1.00, -0.25);
      v[i++] = new Vertex(0.75, 0.5,  -0.25);
      v[i++] = new Vertex(0.75, 1.0,  -0.25);
      v[i++] = new Vertex(1.00, 1.0,  -0.25);
      v[i++] = new Vertex(1.00, 0.0,  -0.25);
      v[i++] = new Vertex(0.75, 0.0,  -0.25);
      v[i++] = new Vertex(0.25, 0.5,  -0.25);
      v[i++] = new Vertex(0.25, 0.0,  -0.25);

      addVertex( v );  // add all the vertices at once

      // Create the back face line segments.
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 4),
                     new LineSegment(4, 5),
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 8),
                     new LineSegment(8, 9),
                     new LineSegment(9, 0));

      // Create the front face line segments.
      addLineSegment(new LineSegment(10, 11),
                     new LineSegment(11, 12),
                     new LineSegment(12, 13),
                     new LineSegment(13, 14),
                     new LineSegment(14, 15),
                     new LineSegment(15, 16),
                     new LineSegment(16, 17),
                     new LineSegment(17, 18),
                     new LineSegment(18, 19),
                     new LineSegment(19, 10));

      // Connect front face to back face.
      addLineSegment(new LineSegment(0, 10),
                     new LineSegment(1, 11),
                     new LineSegment(2, 12),
                     new LineSegment(3, 13),
                     new LineSegment(4, 14),
                     new LineSegment(5, 15),
                     new LineSegment(6, 16),
                     new LineSegment(7, 17),
                     new LineSegment(8, 18),
                     new LineSegment(9, 19));
   }
}
