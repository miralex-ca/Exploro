import XCTest

final class AppNavigationTests: XCTestCase {

    var app: XCUIApplication!

    override func setUpWithError() throws {
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
        waitForHomeScreen()
    }

    override func tearDownWithError() throws {
        app = nil
    }

    private func waitForHomeScreen() {
        let browse = app.buttons["Browse"]
        XCTAssertTrue(browse.waitForExistence(timeout: 10))
        browse.tap()
    }

    @MainActor
    func test1_appLaunchesAndShowsHomeScreen() throws {
        XCTAssertTrue(app.buttons["Browse"].exists)
    }

    @MainActor
    func test2_navigateToFavorites() throws {
        app.buttons["Favorites"].tap()
        XCTAssertTrue(app.buttons["Favorites"].exists)
    }

    @MainActor
    func test3_navigateBackToHomeFromFavorites() throws {
        app.buttons["Favorites"].tap()
        app.buttons["Browse"].tap()
        XCTAssertTrue(app.buttons["Browse"].exists)
    }

    @MainActor
    func test4_openSearchFromTopBar() throws {
        app.buttons["Search"].tap()
        let searchField = app.textFields["Search…"]
        XCTAssertTrue(searchField.waitForExistence(timeout: 3))
    }

    @MainActor
    func test5_openSettingsFromTopBar() throws {
        app.buttons["Settings"].tap()
        let settingsTitle = app.navigationBars["Settings"]
        XCTAssertTrue(settingsTitle.waitForExistence(timeout: 3))
    }
    
    @MainActor
    func test6_navigateHomeToSectionToDetails() throws {
        // Home -> Section
        let seeMore = app.buttons["See more"].firstMatch
        XCTAssertTrue(seeMore.waitForExistence(timeout: 5))
        seeMore.tap()

        let europeTitle = app.staticTexts["Europe"]
        XCTAssertTrue(europeTitle.waitForExistence(timeout: 3))
        XCTAssertTrue(europeTitle.exists)

        // Section -> Details (first country)
        let albania = app.staticTexts["Albania"]
        XCTAssertTrue(albania.waitForExistence(timeout: 5))
        albania.tap()

        // wait for details screen
        let detailsNav = app.navigationBars["Albania"]
        XCTAssertTrue(detailsNav.waitForExistence(timeout: 3))
    }
}
