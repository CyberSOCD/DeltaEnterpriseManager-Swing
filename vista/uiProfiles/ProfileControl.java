package uiProfiles;

import java.util.ArrayList;

import common.UserConnectionData;

/**
 * 
 * Contiene toda la información necesaria para trabajar
 * con la aplicacion
 *
 */
public class ProfileControl {
	private ArrayList<String> list;
	private ArrayList<UserConnectionData> listEnv;
	private String selectedProfile;
	private long healthValidationTimeout; //miliseconds
	
	public ProfileControl(String profile, ArrayList<UserConnectionData> listEnv){
		selectedProfile = profile;
		this.listEnv = listEnv;
		list = getEnvNames(profile);
		setActiveEnvs();
	}
	
	/**
	 * Devuelve si el entorno pasado debe estar activo
	 * @param keyEnv
	 * @return
	 */
	public boolean checkEnvironment(UserConnectionData keyEnv){
		if(list.contains(keyEnv.getEnvKey()))
			return true;
		return false;
	}
	
	/**
	 * Marca como activo/inactivo cada {@link UserConnectionData}
	 */
	private void setActiveEnvs(){
		for(UserConnectionData u:listEnv){
			if(!list.contains(u.getEnvKey()))
				u.setProfile(false);
			else
				u.setProfile(true);
		}
	}
	
	public String getSelectedProfile() {
		return selectedProfile;
	}
	
	
	
	private ArrayList<String> getEnvNames(String profile){
		ArrayList<String> list;
		switch(profile){
		case ProfileConstants.certi:
			list = ProfileConstants.CERTIFICACION_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.CERTIFICACION_HEALTH_TMEOUT;
			break;
		case ProfileConstants.gestion:
			list = ProfileConstants.GESTION_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.GESTION_HEALTH_TMEOUT;
			break;
		case ProfileConstants.desa:
			list = ProfileConstants.DESARROLLO_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.DESARROLLO_HEALTH_TMEOUT;
			break;
		default:
			list = ProfileConstants.GESTION_ENVIRONMENT_KEY;
		}
		return list;
	}

	public long getHealthValidationTimeout() {
		return healthValidationTimeout;
	}
}
