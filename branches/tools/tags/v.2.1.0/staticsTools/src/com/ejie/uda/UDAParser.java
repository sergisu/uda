package com.ejie.uda;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;

public class UDAParser {

	
	//Líneas de error
	public static ArrayList<Integer> lineError;
	
	//Variable para determinar si el fichero es el css de la aplicación
	private static boolean appCss = false;
	private static void setAppCss(String codApp, String fileName){
		if (!fileName.equalsIgnoreCase(codApp+".css")){
			appCss = false;
		} else {
			appCss = true;
		}
	}
	
	/**
	 * Método principal que parsea el CSS
	 * @param file - Nombre del fichero
	 * @return Estilos paraseados
	 * @throws Exception
	 */
	public static String parseFile(String file, String codApp) throws Exception {

		//Leer fichero
		InputSource inputFile = new InputSource(new InputStreamReader(new FileInputStream(file)));
		
		//Reiniciar errores
		lineError = new ArrayList<Integer>();
		
		//Detectar si es el CSS de la aplicación (para parcheos especiales)
		setAppCss(codApp, UDAPortalizeCss.fileName);
		
		//Parsear fichero
		CSSOMParser parser = new CSSOMParser();
		parser.setErrorHandler(UDAParserErrorHandler.getInstance());
		CSSStyleSheet stylesheet = parser.parseStyleSheet(inputFile, null, null);
		
		//Obtener estilos
		CSSRuleList ruleList = stylesheet.getCssRules();
		
		//Procesar estilos
		processRuleList(ruleList);

		//Salida
		String ouputFileContent = ruleList.toString();

		//Parsear 'font-family' (el parser elimina las comas que separan los valores)
		ouputFileContent = parseFontFamily (ouputFileContent);
		
		/** GESTION DE ERRORES DE VALIDACION **/
			//Procesar problemas en estilos
			if (!lineError.isEmpty()){
				
				//Fichero de entrada (gestión de errores)
				String inputFileContent = FileUtils.readFile(file);
				
				//Parsear líneas
				ouputFileContent = parseErrorLines(inputFileContent, ouputFileContent);
			}
		
		
		//Devolver estilos
		return ouputFileContent;
	}
	
