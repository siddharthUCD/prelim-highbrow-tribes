package com.example.Tribes.Repo;

import com.example.Tribes.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {
}
