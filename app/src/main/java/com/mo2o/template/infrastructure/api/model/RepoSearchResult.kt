package com.mo2o.template.infrastructure.api.model

data class RepoSearchResult(
        val query: String,
        val repoIds: List<Int>,
        val totalCount: Int,
        val next: Int
)
