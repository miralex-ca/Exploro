import SwiftUI
import Shared

struct HomeScreen: View {
    let screenState: HomeScreenState
    let eventHandler: HomeEventHandler
    
    var body: some View {
        
        let isIpad = UIDevice.current.userInterfaceIdiom == .pad
        
        Level1ScreenContainer(
            screenTitle: "Discover",
            isAdjustedPadding: false
        ) {
            
            if screenState.isLoading {
                ScreenLoadingView()
            } else {
                HomeScreenContent(
                    screenState: screenState,
                    onEvent: eventHandler.onEvent
                )
               // .navigationBarTitleDisplayMode(.large)
                
            }
            
        }
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(isIpad ? "" : "Discover")
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
//                    Color.clear
//                        .frame(width: 200)
                    ScrollView {
                        LazyVStack(alignment: .leading, spacing: 0) {
                            
                            ForEach(screenState.homeSections, id: \.sectionId) { section in
                                HomeSectionRow(
                                    section: section,
                                    onListItemClick: { onEvent(.onItemClicked($0)) },
                                    onSectionClick: { onEvent(.onSectionClicked(section)) }
                                )
                               // .listRowInsets(EdgeInsets(top: 0, leading: 200, bottom: 0, trailing: 0))
                            }
                            
                            //.padding(.trailing, 200)
                             
                            
                            
                        }
                        .scrollClipDisabled()
                        //.defaultScrollAnchor(.some(.init(x: 0, y: 0)))
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
    
    @State private var drawerSpace: CGFloat = 280
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
           
            ZStack {
                Button(action: onSectionClick) {
                    HStack {
                        Text(section.sectionName)
                            .font(.headline)
                            .padding(.leading, 10)
                        Spacer()
                        
                        ZStack {
                            Circle()
                                .fill(theme.surface)
                                .frame(width: 32, height: 32)
                            
                            Image(systemName: "arrow.forward")
                                .font(.system(size: 15, weight: .semibold))
                                .foregroundColor(theme.onSurfaceVariant)
                        }
                    }
                    .padding(.horizontal)
                    .padding(.vertical, 4)
                }
                .tint(.primary)
            }
            .padding(.leading, appLayout.hasSpaceForDrawer ? 280 : 0)
            
            
            ScrollView(.horizontal, showsIndicators: false) {
                LazyHStack(spacing: 8) {
                    ForEach(section.sectionListItems, id: \.id) { item in
                        HomeSectionListCard(
                            item: item,
                            onClick: { onListItemClick(item) }
                        )
                    }
                }
                .padding(.leading, appLayout.hasSpaceForDrawer ? 280 : 0)
               
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
    
    private let cardWidth: CGFloat = 160
    private let imageHeight: CGFloat = 80
    
    var body: some View {
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

