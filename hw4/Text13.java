import renderer.scene.*;
import renderer.models.ModelShading;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;
import renderer.models.*;

import java.awt.Color;
import java.awt.event.*;

public class Text13
{
	public static void main(String[] args)
	{
		FrameBuffer fb = new FrameBuffer(10, 3);
		Model m = new Model();
		m.addVertex(new Vertex(-1, -1, -1),
					new Vertex( 1,  1, -1));
		m.addLineSegment(new LineSegment(0, 1));
		m.addColor(Color.white, Color.white);

		Scene scene = new Scene();
		scene.addPosition(new Position(m));
		Pipeline.render(scene, fb.vp);

		RaterizeAntialias.debug = true;
		Pipeline.debug = true;
		Pipeline.render(scene, fb.vp);
	}
}
