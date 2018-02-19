package de.hansendesade.multitenancy.repositories;

import de.hansendesade.multitenancy.model.ExternalSystem;
import org.springframework.data.repository.CrudRepository;

public interface ExternalSystemRepository extends CrudRepository<ExternalSystem, Integer> {
}
