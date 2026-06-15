
import SwiftUI

struct AdaptiveDeviceValue<T> {
    let phone: T
    let tablet: T

    init(_ phone: T, _ tablet: T? = nil) {
        self.phone = phone
        self.tablet = tablet ?? phone
    }

    func of(_ layout: AppLayout) -> T {
        layout.formFactor.isTablet ? tablet : phone
    }
}

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

extension AdaptiveHeightValue {
    func of(_ layout: AppLayout) -> T {
        value(for: layout.formFactor)
    }
}

struct AdaptiveHeightValue<T> {
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


func adp(_ phone: CGFloat, _ tablet: CGFloat? = nil) -> AdaptiveDeviceValue<CGFloat> {
    AdaptiveDeviceValue(phone, tablet)
}

func adpw(_ compact: CGFloat, _ medium: CGFloat? = nil, _ expanded: CGFloat? = nil) -> AdaptiveValue<CGFloat> {
    AdaptiveValue(compact: compact, medium: medium, expanded: expanded)
}

func adph(_ compact: CGFloat, _ medium: CGFloat? = nil, _ expanded: CGFloat? = nil) -> AdaptiveHeightValue<CGFloat> {
    AdaptiveHeightValue(compact: compact, medium: medium, expanded: expanded)
}
