package vista.ui.Tabs;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.ui.Profiles.ProfileControl;

@SuppressWarnings("serial")
public abstract class GenericControlTab extends JPanel {

	protected JPanel profilePanel;
	protected JPanel panelMinorista;
	protected JPanel panelMayorista;
	private JLabel labelMin;
	private JLabel labelAM;
	protected boolean isMinoristas;
	protected boolean isMayoristas;
	private ProfileControl profile;
	
	
	public GenericControlTab(boolean isAM,boolean isMIN,ProfileControl profile){
		isMinoristas = isMIN;
		isMayoristas = isAM;
		this.profile = profile;
		initialize();
	}
	
	private void initialize(){
		profilePanel = new JPanel();
		profilePanel.setLayout(new GridLayout(1,2));
		
		panelMinorista = new JPanel();
		panelMinorista.setBorder(BorderFactory.createRaisedBevelBorder());
		panelMayorista = new JPanel();
		panelMayorista.setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(profile.isAdmin()){
			panelMayorista.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {
					changeSelected((JPanel) e.getSource());
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			panelMinorista.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {
					changeSelected((JPanel) e.getSource());
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
		}
		
		if(isMinoristas)
			profilePanel.add(panelMinorista);
		if(isMayoristas || profile.isAdmin())
			profilePanel.add(panelMayorista);
		
		labelMin = new JLabel("MINORISTAS");
		labelAM = new JLabel("MAYORISTAS");
		panelMayorista.add(labelAM);
		panelMinorista.add(labelMin);
		
		labelMin.setAlignmentX(CENTER_ALIGNMENT);
		labelMin.setAlignmentY(CENTER_ALIGNMENT);
		labelAM.setAlignmentX(CENTER_ALIGNMENT);
		labelAM.setAlignmentY(CENTER_ALIGNMENT);
		if(isMinoristas)
			profilePanel.add(panelMinorista);
		if(isMayoristas || profile.isAdmin())
			profilePanel.add(panelMayorista);
		if(profile.isAdmin())
			setSelected(panelMinorista);
	}
	
	protected void setSelected(JPanel panel){
		panel.getBackground().darker();
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	protected void unSelected(JPanel panel){
		panel.getBackground().brighter();
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	public abstract void loadComponent();
	
	protected abstract void changeSelected(JPanel panel);
	
}
