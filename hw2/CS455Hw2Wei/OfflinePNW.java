

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.io.File;
import java.awt.Color;

public class OfflinePNW
{
	public static void main(String[] args)
	{
		// Create model objects.
		Model m1 = new P();
		Model m2 = new N();
		Model m3 = new W();

		// Create Scene object that we should render.
		Scene scene = new Scene();

		// add models to the scene.
		scene.addModel(m1,m2,m3);

		// push the models away from where the camera is.
		for (Model m : scene.modelList)
		{
			for (Vertex v : m.vertexList)
			{
				v.z -= 3;
			}
		}

		// create a framebuffer to render our scene into.
		int vp_width  = 1024;
		int vp_height = 1024;
		FrameBuffer fb = new FrameBuffer(vp_width,vp_height);

		// Give the framebuffer a background color.
		fb.clearFB(Color.black);

		// Render our scence into the frambuffer.
		Pipeline.doClipping = true;
		Pipeline.render(scene,fb.vp);

		// Save the resulting image in a file.
		fb.dumpFB2File(String.format("PPM_%03d.ppm",0));

		for (int i = 1; i <= 30; i++)
		{
		    // update the scene
		    for (Vertex v : scene.modelList.get(0).vertexList) // P
		    {
		        v.x -= 0.1;  // move left
			    v.y -= 0.1;  // move down
		        v.z -= 0.1;  // move back
		     }
		     for (Vertex v : scene.modelList.get(1).vertexList) // N
		     {
		        v.z -= 0.05; // move backwards
		     }
		     for (Vertex v : scene.modelList.get(2).vertexList) // W
		     {
		        v.x += 0.1;  // move right
				v.y += 0.1;  // move up
                v.z -= 0.1;  // move back
		     }
			fb.clearFB(Color.black);
			Pipeline.doClipping = true;
			Pipeline.render(scene, fb.vp);
	    	fb.dumpFB2File(String.format("PPM_%03d.ppm", i));
		}

        for (int i = 31; i <= 70; i++)
		{
		    // update the scene
		    for (Vertex v : scene.modelList.get(0).vertexList) // P
		    {
			    v.y += 0.15;  // move up
		        //v.z -= 0.1;  // move back
		     }
		     for (Vertex v : scene.modelList.get(1).vertexList) // N
		     {
		        //v.z -= 0.05; // move backwards
		     }
		     for (Vertex v : scene.modelList.get(2).vertexList) // W
		     {
				v.y -= 0.15;  // move down
                //v.z -= 0.1;  // move back
		     }
			fb.clearFB(Color.black);
			Pipeline.doClipping = true;
			Pipeline.render(scene, fb.vp);
	    	fb.dumpFB2File(String.format("PPM_%03d.ppm", i));
		}

      	for (int i = 71; i <= 100; i++)
		{
		    // update the scene
		    for (Vertex v : scene.modelList.get(0).vertexList) // P
		    {
			    v.x += 0.1;  // move right
		        v.z += 0.1;  // move back
		     }
		     for (Vertex v : scene.modelList.get(1).vertexList) // N
		     {
		        v.z += 0.1; // move backwards
		     }
		     for (Vertex v : scene.modelList.get(2).vertexList) // W
		     {
				v.x -= 0.1;  // move left
                v.z += 0.1;  // move back
		     }
      	fb.clearFB(Color.black);
	    Pipeline.doClipping = true;
	    Pipeline.render(scene, fb.vp);
	    fb.dumpFB2File(String.format("PPM_%03d.ppm", i));
		}
    }
}



// PNW%03d
