/*
	Course:CS 45500
	Name:Zihan Wei
	Email:wei344@pnw.edu
	Assignment:1

*/

import framebuffer.FrameBuffer;

import java.awt.Color;

/**


*/
public class Hw_1
{
   public static void main(String[] args)
   {
      // Check for a file name on the command line.
      if ( 0 == args.length )
      {
         System.err.println("Usage: java Hw_1 <PPM-file-name>");
         System.exit(-1);
      }

      // This framebuffer holds the image that will be embedded
      // within a viewport of our larger framebuffer.
      FrameBuffer fbEmbedded = new FrameBuffer( args[0] );

      /******************************************/

      // Your code goes here.
      // Create a framebuffer. Fill it with the checkerboard pattern.
      FrameBuffer cb = new FrameBuffer(1000, 600);
      // set the value of the pixels of the both color.
 	  Color []c=new Color [3];
 	  c[0] = new Color(255, 189, 96);
	  c[1] = new Color(192, 56, 14);
	  // width & height of the framebuffer.
      for(int i=0;i<1000;i++){
		  for(int j=0;j<600;j++){
			  cb.setPixelFB(i,j,c[(i+j)%2]);
	  	  }
  	  }


      // Create a viewport to hold the given PPM file. Put the PPM file in the viewport.
      // the ul coordinate of the viewport1
	  cb.Viewport vp1 = cb.new Viewport(75, 125, args[0]);

      // Create another viewport and fill it with a flipped copy of the PPM file.
	  FrameBuffer flp = new FrameBuffer(width, height);
	  for(int i = 0; i < width; i++){
		  for(int j = 0; j < height; j++){
			  flp.setPixelFB(width - 1 + i, j, fbEmbedded.getPixelFB(i,j));
		}
      }

      // Create another viewport and fill it with the striped pattern.
	  // coordinate of ul and width and height
	  cb.viewport vp3 = cb.new Viewport(610,420,300,120);
 	  c[0] = new Color(241,95,116);
 	  c[1] = new Color(152,203,74);
 	  c[2] = new Color(84,129,230);
 	  cb.viewport.vp3.clearVP(Color.blue);
 	  for(int i=0; i < cb.viewport.vp3.getWidth();i++){
		  for(int j=0;j < cb.viewport.vp3.getHeight();j++){
			  // the total width of the viewport3 is 300,there are 10
			  // stripes in all,so the width of each color is assigned 30,and
			  // the order of the color is "red"-"green"-"blue" for all the way,so we just
			  // need to assign the specific color by caluating the reminder
			  cb.viewport.vp3.setPixelVP(i,j,c[(i+j)/30%3]);
			}
		}

 	  // Create another viewport that covers the selected region of the framebuffer.
	  cb.viewport vp4 = cb.new Viewport(500,200,200,300);
      // Create another viewport to hold a copy of the selected region.
 	  cb.viewport vp5 = cb.new Viewport(725,25,250,350);
 	  // Give this viewport a grayish background color.
	  c[0] = new Color(192,192,192);
	  vp4.clearVP(c[0]);
      // Create another viewport inside the last one.
	  cb.viewport vp6 = cb.new Viewport(750,50,cb.Viewport vp4);
      // Copy the selected region's viewport into this last viewport.

      FrameBuffer fb = null;


      /******************************************/
      // Save the resulting image in a file.
      String savedFileName = "Hw_1.ppm";
      fb.dumpFB2File( savedFileName );
      System.err.println("Saved " + savedFileName);
   }
}


