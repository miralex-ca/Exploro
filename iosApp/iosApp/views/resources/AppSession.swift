

import SwiftUI
import Shared

class AppSession {
    static let shared = AppSession()
    private init() {}
    
    var showDrawerOnLandscape: Bool = false
    
}

