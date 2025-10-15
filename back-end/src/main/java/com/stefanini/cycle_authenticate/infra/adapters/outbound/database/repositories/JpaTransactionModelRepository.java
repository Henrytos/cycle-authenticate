package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaTransactionModelRepository extends JpaRepository<TransactionModel, UUID> {

    @Query("SELECT t FROM transactions t WHERE t.sender.id = :senderId")
    List<TransactionModel> findAllBySenderId(@Param("senderId") UUID senderId);

}
