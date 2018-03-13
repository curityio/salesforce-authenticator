import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.UnreachableBrowserException

driver = {
    def firefoxProfile = new FirefoxProfile()

    [
            "intl.accept_languages"                     : "en",
            "toolkit.telemetry.reportingpolicy.firstRun": "false",
            "browser.startup.homepage"                  : "about:blank",
            "startup.homepage_welcome_url"              : "about:blank",
            "startup.homepage_welcome_url.additional"   : "about:blank"
    ].each {
        k, v -> firefoxProfile.setPreference(k, v)
    }

    def testDriver = new FirefoxDriver(firefoxProfile)

    Runtime.addShutdownHook {
        try {
            testDriver.quit()
        } catch (UnreachableBrowserException ignored) {
            println("Browser is gone, so test driver doesn't need to be shutdown")
        }
    }

    testDriver.manage().window().maximize()

    return testDriver
}

//baseUrl = "https://localhost:8443"

reportsDir = new File("target/geb-reports")
//reportOnTestFailureOnly = true