package com.arges.diaryofawesomeness.data;

import com.arges.diaryofawesomeness.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
