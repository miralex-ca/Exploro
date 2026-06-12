

import SwiftUI

struct AdaptiveValue<T> {
    let compact: T
    let medium: T
    let expanded: T

    init(compact: T, medium: T? = nil, expanded: T? = nil) {
        self.compact = compact
        self.medium = medium ?? compact
        self.expanded = expanded ?? self.medium
    }

    func value(for formFactor: FormFactor) -> T {
        switch formFactor.widthType {
        case .compact: return compact
        case .medium: return medium
        case .expanded: return expanded
        }
    }
}

func adp(_ compact: CGFloat, _ medium: CGFloat? = nil, _ expanded: CGFloat? = nil) -> AdaptiveValue<CGFloat> {
    AdaptiveValue(compact: compact, medium: medium, expanded: expanded)
}

extension AdaptiveValue {
    func of(_ layout: AppLayout) -> T {
        value(for: layout.formFactor)
    }
}


struct Dimens {
    
    struct Home {
        static let topPadding = adp(12)
        static let leadingPadding = adp(12, 28)
        static let cardWidth = adp(160, 220)
        static let imageHeight = adp(80, 120)
        static let cardSpacing = adp(8, 12)
        static let sectionHeaderFont = adp(20, 22)
    }
    
    struct Favorites {
        static let gridItemMinWidth = adp(160, 200)
        static let cardSpacing = adp(12, 18)
    }
    
    struct Search {
        static let itemMaxWidth = AdaptiveValue<CGFloat>(compact: 600, medium: 700, expanded: 800)
    }
}




 
