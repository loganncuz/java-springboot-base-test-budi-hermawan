package act.base.test.budi.hermawan.repo;

import act.base.test.budi.hermawan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findBySsn(String ssn);
}
