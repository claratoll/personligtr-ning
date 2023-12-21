package se.claratoll.personligtrning.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.claratoll.personligtrning.R
import se.claratoll.personligtrning.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewEvents: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendarView = root.findViewById(R.id.calendarView)
        recyclerViewEvents = root.findViewById(R.id.calendarRecyclerView)

        val events = listOf(
            Calendar("Händelse 1", "https://strongandhappy.se/", true),
            Calendar("Händelse 2", "",false),
        )

        recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewEvents.adapter = CalendarAdapter(events)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}