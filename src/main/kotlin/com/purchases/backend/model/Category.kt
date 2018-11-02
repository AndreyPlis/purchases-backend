package com.purchases.backend.model

import javax.persistence.*

@Entity
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        val level: Byte,


        @ManyToOne(cascade = [(CascadeType.ALL)])
        @JoinColumn(name = "parentCategory_id")
        val parentCategory: Category,

        @OneToMany(mappedBy = "parentCategory")
        val subCategories: Set<Category> = HashSet(),


        val image: ByteArray,

        @OneToMany(mappedBy = "product", cascade = [(CascadeType.ALL)])
        val products: Set<Product> = HashSet()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (name != other.name) return false
        if (level != other.level) return false
        if (parentCategory != other.parentCategory) return false
        if (subCategories != other.subCategories) return false
        if (!image.contentEquals(other.image)) return false
        if (products != other.products) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + level
        result = 31 * result + parentCategory.hashCode()
        result = 31 * result + subCategories.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + products.hashCode()
        return result
    }

}