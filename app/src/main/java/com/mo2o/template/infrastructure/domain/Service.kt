package com.mo2o.template.infrastructure.domain

import com.mo2o.template.GenericError
import com.mo2o.template.infrastructure.invoke.Request
import kategory.Either
import kategory.Option

typealias UseCase<I, R> = Service<I, R, GenericError>

interface Service<in I : Request, out R, out E : GenericError> {
    fun execute(input: Option<I>): Either<E, R>
}