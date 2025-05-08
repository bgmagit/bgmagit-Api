package agit.bgmagit.repository;

import agit.bgmagit.base.entity.AgitSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgitSettingRepository extends JpaRepository<AgitSetting, Long> {

}
