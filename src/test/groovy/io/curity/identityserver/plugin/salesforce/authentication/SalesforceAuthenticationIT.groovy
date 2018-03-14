package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec
import spock.lang.Requires

import static io.curity.identityserver.plugin.salesforce.authentication.Preconditions.getIsSalesForceClientSecretDefined
import static io.curity.identityserver.plugin.salesforce.authentication.Preconditions.isIdshAvailable
import static io.curity.identityserver.plugin.test.Idsh.loadTestConfig
import static io.curity.identityserver.plugin.test.Idsh.rollback

@Requires( { isIdshAvailable && isSalesForceClientSecretDefined })
class SalesforceAuthenticationIT extends GebReportingSpec {
    private static String clientSecretKeyPath = "/profiles/profile{authentication auth:authentication-service}/settings/auth:authentication-service/authenticators/authenticator{salesforce1}/salesforce:salesforce/client-secret"

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
