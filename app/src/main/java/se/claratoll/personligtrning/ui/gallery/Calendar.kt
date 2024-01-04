package se.claratoll.personligtrning.ui.gallery

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.auth.User
import java.sql.Timestamp
import java.util.Date

data class Calendar(
    @DocumentId val documentId: String = "",
    val title: String = "",
    val link: String? = null,
    val done: Boolean? = null,
    val date: Date = Date(),
    val user: String = ""
)