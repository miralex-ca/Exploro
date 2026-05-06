package com.muralex.data.sources.runtimecache

import com.muralex.data.objects.CountryExtraDto

object CacheObjects {
    internal val countryExtraData: MutableMap<String, CountryExtraDto> by lazy { mutableMapOf() }
}