package conexion.fileAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadFile {
	/**
	 * Devuelve un ArrayList con las lineas cargadas de un fichero 
	 * @param fileName
	 * @return
	 */
	public static ArrayList<String> getFile(String fileName){
		ArrayList<String> fileLines = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
//		LoadFile.class.getClass().getResource("/resources/" + fileName).getFile();
		
		try{
//			fr = new FileReader(LoadFile.class.getClass().getResource("/resources/" + fileName).getFile());
			fr = new FileReader("./resources/" + fileName);
			br = new BufferedReader(fr);
			while ((sCurrentLine = br.readLine()) != null) {
				fileLines.add(sCurrentLine);
			}
			br.close();
		
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
		return fileLines;
	}
	
	/**
	 * Devuelve un ArrayList con las lineas cargadas de un fichero 
	 * @param fileName
	 * @return
	 */
	public static ArrayList<String> getFile(String relativeFolder, String fileName){
		ArrayList<String> fileLines = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
		try{
			fr = new FileReader("./" + relativeFolder + "/" + fileName);
			br = new BufferedReader(fr);
			while ((sCurrentLine = br.readLine()) != null) {
				fileLines.add(sCurrentLine);
			}
			br.close();
		
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
		return fileLines;
	}
}
