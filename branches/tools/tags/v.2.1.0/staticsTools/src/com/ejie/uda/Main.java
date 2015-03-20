package com.ejie.uda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

	/**
	 * Clase para pruebas
	 */
	public static void main(String[] args) {
		// Copiar x21aStatics -> x21aPortalStatics (borrar)
		try {
			System.out.println("Borrando x21aPortalStatics...");
			deleteDir(new File("x21aPortalStatics"));
			System.out.println("Copiando x21aStatics...");
			copyDirectory(new File("x21aStatics"), new File("x21aPortalStatics"));
		} catch (IOException e) {
			System.out.println("Error copiando directorio");
			e.printStackTrace();
			return;
		}

		// Lanzar JAR
		System.out.println("Lanzando portalizador...");
		UDAPortalizeCss obj = new UDAPortalizeCss();
		obj.setCodApp("x21a");
		obj.setDestDir("x21aPortalStatics");
		obj.setParserHacks("D:/Helios UDA/plugins/org.apache.ant_1.7.1.v20100518-1145/lib/com.ejie.uda.statics.tools.style_hacks");
		obj.execute();
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private static void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}
