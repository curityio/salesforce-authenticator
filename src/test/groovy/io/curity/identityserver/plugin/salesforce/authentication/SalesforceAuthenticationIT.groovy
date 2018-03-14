package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec

import spock.lang.Requires
import spock.lang.Shared
import spock.lang.Unroll
import io.curity.identityserver.test.Idsh

import static io.curity.identityserver.test.TestRequirements.isEnvironmentVariableSet
import static io.curity.identityserver.test.TestRequirements.getIsIdshAvailable
import static java.lang.System.getenv

@Requires( { isIdshAvailable && isEnvironmentVariableSet("SALESFORCE_CLIENT_SECRET") })
class SalesforceAuthenticationIT extends GebReportingSpec {
    @Shared
    Idsh idsh = new Idsh()

    private static final SALESFORCE_AUTHENTICATOR_PATH = "profiles profile test-authentication-profile authentication-service settings authentication-service authenticators authenticator salesforce1 salesforce"

    def setupSpec() {
        def salesforceClientSecret = getenv("SALESFORCE_CLIENT_SECRET")
        idsh.loadTestConfig("/test-config.xml", """
            set $SALESFORCE_AUTHENTICATOR_PATH client-secret $salesforceClientSecret 
        """)
    }

    def cleanupSpec() {
        idsh.rollback()
    }

    def "Salesforce Login success test"() {
        when: "go to login page"
        to StartLoginPage, serviceProviderId: "se.curity"

        then: "we're automatically redirected to the Salesforce login page"
        at SalesforceLoginPage
    }

    @Requires({ isEnvironmentVariableSet("SALESFORCE_USERNAME") &&
            isEnvironmentVariableSet("SALESFORCE_PASSWORD") })
    @Unroll
    def "Set some scope"() {
        given:
        def userName = getenv("SALESFORCE_USERNAME")
        def password = getenv("SALESFORCE_PASSWORD")
        idsh.setValue("$SALESFORCE_AUTHENTICATOR_PATH $scope", true)

        when: "go to login page"
        to StartLoginPage, serviceProviderId: "se.curity"

        and:
        at SalesforceLoginPage

        then:
        assert page instanceof SalesforceLoginPage

        when:
        page.login(userName, password)

        then:
        if (isAt(SalesforceConsentPage)) {
            assert page instanceof SalesforceConsentPage

            page.allow()
        }

        then:
        waitFor { at LoginDonePage }
        assert page instanceof LoginDonePage
        page.subject == "teddie.curity@gmail.com"

        cleanup:
        idsh.setValue("$SALESFORCE_AUTHENTICATOR_PATH $scope", false)

        where:
        scope << [
                "full", "api"
        ]
    }
}
