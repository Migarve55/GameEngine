package renderEngine;

import java.util.ArrayList;

import entities.Entity;
import models.RawModel;
import models.TextureModel;
import textures.ModelTexture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class loads a HashMap of entities and models from a file
 * The format of the file is:
 * m: rawModel_fileName texture_fileName
 * e: textured_model pos rx ry rz scale
 * end //Finish parsing
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
		String line;
		//Parsing
		while(true) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			String[] values = line.split(" ");
			if(values[0].startsWith("m:")) { //Model
				RawModel model = OBJLoader.loadObjModel(values[1], loader);
				ModelTexture texture = new ModelTexture(loader.loadTexture(values[2]));
				TextureModel static_model = new TextureModel(model, texture);
			}
			if(values[0].startsWith("e:")) { //Entity
				
			}
			if(values[0].startsWith("end"))break; //Exits file
			
		}
		reader.close();
		
		return entities;
	}
	
}
