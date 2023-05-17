package com.inventorer.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface InventoryListRepository extends PagingAndSortingRepository<Inventory, Long> {

  @Query(
      value = "SELECT * FROM inventory WHERE short_description LIKE %:phrase% AND description LIKE %:phrase%",
      countQuery = "SELECT COUNT(*) FROM inventory WHERE short_description LIKE %:phrase% AND description LIKE %:phrase%",
      nativeQuery = true
  )
  Page<Inventory> searchInventory(@Param("phrase") String phrase, Pageable pageable);

  @Query(
      value = "SELECT * FROM inventory",
      countQuery = "SELECT COUNT(*) FROM inventory",
      nativeQuery = true
  )
  Page<Inventory> getAll(Pageable pageable);
}