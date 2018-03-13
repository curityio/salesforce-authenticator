package io.curity.identityserver.plugin.salesforce.authentication

import geb.spock.GebReportingSpec
import spock.lang.Requires

import static io.curity.identityserver.plugin.salesforce.authentication.Preconditions.isIdshAvailable
import static io.curity.identityserver.plugin.salesforce.authentication.Preconditions.getIsSalesForceClientSecretDefined

@Requires( { isIdshAvailable && isSalesForceClientSecretDefined })
class SalesforceAuthenticationIT extends GebReportingSpec {
    private static String clientSecretKeyPath = "/profiles/profile{authentication auth:authentication-service}/settings/auth:authentication-service/authenticators/authenticator{salesforce1}/salesforce:salesforce/client-secret"

    def setupSpec() {
        //def p = "idsh -s".execute()
        //p.in.withReader { "configure" }
        
        // Setup config
        // idsh <<< "configure
        // load merge foo.xml
        // configure"
    }

    def cleanupSpec() {
        // Rollback
    }

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
    }
}
