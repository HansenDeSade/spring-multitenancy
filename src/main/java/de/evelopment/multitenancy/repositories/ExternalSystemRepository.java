package de.evelopment.multitenancy.repositories;

import de.evelopment.multitenancy.model.ExternalSystem;
import org.springframework.data.repository.CrudRepository;

public interface ExternalSystemRepository extends CrudRepository<ExternalSystem, Integer> {
}
