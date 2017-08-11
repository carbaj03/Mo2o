package com.mo2o.template.infrastructure.ui.repository

import android.view.View
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.ui.common.ViewHolder
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class RepositoryViewHolder(view: View) : ViewHolder<Repo>(view) {
    var tvName = view.find<TextView>(R.id.tvNameRepo)
    var tvDescription = view.find<TextView>(R.id.tvDescription)
    var tvLanguage = view.find<TextView>(R.id.tvLanguage)
    var tvForks = view.find<TextView>(R.id.tvForks)
    var tvStars = view.find<TextView>(R.id.tvStars)
    var tvUpdated = view.find<TextView>(R.id.tvUpdated)

    override fun bind(repo: Repo) = with(repo) {
        tvName.text = name
        tvUpdated.text = """Updated on ${updated.format()}"""
        tvDescription.text = description
        tvStars.text = stars.toString()
        tvForks.text = forks.toString()
        tvLanguage.text = language
    }

    fun String.format(): String {
        val cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        cal.time = sdf.parse(this)
        return """${sdf.calendar.get(Calendar.DAY_OF_MONTH)} ${sdf.calendar.get(Calendar.MONTH)} """
    }
}
