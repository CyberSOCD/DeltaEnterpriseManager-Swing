package common;

import java.util.Comparator;

import uiProfiles.ProfileControl;

/**
 * 
 * Comparator para ordenar listas de UserConnectionData
 *
 */
public class UserConnectionDataComparator implements Comparator<UserConnectionData>{

	private ProfileControl profile;
	
	public UserConnectionDataComparator(ProfileControl profile){
		this.profile = profile;
	}
	
	@Override
	public int compare(UserConnectionData o1, UserConnectionData o2) {
		int compareResult = 0;
		if((o1.isProfile() && o2.isProfile()) || (o1.isProfile() && o2.isProfile()))
			compareResult = o1.getNum() < o2.getNum() ? -1:1;
		else if(o1.isProfile() && !o2.isProfile())
			compareResult = -1;
		else if(!o1.isProfile() && o2.isProfile())
			compareResult = 1;
		return compareResult;
	}
}
