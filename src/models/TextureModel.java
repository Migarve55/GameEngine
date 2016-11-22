package models;

import textures.ModelTexture;

public class TextureModel {

	private RawModel rawModel;
	private ModelTexture texture;
	
	public TextureModel(RawModel rawModel, ModelTexture texture) {
		this.rawModel = rawModel;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
}
