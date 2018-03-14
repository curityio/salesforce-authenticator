package io.curity.identityserver.plugin.salesforce.authentication

import geb.Page

class StartLoginPage extends Page {
    static url = "/test/authn/salesforce1"
}

class SalesforceLoginPage extends Page {
    static url = "https://login.salesforce.com"

    static at = {
        title.endsWith("| Salesforce")
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
        loginButton.click()
    }
}
