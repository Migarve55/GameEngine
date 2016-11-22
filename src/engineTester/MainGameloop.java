package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameloop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		//Rectangle		
		//RawModel rectangle = BasicShapes.rectangle(loader,0.4f,0.3f,0.5f,0.5f);
		
		float[] vertices = {
				-0.5f, 0.5f, 0,  //LU 0
				-0.5f, -0.5f, 0, //RU 1
				0.5f, -0.5f, 0,  //RB 2
				0.5f, 0.5f, 0    //LB 3
		};
		
		int[] indices = {0,1,3,
						3,1,2};
		
		float[] textCoords = {
				0,0,
				0,1,
				1,0,
				1,1};
		// Model 
		RawModel model = loader.loadToVAO(vertices,textCoords,indices);
		// Texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		// Model + texture
		TextureModel staticModel = new TextureModel(model,texture);
		//Entity: model + texture + position
		Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
		
		// Main loop
		while(!Display.isCloseRequested()) {
			//Game logic
			entity.increasePosition(0.01f, 0f, 0f); //Moves the entity
			entity.increaseRotation(0, 1, 0); //Rotates it
			//Render
			renderer.prepare(); //Change the background to green
			shader.start();
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();//Clean up the shaders programs
		loader.cleanUp(); //Clean up all the vaos and vbos
		DisplayManager.closeDisplay(); //Close the display

	}

}
