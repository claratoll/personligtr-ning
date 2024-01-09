<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarViewModel.kt
package se.claratoll.personligtrning.ui.calendar

=======
package se.claratoll.personligtrning.ui.gallery
>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarViewModel.kt
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarViewModel.kt
=======
import com.google.firebase.Timestamp
>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarViewModel.kt

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarViewModel.kt
=======
import se.claratoll.personligtrning.ui.home.User
>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarViewModel.kt
import java.text.SimpleDateFormat
import java.util.*


class CalendarViewModel : ViewModel() {

    private var db : FirebaseFirestore = Firebase.firestore
    private lateinit var auth : FirebaseAuth
    private lateinit var currentUserName : String
    private lateinit var userDocumentId : String
    private var currentUser : String? = FirebaseAuth.getInstance().currentUser?.uid
    val listOfEvents = MutableLiveData<MutableList<Calendar>>()

    fun dateToString(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }

    fun getCalendarData(selectedDate: String) {
        auth = Firebase.auth
        Log.v("!!!", "selected date is $selectedDate")

        auth.currentUser?.let {
            db.collection("users").document(it.uid)
                .collection("calendar")
                .addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        val calendarArray = mutableListOf<Calendar>()
                        for (document in snapshot.documents) {

                            val timestamp = document["date"] as com.google.firebase.Timestamp
                            val date = timestamp.toDate()

<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarViewModel.kt
                            // val calendarDoc = document.toObject<Calendar>()
=======
                           // val calendarDoc = document.toObject<Calendar>()
>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarViewModel.kt
                            Log.v("!!!", "current document is $document")
                            // Jämför datumet från Firestore med det valda datumet
                            if (dateToString(date) == selectedDate) {
                                val calendarDoc = document.toObject<Calendar>()
                                if (calendarDoc != null) {
                                    calendarArray.add(calendarDoc)
                                } else {
                                    Log.v("!!!", "no info")
                                }
                            }
                        }
                        calendarArray.sortBy { it.date }
                        listOfEvents.value = calendarArray
                    }
                }
        }
    }

    fun updateEventDoneStatus(eventId: String, newDoneStatus: Boolean) {
        auth = Firebase.auth

        auth.currentUser?.let {
            db.collection("users").document(it.uid)
                .collection("calendar")
                .document(eventId)
                .update("done", newDoneStatus)
                .addOnSuccessListener {
                    Log.d("!!!", "Event done status updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("!!!", "Error updating event done status", e)
                }
        }
    }


<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarViewModel.kt


=======
    fun getUser(selectedDate: Timestamp) {
        db.collection("users").document(currentUser!!).get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                println("User: $user")
                currentUserName = user?.username.toString()
                userDocumentId = document.id


            }
            .addOnFailureListener { exception ->
                println(exception)
            }
    }

>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarViewModel.kt
}