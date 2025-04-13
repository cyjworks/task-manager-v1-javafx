package uni.usic.infrastructure.repository.users;

import uni.usic.domain.entity.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles file-based storage and retrieval of user data.
 */
public class UserFileRepository implements UserRepository {
    private final String filePath;

    /**
     * Constructs a repository with the given file path.
     *
     * @param filePath the path to the user data file
     */
    public UserFileRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves a user to the file.
     *
     * @param user the user to save
     * @return true if saved successfully, false otherwise
     */
    @Override
    public boolean save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getFullName() + "," + user.getEmail());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads all users from the file.
     *
     * @return list of users
     */
    @Override
    public List<User> findAll() {
        try {
            return loadUserListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Finds a user by username.
     *
     * @param username the username to search
     * @return optional containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return loadUserListFromFile().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Checks if a username exists in the file.
     *
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        try {
            return loadUserListFromFile().stream()
                    .anyMatch(u -> u.getUsername().equals(username));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a user's data in the file.
     *
     * @param updatedUser the updated user
     * @return true if updated successfully, false otherwise
     */
    @Override
    public boolean update(User updatedUser) {
        try {
            List<User> users = loadUserListFromFile();
            boolean found = false;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (User user : users) {
                    if (user.getUsername().equals(updatedUser.getUsername())) {
                        writer.write(toCsv(updatedUser));
                        found = true;
                    } else {
                        writer.write(toCsv(user));
                    }
                    writer.newLine();
                }
            }

            return found;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username to delete
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean deleteByUsername(String username) {
        try {
            List<User> users = loadUserListFromFile();
            List<User> filtered = users.stream()
                    .filter(u -> !u.getUsername().equals(username))
                    .collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (User user : filtered) {
                    writer.write(toCsv(user));
                    writer.newLine();
                }
            }

            return users.size() != filtered.size();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts a User to a CSV line.
     *
     * @param user the user to convert
     * @return a CSV formatted string
     */
    private String toCsv(User user) {
        return user.getUsername() + "," + user.getPassword() + "," + user.getFullName() + "," + user.getEmail();
    }

    /**
     * Loads the list of users from the file.
     *
     * @return list of users
     * @throws IOException if reading fails
     */
    public List<User> loadUserListFromFile() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String username = parts.length > 0 ? parts[0] : null;
                String password = parts.length > 1 ? parts[1] : null;
                String fullName = parts.length > 2 ? parts[2] : null;
                String email    = parts.length > 3 ? parts[3] : null;

                if (username == null || password == null) continue;

                users.add(new User(username, password, fullName, email));
            }
        }
        return users;
    }
}
