package jh.post

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime

@Document(indexName = "post-1")
class PostDocument(
    @Id
    val id: Long,

    private val title: String,
    private val content: String,
    private val categoryName: String,
    private val tags: MutableList<String>,
    @org.springframework.data.elasticsearch.annotations.Field(
        type = org.springframework.data.elasticsearch.annotations.FieldType.Date,
        format = [org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second_millis]
    )
    private val indexedAt: LocalDateTime,
) {
}
