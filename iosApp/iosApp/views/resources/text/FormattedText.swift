import SwiftUI
import Shared


extension FormattedText {
    func asString() -> String {
        switch self {
        case let t as FormattedText.Ref: return t.ref.asStringWithArg("")
        case let t as FormattedText.SimpleText: return t.textRef.asString()
        case let t as FormattedText.WithString: return t.ref.asStringWithArg(t.arg)
        case let t as FormattedText.WithRef: return t.ref.asStringWithArg(t.arg.asString())
        default: return self.ref.ref.simpleName()
        }
    }
    
    func asString(arg: String) -> String {
        switch self {
        case let t as FormattedText.Ref: return t.ref.asStringWithArg(arg)
        default: return self.ref.ref.simpleName()
        }
    }
}
