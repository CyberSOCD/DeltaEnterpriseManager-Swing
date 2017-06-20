package uiPanels.Status;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uiRunnables.TaskTimer;
import validationPackage.EnvStatusValidation;
import common.UserConnectionData;
import controlResult.EnvironmentStatus;

/**
 * 
 * Panel que maneja un entorno y muestra su estado actual
 * 
 */
@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements GenericStatusPanel{
	private EnvironmentStatus status;
	private EnvStatusValidation validate;
	private String envName;
	private Color color;
	private JLabel labelName;
	private JLabel labelVersion;
	private JLabel timerVersion;
	private String serverVersion = "";
	private int freqTimeout = 1;
	private TaskTimer timer;
	private Thread thrd;
	private boolean testing = true;
	private JPanel centerPanel;
	private boolean active = true;
	private boolean isAdmin;

	public StatusPanel(UserConnectionData data, String name, boolean isAdmin){
		this.isAdmin = isAdmin;
		status = new EnvironmentStatus();
		this.envName = name;
		updateColor(Color.GRAY);
		validate = new EnvStatusValidation(data);
		timer = new TaskTimer(this, freqTimeout);
		thrd = new Thread(timer);
		initialize();
	}
	
	public void desactivatePanel(){
		active = false;
		setVisible(false);
	}
	
	public void setAdmin(boolean isAdmin){
		
	}
	
	/**
	 * Inicializa los componentes del panel
	 */
	private void initialize() {
		setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);
		
		labelName = new JLabel();
		labelName.setForeground(Color.black);
		labelName.setText(envName);
		if(isAdmin)
			labelName.setFont(new Font("Arial", Font.BOLD, 14));
		else
			labelName.setFont(new Font("Arial", Font.BOLD, 16));
		labelName.setAlignmentX(CENTER_ALIGNMENT);
		labelName.setAlignmentY(BOTTOM_ALIGNMENT);
		
		labelVersion = new JLabel();
		labelVersion.setForeground(Color.black);
		labelVersion.setAlignmentX(CENTER_ALIGNMENT);
		labelVersion.setAlignmentY(BOTTOM_ALIGNMENT);
		
		timerVersion = new JLabel();
		timerVersion.setForeground(Color.black);
		timerVersion.setText(freqTimeout + " min");
		timerVersion.setAlignmentX(RIGHT_ALIGNMENT);
		timerVersion.setAlignmentY(BOTTOM_ALIGNMENT);
		timerVersion.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				//En caso de botón izquierdo
				int mod = 0;
				if(e.getButton() == MouseEvent.BUTTON1){
					mod = getFrequenceTime(freqTimeout,true);
				}
				//En caso de botón derecho
				if(e.getButton() == MouseEvent.BUTTON3){
					mod = getFrequenceTime(freqTimeout,false);
				}
				freqTimeout = mod + freqTimeout;
				timerVersion.setText(Integer.toString(freqTimeout) + " min");
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		updateComponents();
		centerPanel.add(labelName);
		centerPanel.add(labelVersion);
//		centerPanel.setVisible(true);
		
		add(centerPanel,BorderLayout.CENTER);
		add(timerVersion,BorderLayout.EAST);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		setVisible(true);
		setEnabled(true);
	}
	
	private int getFrequenceTime(int time, boolean incr){
		//Al incrementar el tiempo
		int mod = 0;
		if(incr){
			if(time < 5){
				mod = 1;
			}else
				mod = 5;
		}else{//Al reducir el tiempo
			if(time == 1){
				mod = 0;
			}else if(time <= 5 ){
				mod = -1;
			}else if(time >= 5){
				mod = -5;
			}
		}
		System.out.println("Inc. " + mod);
		return mod;
	}

	/**
	 * Realiza la validación contra el entorno
	 */
	public void validateStatus() {
		if(!active)
			return;
		//Antes de validar cambia a gris para indicar que se está trabajando sobre el entorno
		updateColor(EnvironmentStatus.COLOR_STATUS_PDT);
		try {
			validate.validate();
			validate.logActivity();
			status = validate.getCurrentStatus();
			String version = "";
			String arqVersion = "";
			if(status.getCurrentStatus() != EnvironmentStatus.CURRENT_STATUS_KO){
				version = "v" + validate.getServerVersion();
				arqVersion = validate.getArqVersion();
			}
			if(arqVersion.isEmpty())
				this.serverVersion = version;
			else
				this.serverVersion = version + " - arq. " + arqVersion;
			updateComponents();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopValidation(){
		if(!active)
			return;
		testing = false;
		timerVersion.setText("- min");
		thrd.interrupt();
		timer = null;
		thrd = null;
		validate.stopValidation();
	}
	
	public void startValidation(){
		if(!active)
			return;
		testing = true;
		timer = new TaskTimer(this, freqTimeout);
		timer.setTime(freqTimeout);
		thrd = new Thread(timer);
		thrd.start();
		timerVersion.setText(Integer.toString(freqTimeout) + " min");
	}
	
	public boolean isTesting(){
		return testing;
	}

	/**
	 * Realiza una actualización
	 */
	private void updateComponents() {
		if(!active)
			return;
		updateColor(status.getStatusColor());
		labelVersion.setText(serverVersion);
		labelVersion.setVisible(!serverVersion.isEmpty());
		setBackground(color);
		if (status != null && status.getErrorMessage() != null
				&& !status.getErrorMessage().isEmpty()) {
			this.setToolTipText(status.getErrorMessage());
		} else {
			this.setToolTipText(null);
		}
		validate();
		repaint();
	}
	
	/**
	 * Actualiza el color de todos los componentes
	 * @param c
	 */
	private void updateColor(Color c){
		this.color = c;
		repaint();
	}
	
	public void setVisible(boolean visible){
//		System.out.println("Se cambia visibilidad a " + visible);
		super.setVisible(visible);
		labelName.setVisible(visible);
		labelVersion.setVisible(visible);
		timerVersion.setVisible(visible);
	}

	/**
	 * Degradado para el panel
	 */
//	@Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        int w = getWidth();
//        int h = getHeight();
//        Color color1 = color.brighter();
//        Color color2 = color.darker();
//        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
//        g2d.setPaint(gp);
//        g2d.fillRect(0, 0, w, h);
//    }
}
