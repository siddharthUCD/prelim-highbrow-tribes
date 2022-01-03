package com.example.Tribes;

import com.example.Tribes.Model.User;
import com.example.Tribes.Repo.Constants;
import com.example.Tribes.Repo.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.centralCore.Interests;
import service.centralCore.UserInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;


@SpringBootApplication
public class TribesApplication {

	public static void main(String[] args) {
		Constants.configurableApplicationContext = SpringApplication.run(TribesApplication.class, args);
	}

	public static void setUserInfo(final UserInfo userInfo){
		UserRepo userRepo = Constants.configurableApplicationContext.getBean(UserRepo.class);
		User user = new User(userInfo.getName(),userInfo.getTribeId(), userInfo.getInterests().getProgrammingLanguages().stream().collect(Collectors.joining(",")),userInfo.getGitHubId());
		userRepo.save(user);
	}

	public static UserInfo getUserInfo(final Long uniqueId){
		UserRepo userRepo = Constants.configurableApplicationContext.getBean(UserRepo.class);
		User user = userRepo.findById(uniqueId).get();
		Interests interests = new Interests();
		HashSet<String> programmingLanguages = new HashSet<>(Arrays.asList(user.getProgrammingLanguage().split(",")));

		interests.setProgrammingLanguages(programmingLanguages);
		return new UserInfo(user.getName(),user.getGitHubId(),uniqueId);
	}
}