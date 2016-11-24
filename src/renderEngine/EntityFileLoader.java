package renderEngine;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class loads a HashMap of entities and models from a file
 * The format of the file is:
 * 0  1                 2                3    4    5    6  7  8  9
 * e: rawModel_fileName texture_fileName posX posY posZ rx ry rz scale
 * @author Miguel Garnacho Velez
 * @version 0.0 Alpha
 */
public class EntityFileLoader {

	/**
	 * This method returns all the entitites needed
	 * @param renderer The MasterRender object
	 * @param fileName The file where the list are
	 * @return entities The list with entities 
	 * @throws IOException 
	 */
	public static ArrayList<Entity> loadEntitiesFromFile(MasterRenderer renderer,Loader loader, String fileName) throws IOException {
		ArrayList<Entity> entities = new ArrayList<Entity>(); 
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".txt"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not load entities file!");
			e.printStackTrace();}
		BufferedReader reader = new BufferedReader(fr);
		String line = "";
		//Parsing
		while(line != null) {
			if(!line.startsWith("e:")) {
				line = reader.readLine();  //Read the next line
				continue;
			}
			System.out.println(line);
			String[] values = line.split(" ");
			if(values[0].startsWith("e:") && values.length == 10) { //Model
				RawModel model = OBJLoader.loadObjModel(values[1], loader);
				ModelTexture texture = new ModelTexture(loader.loadTexture(values[2]));
				float posX = Float.parseFloat(values[3]);
				float posY = Float.parseFloat(values[4]);
				float posZ = Float.parseFloat(values[5]);
				float rx = Float.parseFloat(values[6]);
				float ry = Float.parseFloat(values[7]);
				float rz = Float.parseFloat(values[8]);
				float scale = Float.parseFloat(values[9]);
				TexturedModel static_model = new TexturedModel(model, texture);
				Entity entity = new Entity(static_model, new Vector3f(posX,posY,posZ),rx,ry,rz,scale);
				entities.add(entity);
				line = reader.readLine();
			}

			
			}
		reader.close();
		return entities;
		}
	
}
