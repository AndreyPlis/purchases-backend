package com.purchases.backend.model

import java.util.*
import javax.persistence.*

@Entity
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String,
        val level: Int,


        @ManyToOne(cascade = [(CascadeType.ALL)])
        @JoinColumn(name = "parent_id")
        val parentCategory: Category?,

        @OneToMany(mappedBy = "parentCategory", fetch = FetchType.EAGER)
        val subCategories: MutableSet<Category> = mutableSetOf(),


        val image: ByteArray?,

        @OneToMany(mappedBy = "category", cascade = [(CascadeType.ALL)])
        val products: MutableSet<Product> = mutableSetOf()
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
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (products != other.products) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + level
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Category(id=$id, name='$name', level=$level, subCategories=${subCategories.size}, image=${Arrays.toString(image)})"
    }
}