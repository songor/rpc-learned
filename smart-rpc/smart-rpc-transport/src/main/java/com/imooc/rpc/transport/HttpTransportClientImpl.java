package com.imooc.rpc.transport;

import com.imooc.rpc.protocol.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTransportClientImpl implements TransportClient {

    private String uri;

    @Override
    public TransportClient connect(Peer peer) {
        this.uri = "http://" + peer.getHost() + ":" + peer.getPort();
        return this;
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.connect();
            IOUtils.copy(data, connection.getOutputStream());

            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            } else {
                return connection.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }

}
