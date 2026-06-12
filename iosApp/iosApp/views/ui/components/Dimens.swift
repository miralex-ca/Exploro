

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

extension AdaptiveValue {
    func of(_ layout: AppLayout) -> T {
        value(for: layout.formFactor)
    }
}

struct Dimens {
    struct Home {
        static let cardHeight = AdaptiveValue<CGFloat>(compact: 120, medium: 140, expanded: 160)
        static let horizontalPadding = AdaptiveValue<CGFloat>(compact: 16, medium: 24, expanded: 36)
        static let cardSpacing = AdaptiveValue<CGFloat>(compact: 8, medium: 12)
    }
    
    struct Favorites {
        static let imageHeight = AdaptiveValue<CGFloat>(compact: 55, medium: 70)
        static let gridMinWidth = AdaptiveValue<CGFloat>(compact: 280, medium: 320)
    }
    
    struct Search {
        static let itemMaxWidth = AdaptiveValue<CGFloat>(compact: 600, medium: 700, expanded: 800)
    }
}
