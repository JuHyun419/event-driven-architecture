package jh

import org.springframework.stereotype.Component

@Component
class MetadataAdapter(private val metadataClient: MetadataClient) : MetadataPort {

    override fun getCategoryNameByCategoryId(categoryId: Long): String {
        return metadataClient.getCategoryById(categoryId)?.name ?: ""
    }

    override fun getUserNameByUserId(userId: Long): String {
        return metadataClient.getUserById(userId)?.name ?: ""
    }

    override fun listFollowerIdsByUserId(userId: Long): List<Long> {
        return metadataClient.getFollowerIdsByUserId(userId) ?: emptyList()
    }
}
