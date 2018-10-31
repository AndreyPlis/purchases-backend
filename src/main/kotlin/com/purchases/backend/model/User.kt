package com.purchases.backend.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "USER")
data class User(

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
        val id: Long,

        @Column(name = "USERNAME", length = 50, unique = true)
        @Size(min = 4, max = 50)
        val username: String,

        @Column(name = "PASSWORD", length = 100)
        @Size(min = 4, max = 100)
        val password: String,

        @Column(name = "FIRSTNAME", length = 50)
        @Size(min = 4, max = 50)
        val firstname: String,

        @Column(name = "LASTNAME", length = 50)
        @Size(min = 4, max = 50)
        val lastname: String,

        @Column(name = "EMAIL", length = 50)
        @Size(min = 4, max = 50)
        val email: String,

        @Column(name = "ENABLED")
        val enabled: Boolean,

        @Column(name = "LASTPASSWORDRESETDATE")
        @Temporal(TemporalType.TIMESTAMP)
        val lastPasswordResetDate: Date,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "USER_AUTHORITY", joinColumns = arrayOf(JoinColumn(name = "USER_ID", referencedColumnName = "ID")), inverseJoinColumns = arrayOf(JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")))
        val authorities: List<Authority>
)