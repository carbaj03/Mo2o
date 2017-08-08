package com.mo2o.template

import kategory.Either


typealias Result<T> = Either<GenericError, T>

sealed class GenericError {
    object NetworkError : GenericError()
    object ServerError : GenericError()
}