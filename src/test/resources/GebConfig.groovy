import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import org.openqa.selenium.firefox.*
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
    File driverDirectory = new File(System.getProperty("webdriver.gecko.driverDir"))
    String os = System.properties['os.name'].toLowerCase().contains("mac") ? "mac" : "linux"
    String driverName = "geckodriver-$os-64bit"
    File driverFile = new File(driverDirectory, driverName)

    GeckoDriverService service = new GeckoDriverService(driverFile, 4444,
            ImmutableList.of("--log=fatal"), ImmutableMap.of());
    FirefoxOptions options = new FirefoxOptions()

    service.start();
    options.profile = firefoxProfile
    options.setLogLevel(FirefoxDriverLogLevel.ERROR)

    def testDriver = new FirefoxDriver(service, options)

    Runtime.addShutdownHook {
        try {
            testDriver.quit()
        } catch (UnreachableBrowserException ignored) {
            println("Browser is gone, so test driver doesn't need to be shutdown")
        }
    }

    return testDriver
}

baseUrl = "https://localhost:8443"

reportsDir = new File("target/geb-reports")