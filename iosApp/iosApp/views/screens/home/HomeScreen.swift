import SwiftUI
import Shared

struct HomeScreen: View {
    let screenState: HomeScreenState
    let eventHandler: HomeEventHandler
    
    var body: some View {
        
        let isIpad = UIDevice.current.userInterfaceIdiom == .pad
        
        Level1ScreenContainer(
            screenTitle: Strings.homeTitle,
            isAdjustedPadding: false
        ) {
            
            if screenState.isLoading {
                ScreenLoadingView()
            } else {
                HomeScreenContent(
                    screenState: screenState,
                    onEvent: eventHandler.onEvent
                )
            }
        }
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(isIpad ? "" : Strings.homeTitle)
                    .font(.title2)
                    .fontWeight(.semibold)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        
    }
}

struct HomeScreenContent: View {
    let screenState: HomeScreenState
    let onEvent: (HomeUiEvent) -> Void
    @Environment(\.appLayout) var appLayout
    
    var body: some View {
        ZStack {
            if screenState.homeSections.isEmpty {
                //EmptyStateView()
                EmptyView()
            } else {
                HStack(spacing: 0) {
                    ScrollView {
                        LazyVStack(alignment: .leading, spacing: 0) {
                            ForEach(screenState.homeSections, id: \.sectionId) { section in
                                HomeSectionRow(
                                    section: section,
                                    onListItemClick: { onEvent(.onItemClicked($0)) },
                                    onSectionClick: { onEvent(.onSectionClicked(section)) }
                                )
                            }
                        }
                        .padding(.top, Dimens.Home.topPadding.of(appLayout))
                        .padding(.leading, Dimens.Home.leadingPadding.of(appLayout))
                        .scrollClipDisabled()
                    }
                }
                
                
            }
            
        }
    }
}

struct HomeSectionRow: View {
    let section: HomeSectionState
    let onListItemClick: (HomeListItem) -> Void
    let onSectionClick: () -> Void
    
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout
    
    let drawerSpace: CGFloat = 260
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
           
            ZStack {
                Button(action: onSectionClick) {
                    HStack {
                        Text(section.sectionName)
                            .font(.system(size: Dimens.Home.sectionHeaderFont.of(appLayout), weight: .semibold))
                            .padding(.leading, 14)
                        Spacer()
                        
                        ZStack {
                            Circle()
                                .fill(theme.surface)
                                .frame(width: 32, height: 32)
                            
                            Image(systemName: "arrow.forward")
                                .font(.system(size: 15, weight: .semibold))
                                .foregroundColor(theme.onSurfaceVariant)
                                .padding(.horizontal)
                        }
                    }
                    .padding(.vertical, 4)
                }
                .tint(.primary)
            }
            .padding(.leading, appLayout.hasSpaceForDrawer ? drawerSpace : 0)
            
            
            ScrollView(.horizontal, showsIndicators: false) {
                LazyHStack(spacing: Dimens.Home.cardSpacing.of(appLayout)) {
                    ForEach(section.sectionListItems, id: \.id) { item in
                        HomeSectionListCard(
                            item: item,
                            onClick: { onListItemClick(item) }
                        )
                    }
                }
                .padding(.leading, appLayout.hasSpaceForDrawer ? drawerSpace : 0)
               
            }
            .scrollClipDisabled()
        }
        .padding(.bottom, 14)
        .animation(.spring(duration: 0.12), value: appLayout.hasSpaceForDrawer)
    }
}

struct HomeSectionListCard: View {
    let item: HomeListItem
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout
    
    private let cardWidth: CGFloat = 160
    private let imageHeight: CGFloat = 80
    
    var body: some View {
        
        let cardWidth = Dimens.Home.cardWidth.of(appLayout)
        let imageHeight = Dimens.Home.imageHeight.of(appLayout)
        
        let imageSize = CGSize(width: cardWidth - 16, height: imageHeight)
        
        Button(action: onClick) {
            VStack(alignment: .leading, spacing: 0) {
                RemoteImage(
                    url: item.flagPngUrl,
                    size: imageSize
                )
                .scaledToFill()
                .frame(width: imageSize.width, height: imageSize.height)
                .clipped()
                .clipShape(RoundedRectangle(cornerRadius: 4))
                .overlay(
                    RoundedRectangle(cornerRadius: 4)
                        .stroke(Color.gray.opacity(0.25), lineWidth: 1)
                )

                Text(item.name)
                    .font(.subheadline)
                    .lineLimit(1)
                    .padding(.horizontal, 4)
                    .padding(.top, 8)
                    .padding(.bottom, 2)
            }
            .padding(8)
            .frame(width: cardWidth)
            .background(theme.cardSurface)
            .clipShape(RoundedRectangle(cornerRadius: 8))
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(theme.cardBorder, lineWidth: 1)
            )
        }
        .tint(.primary)
        .buttonStyle(.plain)
    }
}

