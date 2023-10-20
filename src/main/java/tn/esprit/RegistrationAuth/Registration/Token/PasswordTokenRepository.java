package tn.esprit.RegistrationAuth.Registration.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    Optional<ResetPasswordToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ResetPasswordToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
