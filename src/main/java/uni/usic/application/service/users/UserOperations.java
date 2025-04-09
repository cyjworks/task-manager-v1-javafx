package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;

public interface UserOperations {
    User signUp(String username, String password, String fullName, String email);
    boolean signIn(String username, String password);
    User findByUsername(String username);
}
