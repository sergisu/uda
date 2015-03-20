/*
* Copyright 2012 E.J.I.E., S.A.
*
* Licencia con arreglo a la EUPL, Versión 1.1 exclusivamente (la «Licencia»);
* Solo podrá usarse esta obra si se respeta la Licencia.
* Puede obtenerse una copia de la Licencia en
*
* http://ec.europa.eu/idabc/eupl.html
*
* Salvo cuando lo exija la legislación aplicable o se acuerde por escrito,
* el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
* SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
* Véase la Licencia en el idioma concreto que rige los permisos y limitaciones
* que establece la Licencia.
*/
package com.ejie.uda.wizard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
 
public class DBProperties {
 
	private static final String CONNECTION_PROPERTIES_FILE = "UDA_DB_CONNECTION.properties";
	//private static final String ECLIPSE_HOME = System.getProperty("eclipse.home.location").substring(6);
	private static final String ECLIPSE_HOME = "D:/Helios UDA";
    
    private static Properties properties;
    
//    /**
//     * Patrón Singleton
//     */
//    private static DBProperties instance = null;
//    private DBProperties() {}
//    private synchronized static void createInstance() {
//    	 if (instance == null) {
//             synchronized(DBProperties.class) {
//                 if (instance == null) { 
//                	 instance = new DBProperties();
//                 }
//             }
//         }
//    }
//    public static DBProperties getInstance() {
//        createInstance();
//        return instance;
//    }
    
    
    //Cargar properties
    private static void loadProperties(){
    	try {
    		//Si no existe el fichero se crea
			File fileProperties = new File(ECLIPSE_HOME, CONNECTION_PROPERTIES_FILE);
			fileProperties.createNewFile(); //Si no existe el fichero, lo crea
        
			//Cargar las propiedades
        	FileInputStream fis = new FileInputStream(ECLIPSE_HOME + "/" +  CONNECTION_PROPERTIES_FILE);
        	properties = new Properties();
        	properties.load(fis);
            fis.close();
    	} catch (Exception e){
    		System.out.println("ERROR LOAD");
    	}
    }
    
    
    public static Map<String, String> getProperties(String codApp){
    	//Cargar propiedades
    	if (properties == null){
    		loadProperties();
    	}
    	
    	//Retorno
    	Map<String, String> map = new HashMap<String, String>();
    	
    	//Filtrar properties
    	for (String key : properties.stringPropertyNames()) {
    		if (key.contains(codApp)){
    			map.put(key.substring(codApp.length()+1), properties.getProperty(key));
    		}
		}
    	return map;
    }
    
    
    public static String[] getConnectionNames(){
    	//Cargar propiedades
    	if (properties == null){
    		loadProperties();
    	}
    	
    	//Obtener conexiones
    	Set<String> set = new TreeSet<String>(); //Ordenado alfabeticamente
    	for (String key : properties.stringPropertyNames()) {
    		set.add(key.substring(0, key.indexOf(".")));
		}
    	
    	//Retorno 
    	String[] names = new String[set.size()];
    	names = set.toArray(new String[0]);
    	return names;
    }
    
    public static void setProperties(String codApp, Map<String, String> map){
    	
    	//Actualizar propiedades
    	for (Map.Entry<String, String> entry : map.entrySet()) {
    	    properties.put(codApp+"."+entry.getKey(), entry.getValue());
    	}
    	
    	//Ordenar propiedades (para guardado en fichero)
    	Map<String, String> propertiesMap = new TreeMap<String, String>(); //Ordenado alfabeticamente
    	for (String key : properties.stringPropertyNames()) {
    		propertiesMap.put(key, properties.getProperty(key));
		}

    	//Alamacenar propiedades
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(CONNECTION_PROPERTIES_FILE));
    		String connection = "";
    		for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
    			//Separar la conexiones con línea vacía
    			if (!connection.equals(entry.getKey().substring(0, entry.getKey().indexOf(".")))){
    				connection = entry.getKey().substring(0, entry.getKey().indexOf("."));
    				writer.newLine();
        	    }
    			writer.write(entry.getKey()+"="+entry.getValue());
    			writer.newLine();
        	}
    		writer.flush();
    		writer.close();
    	} catch (Exception e){
    		System.out.println("ERROR SAVE");
    	}
    }
    
}