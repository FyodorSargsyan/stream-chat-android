package com.getstream.sdk.chat.rest.core.providers;

import com.getstream.sdk.chat.interfaces.CachedTokenProvider;
import com.getstream.sdk.chat.rest.core.ApiClientOptions;
import com.getstream.sdk.chat.rest.core.Client;
import com.getstream.sdk.chat.rest.storage.BaseStorage;
import com.getstream.sdk.chat.rest.storage.StreamPublicStorage;

/*
 * Created by Anton Bevza on 2019-10-24.
 */
public class UploadStorageProvider {

    private ApiClientOptions apiClientOptions;

    public UploadStorageProvider(ApiClientOptions options) {
        this.apiClientOptions = options;
    }

    public BaseStorage provideApiService(CachedTokenProvider tokenProvider, Client client) {
        return new StreamPublicStorage(client, tokenProvider, apiClientOptions);
    }
}
