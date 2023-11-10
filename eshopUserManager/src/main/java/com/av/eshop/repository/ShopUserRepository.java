package com.av.eshop.repository;

import com.av.eshop.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {

    Optional<ShopUser> findByEmail(String email);

}
