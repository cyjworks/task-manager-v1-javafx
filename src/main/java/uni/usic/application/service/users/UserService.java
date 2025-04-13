package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.util.Optional;

public class UserService implements UserOperations {
    private final UserFileRepository userRepository;

    public UserService(UserFileRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public User signUp(String username, String password, String fullName, String email) {
        if (userRepository.existsByUsername(username)) {
            return null; // duplicated user
        }
        User newUser = new User(username, password, fullName, email);
        boolean result = userRepository.save(newUser);

        if (result) {
            return newUser;
        } else {
            return null;
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.filter(user -> user.getPassword().equals(password)).isPresent();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }
}
