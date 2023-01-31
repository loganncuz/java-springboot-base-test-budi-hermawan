package act.base.test.budi.hermawan.repo;

import act.base.test.budi.hermawan.entity.User;
import act.base.test.budi.hermawan.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Integer> {
    Set<UserSetting> findByUserId(User user);
}
