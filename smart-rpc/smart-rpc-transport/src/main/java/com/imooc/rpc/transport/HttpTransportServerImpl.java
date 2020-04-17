package com.imooc.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HttpTransportServerImpl implements TransportServer {

    private RequestHandler handler;

    private Server server;

    @Override
    public void init(Integer port, RequestHandler handler) {
        this.handler = handler;
        this.server = new Server(port);
        ServletContextHandler ctx = new ServletContextHandler();
        ServletHolder holder = new ServletHolder(new RequestServlet());
        ctx.addServlet(holder, "/*");
        this.server.setHandler(ctx);
    }

    @Override
    public void start() {
        try {
            this.server.start();
            this.server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            this.server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    class RequestServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();
            if (handler != null) {
                handler.handle(in, out);
            }
            out.flush();
        }

    }

}
