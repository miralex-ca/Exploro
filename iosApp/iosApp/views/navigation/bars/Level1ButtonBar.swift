
import SwiftUI
import Shared


// this is the bottom horizontal navigation bar for 1-Pane visualization
// (used by small devices and in Portrait mode)
    
struct Level1ButtonBar: View {
    @EnvironmentObject var appObj: AppObservableObject
        
    var body: some View {
        let level1ScreenIdentifier = appObj.localNavigationState.currentLevel1ScreenIdentifier
        Spacer()
        BottomBarButton(
            itemLabel: Strings.navBrowse,
            iconName: "list.bullet",
            selected: level1ScreenIdentifier.URI==Level1Navigation.home.screenIdentifier.URI,
            onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.home) }
        )
        Spacer()
        BottomBarButton(
            itemLabel: Strings.navFavorites,
            iconName: "star.fill",
            selected: level1ScreenIdentifier.URI==Level1Navigation.favorites.screenIdentifier.URI,
            onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.favorites) }
        )
        Spacer()
    }
    
}



struct BottomBarButton: View {
    var itemLabel : String
    var iconName : String
    var selected : Bool
    var onClick : () -> Void
    
    var body: some View {
        Button(action: { onClick() }) {
            VStack(spacing: 5) {
                Image(systemName: iconName).resizable().scaledToFit().frame(height:15)
                Text(itemLabel).font(Font.footnote)
            }
            .foregroundColor(selected ? .white : linkColor)
        }
    }
}

