package org.myboosttest.purchaseorders.repository;

import org.myboosttest.purchaseorders.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
