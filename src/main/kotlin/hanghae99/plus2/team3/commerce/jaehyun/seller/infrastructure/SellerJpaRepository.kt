package hanghae99.plus2.team3.commerce.jaehyun.seller.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

/**
 * SellerJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */
interface SellerJpaRepository :JpaRepository<SellerEntity, Long>
