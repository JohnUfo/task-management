package uz.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.taskmanagementsystem.model.User;
import uz.taskmanagementsystem.record.UserRecord;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u.id FROM Users u where u.username = :username")
    Long getUserIdByUsername(@Param("username") String username);

//    @Query("SELECT new uz.taskmanagementsystem.record.UserRecord(u.id, u.fullName, u.username) " +
//            "FROM Users u WHERE u.username = :username")
//    Optional<UserRecord> findUserRecordByUsername(@Param("username") String username);
}
