package engineTester;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class TerrainGeneration {
	
	static Random gen = new Random();

	/**
	 * This method generates a instance in the terrain
	 * @param loader An instance of the loader class
	 * @param number_of_entities The number of entities you want to generate
	 * @param area The side of a square area
	 * @return A list with all the trees entities
	 */
	public static ArrayList<Entity> generateEntitiesInArea(Loader loader,String model_fileName,String texture_fileName, int number_of_entities, int area, float scale) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		RawModel tree_model = OBJLoader.loadObjModel(model_fileName, loader);
		ModelTexture tree_texture = new ModelTexture(loader.loadTexture(texture_fileName));
		TexturedModel tree_static_model = new TexturedModel(tree_model, tree_texture);
		for (int i = 0;i < number_of_entities;i++) {
			entities.add(new Entity(tree_static_model,new Vector3f(area/2-gen.nextFloat()*area,0,area/2-gen.nextFloat()*area),0,0,0,scale));
		}
		
		return entities;
	}
	
}
