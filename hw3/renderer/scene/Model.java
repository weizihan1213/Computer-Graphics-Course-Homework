/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Model} data structure represents a distinct geometric object
   in a {@link Scene}. A {@code Model} data structure is mainly a {@link List}
   of {@link Vertex} objects and another list of {@link LineSegment} objects.
   Each {@link LineSegment} object contains two integers that are the indices
   of two {@link Vertex} objects from the {@code Model}'s vertex list. The
   two {@link Vertex} objects contain the coordinates, in the camera coordinate
   system, for each of the line segment's two endpoints.
<p>
   A {@code Model} represent the geometric object as a "wire-frame" of line
   segments, that is, the geometric object is drawn as a collection of "edges".
   This is a fairly simplistic way of doing 3D graphics and we will
   improve this in later renderers.
<p>
   See
<br> <a href="http://en.wikipedia.org/wiki/Wire-frame_model" target="_top">
              http://en.wikipedia.org/wiki/Wire-frame_model</a>
<br>or
<br> <a href="https://www.google.com/search?q=graphics+wireframe&tbm=isch" target="_top">
              https://www.google.com/search?q=graphics+wireframe&tbm=isch</a>
*/
public class Model
{
   public List<Vertex> vertexList = new ArrayList<>();
   public List<LineSegment> lineSegmentList = new ArrayList<>();

   public String  name;
   public boolean visible;
   public boolean debug;


   /**
      Construct an empty {@code Model} object.
   */
   public Model()
   {
      this.name = "";
      this.visible = true;
      this.debug = false;
   }


   /**
      Construct an empty {@code Model} object with the given name.

      @param name  a {link String} that is a name for this {@code Model}
   */
   public Model(String name)
   {
      this();
      this.name = name;
   }


   /**
      A "copy constructor". This constructor should make a deep copy
      of the given {@code Model}'s {@link Vertex} list and
      {@link LineSegment} list.

      @param model  {@code Model} to make a copy of
   */
   public Model(Model model) // a "copy constructor"
   {
      super();

      this.name    = model.name;
      this.visible = model.visible;
      this.debug   = model.debug;
      for (Vertex v : model.vertexList)
      {
         this.vertexList.add(new Vertex(v)); // deep copy of each Vertex
      }
      for (LineSegment ls : model.lineSegmentList)
      {
         this.lineSegmentList.add(new LineSegment(ls)); // deep copy of each LineSgement
      }
   }


   /**
      Add a {@link Vertex} (or vertices) to this {@code Model}'s
      {@link List} of vertices.

      @param vArray  array of {@link Vertex} objects to add to this {@code Model}
   */
   public void addVertex(Vertex... vArray)
   {
      for (Vertex v : vArray)
      {
         vertexList.add(new Vertex(v)); // NOTE: deep copy!
      }
   }


   /**
      Get a {@link LineSegment} from this {@code Model}'s
      {@link List} of line segments.

      @param index  integer index of a {@link LineSegment} from this {@code Model}
      @return the {@link LineSegment} object at the given index
   */
   public LineSegment getLineSegment(int index)
   {
      return lineSegmentList.get(index);
   }


   /**
      Add a {@link LineSegment} (or LineSegments) to this {@code Model}'s
      {@link List} of line segments.
      <p>
      NOTE: This method does not add any vertices to the {@code Model}'s
      {@link Vertex} list. This method assumes that the appropriate vertices
      have been added to the {@code Model}'s {@link Vertex} list.

      @param lsArray  array of {@link LineSegment} objects to add to this {@code Model}
   */
   public void addLineSegment(LineSegment... lsArray)
   {
      for (LineSegment ls : lsArray)
      {
         lineSegmentList.add(ls);
      }
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Model} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += "Model: " + name + "\n";
      result += "This Model's visibility is: " + visible + "\n";
      result += "Model has " + vertexList.size() + " vertices.\n";
      result += "Model has " + lineSegmentList.size() + " line segments.\n";
      int i = 0;
      for (Vertex v : this.vertexList)
      {
         result += i + ": " + v.toString();
         i++;
      }
      //result = "Printing out this Model's " + lineSegmentList.size() + " Line segments:\n";
      for (LineSegment ls : this.lineSegmentList)
      {
         result += ls.toString();
      }
      //result += "Done printing out Model\n";
      return result;
   }
}
