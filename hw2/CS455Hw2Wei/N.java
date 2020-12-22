/*

*/

import renderer.scene.*;

/**
   The letter N.
*/
public class N extends Model
{
   /**
      The letter N.
   */
   public N()
   {
      super("N");

      int n = 0; // number of vertices

      Vertex[] v = new Vertex[n];

      // Create vertices.
	  Vertex v0 = new Vertex(-0.5, -0.5,  0.125);
	  Vertex v1 = new Vertex(-0.5,  0.5,  0.125);
	  Vertex v2 = new Vertex(-0.4,  0.5,  0.125);
	  Vertex v3 = new Vertex( 0.4, -0.375,0.125);
	  Vertex v4 = new Vertex( 0.4,  0.5,  0.125);
	  Vertex v5 = new Vertex( 0.5,  0.5,  0.125);
	  Vertex v6 = new Vertex( 0.5, -0.5,  0.125);
	  Vertex v7 = new Vertex(0.375,-0.5,  0.125);
	  Vertex v8 = new Vertex(-0.4, 0.375, 0.125);
	  Vertex v9 = new Vertex(-0.4,  -0.5, 0.125);


	  Vertex v10 = new Vertex(-0.5,  -0.5,  -0.125);
	  Vertex v11 = new Vertex(-0.5,  0.5,  -0.125);
	  Vertex v12 = new Vertex(-0.4,  0.5,  -0.125);
	  Vertex v13 = new Vertex( 0.4, -0.375,-0.125);
	  Vertex v14 = new Vertex( 0.4,  0.5,  -0.125);
	  Vertex v15 = new Vertex( 0.5,  0.5,  -0.125);
	  Vertex v16 = new Vertex( 0.5, -0.5,  -0.125);
	  Vertex v17 = new Vertex(0.375,-0.5,  -0.125);
	  Vertex v18 = new Vertex(-0.4, 0.375, -0.125);
	  Vertex v19 = new Vertex(-0.4,  -0.5, -0.125);







	  addVertex(v0,v1,v2,v3,v4,v5,v6,v7,v8,v9);
	  addVertex(v10,v11,v12,v13,v14,v15,v16,v17,v18,v19);


      // Create line segments.
	  addLineSegment(new LineSegment(0,1),
	  				 new LineSegment(1,2),
					 new LineSegment(2,3),
					 new LineSegment(4,5),
					 new LineSegment(5,6),
					 new LineSegment(6,7),
					 new LineSegment(7,8),
					 new LineSegment(8,9),
					 new LineSegment(9,0),
					 new LineSegment(10,11),
					 new LineSegment(11,12),
					 new LineSegment(12,13),
					 new LineSegment(13,14),
					 new LineSegment(14,15),
					 new LineSegment(15,16),
					 new LineSegment(16,17),
					 new LineSegment(17,18),
					 new LineSegment(18,19),
					 new LineSegment(19,10),
					 new LineSegment(0,10),
					 new LineSegment(1,11),
					 new LineSegment(2,12),
					 new LineSegment(3,13),
					 new LineSegment(4,14),
					 new LineSegment(5,15),
					 new LineSegment(6,16),
					 new LineSegment(7,17),
					 new LineSegment(8,18),
					 new LineSegment(9,19));


   }
}
