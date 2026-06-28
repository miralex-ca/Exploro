import SwiftUI
import Shared

extension Country {
    static let france = Country(
        id: "FRA",
        iso2: "FR",
        name: "France",
        officialName: "French Republic",
        capital: "Paris",
        continent: "Europe",
        location: "Western Europe",
        flagImage: "https://flagcdn.com/w640/fr.png",
        flagEmoji: "🇫🇷"
    )
    
    static let mockList: [Country] = [
        .france,
        Country(
            id: "DEU",
            iso2: "DE",
            name: "Germany",
            officialName: "Federal Republic of Germany",
            capital: "Berlin",
            continent: "Europe",
            location: "Western Europe",
            flagImage: "https://flagcdn.com/w640/de.png",
            flagEmoji: "🇩🇪"
        ),
        Country(
            id: "ITA",
            iso2: "IT",
            name: "Italy",
            officialName: "Italian Republic",
            capital: "Rome",
            continent: "Europe",
            location: "Southern Europe",
            flagImage: "https://flagcdn.com/w640/it.png",
            flagEmoji: "🇮🇹"
        )
    ]
}

extension CountryDetailsState {
    static let france = CountryDetailsState(
        id: "FRA",
        name: "France",
        officialName: "French Republic",
        flagImage: "https://flagcdn.com/w640/fr.png",
        flagEmoji: "🇫🇷",
        coatOfArmsUrl: "https://mainfacts.com/media/images/coats_of_arms/fr.png",
        capital: "Paris",
        continent: "Europe",
        location: "Western Europe",
        languages: ["French"],
        area: 551695.0,
        population: 69081996,
        currency: "Euro (€)",
        timezones: ["UTC+01:00"],
        isFavorite: false,
        mapsUrl: "https://goo.gl/maps/g7QxxSFsWyTPKuzd7",
        wikiUrl: "https://en.wikipedia.org/wiki/France",
        latitude: 48.87,
        longitude: 2.33
    )
    
    static let empty = CountryDetailsState(
        id: "",
        name: "",
        officialName: "",
        flagImage: "",
        flagEmoji: "",
        coatOfArmsUrl: "",
        capital: "",
        continent: "",
        location: "",
        languages: [],
        area: 0.0,
        population: 0,
        currency: "",
        timezones: [],
        isFavorite: false,
        mapsUrl: "",
        wikiUrl: "",
        latitude: 0.0,
        longitude: 0.0
    )
}


extension AppLayout {
    static var previewPhone: AppLayout {
        let layout = AppLayout()
        layout.formFactor = FormFactor(
            widthType: .compact,
            heightType: .medium,
            orientation: .portrait,
            deviceType: .phone
        )
        return layout
    }
    
    static var previewPhoneLandscape: AppLayout {
        let layout = AppLayout()
        layout.formFactor = FormFactor(
            widthType: .medium,
            heightType: .compact,
            orientation: .landscape,
            deviceType: .phone
        )
        return layout
    }
    
    static var previewTablet: AppLayout {
        let layout = AppLayout()
        layout.formFactor = FormFactor(
            widthType: .expanded,
            heightType: .expanded,
            orientation: .portrait,
            deviceType: .tablet
        )
        return layout
    }
    
    static var previewTabletLandscape: AppLayout {
        let layout = AppLayout()
        layout.formFactor = FormFactor(
            widthType: .expanded,
            heightType: .expanded,
            orientation: .landscape,
            deviceType: .tablet
        )
        return layout
    }
}
