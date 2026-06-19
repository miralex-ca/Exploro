import Foundation
import Shared

extension StringRef {
    func asString() -> String {
        return StringRefResolver.resolve(ref: self)
    }
}

extension StringRefWithArgs {
    func asStringWithArgs(_ firstArg: String, _ otherArgs: String...) -> String {
        let allArgs = [firstArg] + otherArgs
        return StringRefResolver.resolve(ref: self, args: allArgs)
    }
    
    func asStringWithArg(_ arg: String) -> String {
        return StringRefResolver.resolve(ref: self, arg: arg)
    }
}

extension FormattedText.Ref {
    func with(_ firstArg: String, _ otherArgs: String...) -> String {
        let allArgs = [firstArg] + otherArgs
        return StringRefResolver.resolve(ref: self.ref, args: allArgs)
    }
    
    func resolveString() -> String {
        return with("")
    }
}

extension FormattedText.WithString {
    func resolveString() -> String {
        return self.ref.asStringWithArg(self.arg)
    }
}

extension FormattedText.WithRef {
    func resolveString() -> String {
        return self.ref.asStringWithArg(self.arg.asString())
    }
}

extension FormattedText.SimpleText {
    func resolveString() -> String {
        return self.textRef.asString()
    }
}

extension FormattedText {
    func asString() -> String {
        if let t = self as? FormattedText.Ref { return t.resolveString() }
        if let t = self as? FormattedText.WithString { return t.resolveString() }
        if let t = self as? FormattedText.WithRef { return t.resolveString() }
        if let t = self as? FormattedText.SimpleText { return t.resolveString() }
        return self.ref.ref.simpleName()
    }
}
