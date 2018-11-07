package com.purchases.backend.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        val price: Float,
        val priceBeginsOn: LocalDateTime?,
        val price_ends_on: LocalDateTime?,

        val price_was: Float?,
        val price_discount: Float?,
        val loyaltyExtraBonus: Float?,
        val isLoyaltyDiscount: Boolean,
        val isOwnProduction: Boolean,
        val isAdult: Boolean,
        val offerType: String,
        val price_mark: Float?,

        val image: ByteArray?,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "PRODUCT_CATEGORY", joinColumns = arrayOf(JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")), inverseJoinColumns = arrayOf(JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")))
        @JsonIgnore
        val categories: MutableList<Category>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (priceBeginsOn != other.priceBeginsOn) return false
        if (price_ends_on != other.price_ends_on) return false
        if (price_was != other.price_was) return false
        if (price_discount != other.price_discount) return false
        if (loyaltyExtraBonus != other.loyaltyExtraBonus) return false
        if (isLoyaltyDiscount != other.isLoyaltyDiscount) return false
        if (isOwnProduction != other.isOwnProduction) return false
        if (isAdult != other.isAdult) return false
        if (offerType != other.offerType) return false
        if (price_mark != other.price_mark) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + (priceBeginsOn?.hashCode() ?: 0)
        result = 31 * result + (price_ends_on?.hashCode() ?: 0)
        result = 31 * result + (price_was?.hashCode() ?: 0)
        result = 31 * result + (price_discount?.hashCode() ?: 0)
        result = 31 * result + (loyaltyExtraBonus?.hashCode() ?: 0)
        result = 31 * result + isLoyaltyDiscount.hashCode()
        result = 31 * result + isOwnProduction.hashCode()
        result = 31 * result + isAdult.hashCode()
        result = 31 * result + offerType.hashCode()
        result = 31 * result + (price_mark?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}