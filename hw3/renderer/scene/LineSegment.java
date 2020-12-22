/*

*/

package renderer.scene;

/**
   A {@code LineSegment} object has two integers that
   represent the endpoints of the line segment. The
   integers are indices into the {@link Vertex} list
   of a {@link Model} object.
*/
public class LineSegment
{
   public int[] index = new int[2]; // indices for this line segment's vertices

   /**
      Construct a {@code LineSegment} object with two integer indices.
      <p>
      NOTE: This method does not put any Vertex objects into this LineSegment's Model.
      This method assumes that the given indices are valid (or will be valid by the
      time this LineSegment gets rendered).

      @param i0  index of 1st endpoint of the new {@code LineSegment}
      @param i1  index of 2nd endpoint of the new {@code LineSegment}
   */
   public LineSegment(int i0, int i1)
   {
      index[0] = i0;
      index[1] = i1;
   }


   /**
      Construct a {@code LineSegment} object with the same two indices
      from the given {@code LineSegment} object.

      @param ls  {@code LineSegment} to make a copy of
   */
   public LineSegment(LineSegment ls) // a "copy constructor"
   {
      this(ls.index[0], ls.index[1]);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code LineSegment} object
   */
   @Override
   public String toString()
   {
      return "Line Segment: (" + index[0] + ", " + index[1] + ")\n";
   }
}
