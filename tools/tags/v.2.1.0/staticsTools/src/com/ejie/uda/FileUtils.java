package com.ejie.uda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase con utilidades de manipulación de ficheros
 */
public class FileUtils {

	/**
	 * Leer fichero
	 * @param inFileName - Ruta al fichero
	 * @return Contenido del fichero
	 * @throws IOException
	 */
	public static String readFile(String inFileName) throws IOException {
		FileInputStream fis = new FileInputStream(new File(inFileName));
		byte b[] = new byte[fis.available()];
		fis.read(b);
		fis.close();
		return new String(b);
	}
	
	/**
	 * Escribir fichero
	 * @param outFileName - Ruta al fichero
	 * @param content - Contenido del fichero
	 * @throws IOException
	 */
	public static void writeFile(String outFileName, String content) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(outFileName));
		fos.write(content.getBytes());
		fos.flush();
		fos.close();
	}
	
	/**
	 * Obtiene (recursivamente) los ficheros con extensión .css en una variable estática de UDAPortalizeCss
	 * @param file - Objeto fichero
	 */
	public static void getCssFiles(File file) {
		if (file.isDirectory()) {
			for (String children : file.list()) {
				getCssFiles(new File(file, children));
			}
		} else {
			if ("css".equals(getFileExtension(file))){
				UDAPortalizeCss.cssFiles.add(file.getAbsolutePath());
			}
		}
	}
	
	/**
	 * Devuelve la extensión del fichero
	 * @param file - Objeto fichero
	 * @return Extensión 
	 */
	private static String getFileExtension(File file) {
	    return file.getName().substring(file.getName().lastIndexOf(".") + 1);
	}
	
}