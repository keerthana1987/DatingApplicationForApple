package com.datingapp.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datingapp.dao.UserCODao;
import com.datingapp.model.UserCO;

@Component
public class UserHelper {

	@Autowired
	UserCODao userCODAOImpl;

	public String addUser(UserCO userCO) {
		try {
			if (null != userCO)
				userCODAOImpl.addUserCO(userCO);
		} catch (Exception e) {
			return "failed to save to DB due to following exception :" + e.getMessage();
		}
		return "success";
	}

	public List<UserCO> getMatchingProfileForUser(String userID, int fetchCount) {
		return userCODAOImpl.returnMatches(userID, fetchCount);
	}

	public void setUserCODAOImpl(UserCODao userCODAOImpl) {
		this.userCODAOImpl = userCODAOImpl;
	}

}
