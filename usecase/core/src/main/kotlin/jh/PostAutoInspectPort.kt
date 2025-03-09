package jh

import jh.inspected.AutoInspectionResult
import jh.post.model.Post

interface PostAutoInspectPort {

    fun inspect(post: Post, categoryName: String): AutoInspectionResult
}
