package com.mo2o.template

import android.view.LayoutInflater
import android.view.ViewGroup


infix fun ViewGroup.inflate(res: Int) = LayoutInflater.from(context).inflate(res, this, false)
