package de.hansendesade.multitenancy.repositories;

import de.hansendesade.multitenancy.model.TenantInfo;
import org.springframework.data.repository.CrudRepository;

public interface TenantInfoRepository extends CrudRepository<TenantInfo, Integer> {
}
