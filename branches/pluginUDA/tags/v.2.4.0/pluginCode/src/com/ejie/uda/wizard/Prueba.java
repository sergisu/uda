package com.ejie.uda.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.ejie.uda.wizard.prueba.Page_1;
import com.ejie.uda.wizard.prueba.Page_2;
import com.ejie.uda.wizard.prueba.Page_3;

public class Prueba extends Wizard implements org.eclipse.ui.INewWizard{

	public Prueba() {
		//Títulos de las ventanas
		setWindowTitle("Generador de código");
	}

	@Override
	public void addPages() {
		addPage(new Page_1());
		addPage(new Page_2());
		addPage(new Page_3());
	}

	/**
	 * Proceso que se lanza al finalizar (si devuelve false se queda abierto)
	 */
	@Override
	public boolean performFinish() {
		System.out.println("Realizando operaciones del WIZARD");
		return true;
	}

	/**
	 * Inicialización del wizard (ejecutar cosas antes de mostrar ventana)
	 */
	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		//NOTA: No se ejecuta desde el TESTER
		System.out.println("Iniciando el WIZARD");
	}

	
	/**
	 * Activa o desactiva el botón de FINISH
	 */
	@Override
	public boolean canFinish() {
		//El nombre de la página es el que se manda en en super() del constructor
		if ("page_3".equals(getContainer().getCurrentPage().getName())){
			return true;
		} else {
			return false;
		}
	}
}
