package com.datingapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datingapp.helper.UserHelper;
import com.datingapp.model.UserCO;

@Controller
public class UserController {
	
	@Autowired
	UserHelper userHelper;

	@PostMapping("/submit")
	public @ResponseBody String submitForm(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("userCO") UserCO usrCO){
		return userHelper.addUser(usrCO);
	}
	
	@GetMapping("/returnMatches")
	public @ResponseBody List<UserCO> getMatchingProfileforUserID(HttpServletRequest req, HttpServletResponse res, @RequestParam String userID, @RequestParam int fetchCount){
		return userHelper.getMatchingProfileForUser(userID, fetchCount);
	}
}
