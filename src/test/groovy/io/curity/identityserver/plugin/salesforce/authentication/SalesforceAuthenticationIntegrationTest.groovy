package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec
import io.curity.identityserver.plugin.salesforce.authentication.SalesforceLoginPage
import io.curity.identityserver.plugin.salesforce.authentication.SignupResultPage
import spock.lang.Requires

class SalesforceAuthenticationIntegrationTest extends GebReportingSpec {
    private static String clientSecretKeyPath = "/profiles/profile{authentication auth:authentication-service}/settings/auth:authentication-service/authenticators/authenticator{salesforce1}/salesforce:salesforce/client-id"

    def setupSpec() {
        // Setup config
        // idsh <<< "configure
        // load merge foo.xml
        // configure"
    }

    def cleanupSpec() {
        // Rollback
    }

    @Requires({  System.getenv("CLIENT_SECRET") != null })
    def "Salesforce Login success test"() {
        given: "go to login page"
        to SalesforceLoginPage

        when: "I signup as a valid user"
        emailField = "geb@test.com"
        firstNameField = "firstname"
        lastNameField = "lastname"
        submitButton.click()

        then: "I'm at the result page "
        at SignupResultPage

        cleanup:
        rollback()
    }
}
