package uni.usic.infrastructure.repository.users;

import uni.usic.domain.entity.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserFileRepository implements UserRepository {
    private final String filePath;

    public UserFileRepository(String filePath) {
        this.filePath = filePath;
    }

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

    @Override
    public List<User> findAll() {
        try {
            return loadUserListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

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

    private String toCsv(User user) {
        return user.getUsername() + "," + user.getPassword() + "," + user.getFullName() + "," + user.getEmail();
    }

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
