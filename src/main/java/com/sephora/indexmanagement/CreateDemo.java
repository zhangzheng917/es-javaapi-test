package com.sephora.indexmanagement;

import org.elasticsearch.action.admin.indices.create.CreateIndexAction;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateDemo {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name","my-application").build();

        TransportClient transportClient = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));

        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();

        CreateIndexResponse createIndexResponse = indicesAdminClient.prepareCreate("index1").get();
        System.out.println(createIndexResponse.isAcknowledged());
    }
}
