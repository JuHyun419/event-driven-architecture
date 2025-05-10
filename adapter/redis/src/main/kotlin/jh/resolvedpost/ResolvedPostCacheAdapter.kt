package jh.resolvedpost

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jh.ResolvedPostCachePort
import jh.post.model.ResolvedPost
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ResolvedPostCacheAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : ResolvedPostCachePort {

    override fun get(postId: Long): ResolvedPost? {
        val jsonString = redisTemplate.opsForValue().get(key(postId)) ?: return null

        try {
            return objectMapper.readValue(jsonString, ResolvedPost::class.java)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    override fun multiGet(postIds: List<Long>): List<ResolvedPost> {
        val jsonStrings = redisTemplate
            .opsForValue()
            .multiGet(postIds.map { key(it) })
            ?: return emptyList()

        return jsonStrings.filterNotNull().map { json -> objectMapper.readValue(json, ResolvedPost::class.java) }
    }

    override fun set(resolvedPost: ResolvedPost) {
        var jsonString: String

        try {
            jsonString = objectMapper.writeValueAsString(resolvedPost)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }

        redisTemplate.opsForValue().set(
            key(resolvedPost.id),
            jsonString,
            Duration.ofSeconds(EXPIRE_SECONDS)
        )
    }

    override fun delete(postId: Long) {
        redisTemplate.delete(key(postId))
    }

    private fun key(postId: Long) = "${KEY_PREFIX}$postId"

    companion object {
        private const val KEY_PREFIX = "resolved_post_v1:"
        private const val EXPIRE_SECONDS = 60L * 60L * 24 * 7L // 1주일
    }
}
