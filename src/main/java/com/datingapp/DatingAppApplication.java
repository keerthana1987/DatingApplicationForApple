package com.datingapp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datingapp.helper.ObjectStore;
import com.datingapp.model.UserCO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DatingAppApplication {

	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("data.json");
			File file = new File(url.toURI());
			List<UserCO> users = Arrays.asList(mapper.readValue(file, UserCO[].class));
			ObjectStore.getInstance().setUserCOList(users);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		SpringApplication.run(DatingAppApplication.class, args);
	}

}
