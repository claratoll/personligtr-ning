package se.claratoll.personligtrning.ui.home

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val documentId: String = "",
    var name : String,
    var email : String,
    var uid : String? = null,
    var username : String
)
