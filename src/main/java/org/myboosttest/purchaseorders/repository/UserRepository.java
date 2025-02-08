package org.myboosttest.purchaseorders.repository;

import org.myboosttest.purchaseorders.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}
