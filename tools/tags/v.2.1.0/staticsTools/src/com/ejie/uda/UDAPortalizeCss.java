package com.ejie.uda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.tools.ant.Task;

/**
 * Clase principal para la tarea ANT
 */
public class UDAPortalizeCss extends Task {

	//Constantes
	public static final String PORTAL_STYLE = "div.r01gContainer"; //Estilo portal
	public static final String LINE_FEED = "\n";	//Salto de línea
	
	//Lista de ficheros a procesar (.css)
	public static ArrayList<String> cssFiles = new ArrayList<String>();
	
	//Estilos especiales
	public static ArrayList<String> styleHacks = new ArrayList<String>();
	
	//Nombre del fichero activo
	public static String fileName;
	
	//Warnings
	public static ArrayList<String> warnings;
	
	//Errores
	public static ArrayList<String> errors;
	
	/**
	 * Método que se lanza desde ANT
	 */
	public void execute() {
		
		//Obtener estilos a excluir (delante del estilo del contenedor del portal)
		String strStyleHacks = "";
		try {
			strStyleHacks = FileUtils.readFile(getParserHacks());
		} catch (IOException e) {
			System.out.println("Error retrieving 'style_hacks' file");
			return;
		}
		for (String line : strStyleHacks.split(LINE_FEED)){
			styleHacks.add(line.trim());
		}
		
		//Obtener ficheros Css (función recursiva que almacena los nombres en variable de clase [ArrayList])
		FileUtils.getCssFiles(new File(getDestDir()));
		
		//Procesar ficheros CSS
		for (String file : cssFiles){
			
				//Trazas
				fileName = file.substring(file.lastIndexOf("\\")+1);
				StringBuilder traze = new StringBuilder("Parsing "+fileName+"...");
				warnings = new ArrayList<String>();
				errors = new ArrayList<String>();
				
			//Obtener fichero procesado
			String outFileContent = "";
			try {
				outFileContent = UDAParser.parseFile(file, getCodApp());
			} catch (Exception e) {
				traze.append(" [ERROR]");
				System.err.println(traze);
				e.printStackTrace();
				continue;
			}
			
			//Gestionar ERRORES
			if (!errors.isEmpty()){
				traze.append(" ["+errors.size()+" ERRORs]");
				System.err.println(traze);
				for (String error : errors) {
					System.err.println("\t"+error);
				}
				continue;
			}

			//Escribir fichero procesado
			try {
				//FIXES
				//*****
					//Parsear estilos especiales de jQuery UI que no procesa el parser correctamente
					if (fileName.startsWith("jquery-ui-")){
						outFileContent = outFileContent.replace("ui-helper-clearfix before","ui-helper-clearfix:before");
						outFileContent = outFileContent.replace("ui-helper-clearfix after" ,"ui-helper-clearfix:after");
					}
					//Arreglar el parseo de '-webkit-gradient' de "mockLoginPage.css"
					if (fileName.equals("mockLoginPage.css")){
						
						//Obtener el valor del estilo
						String inputFileContent = FileUtils.readFile(file);
						String style = "-webkit-gradient", value,  pre, post, inter;
						value = inputFileContent.substring(inputFileContent.lastIndexOf(style));
						value = value.substring(0, value.indexOf("\n")-1);
						
						//Obterner la posición de donde se aplica el estilo
						pre = outFileContent.substring(0, outFileContent.lastIndexOf(style));
						post = outFileContent.substring(outFileContent.lastIndexOf(style));
						inter = post.substring(0, post.indexOf("\n"));
						post = post.substring(post.indexOf("\n"));
						
						//Modificar el estilo
						inter = value + inter.substring(inter.indexOf(";")+1);
						
						//Reconstruir outFileContent
						outFileContent = pre + inter + post;
					}
					//Arreglar parseo de estilo rgba del parser: rgba(0, 0, 0, 0.15) => rgba(0,,,0,,,0,,,0.15)
					outFileContent = outFileContent.replace(",,,",",");
				
				FileUtils.writeFile(file, outFileContent);
			} catch (IOException e) {
				traze.append(" [ERROR] - Error writing parsed file");
				System.out.println(traze);
				continue;
			}
			
			//Gestión de errores (parser CSS)
			if (warnings.isEmpty()){
				traze.append(" [DONE]");
				System.out.println(traze);
			} else {
				traze.append("["+warnings.size()+" WARNINGs]");
				System.out.println(traze);
				for (String warning : warnings) {
					System.out.println("\t"+warning);
				}
			}
		}
	}

	//Parametros entrada desde ANT
	private String codApp;
	private String destDir;
	private String parserHacks;
	public String getCodApp() {
		return codApp;
	}
	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}
	public String getDestDir() {
		return destDir;
	}
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	public String getParserHacks() {
		return parserHacks;
	}
	public void setParserHacks(String parserHacks) {
		this.parserHacks = parserHacks;
	}
}
