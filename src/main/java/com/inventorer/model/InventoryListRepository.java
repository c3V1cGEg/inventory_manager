package com.inventorer.model;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryListRepository extends PagingAndSortingRepository<Inventory, Long> {

}