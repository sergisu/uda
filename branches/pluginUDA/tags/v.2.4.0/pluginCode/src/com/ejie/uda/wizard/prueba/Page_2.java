package com.ejie.uda.wizard.prueba;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Page_2 extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public Page_2() {
		super("page_2");
		setTitle("Wizard Page title");
		setDescription("Page 2");
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
