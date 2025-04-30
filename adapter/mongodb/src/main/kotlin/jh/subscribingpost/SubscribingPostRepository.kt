package jh.subscribingpost

import org.springframework.data.mongodb.repository.MongoRepository

interface SubscribingPostRepository: MongoRepository<SubscribingPostDocument, String>, SubscribingPostCustomRepository
