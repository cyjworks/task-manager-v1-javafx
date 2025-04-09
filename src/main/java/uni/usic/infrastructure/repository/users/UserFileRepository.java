package uni.usic.infrastructure.repository.users;

import uni.usic.domain.entity.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFileRepository implements UserRepository {
    private final String filePath;

    public UserFileRepository(String filePath) {
        this.filePath = filePath;
    }

    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        }
    }



    public boolean existsByUsername(String username) throws IOException {
        return loadUserListFromFile().stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return loadUserListFromFile().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty(); // or fallback like Optional.of(new GuestUser())
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean deleteByUsername(String username) {
        return false;
    }

    public List<User> loadUserListFromFile() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
        return users;
    }
}
