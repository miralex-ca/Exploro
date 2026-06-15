import SwiftUI
import Shared


struct SideDrawer: View {
    @EnvironmentObject var appObj: AppObservableObject
    let screenNavActions: ScreenNavActions

    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout

    var body: some View {
        let currentURI = appObj.localNavigationState.currentLevel1ScreenIdentifier.URI

        VStack(alignment: .leading, spacing: 0) {

            HStack {
                Spacer()
                
                DrawerToogleButton(
                    label: "Collapse",
                    icon: "inset.filled.topthird.rectangle",
                    
                ) {
                    appLayout.updateUseDrawer(false)
                    AppSession.shared.showDrawerOnLandscape = false
                }
            }
            .padding(.bottom, 60)
            
            
            DrawerButton(
                label: Strings.searchTitle,
                icon: "magnifyingglass",
                selectedIcon: "magnifyingglass",
                selected: false,
                isIcon: false
            ) {
                screenNavActions.toSearch()
            }
            .padding(.bottom, 6)
           

            DrawerButton(
                label: Strings.homeTitle,
                icon: "safari",
                selectedIcon: "safari",
                selected: currentURI == Level1Navigation.home.screenIdentifier.URI
            ) {
                appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: .home)
            }
            .padding(.bottom, 6)

            DrawerButton(
                label: Strings.favoritesTitle,
                icon: "star",
                selectedIcon: "star",
                selected: currentURI == Level1Navigation.favorites.screenIdentifier.URI
            ) {
                appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: .favorites)
            }
            .padding(.bottom, 6)
            Spacer()

            DrawerButton(
                label: Strings.settingsTitle,
                icon: "gearshape",
                selectedIcon: "gearshape.fill",
                selected: false,
                isIcon: false
            ) {
                //screenNavActions.toSettings()
                appObj.showSettings = true
            }
            .padding(.bottom, 8)
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 8)
        .frame(width: 250)
        .contentShape(Rectangle())
        
        .drawerGlass()
    }
}


struct DrawerToogleButton: View {
    let label: String
    let icon: String
    let onClick: () -> Void

    @Environment(\.appTheme) var theme

    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 12) {
                Image(systemName: icon)
                    .font(.system(size: 20, weight: .light))
                    .frame(width: 24)
                    .opacity(0.8)

                
 
            }
            .foregroundStyle(theme.navText)
            
            .padding(.top, 4)
            .padding(.trailing, 2)
            .padding(.leading, 20)
            .contentShape(Rectangle())
             
        }
        .buttonStyle(.plain)
    }
}

struct DrawerButton: View {
    let label: String
    let icon: String
    let selectedIcon: String
    let selected: Bool
    var isIcon: Bool = false
    let onClick: () -> Void

    @Environment(\.appTheme) var theme

    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 16) {
                Image(systemName: selected ? selectedIcon : icon)
                    .font(.system(size: 21))
                    .frame(width: 24)

                if !isIcon {
                    Text(label)
                        //.font(.subheadline)
                        .fontWeight(.regular)
                        .font(.system(size: 18))
                }

                Spacer()
            }
            .foregroundStyle(selected ? theme.navSelected : .primary)
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .frame(maxWidth: .infinity)  // add this
             .contentShape(Rectangle())   // add this
            .background {
                if selected {
                    RoundedRectangle(cornerRadius: 16)
                        .fill(.gray.opacity(0.2))
                }
            }
        }
        .buttonStyle(.plain)
    }
}



struct DrawerGlassModifier: ViewModifier {
    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: RoundedRectangle(cornerRadius: 24))
                .background {
                    RoundedRectangle(cornerRadius: 24)
                        .fill(.white.opacity(0.2))
                }
        } else {
            content
                .background(
                    .ultraThinMaterial,
                    in: RoundedRectangle(cornerRadius: 24)
                )
                .overlay {
                    RoundedRectangle(cornerRadius: 24)
                        .strokeBorder(.white.opacity(0.15))
                }
                .shadow(
                    color: .black.opacity(0.15),
                    radius: 20,
                    y: 4
                )
        }
    }
}

extension View {
    func drawerGlass() -> some View {
        modifier(DrawerGlassModifier())
    }
}
