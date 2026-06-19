
import SwiftUI
import Kingfisher


struct RemoteImages: View {
    let url: String?
    let size: CGSize

    var body: some View {
        KFImage(URL(string: url ?? ""))
            .resizable()
            .fade(duration: 0.2)
            .setProcessor(
                DownsamplingImageProcessor(size: size)
            )
            .backgroundDecode()
            .scaleFactor(UIScreen.main.scale)
            .cacheOriginalImage()
    }
}

struct RemoteImage: View {
    let url: String?
    let size: CGSize

    var body: some View {
        KFImage(URL(string: url ?? ""))
            .resizable()
            .fade(duration: 0.2)
            .setProcessor(
                DownsamplingImageProcessor(size: size)
            )
            .scaleFactor(UIScreen.main.scale)
            .cacheOriginalImage()
    }
}


