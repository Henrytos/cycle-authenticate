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

    @Query(value = "SELECT * FROM transactions t WHERE t.sender_id = :senderId AND t.type_transaction = 'SPENT' AND EXTRACT(MONTH FROM t.date_of_payment) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM t.date_of_payment) = EXTRACT(YEAR FROM CURRENT_DATE)  ORDER BY t.value DESC LIMIT 3", nativeQuery = true)
    List<TransactionModel> findThreeLargest(@Param("senderId") UUID senderId);

}
