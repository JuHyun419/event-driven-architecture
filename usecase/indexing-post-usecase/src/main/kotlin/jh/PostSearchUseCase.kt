package jh

import jh.post.model.ResolvedPost

interface PostSearchUseCase {

    fun getSearchResultByKeyword(keyword: String, pageNumber: Int): List<ResolvedPost>
}
