package com.ejie.uda.wizard.prueba;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.ejie.uda.wizard.DBProperties;

public class Page_1 extends WizardPage {

	private Button btnRadioButton_1;
	private Button btnRadioButton_2;
	private Button btnRadioButton_3;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Label lblNewLabel_4;
	private Label lblNewLabel_5;
	private Label lblNewLabel_6;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Combo combo;
	private Button btnGuardar;
	
	/**
	 * Create the wizard.
	 */
	public Page_1() {
		super("page_1"); //ID de la página
		setTitle("Wizard Page title"); //Títulos página
		setDescription("Page 1"); //Subtítulo página
		setPageComplete(true); //No permitir next
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(null);
		
		btnRadioButton_1 = new Button(container, SWT.RADIO);
		btnRadioButton_1.setBounds(5, 5, 100, 16);
		btnRadioButton_1.setText("Cargar conexión");
		
		btnRadioButton_2 = new Button(container, SWT.RADIO);
		btnRadioButton_2.setBounds(5, 39, 100, 16);
		btnRadioButton_2.setText("Conexión nueva");
		
		btnRadioButton_3 = new Button(container, SWT.RADIO);
		btnRadioButton_3.setBounds(5, 69, 63, 16);
		btnRadioButton_3.setText("Opción 3");
		
		
		//DATOS
			lblNewLabel = new Label(container, SWT.NONE);
			lblNewLabel.setBounds(56, 91, 17, 13);
			lblNewLabel.setText("SID");
			text = new Text(container, SWT.BORDER);
			text.setBounds(78, 88, 76, 19);
			
			lblNewLabel_1 = new Label(container, SWT.NONE);
			lblNewLabel_1.setBounds(51, 115, 22, 13);
			lblNewLabel_1.setText("Host");
			text_1 = new Text(container, SWT.BORDER);
			text_1.setBounds(78, 112, 76, 19);
			
			lblNewLabel_2 = new Label(container, SWT.NONE);
			lblNewLabel_2.setBounds(41, 139, 32, 13);
			lblNewLabel_2.setText("Puerto");
			text_2 = new Text(container, SWT.BORDER);
			text_2.setBounds(78, 136, 76, 19);
			
			lblNewLabel_3 = new Label(container, SWT.NONE);
			lblNewLabel_3.setBounds(177, 94, 36, 13);
			lblNewLabel_3.setText("Usuario");
			text_3 = new Text(container, SWT.BORDER);
			text_3.setBounds(218, 91, 76, 19);
			
			lblNewLabel_4 = new Label(container, SWT.NONE);
			lblNewLabel_4.setBounds(167, 118, 46, 13);
			lblNewLabel_4.setText("Password");
			text_4 = new Text(container, SWT.BORDER);
			text_4.setBounds(218, 115, 76, 19);
			
			lblNewLabel_5 = new Label(container, SWT.NONE);
			lblNewLabel_5.setBounds(30, 161, 43, 13);
			lblNewLabel_5.setText("Esquema");
			text_5 = new Text(container, SWT.BORDER);
			text_5.setBounds(78, 158, 76, 19);
			
			lblNewLabel_6 = new Label(container, SWT.NONE);
			lblNewLabel_6.setBounds(170, 158, 43, 13);
			lblNewLabel_6.setText("Catálogo");
			text_6 = new Text(container, SWT.BORDER);
			text_6.setBounds(218, 155, 76, 19);
			
		//Combo conexiones
			combo = new Combo(container, SWT.NONE);
			combo.setBounds(115, 4, 93, 21);
			combo.setItems(DBProperties.getConnectionNames());
			combo.select(0);
			
			text_7 = new Text(container, SWT.BORDER);
			text_7.setEnabled(false);
			text_7.setBounds(115, 39, 93, 19);
			
			btnGuardar = new Button(container, SWT.NONE);
			btnGuardar.setBounds(78, 216, 68, 23);
			btnGuardar.setText("Guardar");
			
			
		//Cargar valores
			loadBDProperties();
		
		//Eventos
		Listener listener = new Listener(){
			public void handleEvent(Event e) {
				if (e.widget instanceof Button){
					Button button = (Button) e.widget;
					if (button == btnRadioButton_1){
						combo.setEnabled(true);
						text_7.setEnabled(false);
						loadBDProperties();
					} else if (button == btnRadioButton_2){
						combo.setEnabled(false);
						text_7.setEnabled(true);
						text.setText("");	
						text_1.setText("");
						text_2.setText("");
						text_3.setText("");
						text_4.setText("");
						text_5.setText("");
						text_6.setText("");
					} else if (button == btnRadioButton_3){
						combo.setEnabled(false);
						text_7.setEnabled(false);
					}
				}
			}
		};
		addListener(SWT.FOCUSED, listener);
		
		SelectionListener selectionListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				if (se.getSource() instanceof Combo) {
					loadBDProperties();
				}
				if (se.getSource() instanceof Button) {
					saveBDProperties(); 
				} 
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent se) {
				this.widgetSelected(se);
			}
		};
		combo.addSelectionListener(selectionListener);
		btnGuardar.addSelectionListener(selectionListener);
//		btnGuadar.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				System.out.println("PUSH");
//			}
//		});
	}

	@Override
	public IWizardPage getNextPage() {
		return super.getNextPage();
//		return getWizard().getContainer().getCurrentPage();
	}

	@Override
	public IWizardPage getPreviousPage() {
		return super.getPreviousPage();
	}

	private void addListener(int eventType, Listener listener) {
		btnRadioButton_1.addListener(eventType, listener);
		btnRadioButton_2.addListener(eventType, listener);
		btnRadioButton_3.addListener(eventType, listener);
	}
	
	private void loadBDProperties(){
		Map <String,String> db_properties = DBProperties.getProperties(combo.getItem(combo.getSelectionIndex()));
		text.setText(db_properties.get("sid"));	
		text_1.setText(db_properties.get("host"));
		text_2.setText(db_properties.get("portnumber"));
		text_3.setText(db_properties.get("username"));
		text_4.setText(db_properties.get("password"));
		text_5.setText(db_properties.get("schema"));
		text_6.setText(db_properties.get("catalog"));
	}
	
	private void saveBDProperties(){
		Map <String,String> db_properties = new HashMap<String, String>();
		db_properties.put("sid", text.getText());
		db_properties.put("host", text_1.getText());
		db_properties.put("portnumber", text_2.getText());
		db_properties.put("username", text_3.getText());
		db_properties.put("password", text_4.getText());
		db_properties.put("schema", text_5.getText());
		db_properties.put("catalog", text_6.getText());
		DBProperties.setProperties(combo.getItem(combo.getSelectionIndex()), db_properties);
	}
}