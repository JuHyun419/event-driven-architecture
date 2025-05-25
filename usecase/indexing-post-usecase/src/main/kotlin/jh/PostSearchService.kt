package jh

import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostSearchService(
    private val postSearchPort: PostElasticsearchPort,
    private val postResolvingHelperUseCase: PostResolvingHelperUseCase,
) : PostSearchUseCase {

    override fun getSearchResultByKeyword(
        keyword: String,
        pageNumber: Int,
    ): List<ResolvedPost> {
        val postIds = postSearchPort.searchPostIdsByKeyword(keyword, pageNumber, 5)

        return postResolvingHelperUseCase.resolvePostsByIds(postIds)
    }
}
