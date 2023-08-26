package com.vimalcvs.bottalkai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(tableName = "messages")
data class MessageModel(
    @PrimaryKey(autoGenerate = false)
    var id: String = Date().time.toString(),
    var conversationId: String = "",
    var question: String = "",
    var answer: String = "",
    var createdAt: String = Calendar.getInstance().time.toString(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageModel

        if (id != other.id) return false
        if (conversationId != other.conversationId) return false
        if (question != other.question) return false
        if (answer != other.answer) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + conversationId.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + answer.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}