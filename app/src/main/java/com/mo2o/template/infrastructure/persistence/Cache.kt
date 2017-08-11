package com.mo2o.template.infrastructure.persistence

const val emptyValue : String = ""

interface Cache {
    fun <T> get(name: String, default: T): T
    fun <T> put(name: String, value: T)
}
