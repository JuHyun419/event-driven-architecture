package jh

interface MetadataPort {

    fun getCategoryNameByCategoryId(categoryId: Long): String

    fun getUserNameByUserId(userId: Long): String

    fun listFollowerIdsByUserId(userId: Long): List<Long>
}
