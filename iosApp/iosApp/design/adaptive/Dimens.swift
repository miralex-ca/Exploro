

import SwiftUI


struct Dimens {
    struct Home {
        static let topPadding = adp(12)
        static let leadingPadding = adp(12, 28)
        static let cardWidth = adp(160, 200)
        static let imageHeight = adp(80, 100)
        static let cardSpacing = adp(8, 12)
        static let sectionHeaderFont = adp(20, 22)
    }
    
    struct Section {
        static let gridItemMinWidth = adp(140, 190)
        static let gridHPadding = adp(16, 50)
        static let gridVPadding = adp(16, 30)
        static let cardSpacing = adp(12, 16)
    }
    
    struct Favorites {
        static let topPadding = adp(16, 30)
        static let gridItemMinWidth = adp(160, 200)
        static let cardSpacing = adp(12, 18)
    }
    
    struct Details {
        static let contentVPadding = adp(16, 30)
        static let infoPadding = adp(0, 12)
    }
    
    struct Search {
        static let itemMaxWidth = adp(500, 600)
        static let searchFieldaxWidth = adph(600, 750, 900)
        static let searchFieldPaddings = adph(12, 24, 40)
    }
}




 
