package agit.bgmagit.repository;

import agit.bgmagit.base.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
