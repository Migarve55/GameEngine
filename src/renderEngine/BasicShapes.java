package renderEngine;

import models.RawModel;
import models.TextureModel;
import textures.ModelTexture;

public class BasicShapes {

	/**
	 * This creates a rectangle
	 * @param loader
	 * @param xPos
	 * @param yPos
	 * @param width
	 * @param height
	 * @return A rectangle shape
	 */
	public static RawModel rectangle(Loader loader, float xPos, float yPos, float width, float height) {
		
		float x = xPos + width;
		float y = yPos + height;
		
		float[] vertices = {
				-x, y, 0,  //LU 0
				-x, -y, 0, //RU 1
				x, -y, 0,  //RB 2
				x, y, 0    //LB 3
		};
		
		int[] indices = {0,1,3,
						3,1,2};
		
		return loader.loadToVAO(vertices, indices);
	}
	
	public static TextureModel picture(Loader loader, float xPos, float yPos, String fileName) {
		
		float x = xPos;
		float y = yPos;
		
		float[] vertices = {
				-x, y, 0,  //LU 0
				-x, -y, 0, //RU 1
				x, -y, 0,  //RB 2
				x, y, 0    //LB 3
		};
		
		int[] indices = {0,1,3,
						3,1,2};
		
		float[] textCoords = {
				0,0,
				0,1,
				1,0,
				1,1};
		
		RawModel model = loader.loadToVAOwithImage(vertices,indices,textCoords);
		ModelTexture texture = new ModelTexture(loader.loadTexture(fileName));
		
		return new TextureModel(model,texture);
	}
	
}
