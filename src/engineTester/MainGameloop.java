package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class MainGameloop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		System.out.println("Loading models");
		// Model 
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		// Texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		// Model + texture
		TextureModel staticModel = new TextureModel(model,texture);
		staticModel.getTexture().setShineDamper(12);
		staticModel.getTexture().setReflectivity(1);
		//Entity: model + texture + position
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-20),0,0,0,1);
		System.out.println("Models loaded");
		
		//Camera and light
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0,5,0), new Vector3f(1,1,1));
		
		System.out.println("Game started");
		
		// Main loop
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//Game logic
			entity.increasePosition(0f, 0f, 0f); //Moves the entity
			entity.increaseRotation(0, 0.4f, 0); //Rotates it
			camera.move(); //Move the camera
			//Render
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		System.out.println("Game ended");
		loader.cleanUp(); //Clean up all the vaos and vbos
		DisplayManager.closeDisplay(); //Close the display

	}

}
