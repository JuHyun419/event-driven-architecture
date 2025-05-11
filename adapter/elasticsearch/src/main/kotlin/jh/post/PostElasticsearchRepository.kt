package jh.post

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface PostElasticsearchRepository : ElasticsearchRepository<PostDocument, Long>
