package jh

interface PostDeleteUseCase {

    fun delete(request: DeleteRequest)

    class DeleteRequest(
        val postId: Long
    )
}
