package org.myboosttest.purchaseorders.repository;

import org.myboosttest.purchaseorders.entity.POHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POHeaderRepository extends JpaRepository<POHeader, Integer> {
}
