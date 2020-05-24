package com.datingapp.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.datingapp.dao.UserCODao;
import com.datingapp.helper.ObjectStore;
import com.datingapp.model.UserCO;

@Component
public class UserCODaoImpl implements UserCODao {

	Map<String, UserCO> userMap = ObjectStore.getInstance().getUserMap();
	List<UserCO> userCOList = ObjectStore.getInstance().getUserCOList();

	public UserCODaoImpl() {
	}

	@Override
	public void addUserCO(UserCO userCO) {

		if (userMap.containsKey(userCO.getUserID())) {
			throw new RuntimeException("User with id : " + userCO.getUserID() + " already exists.");
		}
		if (userCO.getInterests() != null) {
			Collections.sort(userCO.getInterests());
		}
		userCOList.add(userCO);
		userMap.put(userCO.getUserID(), userCO);
	}

	@Override
	public List<UserCO> returnMatches(String userID, int fetchCount) {
		List<String> userIdList = new ArrayList<>();
		UserCO me = userMap.get(userID);

		if (me == null) {
			throw new RuntimeException("User with id " + userID + "doesn't exists");
		}

		Stream<UserCO> genderFilter = userCOList.stream().filter(userCO -> {
			return !me.getGender().equals(userCO.getGender());
		});

		List<UserCO> genderList = genderFilter.collect(Collectors.toList());

		Stream<UserCO> ageFitler = genderList.stream().filter(userCo -> {
			return (userCo.getAge() + 2 >= me.getAge() || userCo.getAge() - 2 <= me.getAge());
		});

		List<UserCO> ageList = ageFitler.collect(Collectors.toList());

		if (ageList.size() > 0) {
			Stream<UserCO> interestsFilter = ageList.stream().filter(userCo -> {
				for (String inr : userCo.getInterests()) {
					if (me.getInterests().contains(inr)) {
						return true;
					}
				}
				return false;
			});
			List<UserCO> interestsList = interestsFilter.collect(Collectors.toList());
			getMatchedProfiles(interestsList, fetchCount, me, userIdList);
		}

		for (UserCO co : ageList) {
			if (!userIdList.contains(co.getUserID())) {
				userIdList.add(co.getUserID());
			}
		}

		for (UserCO co : genderList) {
			if (!userIdList.contains(co.getUserID())) {
				userIdList.add(co.getUserID());
			}
		}

		List<UserCO> lst = new ArrayList<>();
		if (userIdList.size() < fetchCount) {
			for (String userId : userIdList) {
				lst.add(userMap.get(userId));
			}
		}

		return lst;
	}

	private List<String> getMatchedProfiles(List<UserCO> filter, int fetchCount, UserCO me, List<String> finalList) {
		Map<String, Integer> matchingRecords = new HashMap<>();

		filter.forEach(userCo -> {
			int count = 0;
			for (String inr : userCo.getInterests()) {
				if (me.getInterests().contains(inr)) {
					count++;
				}
			}
			matchingRecords.put(userCo.getUserID(), count);
		});

		for (String uid : matchingRecords.keySet()) {
			UserCO userCO = userMap.get(uid);

			if (finalList.size() <= fetchCount) {
				finalList.add(userCO.getUserID());
			}
		}
		return finalList;
	}
}
