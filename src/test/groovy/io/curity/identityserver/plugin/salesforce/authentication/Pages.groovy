package io.curity.identityserver.plugin.salesforce.authentication

import geb.Page
import groovy.json.JsonException
import groovy.json.JsonSlurper

class StartLoginPage extends Page {
    static url = "/test/authn/salesforce1"
}

class SalesforceLoginPage extends Page {
    static url = "https://login.salesforce.com"

    static at = {
        title.endsWith("Login | Salesforce")
    }
    static content = {
        usernameField { $("#username") }
        passwordField { $("#password") }
        loginButton { $("#Login") }
        
        validation_message {$("span[class*='LV_validation_message']").text()}
    }

    void login(username, password) {
        usernameField = username
        passwordField = password
        waitFor { loginButton.displayed }
        loginButton.click()
    }
}

class SalesforceConsentPage extends Page {
    static url = "https://eu12.salesforce.com"

    static at = {
        title == "Allow Access? | Salesforce"
    }

    static content = {
        allowButton { $("#oaapprove") }
    }

    void allow() {
        waitFor { allowButton.displayed }
        allowButton.click()
    }
}

class LoginDonePage extends Page {

    def jsonBody

    static at = {
        def body = $("body")?.text()

        try {
            return body != null && (jsonBody = new JsonSlurper().parseText(body)) != null
        } catch (JsonException e) {
            throw new AssertionError("The response body was not valid JSON: $body")
        }
    }

    static content = {
        subject { jsonBody.subject }
    }
}