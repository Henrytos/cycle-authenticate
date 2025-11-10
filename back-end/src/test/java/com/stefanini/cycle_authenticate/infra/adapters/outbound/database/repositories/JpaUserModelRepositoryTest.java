package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.TestcontainersConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class JpaUserModelRepositoryTest {


}