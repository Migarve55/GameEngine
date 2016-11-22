package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {

	public static RawModel loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load .obj file!");
			e.printStackTrace();}
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		//ArrayList to translate to arrays
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		//Array
		float[] verticesArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		
		try {
			
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" "); //Splits the data
				if(line.startsWith("v ")) { //If is a vertex data
					Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]), //X
							Float.parseFloat(currentLine[2]), //Y
							Float.parseFloat(currentLine[3])  //Z
							);
					vertices.add(vertex); //Adds the data to the arrayList
				} else if (line.startsWith("vt ")) { //If is a texture coordinate data
					Vector2f textureCoordinate = new Vector2f(
							Float.parseFloat(currentLine[1]), //X
							Float.parseFloat(currentLine[2])  //Y
							);
					textures.add(textureCoordinate); //Adds the data to the arrayList
				} else if (line.startsWith("vn ")) { //If is a normal vector data
					Vector3f normal = new Vector3f(
							Float.parseFloat(currentLine[1]), //X
							Float.parseFloat(currentLine[2]), //Y
							Float.parseFloat(currentLine[3])  //Z
							);
					normals.add(normal); //Adds the data to the arrayList
				} else if (line.startsWith("f ")) { //End of the file
					texturesArray = new float[vertices.size()*2];
					normalsArray  = new float[vertices.size()*3];
					break;
				}
			}
			
			while (line != null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();  //Read the next line
					continue;
				}
				String[] currentLine = line.split(" "); //Splits line into: "f ","v1","v2","v3"
				
				String[] vertex1 = currentLine[1].split("/"); //Splits v1 into 1,2,3
				String[] vertex2 = currentLine[2].split("/"); //Splits v1 into 1,2,3
				String[] vertex3 = currentLine[3].split("/"); //Splits v1 into 1,2,3
			
				//Each vertex in the triangles
				processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
				line = reader.readLine(); //Read the next line
			}
			
			reader.close(); //Close the file
			
		} catch (Exception e) {e.printStackTrace();}
		
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()*3];
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		int i = 0;
		for(Integer index:indices) {
			indicesArray[i++] = index;
		}
		
		return loader.loadToVAO(verticesArray, texturesArray, normalsArray, indicesArray);
 		
		}
	
	private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> textures, ArrayList<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		//Add the index
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		//Adds the textures
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		//Adds the norms
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;
	}
	
}
