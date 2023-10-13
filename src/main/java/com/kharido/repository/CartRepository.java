package com.kharido.repository;

import com.kharido.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT DISTINCT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.user.id = :userId")
    public Cart findByUserId(@Param("userId") Long userId);

}
