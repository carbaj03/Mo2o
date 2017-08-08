package com.mo2o.template.ui

import android.view.View
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.api.model.Repo
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class RepoViewHolder(view: View) : ViewHolder<Repo>(view) {
    var name = view.find<TextView>(R.id.tvName)
    var description = view.find<TextView>(R.id.tvDescription)
    var language = view.find<TextView>(R.id.tvLanguage)
    var forks = view.find<TextView>(R.id.tvForks)
    var stars = view.find<TextView>(R.id.tvStars)
    var updated = view.find<TextView>(R.id.tvUpdated)

    override fun bind(repo: Repo) {
        name.text = repo.name
        updated.text = """Updated on ${repo.updated.format()}"""
        description.text = repo.description
        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()
        language.text = repo.language
    }

    fun String.format() : String {
        var cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        cal.time = sdf.parse(this)
        return """${sdf.calendar.get(Calendar.DAY_OF_MONTH)} ${sdf.calendar.get(Calendar.MONTH)} """
    }
}
