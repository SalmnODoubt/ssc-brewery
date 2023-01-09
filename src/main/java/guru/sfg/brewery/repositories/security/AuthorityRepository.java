package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.domain.BeerInventory;
import guru.sfg.brewery.domain.security.Authority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 6/21/20.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
