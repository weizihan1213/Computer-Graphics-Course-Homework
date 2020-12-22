/*

*/

package renderer.pipeline;
import  renderer.scene.*;
import  renderer.framebuffer.*;

import java.util.List;

/**
   This renderer takes as its input a {@link Scene} data structure and a
   {@link FrameBuffer.Viewport} within a {@link FrameBuffer} data structure.
   This renderer mutates the {@link FrameBuffer.Viewport} so that it is
   filled in with the rendered image of the scene represented by the
   {@link Scene} object.
<p>
   This implements our second rendering pipeline. It has two
   pipeline stages, just like the first pipeline, but the second
   stage, rasterization, now has two versions. One version is
   the same as in the first pipeline, and the second version does
   line clipping at the same time as it does rasterization.
*/
public class Pipeline
{
   public static boolean debug = false;
   public static boolean doClipping = false;

   /**
      Mutate the {@link FrameBuffer}'s current {@link FrameBuffer.Viewport}
      so that it holds the rendered image of the {@link Scene} object.

      @param scene  {@link Scene} object to render
      @param vp     {@link FrameBuffer.Viewport} to hold rendered image of the {@link Scene}
   */
   public static void render(Scene scene, FrameBuffer.Viewport vp)
   {
      // Render every Model in the Scene.
      for (Model model : scene.modelList)
      {
         if ( model.visible )
         {
            logMessage(model, "==== Render Model: " + model.name + " ====");

            check(model);

            // 0. Make a deep copy of the model.
            Model model2 = new Model(model);

            logVertexList("0. Input    ", model2);

            // 1. Apply the projection transformation.
            Projection.project(model2.vertexList, scene.camera);

            logVertexList("1. Projected", model2);

            // 2. Rasterize (and possibly clip) each line segment into pixels.
            for (LineSegment ls : model2.lineSegmentList)
            {
               logLineSegment("2. Rasterize", model2, ls);

               Rasterize_Clip.rasterize(model2, ls, vp, doClipping);
            }

            logMessage(model, "==== End Model: " + model.name + " ====");
         }
         else
         {
            logMessage(model, "==== Hidden model: " + model.name + " ====");
         }
      }
   }


   /**
      Determine if there are any obvious problems with the {@link Model}
      being rendered. The purpose of these checks is to make the renderer
      a bit more user friendly. If a user makes a simple mistake and tries
      to render a {@link Model} that is missing vertices or line segments,
      then the user gets a helpful error message.

      @param model  the {@link Model} being rendered
   */
   public static void check(Model model)
   {
      boolean error = false;
      if (model.vertexList.isEmpty())
      {
         System.err.println("***WARNING: This model does not have any vertices.");
         error = true;
      }
      if (model.lineSegmentList.isEmpty())
      {
         System.err.println("***WARNING: This model does not have any line segments.");
         error = true;
      }
      if (error)
      {
         System.err.println(model);
      }
   }


   /**
      Use the pipeline's and the model's debug variables to determine
      if the given message should be printeed to stderr.

      @param model    the {@link Model} being rendered
      @param message  {@link String} to output to stderr
   */
   public static void logMessage(Model model, String message)
   {
      if (Pipeline.debug || model.debug)
         System.err.println( message );
   }


   /**
      This method prints a {@link String} representation of the given
      {@link Model}'s {@link Vertex} list.

      @param stage  name for the pipeline stage
      @param model  the {@link Model} whose vertex list is to be printed
   */
   private static void logVertexList(String stage, Model model)
   {
      if (Pipeline.debug || model.debug)
      {
         int i = 0;
         for (Vertex v : model.vertexList)
         {
            System.err.printf( stage + ": index = %3d, " + v.toString(), i );
            ++i;
         }
      }
   }


   /**
      This method prints a {@link String} representation of the given
      {@link LineSegment}.

      @param stage  name for the pipeline stage
      @param model  {@link Model} that the {@link LineSegment} {@code ls} comes from
      @param ls     {@link LineSegment} whose string representation is to be printed
   */
   private static void logLineSegment(String stage, Model model, LineSegment ls)
   {
      if (Pipeline.debug || model.debug)
      {
         System.err.print( stage + ": " + ls.toString() );
         int index0 = ls.index[0];
         int index1 = ls.index[1];
         Vertex v0 = model.vertexList.get(index0);
         Vertex v1 = model.vertexList.get(index1);
         System.err.printf("   index = %3d, " + v0.toString(), index0);
         System.err.printf("   index = %3d, " + v1.toString(), index1);
      }
   }
}