	/**
	 * Procesa todas las reglas de un fichero determinando qué tipo es e invocando el método correspondiente
	 * @param ruleList - Lista de estilos
	 */
	private static void processRuleList (CSSRuleList ruleList){
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.item(i);
			if (rule instanceof CSSStyleRule) {
				CSSStyleRule styleRule = (CSSStyleRule) rule;
				processStyleRule(styleRule);
			} else if (rule instanceof CSSMediaRule) {
				CSSMediaRule mediaRule = (CSSMediaRule) rule;
				processMediaRule(mediaRule);
			} 
		}
	}
	
	/**
	 * Procesa las reglas relativas a definición de estilos (CSSStyleRule)
	 * @param styleRule - Regla a procesar
	 */
	private static void processStyleRule (CSSStyleRule styleRule){
		//Separar diferentes estilos
		String[] selectores = styleRule.getSelectorText().split(", ");
		StringBuilder portalizedSelectorText = new StringBuilder();
		for (String selector : selectores) {
			//Parsear selector
			portalizedSelectorText.append(parseSelector(selector)).append(", ");
		};
		portalizedSelectorText.delete(portalizedSelectorText.length()-2, portalizedSelectorText.length()); //Eliminar última coma
		
		//Modificar selector en regla
		styleRule.setSelectorText(portalizedSelectorText.toString());
		
		//Si es el CSS de la aplicación aplicar "parches" para portalizar
		if (appCss){
			parseAppCss(styleRule, portalizedSelectorText.toString());
		}
		
	}
	
	/**
	 * Procesa las reglas "media" (CSSMediaRule)
	 * @param styleRule - Regla a procesar
	 */
	private static void processMediaRule (CSSMediaRule mediaRule){
		CSSRuleList mediaRuleList = mediaRule.getCssRules();
		for (int i = 0; i < mediaRuleList.getLength(); i++) {
			CSSRule subrule = mediaRuleList.item(i);
			if (subrule instanceof CSSStyleRule) {
				CSSStyleRule subStyleRule = (CSSStyleRule) subrule;
				processStyleRule(subStyleRule);
			} else if (subrule instanceof CSSMediaRule) {
				CSSMediaRule subMediaRule = (CSSMediaRule) subrule;
				processMediaRule(subMediaRule);
			}
		}
	}
	
	/**
	 * Parsea los selectores del estilo, añadiendo el estilo del portal y evitando los styleHacks
	 * @param selector - Elemento a parsear
	 * @return Selector parseado
	 */
	private static String parseSelector(String selector){
		//Hacks
		String style_hack = "";
		for (String hack : UDAPortalizeCss.styleHacks){
			if (selector.contains(hack.trim())){
				style_hack = hack + " ";
				selector = selector.replace(hack, "").trim();
				break;
			}
		}
		return style_hack + UDAPortalizeCss.PORTAL_STYLE + " " + selector;
	}
	
	
	/**
	 * Parsea los estilos del fichero de la aplicación para ajustarlo al portal
	 * @param styleRule - Regla de estilo
	 * @param portalizedSelectorText - Literal con el selector de estilo
	 */
	private static void parseAppCss(CSSStyleRule styleRule, String portalizedSelectorText){
		
		//Estilo "body"
		if (portalizedSelectorText.trim().equals(UDAPortalizeCss.PORTAL_STYLE + " body")){
			//Eliminar "body" del selector
			styleRule.setSelectorText(portalizedSelectorText.toString().replace("body", ""));
			
			//Eliminar color de fondo
			CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
			styleDeclaration.removeProperty("background");
		}
		
		//Estilo ".contenedor"
		if (portalizedSelectorText.trim().equals(UDAPortalizeCss.PORTAL_STYLE + " *.contenedor")){
			//Eliminar tamaño por defecto (para márgenes)
			CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
			styleDeclaration.removeProperty("width");
		}
		
	}
	
	/**
	 * Parsea las líneas erroneas en el parseo
	 * @param inputFileContent - Contenido original (array de líneas)
	 * @param ouputFile - Fichero de salida
	 * @return Fichero de salida
	 * @throws IOException
	 */
	private static String parseErrorLines(String inputFileContent, String ouputFile) throws IOException{
		
		String[] arrInputFileContent = inputFileContent.split(UDAPortalizeCss.LINE_FEED);
		
		Set<Integer> showed = new HashSet<Integer>();
		String style, pre, post, inter;
		for (Integer line : lineError){
			if (!showed.contains(line)){
				showed.add(line); //Evitamos repetir línea

				//Obtenemos línea original
				style = arrInputFileContent[line-1].trim();
				
				//Comprobamos que no lanza una función
				if (style.contains("()")){ 
					UDAPortalizeCss.warnings.add("Invalid property [line "+line+"]: "+style);
					continue;
				}
				
				//Control de errores en estilos incorrectos
				if (style.indexOf("{")==-1){
					UDAPortalizeCss.errors.add("Style not valid [line: "+line+"]: "+style);
					continue;
				}
				
				//Comprobar que no es duplicado
				if (inputFileContent.split(style.substring(0, style.indexOf("{")).trim().replace("*", "_")).length>2){
					//FIXES
					//*****
					//Controlar el error producido por el estilo '.ui-widget-overlay' del "jquery-ui"
					if (UDAPortalizeCss.fileName.startsWith("jquery-ui-") && style.startsWith(".ui-widget-overlay")){
						String parsedStyleName = parseSelector(style.substring(0, style.indexOf("{")).trim().replaceAll("\\.", "\\*."));
						pre = ouputFile.substring(0, ouputFile.lastIndexOf(parsedStyleName));
						post = ouputFile.substring(ouputFile.lastIndexOf(parsedStyleName));
						inter = post.substring(0, post.indexOf("\n"));
						post = post.substring(post.indexOf("\n"));
						
						//Parsear propiedades
						String propiedades = style.substring(style.indexOf("{")+1, style.indexOf("}"));
						inter = inter.replaceFirst("\\{{1}.+\\}{1}", "{ " + propiedades + " }");
				
						//Reconstruir ouputFile
						ouputFile = pre + inter + post;
					} else {
						UDAPortalizeCss.warnings.add("Duplicate selector [line "+line+"]: "+style);
					}
					continue;
				}
				
				//Obtenemos selector original
					StringBuilder selectorBuilder = new StringBuilder();
					String[] selectores = style.substring(0, style.indexOf("{")).split(", ");
					for (String selector : selectores) {
						selector = selector.trim();
						selectorBuilder.append(parseSelector(selector)).append(", ");
					};
					selectorBuilder.delete(selectorBuilder.length()-2, selectorBuilder.length()); //Eliminar última coma
				String selector = selectorBuilder.toString().replace(" .", " *.");

				//Gestionar IDs
				selector = selector.replace("#", "*#");

				//Obtenemos propiedades originales
				String propiedades = style.substring(style.indexOf("{")+1, style.indexOf("}"));
				
				//Comprobamos selector
				if (ouputFile.indexOf(selector)==-1){
					UDAPortalizeCss.warnings.add("Selector not found [line "+line+"]: "+selector);
					continue;
				}
				
				//Obtener elementos
				pre = ouputFile.substring(0, ouputFile.indexOf(selector));
				post = ouputFile.substring(ouputFile.indexOf(selector));
				inter = post.substring(0, post.indexOf("\n"));
				post = post.substring(post.indexOf("\n"));
				
				//Parsear propiedades
				inter = inter.replaceFirst("\\{{1}.+\\}{1}", "{ " + propiedades + " }");
				
				//Reconstruir ouputFile
				ouputFile = pre + inter + post;
			}
							
		}
		return ouputFile;
	}
	
	/**
	 * Parsea los estilos 'font-family' para añadir las comas entre elementos
	 * @param outputFileContent - Contenido a procesar (fichero de salida)
	 * @return Fichero de salida
	 */
	private static String parseFontFamily(String outputFileContent){
		StringBuilder outputParsed = new StringBuilder();
		String pre, post, inter;
		for (String line : outputFileContent.split(UDAPortalizeCss.LINE_FEED)){
			//Procesar líneas que tienen la propiedad
			if (line.contains("font-family")){
				//Obtener elementos
				pre = line.substring(0, line.indexOf("font-family")+13);
				post = line.substring(line.indexOf("font-family")+13);
				inter = "";
				//Para obtener la propiedad tenemos 2 posibilidades
				if (post.contains(";")){
					//La propiedad está acompañada de otras acaba en ';'
					inter = post.substring(0, post.indexOf(";"));
					post = post.substring(post.indexOf(";"));
				} else {
					//La propiedad esla última (o única) acaba en '}' (el parser elimina el último ';')
					inter = post.substring(0, post.indexOf("}")-1);
					post = post.substring(post.indexOf("}")-1);
				}
				inter = inter.replace(" ", ", ");
				line = pre + inter + post;
				
			}
			outputParsed.append(line).append(UDAPortalizeCss.LINE_FEED);
		}
		return outputParsed.toString();
	}
	
}