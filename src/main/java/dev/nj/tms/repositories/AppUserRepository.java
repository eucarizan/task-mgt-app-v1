package dev.nj.tms.repositories;

import dev.nj.tms.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    boolean existsByEmail(String email);
}
