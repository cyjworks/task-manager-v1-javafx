package uni.usic.infrastructure.repository.users;

import uni.usic.domain.entity.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    boolean update(User user);
    boolean deleteByUsername(String username);
}
