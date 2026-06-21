
import SwiftUI
import Shared


struct SearchScreen: View {
    let screenState: SearchScreenState
    let eventHandler: SearchEventHandler

    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout

    @State private var query: String = ""
    @FocusState private var isFocused: Bool
    @State private var isAppeared: Bool = false

    var body: some View {
        ZStack(alignment: .top ) {
            
            SearchResultContent(
                query: query,
                screenState: screenState,
                onItemClicked: { item in
                    eventHandler.onEvent(.OnItemClicked(item: item))
                }
            )
            
            SearchTextField(
                query: $query,
                isFocused: $isFocused,
                onClear: {
                    query = ""
                    isFocused = true
                }
            )
            .padding(.vertical, 8)
            .frame(maxWidth: Dimens.Search.searchFieldaxWidth.of(appLayout))
            .padding(.horizontal, Dimens.Search.searchFieldPaddings.of(appLayout))
             
        }
        .simultaneousGesture(
            DragGesture().onChanged { _ in
                isFocused = false
            }
        )
        .background(theme.background.ignoresSafeArea())
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if (!isAppeared) {
                isFocused = true
                isAppeared = true
            }
            
        }
        .onChange(of: query) { _, newQuery in
            Task {
                try? await Task.sleep(nanoseconds: 400_000_000)
                eventHandler.onEvent(.SearchByQuery(query: newQuery))
            }
        }
    }
}

struct SearchTextField: View {
    @Binding var query: String
    var isFocused: FocusState<Bool>.Binding
    var onClear: () -> Void

    var body: some View {
        HStack(spacing: 8) {
            Image(systemName: "magnifyingglass")
                .foregroundStyle(.secondary)

            TextField(Strings.searchPlaceholder, text: $query)
                .focused(isFocused)
                .submitLabel(.search)
                .autocorrectionDisabled()
                .textInputAutocapitalization(.never)

            if !query.isEmpty {
                Button(action: onClear) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundStyle(.secondary)
                }
            }
        }
        .font(.system(size: 22))
        .padding(.horizontal, 12)
        .padding(.vertical, 10)
        .glassCapsule()
    }
}

struct SearchResultContent: View {
    let query: String
    let screenState: SearchScreenState
    let onItemClicked: (SearchListItem) -> Void

    var body: some View {
        switch screenState.searchResult {
        case is SearchResult.Idle:
            SearchInitialView()
        case is SearchResult.NotFound:
            SearchEmptyView(query: query)
        case let result as SearchResult.Success:
            SearchResultsList(
                searchResult: result.items,
                newVersionId: result.newVersion,
                onItemClicked: onItemClicked
            )
        default:
            EmptyView()
        }
    }
}

struct SearchInitialView: View {
    var body: some View {
        VStack {
            Spacer()
            Text(Strings.startSearch)
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.center)
            Spacer()
        }
        .padding()
    }
}

struct SearchEmptyView: View {
    let query: String
    var body: some View {
        VStack(spacing: 8) {
            Spacer()
            Text(Strings.emptyTitleNoResults)
                .foregroundStyle(.secondary)
            Text(query.trimmingCharacters(in: .whitespaces))
                .fontWeight(.semibold)
            Spacer()
        }
        .padding()
    }
}

struct SearchResultsList: View {
    let searchResult: [SearchListItem]
    var newVersionId: String? = nil
    let onItemClicked: (SearchListItem) -> Void
    @State private var cachedItems: [SearchListItem] = []
    @Environment(\.appLayout) var appLayout
    
    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(Array(cachedItems.enumerated()), id: \.element.id) { index, item in
                    SearchListRow(
                        item: item,
                        isFirst: index == 0,
                        isLast: index == cachedItems.count - 1,
                        onClick: { onItemClicked(item) }
                    )
                    .padding(.horizontal, 14)
                    .animation(nil, value: cachedItems)
                    .frame(maxWidth: Dimens.Search.itemMaxWidth.of(appLayout))
                }
            }
            .padding(.top, 70)
            .onChange(of: newVersionId, initial: true) {
                if searchResult.count > 10 {
                    cachedItems = searchResult
                } else {
                    withAnimation(.easeInOut(duration: 0.2)) {
                        cachedItems = searchResult
                    }
                }
            }
        }
        .scrollDismissesKeyboard(.interactively)
    }
}

struct SearchListRow: View {
    let item: SearchListItem
    let isFirst: Bool
    let isLast: Bool
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme

    var corners: UIRectCorner {
        if isFirst && isLast { return .allCorners }
        if isFirst { return [.topLeft, .topRight] }
        if isLast { return [.bottomLeft, .bottomRight] }
        return []
    }

    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 12) {
                
                RemoteImage(url: item.flagPngUrl, size: CGSize(width: 80, height: 58))
                    .scaledToFill()
                    .frame(width: 80, height: 58)
                    .clipped()
                    .clipShape(RoundedRectangle(cornerRadius: 6))
                    .overlay(
                        RoundedRectangle(cornerRadius: 6)
                            .stroke(Color(.systemGray4), lineWidth: 0.5)
                    )

                VStack(alignment: .leading, spacing: 2) {
                    Text(item.name)
                        .font(.appBody)
                        .fontWeight(.semibold)
                        .lineLimit(1)
                    Text(item.officialName)
                        .font(.caption)
                        .foregroundStyle(.secondary)
                        .lineLimit(1)
                    Text(Strings.listItemLabelCapital(item.capital))
                        .font(.caption)
                        .foregroundStyle(.secondary)
                        .lineLimit(1)
                }

                Spacer()
            }
            .padding(10)
            .background(theme.cardSurface)
            .clipShape(
                RoundedCornerShape(corners: corners, radius: 12)
            )
            .overlay(
                RoundedCornerShape(corners: corners, radius: 12)
                    .stroke(Color(.systemGray4), lineWidth: 0.5)
            )
        }
        .buttonStyle(.plain)
        .padding(.vertical, 1)
        
    }
}

 
struct RoundedCornerShape: Shape {
    var corners: UIRectCorner
    var radius: CGFloat

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(
            roundedRect: rect,
            byRoundingCorners: corners,
            cornerRadii: CGSize(width: radius, height: radius)
        )
        return Path(path.cgPath)
    }
}
