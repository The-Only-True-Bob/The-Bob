package com.github.the_only_true_bob.the_bob;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        final TransportClient transportClient = HttpTransportClient.getInstance();
        final VkApiClient api = new VkApiClient(transportClient);

        final InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.properties");
        final Properties properties = new Properties();
        properties.load(inputStream);


        final int groupId = Integer.parseInt(properties.getProperty("groupId"));
        final String token = properties.getProperty("token");
        final String confirmationCode = properties.getProperty("confirmationCode");
        //port from heroku server
        final int port = Integer.parseInt(System.getenv("PORT"));

        final GroupActor actor = new GroupActor(groupId, token);

        final Server server = new Server(port);
        server.setHandler(handler);

        server.start();
        server.join();
    }

    private static final AbstractHandler handler = new AbstractHandler() {
        public static final String OK_MESSAGE = "OK";

        @Override
        public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
            if (!"POST".equalsIgnoreCase(request.getMethod())) return;
            final String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            // TODO: 20/10/17 HANDLE BY BobHandler

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println(OK_MESSAGE);
        }
    };
}
