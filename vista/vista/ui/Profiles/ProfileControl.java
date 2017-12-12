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
	public static enum TipoSistema { MAYORISTA, MINORISTA, AMBOS};
	private static String AM_Mark = " AM";
	
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
	 * Obtiene la lista correspondiente al perfil y el sistema seleccionado 
	 * @param fullList
	 * @param system
	 * @return
	 */
	public ArrayList<UserConnectionData> getSystemProfileList(ArrayList<UserConnectionData> fullList, TipoSistema system){
		ArrayList<UserConnectionData> result = new ArrayList<UserConnectionData>();
		for(UserConnectionData usr:fullList){
			if(checkEnvironment(usr) && checkSystem(usr, system)){
				result.add(usr);
			}
		}
		return result;
	}
	
	/**
	 * Obtiene la lista correspondiente al perfil seleccionado 
	 * @param fullList
	 * @param system
	 * @return
	 */
	public ArrayList<UserConnectionData> getProfileList(ArrayList<UserConnectionData> fullList){
		ArrayList<UserConnectionData> result = new ArrayList<UserConnectionData>();
		for(UserConnectionData usr:fullList){
			if(checkEnvironment(usr)){
				result.add(usr);
			}
		}
		return result;
	}
	
	public boolean checkSystem(UserConnectionData user, TipoSistema system){
		boolean condition = true;
		if(system.equals(TipoSistema.MAYORISTA))
			condition = user.getEnvName().contains(AM_Mark);
		else if(system.equals(TipoSistema.MINORISTA))
			condition = !user.getEnvName().contains(AM_Mark);
		else
			condition = true;
		return condition;
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
