package se.claratoll.personligtrning.ui.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
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

                            // val calendarDoc = document.toObject<Calendar>()
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




}