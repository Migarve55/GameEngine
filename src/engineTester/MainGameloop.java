package engineTester;

import java.util.ArrayList;
import java.util.Random;

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
import terrain.Terrain;
import textures.ModelTexture;

public class MainGameloop {

	public static void main(String[] args) {
		
		Random gen = new Random();

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		System.out.println("Loading models");
		// Model 
		RawModel model = OBJLoader.loadObjModel("bunny", loader);
		// Texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("yellow"));
		// Model + texture
		TextureModel staticModel = new TextureModel(model,texture);
		staticModel.getTexture().setShineDamper(25);
		staticModel.getTexture().setReflectivity(1);
		//Entity: model + texture + position
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-20),0,0,0,1);
		System.out.println("Models loaded");
		
		//Terrain
		
		ArrayList<Entity> trees = new ArrayList<Entity>();
		int number_of_trees = 15;
		
		RawModel tree_model = OBJLoader.loadObjModel("tree", loader);
		ModelTexture tree_texture = new ModelTexture(loader.loadTexture("tree"));
		TextureModel tree_static_model = new TextureModel(tree_model, tree_texture);
		for (int i = 0;i < number_of_trees;i++) {
			trees.add(new Entity(tree_static_model,new Vector3f(gen.nextFloat()*10,0,gen.nextFloat()*10),0,0,0,1));
		}
		
		Terrain terrain1 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		System.out.println("Terrain created");
		
		//Camera and light
		Camera camera = new Camera(new Vector3f(0,0.7f,0));
		Light Sun = new Light(new Vector3f(0,5,0), new Vector3f(1,1,1));
		
		System.out.println("Game started");
		
		// Main loop
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//Game logic
			entity.increasePosition(0f, 0f, 0f); //Moves the entity
			entity.increaseRotation(0, 0.4f, 0); //Rotates it
			camera.move(); //Move the camera
			//Entities
			//renderer.processEntity(entity);
			//Terrain
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);
			for (Entity tree:trees) {
				renderer.processEntity(tree);
			}
			//Render
			renderer.render(Sun, camera);
			DisplayManager.updateDisplay();
		}
		
		System.out.println("Game ended");
		loader.cleanUp(); //Clean up all the vaos and vbos
		DisplayManager.closeDisplay(); //Close the display

	}

}
