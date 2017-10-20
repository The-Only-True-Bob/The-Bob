package com.github.the_only_true_bob.the_bob;

import com.github.the_only_true_bob.the_bob.handler.Handler;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.jetty.JettyHandler;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Configuration
@ComponentScan("com.github.the_only_true_bob.the_bob")
@EnableJpaRepositories("com.github.the_only_true_bob.the_bob")
@PropertySource("classpath:config.properties")
public class ApplicationConfiguration {

    @Value("1")
    private int groupId;

    @Value("jhjbhjbh")
    private String token;

    @Value("${confirmation.code}")
    private static String confirmationCode;


    @Bean
    public VkApiClient vkApi() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(groupId, token);
    }

    @Bean
    public UserActor userActor() {
        return new UserActor(groupId, token);
    }

    @Bean
    public List<UserField> userFields() {
        return Stream.of(
                UserField.ABOUT,
                UserField.ACTIVITIES,
                UserField.BDATE,
                UserField.CITY,
                UserField.HOME_TOWN,
                UserField.MUSIC)
                .collect(toList());
    }

    @Bean
    public MessageProvider messageProvider() {
        final MessageSource messageSource = messageSource();
        final Locale locale = locale();
        return (s, objects) -> messageSource.getMessage(s, objects, locale);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message-templates");
        return messageSource;
    }

    @Bean
    public Locale locale() {
        return Locale.forLanguageTag("ru");
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(Database.H2);
        bean.setGenerateDdl(true);
        return bean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan("com.github.the_only_true_bob.the_bob");
        return bean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public Handler bobHandler() {
        return Handler.bob();
    }

    @Bean
    public JettyHandler jettyHandler() {
        return new JettyHandler();
    }
}