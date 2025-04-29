package jh

import jh.inspected.InspectedPost

interface InspectedPostMessageProducePort {

    fun sendCreateMessage(inspectedPost: InspectedPost)

    fun sendUpdateMessage(inspectedPost: InspectedPost)

    fun sendDeleteMessage(postId: Long)
}
