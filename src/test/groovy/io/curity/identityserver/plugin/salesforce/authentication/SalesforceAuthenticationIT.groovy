package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec
import io.curity.identityserver.plugin.test.Idsh
import spock.lang.Requires
import spock.lang.Shared

import static io.curity.identityserver.plugin.test.TestRequirements.isEnvironmentVariableSet
import static io.curity.identityserver.plugin.test.TestRequirements.isIdshAvailable

@Requires( { isIdshAvailable && isEnvironmentVariableSet("SALESFORCE_CLIENT_SECRET") })
class SalesforceAuthenticationIT extends GebReportingSpec {
    @Shared
    Idsh idsh = new Idsh()

    def setupSpec() {
        def salesforceClientSecret = System.getenv("SALESFORCE_CLIENT_SECRET")
        idsh.loadTestConfig("/test-config.xml", """
            set profiles profile test-authentication-profile authentication-service settings authentication-service authenticators authenticator salesforce1 salesforce client-secret $salesforceClientSecret
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
}
