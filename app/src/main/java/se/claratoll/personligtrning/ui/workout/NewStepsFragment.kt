package se.claratoll.personligtrning.ui.workout

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import se.claratoll.personligtrning.R
import se.claratoll.personligtrning.databinding.FragmentCalendarBinding
import se.claratoll.personligtrning.databinding.FragmentNewStepsBinding
import se.claratoll.personligtrning.databinding.FragmentWorkoutBinding
import se.claratoll.personligtrning.ui.calendar.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewStepsFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var workoutVM: WorkoutViewModel

    private var _binding: FragmentNewStepsBinding? = null
    private val binding get() = _binding!!
    private var selectedDate: Date? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStepsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        workoutVM = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        binding.addStepsButton.setOnClickListener {
            showDatePicker()
        }

        binding.submitStepsButton.setOnClickListener {
            try {
            val enteredSteps = binding.editTextTextPersonName2.text.toString().toInt()
                if (selectedDate != null) {
                    workoutVM.getStepsCalendarData(enteredSteps, selectedDate!!)
                } else {
                    Toast.makeText(context, "Vänligen välj ett datum", Toast.LENGTH_SHORT).show()
                }
        } catch (e: java.lang.NumberFormatException ){
                Toast.makeText(context, "Ange ett giltigt antal steg", Toast.LENGTH_SHORT).show()
            }
        }

        workoutVM.toastMessage.observe(this, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
        return root
    }

    private fun showDatePicker() {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        val datePickerDialog = DatePickerDialog(requireContext(), { _, pickedYear, pickedMonth, pickedDay ->
            val calendar = Calendar.getInstance()
            calendar.set(pickedYear, pickedMonth, pickedDay)
            selectedDate = calendar.time

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate!!)
            // Optional: visa det valda datumet i en textvy eller liknande
            binding.textNewSteps.text = "Valt datum: $formattedDate"
        }, year, month, day)

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

}