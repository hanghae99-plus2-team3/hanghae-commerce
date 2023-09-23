package hanghae99.plus2.team3.hanghaeorder.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

/**
 * OrderJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {
}
