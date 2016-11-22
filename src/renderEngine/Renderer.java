package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TextureModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {

	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 1 , 0, 1);
	}
	
	public void renderShape(RawModel model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
	}
	
	public void render(Entity entity, StaticShader shader) {
		TextureModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID()); //Start with the VAO
		GL20.glEnableVertexAttribArray(0); //Vertices slot
		GL20.glEnableVertexAttribArray(1); //Texture slot
		//Transformation
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID()); //Binds the texture
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draws the vertices
		GL20.glDisableVertexAttribArray(0); //Vertices slot
		GL20.glDisableVertexAttribArray(1); //texture slot
		GL30.glBindVertexArray(0); //Finish using the VAO
	}
	
}
