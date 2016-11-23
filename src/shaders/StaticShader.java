package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";     //Location of the vertex GLSL code
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt"; //Location of the fragment GLSL code
	
	//Location of all uniform variables of GLSL
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocaltion("transformationMatrix");
		location_projectionMatrix = super.getUniformLocaltion("projectionMatrix");
		location_viewMatrix = super.getUniformLocaltion("viewMatrix");
		location_lightPosition = super.getUniformLocaltion("lightPosition");
		location_lightColour = super.getUniformLocaltion("lightColour");
		location_shineDamper = super.getUniformLocaltion("shineDamper");
		location_reflectivity = super.getUniformLocaltion("reflectivity");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
}
