package com.ejie.uda.wizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Tester {

	public static void main(String[] args) {
		
		Display display = new Display();

		// Create the parent shell for the dialog, but don't show it
		Shell shell = new Shell(display);

		// Create the dialog
		WizardDialog dlg = new WizardDialog(shell, new Prueba());
		dlg.open();

		// Dispose the display
		display.dispose();
	}

}
