package dev.nj.tms.repositories;

import dev.nj.tms.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);
}
