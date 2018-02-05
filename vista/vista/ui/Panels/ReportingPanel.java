package vista.ui.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerModel;

import controlador.common.UserConnectionData;
import controlador.reporting.ReportingClass;
import controlador.reporting.ReportingClass.Period;
import controlador.reporting.ReportingDaily;
import controlador.reporting.ReportingWeekly;
import vista.ui.Dialog.MessageCenterDialog;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;

@SuppressWarnings("serial")
public class ReportingPanel extends JPanel {
	//DATA
	private ArrayList<UserConnectionData> data;
	private ArrayList<UserConnectionData> filterData;
	private ProfileControl profile;
	private TipoSistema sistema;
	private final String datePatternFormatDialy = "dd/MM/YYYY";
	private final String datePatternFormatWeekly = "ww 'Semana del 'dd/MM/YYYY";
	private UserConnectionData user;
	private Period period = Period.DAILY;
	//UI
	private ButtonGroup btnGroup;
	private ButtonGroup btnGroupPeriod;
	private JRadioButton btnDaily;
	private JRadioButton btnWeekly;
	private EnvironmentRButtonsPanel rButtonsPanel;
	private JPanel reportPanel;
	private JButton currentDate;
	private JButton generateButton;
	private JButton openPathButton;
	private SpinnerModel spinnerModel;
	private JSpinner spinner;
	private JPanel mainPanel; 
	
	public ReportingPanel(ArrayList<UserConnectionData> data,
			ProfileControl profile, TipoSistema tipo) {
		this.data = new ArrayList<UserConnectionData>();
		this.data.addAll(data);
		this.profile = profile;
		this.sistema = tipo;
		filterData = profile.getSystemProfileList(data, sistema);
		initialize();
	}

	/**
	 * Se cambia de sistema MAY/MIN
	 * @param sistema
	 */
	public void changeSystem(TipoSistema sistema) {
		filterData = profile.getSystemProfileList(data, sistema);
		rButtonsPanel.changeSystem(filterData);
		for(Enumeration<AbstractButton> e = btnGroup.getElements();e.hasMoreElements();){
			e.nextElement().addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						//Informa a paneles inferiores del cambio
						doSomething((JRadioButton)e.getSource());
				    }
				}
			});
		}
		validate();
		repaint();
	}
	
	private void initialize(){
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
		btnGroup = new ButtonGroup();
		/**************************************************
		 * Inicializa el panel de seleccion de entornos
		 */
		
		rButtonsPanel = new EnvironmentRButtonsPanel(filterData, profile);
		rButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		rButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		rButtonsPanel.setButtonsGroup(btnGroup);
		for(Enumeration<AbstractButton> e = btnGroup.getElements();e.hasMoreElements();){
			e.nextElement().addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						//Informa a paneles inferiores del cambio
						doSomething((JRadioButton)e.getSource());
				    }
				}
			});
		}
		/**************************************************
		 * Inicializa panel para generacion de informes
		 */
		
		reportPanel = new JPanel();
		reportPanel.setAlignmentX(LEFT_ALIGNMENT);
		reportPanel.setAlignmentY(TOP_ALIGNMENT);
		reportPanel.setLayout(new BoxLayout(reportPanel,BoxLayout.Y_AXIS));
		reportPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel contElements = new JPanel();
		contElements.setAlignmentX(LEFT_ALIGNMENT);
		contElements.setAlignmentY(TOP_ALIGNMENT);
		
		btnGroupPeriod = new ButtonGroup();
		btnDaily = new JRadioButton("Informe diario");
		ItemListener it = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeSpinner((JRadioButton) e.getSource());
			}
		};
		btnDaily.setSelected(true);
		
		
		btnWeekly = new JRadioButton("Informe semanal");
		btnWeekly.setSelected(false);
		
		btnDaily.addItemListener(it);
		btnWeekly.addItemListener(it);
		
		btnGroupPeriod.add(btnDaily);
		btnGroupPeriod.add(btnWeekly);
		
		Date currentDate = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, 1);
		currentDate = cal.getTime();
		
		spinnerModel = new SpinnerDailyModel(currentDate);
		
		spinner = new JSpinner(spinnerModel);
		spinner.setAlignmentX(CENTER_ALIGNMENT);
		
		DateEditor ed = new DateEditor(spinner,this.datePatternFormatDialy);
		spinner.setEditor(ed);
		
		JPanel spinPanel = new JPanel();
		spinPanel.setMaximumSize(new Dimension(100, 15));
		spinPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel buttonPanel = new JPanel();
