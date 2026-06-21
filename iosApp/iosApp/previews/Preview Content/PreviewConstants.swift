import SwiftUI
import Shared

extension Country {
    static let france = Country(
        id: "FRA",
        name: "France",
        officialName: "French Republic",
        capital: "Paris",
        continent: "Europe",
        subregion: "Western Europe",
        flagPngUrl: "https://flags.restcountries.com/v5/w640/fr.png",
        flagAlt: "🇫🇷"
    )
    
    static let mockList: [Country] = [
        .france,
        Country(
            id: "DEU",
            name: "Germany",
            officialName: "Federal Republic of Germany",
            capital: "Berlin",
            continent: "Europe",
            subregion: "Western Europe",
            flagPngUrl: "https://flags.restcountries.com/v5/w640/de.png",
            flagAlt: "🇩🇪"
        ),
        Country(
            id: "ITA",
            name: "Italy",
            officialName: "Italian Republic",
            capital: "Rome",
            continent: "Europe",
            subregion: "Southern Europe",
            flagPngUrl: "https://flags.restcountries.com/v5/w640/it.png",
            flagAlt: "🇮🇹"
        )
    ]
}

extension CountryDetailsState {
    static let france = CountryDetailsState(
        id: "FRA",
        name: "France",
        officialName: "French Republic",
        flagUrl: "https://flags.restcountries.com/v5/w640/fr.png",
        flagAlt: "🇫🇷",
        coatOfArmsUrl: "https://mainfacts.com/media/images/coats_of_arms/fr.png",
        capital: "Paris",
        continent: "Europe",
        subregion: "Western Europe",
        languages: ["French"],
        area: 551695.0,
        population: 69081996,
        currency: "Euro (€)",
        timezones: ["UTC+01:00"],
        isFavorite: false,
        mapsUrl: "https://goo.gl/maps/g7QxxSFsWyTPKuzd7",
        wikiUrl: "https://en.wikipedia.org/wiki/France",
        capitalLat: 48.87,
        capitalLng: 2.33
    )
    
    static let empty = CountryDetailsState(
        id: "",
        name: "",
        officialName: "",
        flagUrl: "",
        flagAlt: "",
        coatOfArmsUrl: "",
        capital: "",
        continent: "",
        subregion: "",
        languages: [],
        area: 0.0,
        population: 0,
        currency: "",
        timezones: [],
        isFavorite: false,
        mapsUrl: "",
        wikiUrl: "",
        capitalLat: 0.0,
        capitalLng: 0.0
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

