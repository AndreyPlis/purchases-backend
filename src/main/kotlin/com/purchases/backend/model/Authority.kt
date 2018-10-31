package com.purchases.backend.model

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AUTHORITY")
data class Authority(

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
        @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
        val id: Long,

        @Column(name = "NAME", length = 50)
        @NotNull
        @Enumerated(EnumType.STRING)
        val name: AuthorityName,

        @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
        val users: List<User>
)