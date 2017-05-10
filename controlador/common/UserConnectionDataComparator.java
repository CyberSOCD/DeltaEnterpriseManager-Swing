package common;

import java.util.Comparator;

/**
 * 
 * Comparator para ordenar listas de UserConnectionData
 *
 */
public class UserConnectionDataComparator implements Comparator<UserConnectionData>{

	@Override
	public int compare(UserConnectionData o1, UserConnectionData o2) {
		return o1.getNum() < o2.getNum() ? -1:1;
	}
}
