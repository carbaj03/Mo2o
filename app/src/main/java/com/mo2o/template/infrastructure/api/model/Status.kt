package com.mo2o.template.infrastructure.api.model

sealed class Status

object SUCCESS : Status()
object ERROR : Status()
object LOADING : Status()
