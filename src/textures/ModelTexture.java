package textures;

public class ModelTexture {
	
	private int textureID;
	
	private int shineDamper = 1;
	private int reflectivity = 1;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return textureID;
	}

	public int getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(int shineDamper) {
		this.shineDamper = shineDamper;
	}

	public int getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(int reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	

}
