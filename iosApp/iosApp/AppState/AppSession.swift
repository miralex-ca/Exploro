

import SwiftUI
import ComposeApp

class AppSession {
    static let shared = AppSession()
    private init() {}
    
    var showDrawerOnLandscape: Bool = false
    
}

