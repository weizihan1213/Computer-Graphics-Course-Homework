 /*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**

*/
public class OfflinePNW
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      final Scene scene = new Scene();

      // Create a set of x and y axes.
      Model axes = new Axes2D(-2, +2, -2, +4, 8, 12);
      ModelShading.setColor(axes, Color.red);
      Position axes_p = new Position( axes );
      scene.addPosition( axes_p );
      // Push the axes away from where the camera is.
      axes_p.matrix = Matrix.translate(0, 0, -3);

      // Add the letters to the Scene.
      scene.addPosition(new Position( new P() ),
                        new Position( new N() ),
                        new Position( new W() ));

      // Give the letters random colors.
      for (Position p : scene.positionList)
      {
         ModelShading.setRandomColor(p.model);
      }

      // Create a FrameBuffer to render our scene into.
      int width  = 900;
      int height = 900;
      FrameBuffer fb = new FrameBuffer(width, height);

      // Create the animation frames.
      for (int i = 0; i < 360; i++)
      {
         // Push the letters away from the camera.
         scene.positionList.get(1).matrix = Matrix.translate(0, 0, -3); // P
         scene.positionList.get(2).matrix = Matrix.translate(0, 0, -3); // N
         scene.positionList.get(3).matrix = Matrix.translate(0, 0, -3); // W

         // do P
		 scene.positionList.get(1).matrixMult(Matrix.rotateZ(-1*i));
		 scene.positionList.get(1).matrixMult(Matrix.translate(-2,0,0));




         // do N
		if(i<=89)
		{
			scene.positionList.get(2).matrixMult(Matrix.translate(0,((-0.0222)*i),0));
		}
	    if(i>=90 && i<=180)
	    {
			scene.positionList.get(2).matrixMult(Matrix.translate(0,((0.0222)*i)-4,0));
		}
		if(i>180 && i<=270)
		{
			scene.positionList.get(2).matrixMult(Matrix.translate(0,((0.0222)*i)-4,0));
		}
		if(i>270 && i<=360)
		{
			scene.positionList.get(2).matrixMult(Matrix.translate(0,((-0.0222)*i)+8,0));
		}
		scene.positionList.get(2).matrixMult(Matrix.translate(0,.5,0));
		scene.positionList.get(2).matrixMult(Matrix.rotateY(i));
		scene.positionList.get(2).matrixMult(Matrix.translate(-0.5,-0.5,0));




         // do W
	     scene.positionList.get(3).matrixMult(Matrix.translate(2,1,0));
		 scene.positionList.get(3).matrixMult(Matrix.rotateZ(2*i));
	     scene.positionList.get(3).matrixMult(Matrix.translate(-1,-1,0));




         // Render again.
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_PNW_Frame%03d.ppm", i));
      }
   }
}
