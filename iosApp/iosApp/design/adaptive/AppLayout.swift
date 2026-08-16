
import SwiftUI
import ComposeApp


@Observable
class AppLayout {
    var formFactor: FormFactor = FormFactor.defaultValue()
    
    var useBottomBar: Bool { isPhone }
    var useLargeScreenNav: Bool { isTablet }
    var useTopbar: Bool { isTablet && !useDrawer }
    var useDrawer: Bool = false
    
 
    var isPhone: Bool { formFactor.isPhone }
    var isTablet: Bool { formFactor.isTablet }
    var isLandscape: Bool { formFactor.orientation == .landscape }
    
    var hasSpaceForDrawer: Bool { useDrawer && isLandscape }
    
    
    func update(horizontal: UserInterfaceSizeClass?, vertical: UserInterfaceSizeClass?, size: CGSize) {
        formFactor = FormFactor.from(horizontal: horizontal, vertical: vertical, size: size)
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




