
import Foundation
import SwiftUI

let customBgColor = Color(.sRGB, red: 55/255, green: 0, blue: 179/255, opacity: 1) // material Purple700
let linkColor = Color(.sRGB, red: 209/255, green: 190/255, blue: 245/255, opacity: 1) // light pink
let magentaColor = Color(.sRGB, red: 1, green: 0, blue: 1, opacity: 1) // purple
let greyColor = Color(.sRGB, red: 170/255, green: 170/255, blue: 170/255, opacity: 1) // grey
let lightGreyColor = Color(.sRGB, red: 200/255, green: 200/255, blue: 200/255, opacity: 1) // light grey


struct NavigationBarColor: ViewModifier {

  init(backgroundColor: UIColor, tintColor: UIColor) {
    let coloredAppearance = UINavigationBarAppearance()
    coloredAppearance.configureWithOpaqueBackground()
    coloredAppearance.backgroundColor = backgroundColor
    coloredAppearance.titleTextAttributes = [.foregroundColor: tintColor]
    coloredAppearance.largeTitleTextAttributes = [.foregroundColor: tintColor]
    UINavigationBar.appearance().standardAppearance = coloredAppearance
    UINavigationBar.appearance().compactAppearance = coloredAppearance
    UINavigationBar.appearance().tintColor = tintColor
  }

  func body(content: Content) -> some View {
    content
  }
}

extension View {
  func navigationBarColor(backgroundUIColor: UIColor, tintUIColor: UIColor) -> some View {
    self.modifier(NavigationBarColor(backgroundColor: backgroundUIColor, tintColor: tintUIColor))
  }
}

struct ToolbarColor: ViewModifier {

  init(backgroundColor: UIColor, tintColor: UIColor) {
    let coloredAppearance = UIToolbarAppearance()
    coloredAppearance.configureWithOpaqueBackground()
    coloredAppearance.backgroundColor = backgroundColor
    UIToolbar.appearance().standardAppearance = coloredAppearance
    UIToolbar.appearance().compactAppearance = coloredAppearance
    UIToolbar.appearance().tintColor = tintColor
  }

  func body(content: Content) -> some View {
    content
  }
}

extension View {
  func toolbarColor(backgroundUIColor: UIColor, tintUIColor: UIColor) -> some View {
    self.modifier(ToolbarColor(backgroundColor: backgroundUIColor, tintColor: tintUIColor))
  }
}
