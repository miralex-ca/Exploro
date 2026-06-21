import SwiftUI


struct PreviewScreen<Content: View>: View {
    let isDark: Bool
    let layout: AppLayout
    @ViewBuilder let content: () -> Content
    
    init(
        dark: Bool = false,
        layout: AppLayout = .previewPhone,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.isDark = dark
        self.layout = layout
        self.content = content
    }
    
    var body: some View {
        ZStack {
            Color("AppBackground").ignoresSafeArea()
            content()
        }
        .environment(\.appTheme, AppTheme.from(isDark ? .dark : .light))
        .environment(\.appLayout, layout)
        .preferredColorScheme(isDark ? .dark : .light)
    }
}

struct PreviewContent<Content: View>: View {
    let isDark: Bool
    let layout: AppLayout
    @ViewBuilder let content: () -> Content
    
    init(
        dark: Bool = false,
        layout: AppLayout = .previewPhone,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.isDark = dark
        self.layout = layout
        self.content = content
    }
    
    var body: some View {
        ZStack {
            content()
        }
        .background(Color("AppBackground"))
        .environment(\.appTheme, AppTheme.from(isDark ? .dark : .light))
        .environment(\.appLayout, layout)
        .preferredColorScheme(isDark ? .dark : .light)
    }
}

struct PreviewCard<Content: View>: View {
    let isDark: Bool
    @ViewBuilder let content: () -> Content
    
    init(dark: Bool = false, @ViewBuilder content: @escaping () -> Content) {
        self.isDark = dark
        self.content = content
    }
    
    var body: some View {
        ZStack {
            content()
                .padding()
        }
        .background(Color("AppBackground"))
        .environment(\.appTheme, AppTheme.from(isDark ? .dark : .light))
        .preferredColorScheme(isDark ? .dark : .light)
    }
}

