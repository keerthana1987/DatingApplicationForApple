package com.datingapp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.datingapp.model.UserCO;

@Component
public interface UserCODao {

	public void addUserCO(UserCO userCO);

	public List<UserCO> returnMatches(String userID, int fetchCount);

}
