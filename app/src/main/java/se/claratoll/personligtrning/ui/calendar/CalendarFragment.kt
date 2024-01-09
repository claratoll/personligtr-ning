package se.claratoll.personligtrning.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.claratoll.personligtrning.R
import se.claratoll.personligtrning.databinding.FragmentCalendarBinding

import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarView: CalendarView

    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarVM: CalendarViewModel
<<<<<<< HEAD:app/src/main/java/se/claratoll/personligtrning/ui/calendar/CalendarFragment.kt
=======

>>>>>>> fdff5efc1d8d882d72d97c13a903118e44187488:app/src/main/java/se/claratoll/personligtrning/ui/gallery/CalendarFragment.kt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendarVM = ViewModelProvider(this)[CalendarViewModel::class.java]

        calendarView = root.findViewById(R.id.calendarView)
        recyclerViewEvents = root.findViewById(R.id.calendarRecyclerView)
        recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())

        calendarAdapter = CalendarAdapter(emptyList(), calendarVM)
        recyclerViewEvents.adapter = calendarAdapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
            Log.v("!!!", "datumet vald dag är $selectedDate")
            calendarVM.getCalendarData(selectedDate)
        }

        calendarVM.listOfEvents.observe(viewLifecycleOwner) { events ->
            calendarAdapter.events = events
            calendarAdapter.notifyDataSetChanged()
            Log.v("!!!", "event size " + events.size.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}