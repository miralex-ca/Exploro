package com.muralex.data.sources.runtimecache

import com.muralex.models.CountryExtraInfo


object CacheObjects {
    internal val countryExtraData: MutableMap<String, CountryExtraInfo> by lazy { mutableMapOf() }
}