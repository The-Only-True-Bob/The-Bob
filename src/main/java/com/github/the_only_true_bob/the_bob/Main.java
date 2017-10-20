package com.github.the_only_true_bob.the_bob;

import org.eclipse.jetty.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        final int port = 8080 /*Integer.parseInt(System.getenv("PORT"))*/;

        final Server server = new Server(port);
        final JettyHandler jettyHandler = (JettyHandler) new AnnotationConfigApplicationContext(ApplicationConfiguration.class).getBean("jettyHandler");
        server.setHandler(jettyHandler);

        server.start();
        server.join();
    }
}
