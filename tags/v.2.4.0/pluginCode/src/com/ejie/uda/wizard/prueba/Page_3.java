package com.ejie.uda.wizard.prueba;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Page_3 extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public Page_3() {
		super("page_3");
		setTitle("Wizard Page title");
		setDescription("Page 3");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
	}

}
