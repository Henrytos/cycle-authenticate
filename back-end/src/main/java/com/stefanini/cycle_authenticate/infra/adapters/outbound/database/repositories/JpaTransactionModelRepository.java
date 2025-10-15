package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaTransactionModelRepository extends JpaRepository<TransactionModel, UUID> {

    @Query(value = "SELECT * FROM transactions t WHERE t.sender_id = :senderId ORDER BY t.date_of_payment DESC", nativeQuery = true)
    List<TransactionModel> findAllBySenderId(@Param("senderId") UUID senderId);

    @Query(value = "SELECT * FROM transactions t WHERE t.sender_id = :senderId ORDER BY t.date_of_payment DESC LIMIT 8", nativeQuery = true)
    List<TransactionModel> findAllBySenderRecent(@Param("senderId") UUID senderId);
}
