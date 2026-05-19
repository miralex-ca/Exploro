package com.muralex.data.repository.utils


val repoDebugLogger = RepoDebugLogger()

class RepoDebugLogger () {
    fun log(message: String) {
        println(message)
    }
}