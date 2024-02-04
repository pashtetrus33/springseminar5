package ru.gb.spring_seminar5.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.spring_seminar5.models.User;

/**
 * Репозиторий для сущности user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(@Param("email") String email);


}
