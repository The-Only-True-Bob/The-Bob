package com.github.the_only_true_bob.the_bob;

import com.github.the_only_true_bob.the_bob.finder.Finder;
import com.github.the_only_true_bob.the_bob.finder.FinderImpl;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.handler.Handler;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.handler.command.*;
import com.github.the_only_true_bob.the_bob.jetty.JettyHandler;
import com.github.the_only_true_bob.the_bob.matcher.BobMatcher;
import com.github.the_only_true_bob.the_bob.matcher.Matcher;
import com.github.the_only_true_bob.the_bob.vk.polls.AgePoll;
import com.github.the_only_true_bob.the_bob.vk.polls.Poll;
import com.github.the_only_true_bob.the_bob.vk.polls.SexPoll;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Configuration
@ComponentScan("com.github.the_only_true_bob.the_bob")
@EnableJpaRepositories("com.github.the_only_true_bob.the_bob")
@PropertySource("classpath:config.properties")
public class ApplicationConfiguration {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.jpa.hibernate.generateDdl}")
    private boolean generateDdl;

    @Value("${confirmation_code}")
    private String confirmationCode;

    @Value("${group.id}")
    private int groupId;

    @Value("${group.token}")
    private String groupToken;

    @Value("${app.id}")
    private int appId;

    @Value("${app.token}")
    private String appToken;

    @Bean
    public Finder finder() {
        return new FinderImpl();
    }

    @Bean
    public Map<String, BobCommand> commandsMap() {
        final Map<String, BobCommand> map = new HashMap<>();
        map.put(CommandStatus.NONE, suggestCommand());
        map.put(CommandStatus.LISTED, chooseEventCommand());
        map.put(CommandStatus.IS_NEED_IN_COMPANION, startCompanionSearchCommand());
        map.put(CommandStatus.CHOOSE_SEARCH_CRITERIA, chooseSearchCriteriaCommand());
        return map;
    }

    @Bean
    public BobCommand chooseSearchCriteriaCommand() {
        return new ChooseSearchCriteriaCommand();
    }

    @Bean
    public BobCommand startCompanionSearchCommand() {
        return new StartCompanionSearchCommand();
    }

    @Bean
    public BobCommand chooseEventCommand() {
        return new ChooseEventCommand();
    }

    @Bean
    public BobCommand suggestCommand() {
        return new SuggestCommand();
    }

    @Bean
    public Matcher bobMatcher() {
        return new BobMatcher();
    }

    @Bean
    public List<Poll> polls() {
        return Stream.of(agePoll(), sexPoll()).collect(toList());
    }

    @Bean
    public Poll sexPoll() {
        return new SexPoll();
    }

    @Bean
    public Poll agePoll() {
        return new AgePoll();
    }

    @Bean
    public VkApiClient vkApi() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(groupId, groupToken);
    }

    @Bean
    public UserActor userActor() {
        return new UserActor(groupId, groupToken);
    }

    @Bean
    public ServiceActor serviceActor() {
        return new ServiceActor(appId, appToken);
    }

    @Bean
    public List<UserField> userFields() {
        return Stream.of(
                UserField.SEX,
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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUser);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(generateDdl);
        return vendorAdapter;
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}