/*
 *  Copyright 2017 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.curity.identityserver.plugin.salesforce.config;


import se.curity.identityserver.sdk.config.Configuration;
import se.curity.identityserver.sdk.config.annotation.DefaultBoolean;
import se.curity.identityserver.sdk.config.annotation.Description;
import se.curity.identityserver.sdk.service.ExceptionFactory;
import se.curity.identityserver.sdk.service.HttpClient;
import se.curity.identityserver.sdk.service.Json;
import se.curity.identityserver.sdk.service.SessionManager;
import se.curity.identityserver.sdk.service.WebServiceClientFactory;
import se.curity.identityserver.sdk.service.authentication.AuthenticatorInformationProvider;

import java.util.Optional;

@SuppressWarnings("InterfaceNeverImplemented")
public interface SalesforceAuthenticatorPluginConfig extends Configuration
{

    @Description("Consumer key")
    String getClientId();

    @Description("Secret key")
    String getClientSecret();

    @Description("Request a scope (api) which allows access to the current, logged-in user’s account using APIs, such as REST API and Bulk API. This value also includes chatter_api, which allows access to Chatter REST API resources.")
    @DefaultBoolean(false)
    Boolean isApi();

    @Description("Request a scope (chatter_api) which allows access to Chatter REST API resources only.")
    @DefaultBoolean(false)
    Boolean isChatterApi();

    @Description("Request a scope (custom_permissions) which allows access to the custom permissions in an organization associated with the connected app, and shows whether the current user has each permission enabled.")
    @DefaultBoolean(false)
    Boolean isCustomPermissions();

    @Description("Request a scope (full) which allows access to all data accessible by the logged-in user, and encompasses all other scopes. full does not return a refresh token. You must explicitly request the refresh_token scope to get a refresh token.")
    @DefaultBoolean(false)
    Boolean isFullAccess();

    @Description("Request a scope (id) which allows access to the identity URL service. You can request profile, email, address, or phone, individually to get the same result as using id; they are all synonymous.")
    @DefaultBoolean(false)
    Boolean isIdentityAccess();

    @Description("Request a scope (openid) which allows access to the current, logged in user’s unique identifier for OpenID Connect apps.")
    @DefaultBoolean(true)
    Boolean isOpenidConnect();

    @Description("Request a scope (refresh_token) which allows a refresh token to be returned if you are eligible to receive one. This lets the app interact with the user’s data while the user is offline, and is synonymous with requesting offline_access.")
    @DefaultBoolean(false)
    Boolean isRefreshToken();

    @Description("Request a scope (visualforce) which allows access to Visualforce pages.")
    @DefaultBoolean(false)
    Boolean isVisualForceAccess();

    @Description("Request a scope (web) which allows the ability to use the access_token on the Web. This also includes visualforce, allowing access to Visualforce pages.")
    @DefaultBoolean(false)
    Boolean isAllowUseAccessTokenOnWeb();

    @Description("The HTTP client with any proxy and TLS settings that will be used to connect to Dropbox")
    Optional<HttpClient> getHttpClient();

    SessionManager getSessionManager();

    ExceptionFactory getExceptionFactory();

    AuthenticatorInformationProvider getAuthenticatorInformationProvider();

    WebServiceClientFactory getWebServiceClientFactory();

    Json getJson();
}
