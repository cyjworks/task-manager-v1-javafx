package uni.usic.infrastructure.repository.users;

import uni.usic.domain.entity.users.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for user-related data operations such as save, find, update, and delete.
 */
public interface UserRepository {

    /**
     * Saves a new user to the data source.
     *
     * @param user the user to save
     * @return true if saved successfully, false otherwise
     */
    boolean save(User user);

    /**
     * Retrieves all users from the data source.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists by username.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Updates an existing user's information.
     *
     * @param user the user with updated information
     * @return true if updated successfully, false otherwise
     */
    boolean update(User user);

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteByUsername(String username);
}
