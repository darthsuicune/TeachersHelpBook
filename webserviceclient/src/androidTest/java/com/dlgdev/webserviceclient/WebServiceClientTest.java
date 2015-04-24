package com.dlgdev.webserviceclient;

import junit.framework.TestCase;

/**
 * Created by denis on 16.04.15.
 */
public class WebServiceClientTest extends TestCase {
    WebServiceClient client;
    public void setUp() throws Exception {
        super.setUp();
        client = new WebServiceClient() {
        };
    }
}