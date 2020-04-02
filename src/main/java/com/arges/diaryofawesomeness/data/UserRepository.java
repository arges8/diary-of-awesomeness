package com.arges.diaryofawesomeness.data;

import com.arges.diaryofawesomeness.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
