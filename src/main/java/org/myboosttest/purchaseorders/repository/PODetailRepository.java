package org.myboosttest.purchaseorders.repository;

import org.myboosttest.purchaseorders.entity.PODetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PODetailRepository extends JpaRepository<PODetail, Integer> {
}
