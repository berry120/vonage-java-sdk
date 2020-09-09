/*
 * Copyright (c) 2020 Vonage
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.vonage.client.voice;

import com.vonage.client.HttpWrapper;
import com.vonage.client.VonageClientException;
import com.vonage.client.auth.JWTAuthMethod;

/**
 * Allows actions to be taken on {@code /calls/*} endpoints.
 * <p>
 * <b>Note:</b> This is an internal object. All functionality is provided publicly by the {@link VoiceClient} class.
 */
class CallsEndpoint {
    private final CreateCallMethod createCall;
    private final ReadCallMethod readCall;
    private final ListCallsMethod listCalls;
    private final ModifyCallMethod modifyCall;

    /**
     * Constructor.
     *
     * @param httpWrapper (required) shared HTTP wrapper object used for making REST calls.
     */
    CallsEndpoint(HttpWrapper httpWrapper) {
        this.createCall = new CreateCallMethod(httpWrapper);
        this.readCall = new ReadCallMethod(httpWrapper);
        this.listCalls = new ListCallsMethod(httpWrapper);
        this.modifyCall = new ModifyCallMethod(httpWrapper);
    }

    /**
     * Start a call configured by the provided {@link Call} object.
     * <p>
     * Requires a {@link JWTAuthMethod} to be provided to the VonageClient which constructs
     *
     * @param callRequest A Call object configuring the call to be created
     *
     * @return A CallEvent describing the call that was initiated.
     *
     * @throws VonageClientException if an error occurs constructing the Vonage API request or response
     */
    CallEvent post(Call callRequest) throws VonageClientException {
        return this.createCall.execute(callRequest);
    }

    /**
     * List previous and ongoing calls which match the provided {@code filter}.
     *
     * @param filter A CallsFilter describing the calls to be searched, or {@code null} for all calls.
     *
     * @return A CallInfoPage containing a single page of {@link CallInfo} results
     *
     * @throws VonageClientException if an error occurs constructing the Vonage API request or response
     */
    CallInfoPage get(CallsFilter filter) throws VonageClientException {
        return this.listCalls.execute(filter);
    }

    /**
     * Get details of a single call, identified by {@code uuid}.
     *
     * @param uuid The uuid of the CallInfo object to be retrieved
     *
     * @return A CallInfo object describing the state of the call that was made or is in progress
     *
     * @throws VonageClientException if an error occurs constructing the Vonage API request or response
     */
    CallInfo get(String uuid) throws VonageClientException {
        return this.readCall.execute(uuid);
    }

    /**
     * Modify an ongoing call.
     *
     * @param modifier A CallModifier describing the modification to make to the call.
     *
     * @return A ModifyCallResponse object describing the state of the call that was modified
     *
     * @throws VonageClientException if an error occurs constructing the Vonage API request or response
     */
    ModifyCallResponse put(CallModifier modifier) throws VonageClientException {
        return this.modifyCall.execute(modifier);
    }
}