package org.housemart.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.Entity;

public class CommonUtils {
	
	/**
	 * 容器排重
	 * @param list
	 * @return
	 */
	public static <T> List<T> duplicateList(List<T> list) {

		if (CollectionUtils.isEmpty(list)) {
			return list;
		}

		Set<T> uniqueSet = new TreeSet<T>(new Comparator<T>() {
			public int compare(T o1, T o2) {
				return ((Entity)o1).getId() - ((Entity)o2).getId();
			}
		});
		
		uniqueSet.addAll(list);	
		return new ArrayList<T>(uniqueSet);
	}

}