//		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setAlignmentY(TOP_ALIGNMENT);
		generateButton = new JButton("Generar informe");
		generateButton.setAlignmentX(LEFT_ALIGNMENT);
		generateButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				generateReport();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		spinPanel.add(spinner);
		reportPanel.add(btnDaily);
		reportPanel.add(btnWeekly);
		reportPanel.add(spinPanel);
		reportPanel.add(generateButton);
		reportPanel.add(buttonPanel);
		
		mainPanel.add(rButtonsPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(10,0)));
		mainPanel.add(reportPanel);
		add(mainPanel);
	}
	
	private void doSomething(JRadioButton btn){
		HashMap<UserConnectionData, JRadioButton> relation = 
				rButtonsPanel.getButtonRelation();
		user = null;
		//Se recupera el entorno que corresponde al Button seleccionado
		for(UserConnectionData usr:relation.keySet()){
			if(relation.get(usr).equals(btn)){
				user = usr;
				break;
			}
		}
	}
	
	private void changeSpinner(JRadioButton btn){
		if(btn.equals(btnDaily)){
			spinnerModel = new SpinnerDailyModel(new Date());
			spinner.setModel(spinnerModel);
			DateEditor ed = new DateEditor(spinner,this.datePatternFormatDialy);
			spinner.setEditor(ed);
			period = Period.DAILY;
		}else if(btn.equals(btnWeekly)){
			spinnerModel = new SpinnerDateModel(new Date(),null,null,Calendar.WEEK_OF_YEAR);
			spinner.setModel(spinnerModel);
			DateEditor ed = new DateEditor(spinner,this.datePatternFormatWeekly);
			spinner.setEditor(ed);
			period = Period.WEEKLY;
		}
		validate();
		repaint();
	}
	
	private void generateReport(){
		if(user == null){
			MessageCenterDialog.showWarningDialog(this, "Se debe seleccionar un entorno del que obtener el informe");
			return;
		}
		ReportingClass r;
		//Se instancia diario o semanal en funcion del tipo seleccionado
		if(period.equals(Period.DAILY)){
			r = new ReportingDaily((Date) spinner.getValue(), user);
		}else{
			r = new ReportingWeekly((Date) spinner.getValue(), user);
		}
		startWaiting();
		r.generateReport();
		resuming();
	}
	
	private void startWaiting(){
		btnDaily.setEnabled(false);
		btnWeekly.setEnabled(false);
		spinner.setEnabled(false);
		generateButton.setEnabled(false);
	}
	
	private void resuming(){
		btnDaily.setEnabled(true);
		btnWeekly.setEnabled(true);
		spinner.setEnabled(true);
		generateButton.setEnabled(true);
	}
	
	private class SpinnerDailyModel extends SpinnerDateModel{
		private Date realDate;
		public SpinnerDailyModel(Date date){
			super(date,null,null,1);
			realDate = date;
		}
		
		public Object getNextValue(){
			Date currentDate = realDate;
			Calendar cal = new GregorianCalendar();
			cal.setTime(currentDate);
			cal.add(Calendar.DATE, 1);
			realDate = cal.getTime();
			return realDate;
		}
		
		public Object getPreviousValue(){
			Date currentDate = realDate;
			Calendar cal = new GregorianCalendar();
			cal.setTime(currentDate);
			cal.add(Calendar.DATE, -1);
			realDate = cal.getTime();
			return realDate;
		}
	}
}

