package com.mo2o.template.infrastructure.ui.content

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mo2o.template.R
import com.mo2o.template.infrastructure.api.model.File
import com.mo2o.template.infrastructure.api.model.Repo
import com.mo2o.template.infrastructure.ui.common.ViewHolder
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class ContentViewHolder(view: View) : ViewHolder<File>(view) {
    var tvName = view.find<TextView>(R.id.tvName)
    var tvType = view.find<ImageView>(R.id.tvType)
    var tvCommit = view.find<TextView>(R.id.tvCommit)
    var tvUpdated = view.find<TextView>(R.id.tvUpdated)

    override fun bind(repo: File) = with(repo) {
        tvName.text = name
        var image = 0;
        if(type.equals("file"))
            image = R.drawable.ic_file
        else if (type.equals("dir"))
            image = R.drawable.ic_folder_black_24dp

        tvType.setImageResource(image)
        tvCommit.text = content
        tvUpdated.text = name
    }
}
