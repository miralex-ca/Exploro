import SwiftUI
import Shared


struct HomeScreen: View {

    let screenState: HomeScreenState
    let onItemClick: (HomeListItem) -> Void
    let onSectionClick: (HomeSectionState) -> Void

    var body: some View {

        if screenState.isLoading {
            ProgressView()
        } else {
            ScrollView {
                LazyVStack(alignment: .leading, spacing: 24) {

                    ForEach(screenState.homeSections, id: \.sectionId) { section in

                        Button {
                            onSectionClick(section)
                        } label: {
                            HStack {
                                Text(section.sectionName)
                                    .font(.headline)

                                Spacer()

                                Image(systemName: "chevron.right")
                            }
                        }
                        .tint(Color.primary)
                        .padding(.horizontal)

                        ScrollView(.horizontal, showsIndicators: false) {
                            HStack(spacing: 12) {

                                ForEach(section.sectionListItems, id: \.id) { item in

                                    Button {
                                        onItemClick(item)
                                    } label: {
                                        VStack(alignment: .leading) {
                                            
                                            AsyncImage(
                                                url: URL(string: item.flagPngUrl)
                                            ) { image in
                                                image
                                                    .resizable()
                                                    .scaledToFill()
                                            } placeholder: {
                                                ProgressView()
                                            }
                                            .frame(width: 140, height: 80)
                                            .clipShape(
                                                RoundedRectangle(cornerRadius: 6)
                                            )

//                                            Rectangle()
//                                                .frame(width: 160, height: 100)
//                                                .opacity(0.5)

                                            Text(item.name)
                                                .lineLimit(1)
                                        }
                                        .frame(width: 140)
                                    }
                                    
                                }
                            }
                            .tint(Color.primary)
                            .padding(.horizontal)
                        }
                    }
                }
                .padding(.vertical)
            }
        }
    }
}




struct HomeScreens: View {
    var screenState: HomeScreenState

    
    var body: some View {
        VStack {
            if screenState.isLoading {
               // LoadingScreen()
                Text("Loading...")
            } else {
                List {
                    if screenState.homeSections.count == 0 {
                        HStack(spacing: 0) {
                            Text("empty list")
                        }
                    } else {
                        Section(header: CountriesListHeader()) {
                            ForEach (screenState.homeSections, id: \.sectionId) { item in
                                VStack {
                                    Text("\(item.sectionName)")
                                }
                                
                                
                                
//                                NavigationLink(value: {} ) {
//                                    CountriesListRow(
//                                        item: item,
//                                        favorite: countriesListState.favoriteCountries[item.name] != nil,
//                                        onFavoriteIconClick: { onFavoriteIconClick(item.name) }
//                                    )
//                                }
                            }
                         }
                    }
                }
                .listStyle(.insetGrouped)
            }
        }
    }
    
}

struct CountriesListHeader: View {
    
    var body: some View {
        HStack {
            Text("country").font(Font.caption).frame(alignment: .leading)
            Spacer()
            Text("first\ndose").font(Font.caption).multilineTextAlignment(.center)
                .frame(width: 60)
            Text("fully\nvax'd").font(Font.caption).multilineTextAlignment(.center)
                .frame(width: 60)
            Text("favorite?").font(Font.caption).frame(alignment: .center)
                .frame(width: 80)
                .padding(.trailing, 25)
        }.frame(height: 50)
    }
}
