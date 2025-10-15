package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers;

public interface MapperI<DOMAIN, INFRA> {

    INFRA toInfra(DOMAIN domain);
    DOMAIN toDomain(INFRA infra);

}
