package com.mo2o.template

import java.io.Serializable
import java.util.*

sealed class Command : Serializable

data class Id(
        val value: String = UUID.randomUUID().toString()
) : Command()
