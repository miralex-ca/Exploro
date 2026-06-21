
import SwiftUI
import Kingfisher

struct RemoteImage: View {
    let url: String?
    let size: CGSize
    var hasPlaceholder: Bool = true
    
    var body: some View {
        KFImage(URL(string: url ?? ""))
            .resizable()
            .placeholder {
                hasPlaceholder ? Color.gray.opacity(0.1) : Color.clear
            }
            .fade(duration: 0.2)
            .setProcessor(
                DownsamplingImageProcessor(size: size)
            )
            .scaleFactor(UIScreen.main.scale)
            .cacheOriginalImage()
    }
}


