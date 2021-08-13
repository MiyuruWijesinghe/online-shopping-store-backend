package com.spm.onlineshopping.util;

import java.util.List;

public class IdGenerator {
	
	public static int generateIDs(List<Integer> arrayList) {

		int id;
		int next = arrayList.size();
		next++;
		id = next;
		if (arrayList.contains(id)) {
			next++;
			id = next;
		}
		return id;
	}

}
