package com.example.Tribes;

import com.example.Tribes.Model.User;
import com.example.Tribes.Repo.Constants;
import com.example.Tribes.Repo.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.core.Interests;
import service.core.UserInfo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
public class TribesApplication {

	public static void main(String[] args) {
		Constants.configurableApplicationContext = SpringApplication.run(TribesApplication.class, args);
	}

	public static void setUserInfo(final UserInfo userInfo){
		UserRepo userRepo = Constants.configurableApplicationContext.getBean(UserRepo.class);
		User user = new User(userInfo.getName(),0,userInfo.getProgrammingLanguages(),userInfo.getGitHubId());
		userRepo.save(user);
	}

	public static UserInfo getUserInfo(final Long uniqueId){
		UserRepo userRepo = Constants.configurableApplicationContext.getBean(UserRepo.class);
		User user = userRepo.findById(uniqueId).get();
		Interests interests = new Interests();
		interests.setProgrammingLanguages(user.getProgrammingLanguage());
		return new UserInfo(user.getName(),user.getGitHubId(),interests);
	}
}
