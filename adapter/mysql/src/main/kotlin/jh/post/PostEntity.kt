package jh.post

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "post")
class PostEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String,
    val userId: Long,
    val categoryId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime? = null,
)
