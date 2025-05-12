package agit.bgmagit.repository;

import agit.bgmagit.base.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
