import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    let navigation: Navigation
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(navigation: navigation)
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
