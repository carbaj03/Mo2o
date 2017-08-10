package com.mo2o.template.infrastructure.invoke

import com.mo2o.template.Future
import com.mo2o.template.GenericError
import kategory.Either

class ServiceExecutionFutureTask<I : Request, E : GenericError, R>(
        val interactorExecution: Logic<I, E, R>
) {
    fun init() = with(Future { callUseCases() }) { onComplete { renderFeedResult(it) } }

    private fun callUseCases() = with(interactorExecution) { service.execute(params) }

    private fun renderFeedResult(result: Either<E, R>) = when (result) {
        is Either.Right -> interactorExecution.success(result.b)
        is Either.Left -> interactorExecution.error(result.a)
    }
}