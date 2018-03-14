package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec
import spock.lang.Requires

import static io.curity.identityserver.plugin.test.TestRequirements.isEnvironmentVariableSet
import static io.curity.identityserver.plugin.test.TestRequirements.isIdshAvailable
import static io.curity.identityserver.plugin.test.Idsh.loadTestConfig
import static io.curity.identityserver.plugin.test.Idsh.rollback

@Requires( { isIdshAvailable && isEnvironmentVariableSet("SALESFORCE_CLIENT_SECRET") })
class SalesforceAuthenticationIT extends GebReportingSpec {
    def setupSpec() {
        def salesforceClientSecret = System.getenv("SALESFORCE_CLIENT_SECRET")
        loadTestConfig("/test-config.xml", """
            set profiles profile test-authentication-profile authentication-service settings authentication-service authenticators authenticator salesforce1 salesforce client-secret $salesforceClientSecret
        """)
    }

    def cleanupSpec() {
        rollback()
    }

    def "Salesforce Login success test"() {
        when: "go to login page"
        to StartLoginPage, serviceProviderId: "se.curity"

        then: "we're automatically redirected to the Salesforce login page"
        at SalesforceLoginPage
    }
}
