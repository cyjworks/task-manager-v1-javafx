package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.util.Optional;

/**
 * Provides user-related logic such as registration, login, and lookup.
 */
public class UserService implements UserOperations {
    private final UserFileRepository userRepository;

    /**
     * Constructs a UserService with the given user repository.
     *
     * @param repository the user repository
     */
    public UserService(UserFileRepository repository) {
        this.userRepository = repository;
    }

    /**
     * Registers a new user.
     *
     * @param username the username
     * @param password the password
     * @param fullName the full name
     * @param email the email address
     * @return the created User object, or null if registration failed
     */
    @Override
    public User signUp(String username, String password, String fullName, String email) {
        if (userRepository.existsByUsername(username)) {
            return null; // duplicated user
        }
        User newUser = new User(username, password, fullName, email);
        boolean result = userRepository.save(newUser);

        return result ? newUser : null;
    }

    /**
     * Checks if the provided username and password are valid.
     *
     * @param username the username
     * @param password the password
     * @return true if login is successful, false otherwise
     */
    @Override
    public boolean signIn(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.filter(user -> user.getPassword().equals(password)).isPresent();
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search
     * @return the User object if found, null otherwise
     */
    @Override
    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }
}
