/*

*/
import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.io.File;
import java.awt.Color;

/**
   This program creates an animation by repeatedly saving the framebuffer's image to a file.

   You can see the animation by quickly sequencing through the frame images.
*/
public class OfflineModels
{
   public static void main(String[] args)
   {
      // Create several Model objects.
      Model m1 = new Torus();
      Model m2 = new Sphere();
      Model m3 = new Cone();
      Model m4 = new Cube4(3, 4, 3);

      // Create the Scene object that we shall render
      Scene scene = new Scene();

      // Add Models to the Scene.
      scene.addModel(m1, m2, m3, m4);

      // Push the models away from where the camera is.
      for (Model m : scene.modelList)
      {
         for (Vertex v : m.vertexList)
         {
            v.z -= 3;
         }
      }

      // log interesting information to standard output
      //System.out.println( scene.toString() );

      // Create a framebuffer to render our scene into.
      int vp_width  = 1024;
      int vp_height = 1024;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);
      // Give the framebuffer a background color.
      fb.clearFB(Color.black);

      // Render our scene into the frame buffer.
      Pipeline.doClipping = true;
      Pipeline.render(scene, fb.vp);
      // Save the resulting image in a file.
      fb.dumpFB2File( String.format("PPM_Frame%03d.ppm", 0) );

      for (int i = 1; i <= 100; i++)
      {
         // update the scene
         for (Vertex v : scene.modelList.get(0).vertexList)
         {
            v.x += 0.1;  // move right
            v.y += 0.1;  // move up
            v.z -= 0.1;  // move back
         }
         for (Vertex v : scene.modelList.get(1).vertexList)
         {
            v.x -= 0.1;  // move left
            v.y -= 0.1;  // move down
            v.z -= 0.1;  // move back
         }
         for (Vertex v : scene.modelList.get(2).vertexList)
         {
            v.x += 0.02; // move right
         }
         for (Vertex v : scene.modelList.get(3).vertexList)
         {
            v.z -= 0.05; // move backwards
         }
         // Render again.
         fb.clearFB(Color.black);
         Pipeline.doClipping = true;
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Frame%03d.ppm", i));
      }
   }
}
