package com.mo2o.template.api.model

sealed class Status

object SUCCESS : Status()
object ERROR : Status()
object LOADING : Status()
