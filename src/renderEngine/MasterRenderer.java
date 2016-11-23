package renderEngine;

import shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TextureModel;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private HashMap<TextureModel,List<Entity>> entities = new HashMap<TextureModel,List<Entity>>();
	
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity) {
		TextureModel e = entity.getModel();
		List<Entity> batch = entities.get(e);
		if (batch!=null) { //If the instance list for the model already exists
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(e, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
