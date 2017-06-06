package common;

/**
 * 
 * Clase que maneja datos necesarios para realizar una conexión a un entorno
 *
 */
public class UserConnectionData {
	private String user;
	private String password;
	private String envName;
	private String envKey;
	private String dnsAccess;
	private String dbHost;
	private String dbUser;
	private String dbPassword;
	private int num;
	private boolean profile;
	
	public UserConnectionData(String user, String password, String envName,String dnsAccess, int num){
		this.user = user;
		this.password = password;
		this.dnsAccess = dnsAccess;
		this.setEnvName(envName);
	}
	public int getNum(){
		return num;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDnsAccess() {
		return dnsAccess;
	}
	public void setDnsAccess(String dnsAccess) {
		this.dnsAccess = dnsAccess;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getDbURL() {
		return dbHost;
	}
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getEnvKey() {
		return envKey;
	}
	public void setEnvKey(String envKey) {
		this.envKey = envKey;
	}
	public boolean isProfile() {
		return profile;
	}
	public void setProfile(boolean profile) {
		this.profile = profile;
	}
}
