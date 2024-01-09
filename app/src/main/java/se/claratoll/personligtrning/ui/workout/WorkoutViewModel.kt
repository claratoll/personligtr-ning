package se.claratoll.personligtrning.ui.workout

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import se.claratoll.personligtrning.ui.calendar.Calendar
import java.text.SimpleDateFormat
import java.util.*

class WorkoutViewModel : ViewModel() {

    private var db: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    val toastMessage = MutableLiveData<String>()


    private fun updateStepsOnFirebase(steps: Int, documentId: String){
        auth = Firebase.auth
        auth.currentUser?.let {
            db.collection("users").document(it.uid)
                .collection("calendar").document(documentId)
                .update("steps", steps)
                .addOnFailureListener { exception ->
                    toastMessage.value = "Kunde inte lägga till steg, vänligen försök igen"
                }
        }
    }

    private fun addStepsToFirebase(steps: Int, date: Date) {
        auth = Firebase.auth
        val title = "steps"

        auth.currentUser?.let {
            val newStepsCalendarObj = Calendar(title = title, date = date, steps = steps)

            db.collection("users").document(it.uid)
                .collection("calendar")
                .add(newStepsCalendarObj)
                .addOnFailureListener { exception ->
                    toastMessage.value = "Kunde inte lägga till steg, vänligen försök igen"
                }
        }
    }

    fun dateToString(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }

    fun getStepsCalendarData(steps: Int, date: Date) {
        auth = Firebase.auth

        auth.currentUser?.let {
            db.collection("users").document(it.uid)
                .collection("calendar").get()
                .addOnSuccessListener { snapshot ->
                    val selectedDateString = dateToString(date)
                    var foundMatchingDocument = false
                    snapshot?.documents?.forEach { document ->
                        val timestamp = document["date"] as com.google.firebase.Timestamp
                        val documentDate = timestamp.toDate()
                        val documentDateString = dateToString(documentDate)
                        if (documentDateString == selectedDateString) {
                            foundMatchingDocument = true
                            val documentId = document.id
                            updateStepsOnFirebase(steps, documentId)
                        }
                    }
                    if (!foundMatchingDocument) {
                        addStepsToFirebase(steps, date)
                    }
                }

        }
    }
}