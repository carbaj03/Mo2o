package com.mo2o.template.ui

import android.view.View
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.api.model.Repo
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class RepoViewHolder(view: View) : ViewHolder<Repo>(view) {
    var tvName = view.find<TextView>(R.id.tvName)
    var tvDescription = view.find<TextView>(R.id.tvDescription)
    var tvLanguage = view.find<TextView>(R.id.tvLanguage)
    var tvForks = view.find<TextView>(R.id.tvForks)
    var tvStars = view.find<TextView>(R.id.tvStars)
    var tvUpdated = view.find<TextView>(R.id.tvUpdated)

    override fun bind(repo: Repo) {
        tvName.text = repo.name
        tvUpdated.text = """Updated on ${repo.updated.format()}"""
        tvDescription.text = repo.description
        tvStars.text = repo.stars.toString()
        tvForks.text = repo.forks.toString()
        tvLanguage.text = repo.language
    }

    fun String.format() : String {
        val cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        cal.time = sdf.parse(this)
        return """${sdf.calendar.get(Calendar.DAY_OF_MONTH)} ${sdf.calendar.get(Calendar.MONTH)} """
    }
}
