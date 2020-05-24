package com.datingapp.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datingapp.model.UserCO;

public class ObjectStore {

	List<UserCO> userCOList = new ArrayList<UserCO>();
	Map<String, UserCO> userMap = new HashMap<>();

	private static final ObjectStore _instance = new ObjectStore();

	private ObjectStore() {
	}

	public static ObjectStore getInstance() {
		return _instance;
	}

	public List<UserCO> getUserCOList() {
		return userCOList;
	}

	public void setUserCOList(List<UserCO> userCOList) {
		this.userCOList = userCOList;
		for (UserCO co : userCOList) {
			userMap.put(co.getUserID(), co);
		}
	}
	
	public Map<String, UserCO> getUserMap() {
		return userMap;
	}
}
