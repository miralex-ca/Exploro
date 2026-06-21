package com.exploramus.data.network.environment

class EnvironmentProvider {
    private var environment = NetworkEnvironments.PROD

    fun current(): NetworkEnvironment {
        return environment
    }

    fun setEnvironment(environment: NetworkEnvironment) {
        this.environment = environment
    }
}


