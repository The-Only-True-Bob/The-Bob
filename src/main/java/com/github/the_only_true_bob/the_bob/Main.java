package com.github.the_only_true_bob.the_bob;

import com.github.the_only_true_bob.the_bob.jetty.JettyHandler;
import org.eclipse.jetty.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        final int port = 8080 /*Integer.parseInt(System.getenv("PORT"))*/;

        final Server server = new Server(port);
        final JettyHandler jettyHandler =
                new AnnotationConfigApplicationContext(ApplicationConfiguration.class)
                        .getBean("jettyHandler", JettyHandler.class);
        server.setHandler(jettyHandler);

        server.start();
        server.join();
    }
}
