package se.claratoll.personligtrning.ui.gallery

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import se.claratoll.personligtrning.R
import java.net.MalformedURLException
import java.net.URL

class CalendarAdapter (private val events: List<Calendar>) : RecyclerView.Adapter<CalendarAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.recyclertextview)
        val switchView: Switch = itemView.findViewById(R.id.switch1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title

        holder.titleTextView.setOnClickListener {
            if (!event.link.isNullOrBlank()) {
                val returntrue = isValidUrl(event.link)
                if (returntrue) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    holder.titleTextView.context.startActivity(intent)
                } else {
                    Toast.makeText(
                        holder.titleTextView.context,
                        "Länken går inte att öppna",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        if (event.done != null) {
            holder.switchView.isChecked = event.done
            holder.switchView.visibility = View.VISIBLE
        } else {
            holder.switchView.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = events.size

    fun isValidUrl(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: MalformedURLException) {
            false
        }
    }
}