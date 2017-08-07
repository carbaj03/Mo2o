package com.mo2o.template.ui

import android.view.View
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.api.model.Repo
import org.jetbrains.anko.find

class RepoViewHolder(view: View) : ViewHolder<Repo>(view) {
    var name = view.find<TextView>(R.id.tvName)
    var lang = view.find<TextView>(R.id.tvLanguaje)
    var updated = view.find<TextView>(R.id.tvUpdated)

    override fun bind(exercise: Repo) {
        name.text = exercise.name
    }
}
