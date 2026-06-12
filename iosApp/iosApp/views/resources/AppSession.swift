

import SwiftUI
import Shared

class AppSession {
    static let shared = AppSession()
    private init() {}
    
    var showDrawerOnLandscape: Bool = false
    
//    var showDrawerOnLandscape: Bool {
//        get { UserDefaults.standard.bool(forKey: "showDrawerOnLandscape") }
//        set { UserDefaults.standard.set(newValue, forKey: "showDrawerOnLandscape") }
//    }
}

