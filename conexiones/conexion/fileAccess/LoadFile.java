package conexion.fileAccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public static HashMap<Integer,String> getFileMap(String fileName){
		HashMap<Integer,String> fileMap = new HashMap<Integer,String>();
		int lineCount = 0;
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
//		LoadFile.class.getClass().getResource("/resources/" + fileName).getFile();
		
		try{
//			fr = new FileReader(LoadFile.class.getClass().getResource("/resources/" + fileName).getFile());
			fr = new FileReader("./resources/" + fileName);
			br = new BufferedReader(fr);
			while ((sCurrentLine = br.readLine()) != null) {
				fileMap.put(lineCount, sCurrentLine);
				lineCount++;
			}
			br.close();
		
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
		return fileMap;
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
	
	public static HashMap<Integer,String> getFileMap(String relativeFolder, String fileName){
		HashMap<Integer,String> fileMap = new HashMap<Integer,String>();
		int lineCount = 0;
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
		try{
			fr = new FileReader("./" + relativeFolder + "/" + fileName);
			br = new BufferedReader(fr);
			while ((sCurrentLine = br.readLine()) != null) {
				fileMap.put(lineCount, sCurrentLine);
				lineCount++;
			}
			br.close();
		
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
		return fileMap;
	}
	
	public static void appendText(String relativeFolder, String fileName, String text){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try{
			fw = new FileWriter("./" + relativeFolder + "/" + fileName,true);
			bw = new BufferedWriter(fw);
			bw.append(text);
			bw.close();
			fw.close();
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
	}
	
	public static void appendNewLine(String relativeFolder, String fileName){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try{
			fw = new FileWriter("./" + relativeFolder + "/" + fileName, true);
			bw = new BufferedWriter(fw);
			bw.newLine();
			bw.close();
			fw.close();
		}catch(IOException ex){
			System.out.println("ERROR DE LECTURA/APERTURA DEL FICHERO");
			System.out.println(ex.getMessage());
		}catch(Exception e){
			System.out.println("ERROR INESPERADO CONTACTE CON ADMINISTRADOR");
			System.out.println(e.getMessage());
		}
	}
}
