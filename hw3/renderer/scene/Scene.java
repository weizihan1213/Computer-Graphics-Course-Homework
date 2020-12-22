/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Scene} data structure is a list of {@link Model} data
   structures and a {@link Camera} data structure.
<p>
   Each {@link Model} object represents a distinct geometric object
   in the scene.
<p>
   The {@link Camera} object determines a "view volume", which
   determines how much of the scene is actually visible (to the
   camera) and gets rendered into the framebuffer.
*/
public class Scene
{
   public List<Model> modelList = new ArrayList<>();

   public Camera camera;


   /**
      Construct a {@code Scene} with a default {@link Camera} object.
   */
   public Scene()
   {
      this.camera = new Camera();
   }


   /**
      Construct a {@code Scene} with the given {@link Camera} object.

      @param camera  {@link Camera} object for this {@code Scene}
   */
   public Scene(Camera camera)
   {
      this.camera = camera;
   }


   /**
      Change this {@code Scene}'s {@link Camera} to the given {@link Camera} object.

      @param camera  new {@link Camera} object for this {@code Scene}
   */
   public void setCamera(Camera camera)
   {
      this.camera = camera;
   }


   /**
      Add a {@link Model} (or Models) to this {@code Scene}.

      @param mArray  array of {@link Model}s to add to this {@code Scene}
   */
   public void addModel(Model... mArray)
   {
      for (Model model : mArray)
      {
         modelList.add(model);
      }
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Scene} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += camera.toString();
      result += "This Scene has " + modelList.size() + " models\n";
      int i = 0;
      for (Model m : modelList)
      {
         result += "Model " + (i++) + "\n";
         result += m.toString();
      }
      return result;
   }
}
