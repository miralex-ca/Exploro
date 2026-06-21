

import SwiftUI


struct FormFactor {
    let widthType: SizeType
    let heightType: SizeType
    let orientation: ScreenOrientation
    let deviceType: DeviceType
    
    var isPhone: Bool { deviceType == .phone }
    var isTablet: Bool { deviceType == .tablet }
}

extension FormFactor {
    static func defaultValue() -> FormFactor {
        return FormFactor(
            widthType: .compact,
            heightType: .medium,
            orientation: .portrait,
            deviceType: .phone //deviceType
        )
    }
    
    static func from(
        horizontal: UserInterfaceSizeClass?,
        vertical: UserInterfaceSizeClass?,
        size: CGSize
    ) -> FormFactor {
        
        let deviceType: DeviceType = UIDevice.current.userInterfaceIdiom == .pad ? .tablet : .phone
        
        let widthType: SizeType = {
            if horizontal == .compact { return .compact }
            if horizontal == .regular && vertical == .regular { return .expanded }
            return .medium
        }()
        
        let heightType: SizeType = {
            if vertical == .compact { return .compact }
            if vertical == .regular && horizontal == .regular { return .expanded }
            return .medium
        }()

        let orientation: ScreenOrientation = size.width > size.height ? .landscape : .portrait

        return FormFactor(
            widthType: widthType,
            heightType: heightType,
            orientation: orientation,
            deviceType: deviceType
        )
    }
}


enum SizeType {
    case compact, medium, expanded
}

enum ScreenOrientation {
    case portrait, landscape
}

enum DeviceType {
    case phone, tablet
}


