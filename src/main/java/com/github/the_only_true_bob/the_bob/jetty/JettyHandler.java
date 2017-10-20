package com.github.the_only_true_bob.the_bob.jetty;

import com.github.the_only_true_bob.the_bob.handler.Handler;
import com.github.the_only_true_bob.the_bob.vk.Message;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class JettyHandler extends AbstractHandler {
    static final String OK_MESSAGE = "OK";

    @Autowired
    private Handler bobHandler;

    @Value("${confirmation.code}")
    private String confirmationCode;

    @Override
    public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) return;
        final String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        final JsonParser jsonParser = new JsonParser();
        final JsonObject callbackMessage = jsonParser.parse(body).getAsJsonObject();
        if (callbackMessage.get("type").getAsString().equals("confirmation")) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println(confirmationCode);
        } else {
            bobHandler.accept(Message.from(callbackMessage));
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println(OK_MESSAGE);
    }
}
