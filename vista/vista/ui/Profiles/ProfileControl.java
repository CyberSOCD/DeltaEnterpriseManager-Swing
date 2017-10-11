package vista.ui.Profiles;

import java.util.ArrayList;

import controlador.common.UserConnectionData;

/**
 * 
 * Contiene toda la información necesaria para trabajar
 * con la aplicacion
 *
 */
public final class ProfileControl {
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
	
	public boolean isAdmin(){
		return selectedProfile.equals(ProfileConstants.ADMINISTRADOR);
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
	
	public String getSelectedProfile() {
		return selectedProfile;
	}
	
	public long getHealthValidationTimeout() {
		return healthValidationTimeout;
	}
	
	public int getMinFreqValidationProfile(){
		int value = 5;
		switch(selectedProfile){
			case ProfileConstants.CERTIFICACION_MIN:
				value = 5;
				break;
			case ProfileConstants.GESTION_MIN:
				value = 5;
				break;
			case ProfileConstants.GESTION_MAY:
				value = 5;
				break;
			case ProfileConstants.ADMINISTRADOR:
				value = 1;
				break;
			default:
				value = 5;
		}
		return value;
	}
	
	private ArrayList<String> getEnvNames(String profile){
		ArrayList<String> list;
//		System.out.println(profile);
		switch(profile){
		case ProfileConstants.CERTIFICACION_MIN:
			list = ProfileConstants.CERTIFICACION_MIN_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.CERTIFICACION_MIN_HEALTH_TMEOUT;
			break;
		case ProfileConstants.GESTION_MIN:
			list = ProfileConstants.GESTION_MIN_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.GESTION_MIN_HEALTH_TMEOUT;
			break;
		case ProfileConstants.GESTION_MAY:
			list = ProfileConstants.GESTION_MAY_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.GESTION_MAY_HEALTH_TMEOUT;
			break;
		case ProfileConstants.ADMINISTRADOR:
			list = ProfileConstants.ADMIN_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.ADMIN_HEALTH_TMEOUT;
			break;
		case ProfileConstants.DESA:
			list = ProfileConstants.DESA_ENVIRONMENT_KEY;
			healthValidationTimeout = ProfileConstants.ADMIN_HEALTH_TMEOUT;
			break;
		default:
			list = ProfileConstants.GESTION_MIN_ENVIRONMENT_KEY;
		}
		return list;
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
}
