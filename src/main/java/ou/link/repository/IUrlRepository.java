package ou.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ou.link.model.Url;

import java.util.Optional;

@Repository
public interface IUrlRepository extends JpaRepository<Url, Long> {
    boolean existsByUrlLong(String urlLong);
    Optional<Url> findByUrlShort(String urlShort);
    Optional<Url> findByUrlLong(String urlLong);
}
