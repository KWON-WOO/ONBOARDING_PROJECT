package com.onboarding_project.domain.order;

import com.onboarding_project.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"menu", "user"})
    List<Order> findAllByUserAndIsDeletedFalseOrderByIdDesc(User user);

    @EntityGraph(attributePaths = {"menu", "user"})
    List<Order> findAllByMenuUserAndIsDeletedFalseOrderByIdDesc(User owner);

    // 판매자 확인용 단건 조회
    @EntityGraph(attributePaths = {"user", "menu", "menu.user"})
    Optional<Order> findByIdAndIsDeletedFalse(Long id);
}
