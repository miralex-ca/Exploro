
import SwiftUI
import Shared

enum WidthType {
    case compact, medium, expanded
}

enum ScreenOrientation {
    case portrait, landscape
}

struct FormFactor {
    let widthType: WidthType
    let orientation: ScreenOrientation
}

@Observable
class AppLayout {
    var formFactor: FormFactor = FormFactor(widthType: .compact, orientation: .portrait)
    
    var useBottomBar: Bool { formFactor.widthType == .compact }
    var useTopbar: Bool { isTablet && !useDrawer }
    
    var useDrawer: Bool = false
    
 
    var isPhone: Bool { formFactor.widthType == .compact }
    var isTablet: Bool { formFactor.widthType != .compact }
    var isLandscape: Bool { formFactor.orientation == .landscape }
    
    var hasSpaceForDrawer: Bool { useDrawer && isLandscape }
    
    
    func update(horizontal: UserInterfaceSizeClass?, vertical: UserInterfaceSizeClass?, size: CGSize) {
        formFactor = FormFactor.from(horizontal: horizontal, vertical: vertical, size: size)
        // reset drawer on phone
        if isPhone { useDrawer = false }
    }
    
    func updateUseDrawer(_ useDrawer: Bool) {
        self.useDrawer = useDrawer
    }
}

struct AppLayoutKey: EnvironmentKey {
    static let defaultValue = AppLayout()
}

extension EnvironmentValues {
    var appLayout: AppLayout {
        get { self[AppLayoutKey.self] }
        set { self[AppLayoutKey.self] = newValue }
    }
}


struct AppLayouts {
    let widthType: WidthType
    let orientation: ScreenOrientation

    var isPhone: Bool { widthType == .compact }
    var isTablet: Bool { widthType != .compact }
    var isLandscape: Bool { orientation == .landscape }
    var useBottomBar: Bool { widthType == .compact }
    
    var useDrawer: Bool { UIDevice.current.userInterfaceIdiom == .pad && orientation == .landscape }
    
    var useTopbar: Bool { UIDevice.current.userInterfaceIdiom == .pad   }
    
    
}


extension FormFactor {
    static func from(
        horizontal: UserInterfaceSizeClass?,
        vertical: UserInterfaceSizeClass?,
        size: CGSize
    ) -> FormFactor {
        
        print("horizontal: \(String(describing: horizontal)), vertical: \(String(describing: vertical))")
        
        let widthType: WidthType = {
            if horizontal == .compact { return .compact }
            if horizontal == .regular && vertical == .regular { return .expanded }
            return .medium
        }()

        let orientation: ScreenOrientation = size.width > size.height ? .landscape : .portrait

        return FormFactor(widthType: widthType, orientation: orientation)
    }
}

