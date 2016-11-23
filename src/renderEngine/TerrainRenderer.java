package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import shaders.TerrainShader;
import terrain.Terrain;
import textures.ModelTexture;
import toolbox.Maths;

public class TerrainRenderer {
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(List<Terrain> terrains) {
		for(Terrain terrain:terrains) {
			prepareTerrain(terrain);
			loadModelTerrain(terrain);
			//Render
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draws the vertices
			unbindTextureModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain) {
		RawModel rawModel = terrain.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID()); //Start with the VAO
		GL20.glEnableVertexAttribArray(0); //Vertices slot
		GL20.glEnableVertexAttribArray(1); //Texture slot
		GL20.glEnableVertexAttribArray(2); //Normal slot
		ModelTexture texture = terrain.getTexture(); //Get the texture
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID()); //Binds the texture
	}
	
	private void unbindTextureModel(){
		GL20.glDisableVertexAttribArray(0); //Vertices slot
		GL20.glDisableVertexAttribArray(1); //texture slot
		GL20.glDisableVertexAttribArray(2); //Normal slot
		GL30.glBindVertexArray(0); //Finish using the VAO
	}
	
	private void loadModelTerrain(Terrain terrain) {
		Matrix4f transformationMatrix = 
				Maths.createTransformationMatrix(
						new Vector3f(terrain.getX(),0,terrain.getZ()),
						0, //No rotation
						0,
						0,
						1); //Scale 1
		shader.loadTransformationMatrix(transformationMatrix);
	}

}
