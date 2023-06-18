## Spring-Security源码学习

## 1.过滤器链加载分析

### 1.1 从spring.factories文件开始

org\springframework\boot\spring-boot-autoconfigure\2.3.4.RELEASE\spring-boot-autoconfigure-2.3.4.RELEASE.jar!\META-INF\spring.factories

```properties
# Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,\
org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener

# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.autoconfigure.BackgroundPreinitializer

# Auto Configuration Import Listeners
org.springframework.boot.autoconfigure.AutoConfigurationImportListener=\
org.springframework.boot.autoconfigure.condition.ConditionEvaluationReportAutoConfigurationImportListener

# Auto Configuration Import Filters
org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=\
org.springframework.boot.autoconfigure.condition.OnBeanCondition,\
org.springframework.boot.autoconfigure.condition.OnClassCondition,\
org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration

# Failure analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
org.springframework.boot.autoconfigure.data.redis.RedisUrlSyntaxFailureAnalyzer,\
org.springframework.boot.autoconfigure.diagnostics.analyzer.NoSuchBeanDefinitionFailureAnalyzer,\
org.springframework.boot.autoconfigure.flyway.FlywayMigrationScriptMissingFailureAnalyzer,\
org.springframework.boot.autoconfigure.jdbc.DataSourceBeanCreationFailureAnalyzer,\
org.springframework.boot.autoconfigure.jdbc.HikariDriverConfigurationFailureAnalyzer,\
org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryBeanCreationFailureAnalyzer,\
org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryFailureAnalyzer

# Template availability providers
org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider=\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.mustache.MustacheTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.web.servlet.JspTemplateAvailabilityProvider

```

从以上的配制中，可以找到

```tex
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
```

此类就是安全过滤器自动装配的类。

### 1.2 查看SecurityFilterAutoConfiguration源码

```java
package org.springframework.boot.autoconfigure.security.servlet;

import java.util.EnumSet;
import java.util.stream.Collectors;

import javax.servlet.DispatcherType;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security's Filter.
 * Configured separately from {@link SpringBootWebSecurityConfiguration} to ensure that
 * the filter's order is still configured when a user-provided
 * {@link WebSecurityConfiguration} exists.
 *
 * @author Rob Winch
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnClass({ AbstractSecurityWebApplicationInitializer.class, SessionCreationPolicy.class })
@AutoConfigureAfter(SecurityAutoConfiguration.class)
public class SecurityFilterAutoConfiguration {

	private static final String DEFAULT_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

	@Bean
	@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
	public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(
			SecurityProperties securityProperties) {
		DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
				DEFAULT_FILTER_NAME);
		registration.setOrder(securityProperties.getFilter().getOrder());
		registration.setDispatcherTypes(getDispatcherTypes(securityProperties));
		return registration;
	}

	private EnumSet<DispatcherType> getDispatcherTypes(SecurityProperties securityProperties) {
		if (securityProperties.getFilter().getDispatcherTypes() == null) {
			return null;
		}
		return securityProperties.getFilter().getDispatcherTypes().stream()
				.map((type) -> DispatcherType.valueOf(type.name()))
				.collect(Collectors.toCollection(() -> EnumSet.noneOf(DispatcherType.class)));
	}

}
```

此类中的源码无需过多的关注，重点关注下

```java
@AutoConfigureAfter(SecurityAutoConfiguration.class)
```

这个类加载完成后，触发加载



### 1.3 查看SecurityAutoConfiguration源码

```java
package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @author Madhura Bhave
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
//默认的配制，包括用户名、密码
@EnableConfigurationProperties(SecurityProperties.class)
//WebSecurityEnablerConfiguration web安全的配制类,非常重要。
@Import({ SpringBootWebSecurityConfiguration.class, WebSecurityEnablerConfiguration.class,
		SecurityDataConfiguration.class })
public class SecurityAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(AuthenticationEventPublisher.class)
	public DefaultAuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher publisher) {
		return new DefaultAuthenticationEventPublisher(publisher);
	}

}
```

在源码中@EnableConfigurationProperties(SecurityProperties.class)用于定认默认的用户名和默认的密码

在导入中WebSecurityEnablerConfiguration用开启WEB安全启动配制。

### 1.4 查看SecurityProperties

```java
package org.springframework.boot.autoconfigure.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * Configuration properties for Spring Security.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @author Madhura Bhave
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.security")
public class SecurityProperties {

	/**
	 * Order applied to the WebSecurityConfigurerAdapter that is used to configure basic
	 * authentication for application endpoints. If you want to add your own
	 * authentication for all or some of those endpoints the best thing to do is to add
	 * your own WebSecurityConfigurerAdapter with lower order.
	 */
	public static final int BASIC_AUTH_ORDER = Ordered.LOWEST_PRECEDENCE - 5;

	/**
	 * Order applied to the WebSecurityConfigurer that ignores standard static resource
	 * paths.
	 */
	public static final int IGNORED_ORDER = Ordered.HIGHEST_PRECEDENCE;

	/**
	 * Default order of Spring Security's Filter in the servlet container (i.e. amongst
	 * other filters registered with the container). There is no connection between this
	 * and the {@code @Order} on a WebSecurityConfigurer.
	 */
	public static final int DEFAULT_FILTER_ORDER = OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 100;

	private final Filter filter = new Filter();

	private User user = new User();

	public User getUser() {
		return this.user;
	}

	public Filter getFilter() {
		return this.filter;
	}

	public static class Filter {

		/**
		 * Security filter chain order.
		 * 过滤器链排队的规则
		 */
		private int order = DEFAULT_FILTER_ORDER;

		/**
		 * Security filter chain dispatcher types.
		 */
		private Set<DispatcherType> dispatcherTypes = new HashSet<>(
				Arrays.asList(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.REQUEST));

		public int getOrder() {
			return this.order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public Set<DispatcherType> getDispatcherTypes() {
			return this.dispatcherTypes;
		}

		public void setDispatcherTypes(Set<DispatcherType> dispatcherTypes) {
			this.dispatcherTypes = dispatcherTypes;
		}

	}
	/**
	 * 默认的用户信息
	 */
	public static class User {

		/**
		 * Default user name.
		 * 默认的用户名
		 */
		private String name = "user";

		/**
		 * Password for the default user name.
		 * 默认的用户名所对应的密码，是UUID生成的。
		 */
		private String password = UUID.randomUUID().toString();

		/**
		 * Granted roles for the default user name.
		 */
		private List<String> roles = new ArrayList<>();

		private boolean passwordGenerated = true;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			if (!StringUtils.hasLength(password)) {
				return;
			}
			this.passwordGenerated = false;
			this.password = password;
		}

		public List<String> getRoles() {
			return this.roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = new ArrayList<>(roles);
		}

		public boolean isPasswordGenerated() {
			return this.passwordGenerated;
		}

	}

}
```

### 1.5 WebSecurityEnablerConfiguration源码

```java
package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * If there is a bean of type WebSecurityConfigurerAdapter, this adds the
 * {@link EnableWebSecurity @EnableWebSecurity} annotation. This will make sure that the
 * annotation is present with default security auto-configuration and also if the user
 * adds custom security and forgets to add the annotation. If
 * {@link EnableWebSecurity @EnableWebSecurity} has already been added or if a bean with
 * name {@value BeanIds#SPRING_SECURITY_FILTER_CHAIN} has been configured by the user,
 * this will back-off.
 *
 * @author Madhura Bhave
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(name = BeanIds.SPRING_SECURITY_FILTER_CHAIN)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
public class WebSecurityEnablerConfiguration {

}
```

@EnableWebSecurity用于开启Security安全的功能。



### 1.6  查看@EnableWebSecurity源码

```java
package org.springframework.security.config.annotation.web.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

/**
 * Add this annotation to an {@code @Configuration} class to have the Spring Security
 * configuration defined in any {@link WebSecurityConfigurer} or more likely by extending
 * the {@link WebSecurityConfigurerAdapter} base class and overriding individual methods:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableWebSecurity
 * public class MyWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
 *
 * 	&#064;Override
 * 	public void configure(WebSecurity web) throws Exception {
 * 		web.ignoring()
 * 		// Spring Security should completely ignore URLs starting with /resources/
 * 				.antMatchers(&quot;/resources/**&quot;);
 * 	}
 *
 * 	&#064;Override
 * 	protected void configure(HttpSecurity http) throws Exception {
 * 		http.authorizeRequests().antMatchers(&quot;/public/**&quot;).permitAll().anyRequest()
 * 				.hasRole(&quot;USER&quot;).and()
 * 				// Possibly more configuration ...
 * 				.formLogin() // enable form based log in
 * 				// set permitAll for all URLs associated with Form Login
 * 				.permitAll();
 * 	}
 *
 * 	&#064;Override
 * 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 * 		auth
 * 		// enable in memory based authentication with a user named &quot;user&quot; and &quot;admin&quot;
 * 		.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;)
 * 				.and().withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
 * 	}
 *
 * 	// Possibly more overridden methods ...
 * }
 * </pre>
 *
 * @see WebSecurityConfigurer
 * @see WebSecurityConfigurerAdapter
 *
 * @author Rob Winch
 * @since 3.2
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ WebSecurityConfiguration.class,
		SpringWebMvcImportSelector.class,
		OAuth2ImportSelector.class })
@EnableGlobalAuthentication
@Configuration
public @interface EnableWebSecurity {

	/**
	 * Controls debugging support for Spring Security. Default is false.
	 * @return if true, enables debug support with Spring Security
	 */
	boolean debug() default false;
}
```

WebSecurityConfiguration就是Security安全的配制类。在这个配制类中就生成了过滤器链。

### 1.7 查看WebSecurityConfiguration源码

```java
package org.springframework.security.config.annotation.web.configuration;

import java.util.List;
import java.util.Map;
import javax.servlet.Filter;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


/**
 * Uses a {@link WebSecurity} to create the {@link FilterChainProxy} that performs the web
 * based security for Spring Security. It then exports the necessary beans. Customizations
 * can be made to {@link WebSecurity} by extending {@link WebSecurityConfigurerAdapter}
 * and exposing it as a {@link Configuration} or implementing
 * {@link WebSecurityConfigurer} and exposing it as a {@link Configuration}. This
 * configuration is imported when using {@link EnableWebSecurity}.
 *
 * @see EnableWebSecurity
 * @see WebSecurity
 *
 * @author Rob Winch
 * @author Keesun Baik
 * @since 3.2
 */
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration implements ImportAware, BeanClassLoaderAware {
	private WebSecurity webSecurity;

	private Boolean debugEnabled;

	private List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers;

	private ClassLoader beanClassLoader;

	@Autowired(required = false)
	private ObjectPostProcessor<Object> objectObjectPostProcessor;

	@Bean
	public static DelegatingApplicationListener delegatingApplicationListener() {
		return new DelegatingApplicationListener();
	}

	@Bean
	@DependsOn(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
	public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler() {
		return webSecurity.getExpressionHandler();
	}

	/**
	 * Creates the Spring Security Filter Chain
	 * @return the {@link Filter} that represents the security filter chain
	 * @throws Exception
	 */
	@Bean(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
	public Filter springSecurityFilterChain() throws Exception {
		boolean hasConfigurers = webSecurityConfigurers != null
				&& !webSecurityConfigurers.isEmpty();
		if (!hasConfigurers) {
			WebSecurityConfigurerAdapter adapter = objectObjectPostProcessor
					.postProcess(new WebSecurityConfigurerAdapter() {
					});
			webSecurity.apply(adapter);
		}
		return webSecurity.build();
	}
......  
}
```

在这个类中有个方法springSecurityFilterChain()，这个方法返回的就是过滤器链。这个Bean的名称就是springSecurityFilterChain,在这个方法中，核心生成的过滤器链的方法就是webSecurity.build()方法。那就继续跟踪webSecurity.build();

接下来的代码，使用断点调试模式，进入，断点放在 webSecurity.build();这行，或者之前。

### 1.8 来到了AbstractSecurityBuilder的build方法

```java
package org.springframework.security.config.annotation;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A base {@link SecurityBuilder} that ensures the object being built is only built one
 * time.
 *
 * @param <O> the type of Object that is being built
 *
 * @author Rob Winch
 *
 */
public abstract class AbstractSecurityBuilder<O> implements SecurityBuilder<O> {
	private AtomicBoolean building = new AtomicBoolean();

	private O object;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.config.annotation.SecurityBuilder#build()
	 */
	public final O build() throws Exception {
		if (this.building.compareAndSet(false, true)) {
			this.object = doBuild();
			return this.object;
		}
		throw new AlreadyBuiltException("This object has already been built");
	}

	/**
	 * Gets the object that was built. If it has not been built yet an Exception is
	 * thrown.
	 *
	 * @return the Object that was built
	 */
	public final O getObject() {
		if (!this.building.get()) {
			throw new IllegalStateException("This object has not been built");
		}
		return this.object;
	}

	/**
	 * Subclasses should implement this to perform the build.
	 *
	 * @return the object that should be returned by {@link #build()}.
	 *
	 * @throws Exception if an error occurs
	 */
	protected abstract O doBuild() throws Exception;
}

```

### 1.9 来到AbstractConfiguredSecurityBuilder的doBuild方法

```java
package org.springframework.security.config.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * <p>
 * A base {@link SecurityBuilder} that allows {@link SecurityConfigurer} to be applied to
 * it. This makes modifying the {@link SecurityBuilder} a strategy that can be customized
 * and broken up into a number of {@link SecurityConfigurer} objects that have more
 * specific goals than that of the {@link SecurityBuilder}.
 * </p>
 *
 * <p>
 * For example, a {@link SecurityBuilder} may build an {@link DelegatingFilterProxy}, but
 * a {@link SecurityConfigurer} might populate the {@link SecurityBuilder} with the
 * filters necessary for session management, form based login, authorization, etc.
 * </p>
 *
 * @see WebSecurity
 *
 * @author Rob Winch
 *
 * @param <O> The object that this builder returns
 * @param <B> The type of this builder (that is returned by the base class)
 */
public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>>
		extends AbstractSecurityBuilder<O> {
	private final Log logger = LogFactory.getLog(getClass());

	private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<>();
	......

	/**
	 * Executes the build using the {@link SecurityConfigurer}'s that have been applied
	 * using the following steps:
	 *
	 * <ul>
	 * <li>Invokes {@link #beforeInit()} for any subclass to hook into</li>
	 * <li>Invokes {@link SecurityConfigurer#init(SecurityBuilder)} for any
	 * {@link SecurityConfigurer} that was applied to this builder.</li>
	 * <li>Invokes {@link #beforeConfigure()} for any subclass to hook into</li>
	 * <li>Invokes {@link #performBuild()} which actually builds the Object</li>
	 * </ul>
	 */
	@Override
	protected final O doBuild() throws Exception {
		synchronized (configurers) {
			buildState = BuildState.INITIALIZING;

			beforeInit();
             //初始化操作
			init();

			buildState = BuildState.CONFIGURING;

			beforeConfigure();
             //执行配制操作，
			configure();

			buildState = BuildState.BUILDING;
			//构建过滤器链。则进入了WebSecurity的performBuild方法，
			O result = performBuild();

			buildState = BuildState.BUILT;

			return result;
		}
	}

	/**
	 * Invoked prior to invoking each {@link SecurityConfigurer#init(SecurityBuilder)}
	 * method. Subclasses may override this method to hook into the lifecycle without
	 * using a {@link SecurityConfigurer}.
	 */
	protected void beforeInit() throws Exception {
	}

	/**
	 * Invoked prior to invoking each
	 * {@link SecurityConfigurer#configure(SecurityBuilder)} method. Subclasses may
	 * override this method to hook into the lifecycle without using a
	 * {@link SecurityConfigurer}.
	 */
	protected void beforeConfigure() throws Exception {
	}

	/**
	 * Subclasses must implement this method to build the object that is being returned.
	 *
	 * @return the Object to be buit or null if the implementation allows it
	 */
	protected abstract O performBuild() throws Exception;

	@SuppressWarnings("unchecked")
	private void init() throws Exception {
        //获取配制信息
		Collection<SecurityConfigurer<O, B>> configurers = getConfigurers();

         //遍历拿到的自定义配制信息，进行初始化操作，即来到了WebSecurityConfigurerAdapter的init，具体请查看
         //WebSecurityConfigurerAdapter的init方法
		for (SecurityConfigurer<O, B> configurer : configurers) {
			configurer.init((B) this);
		}

		for (SecurityConfigurer<O, B> configurer : configurersAddedInInitializing) {
			configurer.init((B) this);
		}
	}

	@SuppressWarnings("unchecked")
	private void configure() throws Exception {
        //首先还是拿到自定义的配制信息
		Collection<SecurityConfigurer<O, B>> configurers = getConfigurers();
		//执行配制类的配制方法。
		for (SecurityConfigurer<O, B> configurer : configurers) {
            //在自定义的配制实现中，有一个方法configure(WebSecurity web)，调用此方法。
			configurer.configure((B) this);
		}
	}

	private Collection<SecurityConfigurer<O, B>> getConfigurers() {
		List<SecurityConfigurer<O, B>> result = new ArrayList<>();
         //重点关注下this.configurers，这个信息，这个就是我们为SpringSecurit编写的配制类的信息，即实现了
         //WebSecurityConfigurerAdapter的实现.即可拿到所有自定义本所的安全配制信息。
		for (List<SecurityConfigurer<O, B>> configs : this.configurers.values()) {
			result.addAll(configs);
		}
		return result;
	}

	/**
	 * Determines if the object is unbuilt.
	 * @return true, if unbuilt else false
	 */
	private boolean isUnbuilt() {
		synchronized (configurers) {
			return buildState == BuildState.UNBUILT;
		}
	}

	/**
	 * The build state for the application
	 *
	 * @author Rob Winch
	 * @since 3.2
	 */
	private enum BuildState {
		/**
		 * This is the state before the {@link Builder#build()} is invoked
		 */
		UNBUILT(0),

		/**
		 * The state from when {@link Builder#build()} is first invoked until all the
		 * {@link SecurityConfigurer#init(SecurityBuilder)} methods have been invoked.
		 */
		INITIALIZING(1),

		/**
		 * The state from after all {@link SecurityConfigurer#init(SecurityBuilder)} have
		 * been invoked until after all the
		 * {@link SecurityConfigurer#configure(SecurityBuilder)} methods have been
		 * invoked.
		 */
		CONFIGURING(2),

		/**
		 * From the point after all the
		 * {@link SecurityConfigurer#configure(SecurityBuilder)} have completed to just
		 * after {@link AbstractConfiguredSecurityBuilder#performBuild()}.
		 */
		BUILDING(3),

		/**
		 * After the object has been completely built.
		 */
		BUILT(4);

		private final int order;

		BuildState(int order) {
			this.order = order;
		}

		public boolean isInitializing() {
			return INITIALIZING.order == order;
		}

		/**
		 * Determines if the state is CONFIGURING or later
		 * @return
		 */
		public boolean isConfigured() {
			return order >= CONFIGURING.order;
		}
	}
}

```

### 1.10 查看WebSecurityConfigurerAdapter配制初始化

```java
package org.springframework.security.config.annotation.web.configuration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/**
 * Provides a convenient base class for creating a {@link WebSecurityConfigurer}
 * instance. The implementation allows customization by overriding methods.
 *
 * <p>
 * Will automatically apply the result of looking up
 * {@link AbstractHttpConfigurer} from {@link SpringFactoriesLoader} to allow
 * developers to extend the defaults.
 * To do this, you must create a class that extends AbstractHttpConfigurer and then create a file in the classpath at "META-INF/spring.factories" that looks something like:
 * </p>
 * <pre>
 * org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer = sample.MyClassThatExtendsAbstractHttpConfigurer
 * </pre>
 * If you have multiple classes that should be added you can use "," to separate the values. For example:
 *
 * <pre>
 * org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer = sample.MyClassThatExtendsAbstractHttpConfigurer, sample.OtherThatExtendsAbstractHttpConfigurer
 * </pre>
 *
 * @see EnableWebSecurity
 *
 * @author Rob Winch
 */
@Order(100)
public abstract class WebSecurityConfigurerAdapter implements
		WebSecurityConfigurer<WebSecurity> {
	......
	
    /**
	 * Creates the {@link HttpSecurity} or returns the current instance
	 *
	 * ] * @return the {@link HttpSecurity}
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final HttpSecurity getHttp() throws Exception {
		if (http != null) {
			return http;
		}

		AuthenticationEventPublisher eventPublisher = getAuthenticationEventPublisher();
		localConfigureAuthenticationBldr.authenticationEventPublisher(eventPublisher);

		AuthenticationManager authenticationManager = authenticationManager();
		authenticationBuilder.parentAuthenticationManager(authenticationManager);
		Map<Class<?>, Object> sharedObjects = createSharedObjects();

		http = new HttpSecurity(objectPostProcessor, authenticationBuilder,
				sharedObjects);
		if (!disableDefaults) {
			// @formatter:off
			http
                  //此处可以看出，在默认的配制中，是开启csrf防护的
             	  //经过此行的代码后，将会在configurers中添加一个配制类        
                  //org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
				.csrf().and()
                  //添加第一个过滤器WebAsyncManagerIntegrationFilter
				.addFilter(new WebAsyncManagerIntegrationFilter())
                  //org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
                  //将一个异常处理的配制类加入到configurers中
				.exceptionHandling().and()
				.headers().and()
				.sessionManagement().and()
				.securityContext().and()
				.requestCache().and()
				.anonymous().and()
				.servletApi().and()
				.apply(new DefaultLoginPageConfigurer<>()).and()
				.logout();
			// @formatter:on
			ClassLoader classLoader = this.context.getClassLoader();
			List<AbstractHttpConfigurer> defaultHttpConfigurers =
					SpringFactoriesLoader.loadFactories(AbstractHttpConfigurer.class, classLoader);

			for (AbstractHttpConfigurer configurer : defaultHttpConfigurers) {
				http.apply(configurer);
			}
		}
        //执行默认载加后，的本所信息，可查看《默认加载完成后的配制信息》
        //此处将调用自定义的安全配制类，传递的是HttpSecurity的参数。
		configure(http);
        //执行完所有参数后，可查看《配制完成后的信息》
		return http;
	}
	
	public void init(final WebSecurity web) throws Exception {
         //获取一个HTTP的安全配制对象，定义了默认值。
		final HttpSecurity http = getHttp();
        //将构建的HttpSecurity对象加入到WebSecurity对象中，从这可以了解，WebSecurity是比HttpSecurity范围更大概念。
        //从这就可以体现出来。
        //在WebSecurity对象中，有一个属性名称为securityFilterChainBuilders的集合，将HttpSecurity对象加入此集合中
		web.addSecurityFilterChainBuilder(http).postBuildAction(() -> {
			FilterSecurityInterceptor securityInterceptor = http
					.getSharedObject(FilterSecurityInterceptor.class);
			web.securityInterceptor(securityInterceptor);
		});
	}

}
```

在HttpSecurity这个对象中，需要重点关注两个属性，一个是filters，另外一个是configurers,

这个filters表示就是过滤器，而configurers是一个配制的信息，通过这个配制，可以生成这个filter

![image-20230615104525491](D:\work\learn\learn-md\spring-security\img\image-20230615104525491.png)

默认加载完成后的配制信息

![image-20230615110109654](D:\work\learn\learn-md\spring-security\img\image-20230615110109654.png)

配制完成后的信息

![image-20230615110705934](D:\work\learn\learn-md\spring-security\img\image-20230615110705934.png)

从以上的init方法走完了。回到AbstractConfiguredSecurityBuilder的doBuild方法

### 1.11 查看WebSecurity的performBuild方法。

```java
package org.springframework.security.config.annotation.web.builders;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.debug.DebugFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * <p>
 * The {@link WebSecurity} is created by {@link WebSecurityConfiguration} to create the
 * {@link FilterChainProxy} known as the Spring Security Filter Chain
 * (springSecurityFilterChain). The springSecurityFilterChain is the {@link Filter} that
 * the {@link DelegatingFilterProxy} delegates to.
 * </p>
 *
 * <p>
 * Customizations to the {@link WebSecurity} can be made by creating a
 * {@link WebSecurityConfigurer} or more likely by overriding
 * {@link WebSecurityConfigurerAdapter}.
 * </p>
 *
 * @see EnableWebSecurity
 * @see WebSecurityConfiguration
 *
 * @author Rob Winch
 * @author Evgeniy Cheban
 * @since 3.2
 */
public final class WebSecurity extends
		AbstractConfiguredSecurityBuilder<Filter, WebSecurity> implements
		SecurityBuilder<Filter>, ApplicationContextAware {
......

	@Override
	protected Filter performBuild() throws Exception {
		Assert.state(
				!securityFilterChainBuilders.isEmpty(),
				() -> "At least one SecurityBuilder<? extends SecurityFilterChain> needs to be specified. "
						+ "Typically this done by adding a @Configuration that extends WebSecurityConfigurerAdapter. "
						+ "More advanced users can invoke "
						+ WebSecurity.class.getSimpleName()
						+ ".addSecurityFilterChainBuilder directly");
		//初始大小为排除资源的大小和过滤器链配制的大小之和
    	int chainSize = ignoredRequests.size() + securityFilterChainBuilders.size();
		List<SecurityFilterChain> securityFilterChains = new ArrayList<>(
				chainSize);
    	//将排除的资源加入至过滤器链中。
		for (RequestMatcher ignoredRequest : ignoredRequests) {
			securityFilterChains.add(new DefaultSecurityFilterChain(ignoredRequest));
		}
    	//securityFilterChainBuilders中的对象是一个HttpSecurity对象，将此对象中的configurers对象构建到过滤器连中。
		for (SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder : securityFilterChainBuilders) {
             //进入build方法查看，便又来到了，AbstractSecurityBuilder的build方法，只是此对象已经转换为了HttpSecurity
			securityFilterChains.add(securityFilterChainBuilder.build());
		}
		FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
		if (httpFirewall != null) {
			filterChainProxy.setFirewall(httpFirewall);
		}
		filterChainProxy.afterPropertiesSet();

		Filter result = filterChainProxy;
		if (debugEnabled) {
			logger.warn("\n\n"
					+ "********************************************************************\n"
					+ "**********        Security debugging is enabled.       *************\n"
					+ "**********    This may include sensitive information.  *************\n"
					+ "**********      Do not use in a production system!     *************\n"
					+ "********************************************************************\n\n");
			result = new DebugFilter(filterChainProxy);
		}
		postBuildAction.run();
		return result;
	}  
    
......
}
```

### 1.12 继续跟踪securityFilterChainBuilder.build()

```java
package org.springframework.security.config.annotation;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A base {@link SecurityBuilder} that ensures the object being built is only built one
 * time.
 *
 * @param <O> the type of Object that is being built
 *
 * @author Rob Winch
 *
 */
public abstract class AbstractSecurityBuilder<O> implements SecurityBuilder<O> {
	private AtomicBoolean building = new AtomicBoolean();

	private O object;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.config.annotation.SecurityBuilder#build()
	 */
	public final O build() throws Exception {
		if (this.building.compareAndSet(false, true)) {
			this.object = doBuild();
			return this.object;
		}
		throw new AlreadyBuiltException("This object has already been built");
	}

......
}

```

### 1.13 继续跟踪doBuild()方法

```java
package org.springframework.security.config.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * <p>
 * A base {@link SecurityBuilder} that allows {@link SecurityConfigurer} to be applied to
 * it. This makes modifying the {@link SecurityBuilder} a strategy that can be customized
 * and broken up into a number of {@link SecurityConfigurer} objects that have more
 * specific goals than that of the {@link SecurityBuilder}.
 * </p>
 *
 * <p>
 * For example, a {@link SecurityBuilder} may build an {@link DelegatingFilterProxy}, but
 * a {@link SecurityConfigurer} might populate the {@link SecurityBuilder} with the
 * filters necessary for session management, form based login, authorization, etc.
 * </p>
 *
 * @see WebSecurity
 *
 * @author Rob Winch
 *
 * @param <O> The object that this builder returns
 * @param <B> The type of this builder (that is returned by the base class)
 */
public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>>
		extends AbstractSecurityBuilder<O> {
......
	@Override
	protected final O doBuild() throws Exception {
		synchronized (configurers) {
			buildState = BuildState.INITIALIZING;

			beforeInit();
			init();

			buildState = BuildState.CONFIGURING;

			beforeConfigure();
             //重点关注configure方法
			configure();

			buildState = BuildState.BUILDING;

			O result = performBuild();

			buildState = BuildState.BUILT;

			return result;
		}
	}
    
	@SuppressWarnings("unchecked")
	private void configure() throws Exception {
        //可查看《配制列表》
		Collection<SecurityConfigurer<O, B>> configurers = getConfigurers();
		//然后调用每个实现的configure方法。
		for (SecurityConfigurer<O, B> configurer : configurers) {
			configurer.configure((B) this);
		}
	}
......
}
```

配制列表

![image-20230615121738234](D:\work\learn\learn-md\spring-security\img\image-20230615121738234.png)



### 1.14 安全配制实现-CsrfConfigurer

用于CSRS的防护

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfLogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.session.InvalidSessionAccessDeniedHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * Adds
 * <a href="https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)" >CSRF</a>
 * protection for the methods as specified by
 * {@link #requireCsrfProtectionMatcher(RequestMatcher)}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link CsrfFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * <ul>
 * <li>{@link ExceptionHandlingConfigurer#accessDeniedHandler(AccessDeniedHandler)} is
 * used to determine how to handle CSRF attempts</li>
 * <li>{@link InvalidSessionStrategy}</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class CsrfConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<CsrfConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		CsrfFilter filter = new CsrfFilter(this.csrfTokenRepository);
		RequestMatcher requireCsrfProtectionMatcher = getRequireCsrfProtectionMatcher();
		if (requireCsrfProtectionMatcher != null) {
			filter.setRequireCsrfProtectionMatcher(requireCsrfProtectionMatcher);
		}
		AccessDeniedHandler accessDeniedHandler = createAccessDeniedHandler(http);
		if (accessDeniedHandler != null) {
			filter.setAccessDeniedHandler(accessDeniedHandler);
		}
		LogoutConfigurer<H> logoutConfigurer = http.getConfigurer(LogoutConfigurer.class);
		if (logoutConfigurer != null) {
			logoutConfigurer
					.addLogoutHandler(new CsrfLogoutHandler(this.csrfTokenRepository));
		}
		SessionManagementConfigurer<H> sessionConfigurer = http
				.getConfigurer(SessionManagementConfigurer.class);
		if (sessionConfigurer != null) {
			sessionConfigurer.addSessionAuthenticationStrategy(
					getSessionAuthenticationStrategy());
		}
		filter = postProcess(filter);
		http.addFilter(filter);
	}
......
}
```

### 1.15 安全配制实现-ExceptionHandlingConfigurer

用于进行异常的处理.

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.LinkedHashMap;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.RequestMatcherDelegatingAccessDeniedHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Adds exception handling for Spring Security related exceptions to an application. All
 * properties have reasonable defaults, so no additional configuration is required other
 * than applying this
 * {@link org.springframework.security.config.annotation.SecurityConfigurer}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link ExceptionTranslationFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>If no explicit {@link RequestCache}, is provided a {@link RequestCache} shared
 * object is used to replay the request after authentication is successful</li>
 * <li>{@link AuthenticationEntryPoint} - see
 * {@link #authenticationEntryPoint(AuthenticationEntryPoint)}</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class ExceptionHandlingConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<ExceptionHandlingConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		AuthenticationEntryPoint entryPoint = getAuthenticationEntryPoint(http);
		ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(
				entryPoint, getRequestCache(http));
		AccessDeniedHandler deniedHandler = getAccessDeniedHandler(http);
		exceptionTranslationFilter.setAccessDeniedHandler(deniedHandler);
		exceptionTranslationFilter = postProcess(exceptionTranslationFilter);
		http.addFilter(exceptionTranslationFilter);
	}
......
}
```

### 1.16 安全配制实现-HeadersConfigurer 

向请求的Header中添加相应的信息,可在http标签内部使用security:headers来控制

```java
package org.springframework.security.config.annotation.web.configurers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.*;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * <p>
 * Adds the Security HTTP headers to the response. Security HTTP headers is activated by
 * default when using {@link WebSecurityConfigurerAdapter}'s default constructor.
 * </p>
 *
 * <p>
 * The default headers include are:
 * </p>
 *
 * <pre>
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * X-Content-Type-Options: nosniff
 * Strict-Transport-Security: max-age=31536000 ; includeSubDomains
 * X-Frame-Options: DENY
 * X-XSS-Protection: 1; mode=block
 * </pre>
 *
 * @author Rob Winch
 * @author Tim Ysewyn
 * @author Joe Grandja
 * @author Eddú Meléndez
 * @author Vedran Pavic
 * @since 3.2
 */
public class HeadersConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<HeadersConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		HeaderWriterFilter headersFilter = createHeaderWriterFilter();
		http.addFilter(headersFilter);
	}
......
}
```

### 1.17 安全配制实现-SessionManagementConfigurer

securityContextRepository限制同一用户开启多个会话的数量

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Allows configuring session management.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link SessionManagementFilter}</li>
 * <li>{@link ConcurrentSessionFilter} if there are restrictions on how many concurrent
 * sessions a user can have</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are created:
 *
 * <ul>
 * <li>{@link RequestCache}</li>
 * <li>{@link SecurityContextRepository}</li>
 * <li>{@link SessionManagementConfigurer}</li>
 * <li>{@link InvalidSessionStrategy}</li>
 * </ul>
 *
 * <h2>Shared Objects Used</h2>
 *
 * <ul>
 * <li>{@link SecurityContextRepository}</li>
 * <li>{@link AuthenticationTrustResolver} is optionally used to populate the
 * {@link HttpSessionSecurityContextRepository} and {@link SessionManagementFilter}</li>
 * </ul>
 *
 * @author Rob Winch
 * @author Onur Kagan Ozcan
 * @since 3.2
 * @see SessionManagementFilter
 * @see ConcurrentSessionFilter
 */
public final class SessionManagementConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<SessionManagementConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		SecurityContextRepository securityContextRepository = http
				.getSharedObject(SecurityContextRepository.class);
		SessionManagementFilter sessionManagementFilter = new SessionManagementFilter(
				securityContextRepository, getSessionAuthenticationStrategy(http));
		if (this.sessionAuthenticationErrorUrl != null) {
			sessionManagementFilter.setAuthenticationFailureHandler(
					new SimpleUrlAuthenticationFailureHandler(
							this.sessionAuthenticationErrorUrl));
		}
		InvalidSessionStrategy strategy = getInvalidSessionStrategy();
		if (strategy != null) {
			sessionManagementFilter.setInvalidSessionStrategy(strategy);
		}
		AuthenticationFailureHandler failureHandler = getSessionAuthenticationFailureHandler();
		if (failureHandler != null) {
			sessionManagementFilter.setAuthenticationFailureHandler(failureHandler);
		}
		AuthenticationTrustResolver trustResolver = http
				.getSharedObject(AuthenticationTrustResolver.class);
		if (trustResolver != null) {
			sessionManagementFilter.setTrustResolver(trustResolver);
		}
		sessionManagementFilter = postProcess(sessionManagementFilter);

		http.addFilter(sessionManagementFilter);
		if (isConcurrentSessionControlEnabled()) {
			ConcurrentSessionFilter concurrentSessionFilter = createConcurrencyFilter(http);

			concurrentSessionFilter = postProcess(concurrentSessionFilter);
			http.addFilter(concurrentSessionFilter);
		}
	}
......
}
```

### 1.18 安全配制实现-SecurityContextConfigurer

SecurityContextPersistenceFilter主要是使用SecurityContextRepository在session中保存 或更新一个SecurityContext，并将SecurityContext给以后的过滤器使用，来为后续ﬁﬁlter

建立所需的上下文。SecurityContext中存储了当前用户的认证以及权限信息。

```java
package org.springframework.security.config.annotation.web.configurers;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * Allows persisting and restoring of the {@link SecurityContext} found on the
 * {@link SecurityContextHolder} for each request by configuring the
 * {@link SecurityContextPersistenceFilter}. All properties have reasonable defaults, so
 * no additional configuration is required other than applying this
 * {@link org.springframework.security.config.annotation.SecurityConfigurer}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link SecurityContextPersistenceFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>If {@link SessionManagementConfigurer}, is provided and set to always, then the
 * {@link SecurityContextPersistenceFilter#setForceEagerSessionCreation(boolean)} will be
 * set to true.</li>
 * <li>{@link SecurityContextRepository} must be set and is used on
 * {@link SecurityContextPersistenceFilter}.</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class SecurityContextConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<SecurityContextConfigurer<H>, H> {
......
	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {

		SecurityContextRepository securityContextRepository = http
				.getSharedObject(SecurityContextRepository.class);
		if (securityContextRepository == null) {
			securityContextRepository = new HttpSessionSecurityContextRepository();
		}
		SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter(
				securityContextRepository);
		SessionManagementConfigurer<?> sessionManagement = http
				.getConfigurer(SessionManagementConfigurer.class);
		SessionCreationPolicy sessionCreationPolicy = sessionManagement == null ? null
				: sessionManagement.getSessionCreationPolicy();
		if (SessionCreationPolicy.ALWAYS == sessionCreationPolicy) {
			securityContextFilter.setForceEagerSessionCreation(true);
		}
		securityContextFilter = postProcess(securityContextFilter);
		http.addFilter(securityContextFilter);
	}
......
}
```



### 1.19-安全配制实现-RequestCacheConfigurer

通过HttpSessionRequestCache内部维护了一个RequestCache，用于缓存

HttpServletRequest

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/**
 * Adds request cache for Spring Security. Specifically this ensures that requests that
 * are saved (i.e. after authentication is required) are later replayed. All properties
 * have reasonable defaults, so no additional configuration is required other than
 * applying this {@link org.springframework.security.config.annotation.SecurityConfigurer}
 * .
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link RequestCacheAwareFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>If no explicit {@link RequestCache}, is provided a {@link RequestCache} shared
 * object is used to replay the request after authentication is successful</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 * @see RequestCache
 */
public final class RequestCacheConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<RequestCacheConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		RequestCache requestCache = getRequestCache(http);
		RequestCacheAwareFilter requestCacheFilter = new RequestCacheAwareFilter(
				requestCache);
		requestCacheFilter = postProcess(requestCacheFilter);
		http.addFilter(requestCacheFilter);
	}
......
}
```

### 1.20 安全配制实现-AnonymousConfigurer

当SecurityContextHolder中认证信息为空,则会创建一个匿名用户存入到

SecurityContextHolder中。spring security为了兼容未登录的访问，也走了一套认证流程， 只不过是一个匿名的身份。

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Configures Anonymous authentication (i.e. populate an {@link Authentication} that
 * represents an anonymous user instead of having a null value) for an
 * {@link HttpSecurity}. Specifically this will configure an
 * {@link AnonymousAuthenticationFilter} and an {@link AnonymousAuthenticationProvider}.
 * All properties have reasonable defaults, so no additional configuration is required
 * other than applying this {@link SecurityConfigurer}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class AnonymousConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<AnonymousConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		authenticationFilter.afterPropertiesSet();
		http.addFilter(authenticationFilter);
	}
......
}
```

### 1.21 安全配制实现-ServletApiConfigurer

针对ServletRequest进行了一次包装，使得request具有更加丰富的API

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * Implements select methods from the {@link HttpServletRequest} using the
 * {@link SecurityContext} from the {@link SecurityContextHolder}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link SecurityContextHolderAwareRequestFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * <ul>
 * <li>{@link AuthenticationTrustResolver} is optionally used to populate the
 * {@link SecurityContextHolderAwareRequestFilter}</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class ServletApiConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<ServletApiConfigurer<H>, H> {
......
	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {
		securityContextRequestFilter.setAuthenticationManager(http
				.getSharedObject(AuthenticationManager.class));
		ExceptionHandlingConfigurer<H> exceptionConf = http
				.getConfigurer(ExceptionHandlingConfigurer.class);
		AuthenticationEntryPoint authenticationEntryPoint = exceptionConf == null ? null
				: exceptionConf.getAuthenticationEntryPoint(http);
		securityContextRequestFilter
				.setAuthenticationEntryPoint(authenticationEntryPoint);
		LogoutConfigurer<H> logoutConf = http.getConfigurer(LogoutConfigurer.class);
		List<LogoutHandler> logoutHandlers = logoutConf == null ? null : logoutConf
				.getLogoutHandlers();
		securityContextRequestFilter.setLogoutHandlers(logoutHandlers);
		AuthenticationTrustResolver trustResolver = http
				.getSharedObject(AuthenticationTrustResolver.class);
		if (trustResolver != null) {
			securityContextRequestFilter.setTrustResolver(trustResolver);
		}
		ApplicationContext context = http.getSharedObject(ApplicationContext.class);
		if (context != null) {
			String[] grantedAuthorityDefaultsBeanNames = context.getBeanNamesForType(GrantedAuthorityDefaults.class);
			if (grantedAuthorityDefaultsBeanNames.length == 1) {
				GrantedAuthorityDefaults grantedAuthorityDefaults = context.getBean(grantedAuthorityDefaultsBeanNames[0], GrantedAuthorityDefaults.class);
				securityContextRequestFilter.setRolePrefix(grantedAuthorityDefaults.getRolePrefix());
			}
		}
		securityContextRequestFilter = postProcess(securityContextRequestFilter);
		http.addFilter(securityContextRequestFilter);
	}
......
}
```

### 1.22 安全配制实现-ServletApiConfigurer

针对ServletRequest进行了一次包装，使得request具有更加丰富的API

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * Implements select methods from the {@link HttpServletRequest} using the
 * {@link SecurityContext} from the {@link SecurityContextHolder}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link SecurityContextHolderAwareRequestFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created.
 *
 * <h2>Shared Objects Used</h2>
 *
 * <ul>
 * <li>{@link AuthenticationTrustResolver} is optionally used to populate the
 * {@link SecurityContextHolderAwareRequestFilter}</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class ServletApiConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<ServletApiConfigurer<H>, H> {
	private SecurityContextHolderAwareRequestFilter securityContextRequestFilter = new SecurityContextHolderAwareRequestFilter();

......
	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {
		securityContextRequestFilter.setAuthenticationManager(http
				.getSharedObject(AuthenticationManager.class));
		ExceptionHandlingConfigurer<H> exceptionConf = http
				.getConfigurer(ExceptionHandlingConfigurer.class);
		AuthenticationEntryPoint authenticationEntryPoint = exceptionConf == null ? null
				: exceptionConf.getAuthenticationEntryPoint(http);
		securityContextRequestFilter
				.setAuthenticationEntryPoint(authenticationEntryPoint);
		LogoutConfigurer<H> logoutConf = http.getConfigurer(LogoutConfigurer.class);
		List<LogoutHandler> logoutHandlers = logoutConf == null ? null : logoutConf
				.getLogoutHandlers();
		securityContextRequestFilter.setLogoutHandlers(logoutHandlers);
		AuthenticationTrustResolver trustResolver = http
				.getSharedObject(AuthenticationTrustResolver.class);
		if (trustResolver != null) {
			securityContextRequestFilter.setTrustResolver(trustResolver);
		}
		ApplicationContext context = http.getSharedObject(ApplicationContext.class);
		if (context != null) {
			String[] grantedAuthorityDefaultsBeanNames = context.getBeanNamesForType(GrantedAuthorityDefaults.class);
			if (grantedAuthorityDefaultsBeanNames.length == 1) {
				GrantedAuthorityDefaults grantedAuthorityDefaults = context.getBean(grantedAuthorityDefaultsBeanNames[0], GrantedAuthorityDefaults.class);
				securityContextRequestFilter.setRolePrefix(grantedAuthorityDefaults.getRolePrefix());
			}
		}
		securityContextRequestFilter = postProcess(securityContextRequestFilter);
		http.addFilter(securityContextRequestFilter);
	}
......

}
```



### 1.23 安全配制实现-DefaultLoginPageConfigurer

org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter

如果没有在配置文件中指定认证页面，则由该过滤器生成一个默认认证页面。

org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter

由此过滤器可以生产一个默认的退出登录页面

```java
package org.springframework.security.config.annotation.web.configurers;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Adds a Filter that will generate a login page if one is not specified otherwise when
 * using {@link WebSecurityConfigurerAdapter}.
 *
 * <p>
 * By default an
 * {@link org.springframework.security.web.access.channel.InsecureChannelProcessor} and a
 * {@link org.springframework.security.web.access.channel.SecureChannelProcessor} will be
 * registered.
 * </p>
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are conditionally populated
 *
 * <ul>
 * <li>{@link DefaultLoginPageGeneratingFilter} if the {@link FormLoginConfigurer} did not
 * have a login page specified</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared objects are created. isLogoutRequest <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>{@link org.springframework.security.web.PortMapper} is used to create the default
 * {@link org.springframework.security.web.access.channel.ChannelProcessor} instances</li>
 * <li>{@link FormLoginConfigurer} is used to determine if the
 * {@link DefaultLoginPageConfigurer} should be added and how to configure it.</li>
 * </ul>
 *
 * @see WebSecurityConfigurerAdapter
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class DefaultLoginPageConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<DefaultLoginPageConfigurer<H>, H> {

	private DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();

	private DefaultLogoutPageGeneratingFilter logoutPageGeneratingFilter = new DefaultLogoutPageGeneratingFilter();

	@Override
	public void init(H http) {
		Function<HttpServletRequest, Map<String, String>> hiddenInputs = request -> {
			CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			if (token == null) {
				return Collections.emptyMap();
			}
			return Collections.singletonMap(token.getParameterName(), token.getToken());
		};
		this.loginPageGeneratingFilter.setResolveHiddenInputs(hiddenInputs);
		this.logoutPageGeneratingFilter.setResolveHiddenInputs(hiddenInputs);
		http.setSharedObject(DefaultLoginPageGeneratingFilter.class,
				loginPageGeneratingFilter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {
		AuthenticationEntryPoint authenticationEntryPoint = null;
		ExceptionHandlingConfigurer<?> exceptionConf = http
				.getConfigurer(ExceptionHandlingConfigurer.class);
		if (exceptionConf != null) {
			authenticationEntryPoint = exceptionConf.getAuthenticationEntryPoint();
		}

		if (loginPageGeneratingFilter.isEnabled() && authenticationEntryPoint == null) {
			loginPageGeneratingFilter = postProcess(loginPageGeneratingFilter);
			http.addFilter(loginPageGeneratingFilter);
			http.addFilter(this.logoutPageGeneratingFilter);
		}
	}

}

```

### 1.24 安全配制实现-LogoutConfigurer

匹配URL为/logout的请求，实现用户退出,清除认证信息。

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * Adds logout support. Other {@link SecurityConfigurer} instances may invoke
 * {@link #addLogoutHandler(LogoutHandler)} in the {@link #init(HttpSecurityBuilder)} phase.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>
 * {@link LogoutFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * No shared Objects are created
 *
 * <h2>Shared Objects Used</h2>
 *
 * No shared objects are used.
 *
 * @author Rob Winch
 * @author Onur Kagan Ozcan
 * @since 3.2
 * @see RememberMeConfigurer
 */
public final class LogoutConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractHttpConfigurer<LogoutConfigurer<H>, H> {
......

	@Override
	public void configure(H http) throws Exception {
		LogoutFilter logoutFilter = createLogoutFilter(http);
		http.addFilter(logoutFilter);
	}
......
}
```

### 1.25 安全配制实现-AbstractAuthenticationFilterConfigurer

org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

表单认证操作全靠这个过滤器，默认匹配URL为/login且必须为POST请求。

```java
package org.springframework.security.config.annotation.web.configurers;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.openid.OpenIDLoginConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

/**
 * Base class for configuring {@link AbstractAuthenticationFilterConfigurer}. This is
 * intended for internal use only.
 *
 * @see FormLoginConfigurer
 * @see OpenIDLoginConfigurer
 *
 * @param T refers to "this" for returning the current configurer
 * @param F refers to the {@link AbstractAuthenticationProcessingFilter} that is being
 * built
 *
 * @author Rob Winch
 * @since 3.2
 */
public abstract class AbstractAuthenticationFilterConfigurer<B extends HttpSecurityBuilder<B>, T extends AbstractAuthenticationFilterConfigurer<B, T, F>, F extends AbstractAuthenticationProcessingFilter>
		extends AbstractHttpConfigurer<T, B> {

......

	@Override
	public void configure(B http) throws Exception {
		PortMapper portMapper = http.getSharedObject(PortMapper.class);
		if (portMapper != null) {
			authenticationEntryPoint.setPortMapper(portMapper);
		}

		RequestCache requestCache = http.getSharedObject(RequestCache.class);
		if (requestCache != null) {
			this.defaultSuccessHandler.setRequestCache(requestCache);
		}

		authFilter.setAuthenticationManager(http
				.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationSuccessHandler(successHandler);
		authFilter.setAuthenticationFailureHandler(failureHandler);
		if (authenticationDetailsSource != null) {
			authFilter.setAuthenticationDetailsSource(authenticationDetailsSource);
		}
		SessionAuthenticationStrategy sessionAuthenticationStrategy = http
				.getSharedObject(SessionAuthenticationStrategy.class);
		if (sessionAuthenticationStrategy != null) {
			authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
		}
		RememberMeServices rememberMeServices = http
				.getSharedObject(RememberMeServices.class);
		if (rememberMeServices != null) {
			authFilter.setRememberMeServices(rememberMeServices);
		}
		F filter = postProcess(authFilter);
		http.addFilter(filter);
	}

......
}

```

### 1.26 安全配制实现-RememberMeConfigurer

```java
package org.springframework.security.config.annotation.web.configurers;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

/**
 * Configures Remember Me authentication. This typically involves the user checking a box
 * when they enter their username and password that states to "Remember Me".
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link RememberMeAuthenticationFilter}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are populated
 *
 * <ul>
 * <li>
 * {@link HttpSecurity#authenticationProvider(org.springframework.security.authentication.AuthenticationProvider)}
 * is populated with a {@link RememberMeAuthenticationProvider}</li>
 * <li>{@link RememberMeServices} is populated as a shared object and available on
 * {@link HttpSecurity#getSharedObject(Class)}</li>
 * <li>{@link LogoutConfigurer#addLogoutHandler(LogoutHandler)} is used to add a logout
 * handler to clean up the remember me authentication.</li>
 * </ul>
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>{@link AuthenticationManager}</li>
 * <li>{@link UserDetailsService} if no {@link #userDetailsService(UserDetailsService)}
 * was specified.</li>
 * <li>{@link DefaultLoginPageGeneratingFilter} - if present will be populated with
 * information from the configuration</li>
 * </ul>
 *
 * @author Rob Winch
 * @author Eddú Meléndez
 * @since 3.2
 */
public final class RememberMeConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<RememberMeConfigurer<H>, H> {
......
	@Override
	public void configure(H http) {
		RememberMeAuthenticationFilter rememberMeFilter = new RememberMeAuthenticationFilter(
				http.getSharedObject(AuthenticationManager.class),
				this.rememberMeServices);
		if (this.authenticationSuccessHandler != null) {
			rememberMeFilter
					.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
		}
		rememberMeFilter = postProcess(rememberMeFilter);
		http.addFilter(rememberMeFilter);
	}

......
}

```

### 1.27 安全配制实现-AbstractInterceptUrlConfigurer

获取所配置资源访问的授权信息，根据SecurityContextHolder中存储的用户信息来决定其 是否有权限。

```java
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.config.annotation.web.configurers;

import java.util.List;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * A base class for configuring the {@link FilterSecurityInterceptor}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link FilterSecurityInterceptor}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are populated to allow other {@link SecurityConfigurer}'s
 * to customize:
 * <ul>
 * <li>{@link FilterSecurityInterceptor}</li>
 * </ul>
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>
 * {@link AuthenticationManager}
 * </li>
 * </ul>
 *
 *
 * @param <C> the AbstractInterceptUrlConfigurer
 * @param <H> the type of {@link HttpSecurityBuilder} that is being configured
 *
 * @author Rob Winch
 * @since 3.2
 * @see ExpressionUrlAuthorizationConfigurer
 * @see UrlAuthorizationConfigurer
 */
abstract class AbstractInterceptUrlConfigurer<C extends AbstractInterceptUrlConfigurer<C, H>, H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<C, H> {
......
	@Override
	public void configure(H http) throws Exception {
		FilterInvocationSecurityMetadataSource metadataSource = createMetadataSource(http);
		if (metadataSource == null) {
			return;
		}
		FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor(
				http, metadataSource, http.getSharedObject(AuthenticationManager.class));
		if (filterSecurityInterceptorOncePerRequest != null) {
			securityInterceptor
					.setObserveOncePerRequest(filterSecurityInterceptorOncePerRequest);
		}
		securityInterceptor = postProcess(securityInterceptor);
		http.addFilter(securityInterceptor);
		http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);
	}
......
}

```



### 1.28 安全配制实现-CorsConfigurer

csrf又称跨域请求伪造，SpringSecurity会对所有post请求验证是否包含系统生成的csrf的

token信息，如果不包含，则报错。起到防止csrf攻击的效果

```java

package org.springframework.security.config.annotation.web.configurers;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.util.ClassUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Adds {@link CorsFilter} to the Spring Security filter chain. If a bean by the name of
 * corsFilter is provided, that {@link CorsFilter} is used. Else if
 * corsConfigurationSource is defined, then that {@link CorsConfiguration} is used.
 * Otherwise, if Spring MVC is on the classpath a {@link HandlerMappingIntrospector} is
 * used.
 *
 * @param <H> the builder to return.
 * @author Rob Winch
 * @since 4.1.1
 */
public class CorsConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<CorsConfigurer<H>, H> {

......

	@Override
	public void configure(H http) {
		ApplicationContext context = http.getSharedObject(ApplicationContext.class);

		CorsFilter corsFilter = getCorsFilter(context);
		if (corsFilter == null) {
			throw new IllegalStateException(
					"Please configure either a " + CORS_FILTER_BEAN_NAME + " bean or a "
							+ CORS_CONFIGURATION_SOURCE_BEAN_NAME + "bean.");
		}
		http.addFilter(corsFilter);
	}

......
}

```







### 1.29 过滤器链

```
1. org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter

根据请求封装获取WebAsyncManager，从WebAsyncManager获取/注册的安全上下文可调
用处理拦截器

2. org.springframework.security.web.context.SecurityContextPersistenceFilter

SecurityContextPersistenceFilter主要是使用SecurityContextRepository在session中保存
或更新一个SecurityContext，并将SecurityContext给以后的过滤器使用，来为后续ﬁﬁlter

建立所需的上下文。SecurityContext中存储了当前用户的认证以及权限信息。

3. org.springframework.security.web.header.HeaderWriterFilter

向请求的Header中添加相应的信息,可在http标签内部使用security:headers来控制

4. org.springframework.security.web.csrf.CsrfFilter

csrf又称跨域请求伪造，SpringSecurity会对所有post请求验证是否包含系统生成的csrf的

token信息，如果不包含，则报错。起到防止csrf攻击的效果。

5. org.springframework.security.web.authentication.logout.LogoutFilter

</project>

<!--添加Spring Security 依赖 -->

<dependency>

     <groupId>org.springframework.boot</groupId>

     <artifactId>spring-boot-starter-security</artifactId>
</dependency>
匹配URL为/logout的请求，实现用户退出,清除认证信息。

6. org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

表单认证操作全靠这个过滤器，默认匹配URL为/login且必须为POST请求。

7. org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter

如果没有在配置文件中指定认证页面，则由该过滤器生成一个默认认证页面。

8. org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter

由此过滤器可以生产一个默认的退出登录页面

9. org.springframework.security.web.authentication.www.BasicAuthenticationFilter

此过滤器会自动解析HTTP请求中头部名字为Authentication，且以Basic开头的头信息。

10. org.springframework.security.web.savedrequest.RequestCacheAwareFilter

通过HttpSessionRequestCache内部维护了一个RequestCache，用于缓存

HttpServletRequest

11. org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter

针对ServletRequest进行了一次包装，使得request具有更加丰富的API

12. org.springframework.security.web.authentication.AnonymousAuthenticationFilter

当SecurityContextHolder中认证信息为空,则会创建一个匿名用户存入到

SecurityContextHolder中。spring security为了兼容未登录的访问，也走了一套认证流程，
只不过是一个匿名的身份。

13. org.springframework.security.web.session.SessionManagementFilter

securityContextRepository限制同一用户开启多个会话的数量

14. org.springframework.security.web.access.ExceptionTranslationFilter

异常转换过滤器位于整个springSecurityFilterChain的后方，用来转换整个链路中出现的异
常

15. org.springframework.security.web.access.intercept.FilterSecurityInterceptor

获取所配置资源访问的授权信息，根据SecurityContextHolder中存储的用户信息来决定其
是否有权限。

Spring Security默认加载15个过滤器, 但是随着配置可以增加或者删除一些过滤器.

```

最终生成的过滤器链

```
0 = {WebAsyncManagerIntegrationFilter@7326} 
1 = {SecurityContextPersistenceFilter@7219} 
2 = {HeaderWriterFilter@7173} 
3 = {CorsFilter@7320} 
4 = {CsrfFilter@7097} 
5 = {LogoutFilter@7269} 
6 = {ValidateCodeFilter@7327} 
7 = {UsernamePasswordAuthenticationFilter@7275} 
8 = {ConcurrentSessionFilter@7328} 
9 = {RequestCacheAwareFilter@7227} 
10 = {SecurityContextHolderAwareRequestFilter@7236} 
11 = {RememberMeAuthenticationFilter@7298} 
12 = {AnonymousAuthenticationFilter@7229} 
13 = {SessionManagementFilter@7191} 
14 = {ExceptionTranslationFilter@7158} 
15 = {FilterSecurityInterceptor@7314} 
```



![image-20230615151143999](.\img\image-20230615151143999.png)



## 2. 认证流程分析

### 2.1 认证流程

![image-20230615154839522](.\img\image-20230615154839522.png)

### 2.2 开始UsernamePasswordAuthenticationFilter

此处使用debug方式进行跟踪。attemptAuthentication是处理的入口，打此方法入口代码处。

```java
package org.springframework.security.web.authentication;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes an authentication form submission. Called
 * {@code AuthenticationProcessingFilter} prior to Spring Security 3.0.
 * <p>
 * Login forms must present two parameters to this filter: a username and password. The
 * default parameter names to use are contained in the static fields
 * {@link #SPRING_SECURITY_FORM_USERNAME_KEY} and
 * {@link #SPRING_SECURITY_FORM_PASSWORD_KEY}. The parameter names can also be changed by
 * setting the {@code usernameParameter} and {@code passwordParameter} properties.
 * <p>
 * This filter by default responds to the URL {@code /login}.
 *
 * @author Ben Alex
 * @author Colin Sampaleanu
 * @author Luke Taylor
 * @since 3.0
 */
public class UsernamePasswordAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;

	// ~ Constructors
	// ===================================================================================================

	public UsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}

	/**
	 * 认证流程的方法，此方法是何处被调用的？
	 */
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		//获取用户名
		String username = obtainUsername(request);
        //获取密码
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();
		//创建认证对象，此时仅传递了用户名和密码，将authenticated设置为false,未认证。
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
        //获取认证管理器，调用authenticate方法。
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Enables subclasses to override the composition of the password, such as by
	 * including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in addition to
	 * the password. A delimiter such as a pipe (|) should be used to separate the
	 * password and extended value(s). The <code>AuthenticationDao</code> will need to
	 * generate the expected password in a corresponding manner.
	 * </p>
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the password that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	@Nullable
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as by
	 * including additional values and a separator.
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	@Nullable
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the authentication
	 * request's details property.
	 *
	 * @param request that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details
	 * set
	 */
	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login
	 * request.
	 *
	 * @param usernameParameter the parameter name. Defaults to "username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login
	 * request..
	 *
	 * @param passwordParameter the parameter name. Defaults to "password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter. If set to
	 * true, and an authentication request is received which is not a POST request, an
	 * exception will be raised immediately and authentication will not be attempted. The
	 * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
	 * authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
}

```

这里有一个问题1，此attemptAuthentication是何处被调用的？

1、UsernamePasswordAuthenticationFilter继承自AbstractAuthenticationProcessingFilter。

那接下查看下AbstractAuthenticationProcessingFilter的代码

```java
package org.springframework.security.web.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Abstract processor of browser-based HTTP-based authentication requests.
 *
 * <h3>Authentication Process</h3>
 *
 * The filter requires that you set the <tt>authenticationManager</tt> property. An
 * <tt>AuthenticationManager</tt> is required to process the authentication request tokens
 * created by implementing classes.
 * <p>
 * This filter will intercept a request and attempt to perform authentication from that
 * request if the request matches the
 * {@link #setRequiresAuthenticationRequestMatcher(RequestMatcher)}.
 * <p>
 * Authentication is performed by the
 * {@link #attemptAuthentication(HttpServletRequest, HttpServletResponse)
 * attemptAuthentication} method, which must be implemented by subclasses.
 *
 * <h4>Authentication Success</h4>
 *
 * If authentication is successful, the resulting {@link Authentication} object will be
 * placed into the <code>SecurityContext</code> for the current thread, which is
 * guaranteed to have already been created by an earlier filter.
 * <p>
 * The configured {@link #setAuthenticationSuccessHandler(AuthenticationSuccessHandler)
 * AuthenticationSuccessHandler} will then be called to take the redirect to the
 * appropriate destination after a successful login. The default behaviour is implemented
 * in a {@link SavedRequestAwareAuthenticationSuccessHandler} which will make use of any
 * <tt>DefaultSavedRequest</tt> set by the <tt>ExceptionTranslationFilter</tt> and
 * redirect the user to the URL contained therein. Otherwise it will redirect to the
 * webapp root "/". You can customize this behaviour by injecting a differently configured
 * instance of this class, or by using a different implementation.
 * <p>
 * See the
 * {@link #successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
 * method for more information.
 *
 * <h4>Authentication Failure</h4>
 *
 * If authentication fails, it will delegate to the configured
 * {@link AuthenticationFailureHandler} to allow the failure information to be conveyed to
 * the client. The default implementation is {@link SimpleUrlAuthenticationFailureHandler}
 * , which sends a 401 error code to the client. It may also be configured with a failure
 * URL as an alternative. Again you can inject whatever behaviour you require here.
 *
 * <h4>Event Publication</h4>
 *
 * If authentication is successful, an {@link InteractiveAuthenticationSuccessEvent} will
 * be published via the application context. No events will be published if authentication
 * was unsuccessful, because this would generally be recorded via an
 * {@code AuthenticationManager}-specific application event.
 *
 * <h4>Session Authentication</h4>
 *
 * The class has an optional {@link SessionAuthenticationStrategy} which will be invoked
 * immediately after a successful call to {@code attemptAuthentication()}. Different
 * implementations
 * {@link #setSessionAuthenticationStrategy(SessionAuthenticationStrategy) can be
 * injected} to enable things like session-fixation attack prevention or to control the
 * number of simultaneous sessions a principal may have.
 *
 * @author Ben Alex
 * @author Luke Taylor
 */
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
......
	/**
	 * Invokes the
	 * {@link #requiresAuthentication(HttpServletRequest, HttpServletResponse)
	 * requiresAuthentication} method to determine whether the request is for
	 * authentication and should be handled by this filter. If it is an authentication
	 * request, the
	 * {@link #attemptAuthentication(HttpServletRequest, HttpServletResponse)
	 * attemptAuthentication} will be invoked to perform the authentication. There are
	 * then three possible outcomes:
	 * <ol>
	 * <li>An <tt>Authentication</tt> object is returned. The configured
	 * {@link SessionAuthenticationStrategy} will be invoked (to handle any
	 * session-related behaviour such as creating a new session to protect against
	 * session-fixation attacks) followed by the invocation of
	 * {@link #successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
	 * method</li>
	 * <li>An <tt>AuthenticationException</tt> occurs during authentication. The
	 * {@link #unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)
	 * unsuccessfulAuthentication} method will be invoked</li>
	 * <li>Null is returned, indicating that the authentication process is incomplete. The
	 * method will then return immediately, assuming that the subclass has done any
	 * necessary work (such as redirects) to continue the authentication process. The
	 * assumption is that a later request will be received by this method where the
	 * returned <tt>Authentication</tt> object is not null.
	 * </ol>
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}

......
}

```

在AbstractAuthenticationProcessingFilter的代码中，可以看到这里有一个doFilter方法，所有过滤器链都会调用的一个方法，这里就调用了attemptAuthentication方法。

### 2.3 查看ProviderManager的authenticate方法

```java
package org.springframework.security.authentication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * Iterates an {@link Authentication} request through a list of
 * {@link AuthenticationProvider}s.
 *
 * <p>
 * <tt>AuthenticationProvider</tt>s are usually tried in order until one provides a
 * non-null response. A non-null response indicates the provider had authority to decide
 * on the authentication request and no further providers are tried. If a subsequent
 * provider successfully authenticates the request, the earlier authentication exception
 * is disregarded and the successful authentication will be used. If no subsequent
 * provider provides a non-null response, or a new <code>AuthenticationException</code>,
 * the last <code>AuthenticationException</code> received will be used. If no provider
 * returns a non-null response, or indicates it can even process an
 * <code>Authentication</code>, the <code>ProviderManager</code> will throw a
 * <code>ProviderNotFoundException</code>. A parent {@code AuthenticationManager} can also
 * be set, and this will also be tried if none of the configured providers can perform the
 * authentication. This is intended to support namespace configuration options though and
 * is not a feature that should normally be required.
 * <p>
 * The exception to this process is when a provider throws an
 * {@link AccountStatusException}, in which case no further providers in the list will be
 * queried.
 *
 * Post-authentication, the credentials will be cleared from the returned
 * {@code Authentication} object, if it implements the {@link CredentialsContainer}
 * interface. This behaviour can be controlled by modifying the
 * {@link #setEraseCredentialsAfterAuthentication(boolean)
 * eraseCredentialsAfterAuthentication} property.
 *
 * <h2>Event Publishing</h2>
 * <p>
 * Authentication event publishing is delegated to the configured
 * {@link AuthenticationEventPublisher} which defaults to a null implementation which
 * doesn't publish events, so if you are configuring the bean yourself you must inject a
 * publisher bean if you want to receive events. The standard implementation is
 * {@link DefaultAuthenticationEventPublisher} which maps common exceptions to events (in
 * the case of authentication failure) and publishes an
 * {@link org.springframework.security.authentication.event.AuthenticationSuccessEvent
 * AuthenticationSuccessEvent} if authentication succeeds. If you are using the namespace
 * then an instance of this bean will be used automatically by the <tt>&lt;http&gt;</tt>
 * configuration, so you will receive events from the web part of your application
 * automatically.
 * <p>
 * Note that the implementation also publishes authentication failure events when it
 * obtains an authentication result (or an exception) from the "parent"
 * {@code AuthenticationManager} if one has been set. So in this situation, the parent
 * should not generally be configured to publish events or there will be duplicates.
 *
 *
 * @author Ben Alex
 * @author Luke Taylor
 *
 * @see DefaultAuthenticationEventPublisher
 */
public class ProviderManager implements AuthenticationManager, MessageSourceAware,
		InitializingBean {
......

	/**
	 * Attempts to authenticate the passed {@link Authentication} object.
	 * <p>
	 * The list of {@link AuthenticationProvider}s will be successively tried until an
	 * <code>AuthenticationProvider</code> indicates it is capable of authenticating the
	 * type of <code>Authentication</code> object passed. Authentication will then be
	 * attempted with that <code>AuthenticationProvider</code>.
	 * <p>
	 * If more than one <code>AuthenticationProvider</code> supports the passed
	 * <code>Authentication</code> object, the first one able to successfully
	 * authenticate the <code>Authentication</code> object determines the
	 * <code>result</code>, overriding any possible <code>AuthenticationException</code>
	 * thrown by earlier supporting <code>AuthenticationProvider</code>s.
	 * On successful authentication, no subsequent <code>AuthenticationProvider</code>s
	 * will be tried.
	 * If authentication was not successful by any supporting
	 * <code>AuthenticationProvider</code> the last thrown
	 * <code>AuthenticationException</code> will be rethrown.
	 *
	 * @param authentication the authentication request object.
	 *
	 * @return a fully authenticated object including credentials.
	 *
	 * @throws AuthenticationException if authentication fails.
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Class<? extends Authentication> toTest = authentication.getClass();
		AuthenticationException lastException = null;
		AuthenticationException parentException = null;
		Authentication result = null;
		Authentication parentResult = null;
		boolean debug = logger.isDebugEnabled();
		
    	//获取所有的执行者，检查是哪个来处理。此处主要处理记住密码和匿名用户的处理。
    	//0 = {AnonymousAuthenticationProvider@11710} 
	    //1 = {RememberMeAuthenticationProvider@11711} 
		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}

			if (debug) {
				logger.debug("Authentication attempt using "
						+ provider.getClass().getName());
			}

			try {
				result = provider.authenticate(authentication);

				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			}
			catch (AccountStatusException | InternalAuthenticationServiceException e) {
				prepareException(e, authentication);
				// SEC-546: Avoid polling additional providers if auth failure is due to
				// invalid account status
				throw e;
			} catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result == null && parent != null) {
			// Allow the parent to try.
			try {
                  //调用父类的认证。
                  //父类的认证中，还是调用本方法。
                  //
				result = parentResult = parent.authenticate(authentication);
			}
			catch (ProviderNotFoundException e) {
				// ignore as we will throw below if no other exception occurred prior to
				// calling parent and the parent
				// may throw ProviderNotFound even though a provider in the child already
				// handled the request
			}
			catch (AuthenticationException e) {
				lastException = parentException = e;
			}
		}

		if (result != null) {
			if (eraseCredentialsAfterAuthentication
					&& (result instanceof CredentialsContainer)) {
				// Authentication is complete. Remove credentials and other secret data
				// from authentication
				((CredentialsContainer) result).eraseCredentials();
			}

			// If the parent AuthenticationManager was attempted and successful then it will publish an AuthenticationSuccessEvent
			// This check prevents a duplicate AuthenticationSuccessEvent if the parent AuthenticationManager already published it
			if (parentResult == null) {
				eventPublisher.publishAuthenticationSuccess(result);
			}
			return result;
		}

		// Parent was null, or didn't authenticate (or throw an exception).

		if (lastException == null) {
			lastException = new ProviderNotFoundException(messages.getMessage(
					"ProviderManager.providerNotFound",
					new Object[] { toTest.getName() },
					"No AuthenticationProvider found for {0}"));
		}

		// If the parent AuthenticationManager was attempted and failed then it will publish an AbstractAuthenticationFailureEvent
		// This check prevents a duplicate AbstractAuthenticationFailureEvent if the parent AuthenticationManager already published it
		if (parentException == null) {
			prepareException(lastException, authentication);
		}

		throw lastException;
	}

	@SuppressWarnings("deprecation")
	private void prepareException(AuthenticationException ex, Authentication auth) {
		eventPublisher.publishAuthenticationFailure(ex, auth);
	}

	/**
	 * Copies the authentication details from a source Authentication object to a
	 * destination one, provided the latter does not already have one set.
	 *
	 * @param source source authentication
	 * @param dest the destination authentication object
	 */
	private void copyDetails(Authentication source, Authentication dest) {
		if ((dest instanceof AbstractAuthenticationToken) && (dest.getDetails() == null)) {
			AbstractAuthenticationToken token = (AbstractAuthenticationToken) dest;

			token.setDetails(source.getDetails());
		}
	}

	public List<AuthenticationProvider> getProviders() {
		return providers;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void setAuthenticationEventPublisher(
			AuthenticationEventPublisher eventPublisher) {
		Assert.notNull(eventPublisher, "AuthenticationEventPublisher cannot be null");
		this.eventPublisher = eventPublisher;
	}

	/**
	 * If set to, a resulting {@code Authentication} which implements the
	 * {@code CredentialsContainer} interface will have its
	 * {@link CredentialsContainer#eraseCredentials() eraseCredentials} method called
	 * before it is returned from the {@code authenticate()} method.
	 *
	 * @param eraseSecretData set to {@literal false} to retain the credentials data in
	 * memory. Defaults to {@literal true}.
	 */
	public void setEraseCredentialsAfterAuthentication(boolean eraseSecretData) {
		this.eraseCredentialsAfterAuthentication = eraseSecretData;
	}

	public boolean isEraseCredentialsAfterAuthentication() {
		return eraseCredentialsAfterAuthentication;
	}

	private static final class NullEventPublisher implements AuthenticationEventPublisher {
		public void publishAuthenticationFailure(AuthenticationException exception,
				Authentication authentication) {
		}

		public void publishAuthenticationSuccess(Authentication authentication) {
		}
	}
}

```

### 2.4 调用父类的方法authenticate

```java
public class ProviderManager implements AuthenticationManager, MessageSourceAware,
		InitializingBean {
......
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Class<? extends Authentication> toTest = authentication.getClass();
		AuthenticationException lastException = null;
		AuthenticationException parentException = null;
		Authentication result = null;
		Authentication parentResult = null;
		boolean debug = logger.isDebugEnabled();
		
    	//获取所有的执行者，检查是哪个来处理。
    	//此处可以获得DaoAuthenticationProvider,此就是处理用户名和密码的认证。
		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}

			if (debug) {
				logger.debug("Authentication attempt using "
						+ provider.getClass().getName());
			}

			try {
                  //调用此方法进行认证。继续跟踪。
				result = provider.authenticate(authentication);

				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			}
			catch (AccountStatusException | InternalAuthenticationServiceException e) {
				prepareException(e, authentication);
				// SEC-546: Avoid polling additional providers if auth failure is due to
				// invalid account status
				throw e;
			} catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result == null && parent != null) {
			// Allow the parent to try.
			try {
                  //调用父类的认证。
                  //父类的认证中，还是调用本方法。
                  //
				result = parentResult = parent.authenticate(authentication);
			}
			catch (ProviderNotFoundException e) {
				// ignore as we will throw below if no other exception occurred prior to
				// calling parent and the parent
				// may throw ProviderNotFound even though a provider in the child already
				// handled the request
			}
			catch (AuthenticationException e) {
				lastException = parentException = e;
			}
		}

		if (result != null) {
			if (eraseCredentialsAfterAuthentication
					&& (result instanceof CredentialsContainer)) {
				// Authentication is complete. Remove credentials and other secret data
				// from authentication
				((CredentialsContainer) result).eraseCredentials();
			}

			// If the parent AuthenticationManager was attempted and successful then it will publish an AuthenticationSuccessEvent
			// This check prevents a duplicate AuthenticationSuccessEvent if the parent AuthenticationManager already published it
			if (parentResult == null) {
				eventPublisher.publishAuthenticationSuccess(result);
			}
			return result;
		}

		// Parent was null, or didn't authenticate (or throw an exception).

		if (lastException == null) {
			lastException = new ProviderNotFoundException(messages.getMessage(
					"ProviderManager.providerNotFound",
					new Object[] { toTest.getName() },
					"No AuthenticationProvider found for {0}"));
		}

		// If the parent AuthenticationManager was attempted and failed then it will publish an AbstractAuthenticationFailureEvent
		// This check prevents a duplicate AbstractAuthenticationFailureEvent if the parent AuthenticationManager already published it
		if (parentException == null) {
			prepareException(lastException, authentication);
		}

		throw lastException;
	}
......

}

```

### 2.5 查看DaoAuthenticationProvider的authenticate方法。

```java
package org.springframework.security.authentication.dao;


public abstract class AbstractUserDetailsAuthenticationProvider implements
		AuthenticationProvider, InitializingBean, MessageSourceAware {

......

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				() -> messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
    	//从缓存中获取，默认为NullUserCache，无缓存。
		UserDetails user = this.userCache.getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;

			try {
                 //查找用户信息，则调用DaoAuthenticationProvider的retrieveUser
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
				else {
					throw notFound;
				}
			}

			Assert.notNull(user,
					"retrieveUser returned null - a violation of the interface contract");
		}

		try {
             //进行状态的检查操作。
			preAuthenticationChecks.check(user);
             //比对用户传递的与查询出来的信息是否一致。
			additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
				preAuthenticationChecks.check(user);
				additionalAuthenticationChecks(user,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			else {
				throw exception;
			}
		}
         //后检查，用户凭证是否过期。
		postAuthenticationChecks.check(user);

    	//如果用户没有问题，则放入至缓存中。
		if (!cacheWasUsed) {
			this.userCache.putUserInCache(user);
		}

		Object principalToReturn = user;

		if (forcePrincipalAsString) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}
    
   
	protected abstract UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException;
......

}

```

### 2.6 DaoAuthenticationProvider的retrieveUser方法

```java
package org.springframework.security.authentication.dao;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details from a
 * {@link UserDetailsService}.
 *
 * @author Ben Alex
 * @author Rob Winch
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
......
	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		prepareTimingAttackProtection();
		try {
             //调用自定义实现的用户认证方法。
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
    
......
}
```

当用户获取到之后，则回到了AbstractUserDetailsAuthenticationProvider的authenticate方法。

### 2.7 DaoAuthenticationProvider的additionalAuthenticationChecks方法。

```java
package org.springframework.security.authentication.dao;


public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
......
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
         //获取密码
		String presentedPassword = authentication.getCredentials().toString();
		//进行密码对比。
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
	}

......
}
```

### 2.8 进入密码对比方法WebSecurityConfigurerAdapter的matches

```java
package org.springframework.security.config.annotation.web.configuration;


@Order(100)
public abstract class WebSecurityConfigurerAdapter implements
		WebSecurityConfigurer<WebSecurity> {
......

         @Override
		public boolean matches(CharSequence rawPassword,
			String encodedPassword) {
			return getPasswordEncoder().matches(rawPassword, encodedPassword);
		}
......
}
```

### 2.9 密码对比DelegatingPasswordEncoder的matches

```java
package org.springframework.security.crypto.password;


public class DelegatingPasswordEncoder implements PasswordEncoder {
......
    @Override
	public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {
		if (rawPassword == null && prefixEncodedPassword == null) {
			return true;
		}
    	//获取加密的类型
		String id = extractId(prefixEncodedPassword);
    	//拿到加密的对象
		PasswordEncoder delegate = this.idToPasswordEncoder.get(id);
		if (delegate == null) {
			return this.defaultPasswordEncoderForMatches
				.matches(rawPassword, prefixEncodedPassword);
		}
         //对密码进行加密操作。
		String encodedPassword = extractEncodedPassword(prefixEncodedPassword);
         //对比密码。
		return delegate.matches(rawPassword, encodedPassword);
	}
......
}
```

方法调用结束，则返回至DaoAuthenticationProvider的authenticate方法中。

### 2.10 调用DaoAuthenticationProvider的createSuccessAuthentication

```java
package org.springframework.security.authentication.dao;


public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
......
	@Override
	protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
				&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
    	//创建token对象
		return super.createSuccessAuthentication(principal, authentication, user);
	}
......
}

```

### 2.11 调用AbstractUserDetailsAuthenticationProvider的createSuccessAuthentication方法

```java
package org.springframework.security.authentication.dao;


public abstract class AbstractUserDetailsAuthenticationProvider implements
		AuthenticationProvider, InitializingBean, MessageSourceAware {


     protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		// Ensure we return the original credentials the user supplied,
		// so subsequent attempts are successful even with encoded passwords.
		// Also ensure we return the original getDetails(), so that future
		// authentication events after cache expiry contain the details
         //构建认证的TOKEN信息。
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				principal, authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());

		return result;
	}
....
}
```

### 2.12 查看新创建的UsernamePasswordAuthenticationToken对象

```java
package org.springframework.security.authentication;

public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
......
	public UsernamePasswordAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
    	//将认证标识改为true,标识为已经认证。
		super.setAuthenticated(true); // must use super, as we override
	}
......
}
```

此处认证成功，则回到了

### 2.13 进入AbstractAuthenticationProcessingFilter的successfulAuthentication方法

```java
package org.springframework.security.web.authentication;


public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
......
      protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}
		//将认证信息存储到上下文中
		SecurityContextHolder.getContext().setAuthentication(authResult);

		rememberMeServices.loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
		}
		//调用自定义的认证成功处理逻辑。
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}
......
}
```



## 3. CSRF流程的分析

### 3.1 CSRF的主要流程

![image-20230616163114646](./img\image-20230616163114646.png)



### 3.2 CsrfFilter源码开始

调试源码时，记得将csrf的防护打开。默认CSRF是开启的。

验证码时间记得调大点。



```java
package org.springframework.security.web.csrf;


public final class CsrfFilter extends OncePerRequestFilter {
	/**
	 * The default {@link RequestMatcher} that indicates if CSRF protection is required or
	 * not. The default is to ignore GET, HEAD, TRACE, OPTIONS and process all other
	 * requests.
	 */
	public static final RequestMatcher DEFAULT_CSRF_MATCHER = new DefaultRequiresCsrfMatcher();

	/**
	 * The attribute name to use when marking a given request as one that should not be filtered.
	 *
	 * To use, set the attribute on your {@link HttpServletRequest}:
	 * <pre>
	 * 	CsrfFilter.skipRequest(request);
	 * </pre>
	 */
	private static final String SHOULD_NOT_FILTER = "SHOULD_NOT_FILTER" + CsrfFilter.class.getName();

	private final Log logger = LogFactory.getLog(getClass());
	private final CsrfTokenRepository tokenRepository;
	private RequestMatcher requireCsrfProtectionMatcher = DEFAULT_CSRF_MATCHER;
	private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

	public CsrfFilter(CsrfTokenRepository csrfTokenRepository) {
		Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
		this.tokenRepository = csrfTokenRepository;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return TRUE.equals(request.getAttribute(SHOULD_NOT_FILTER));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
		request.setAttribute(HttpServletResponse.class.getName(), response);
		//首先加载Token，查看《加载Token-3.3》
        // 在第二次加载时，Token已经存在，则直接从Session中获取。也不会再次生成Token操作。
		CsrfToken csrfToken = this.tokenRepository.loadToken(request);
		final boolean missingToken = csrfToken == null;
		if (missingToken) {
             //首次没有token将生成一个token的信息。跟踪3.5-生成Token
			csrfToken = this.tokenRepository.generateToken(request);
             //将生成的一个token进行保存操作。跟踪3.8-保存Token
			this.tokenRepository.saveToken(csrfToken, request, response);
		}
        //将CSRF的Token放入至request域中。
		request.setAttribute(CsrfToken.class.getName(), csrfToken);
		request.setAttribute(csrfToken.getParameterName(), csrfToken);
		
        //判断当前请求是否需要做一个CSRF的防护。跟踪3.9-CSRF检查
        //在第一次的时候，此为Get请求，则跳过当前检查，执行doFilter放行。
        //然后就会到login.htmld页面，在页面中会加载csrf的页面参数。
        //在加载${_csrf.token}此参数时，实际上就是从Token中获取信息，
        //之前在源码中已经查看过保存的Token是一个SaveOnAccessCsrfToken对象，
        //去getToken方法打上断点看下,查看3.10-页面获取Token
        
        //当第二发起登录请求时，当前为Post的登录请求。则会被处理。
		if (!this.requireCsrfProtectionMatcher.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String actualToken = request.getHeader(csrfToken.getHeaderName());
		if (actualToken == null) {
			actualToken = request.getParameter(csrfToken.getParameterName());
		}
        //从请求的参数中获取到的Token与session中的Token对比。失败则执行自定义失败处理。
		if (!csrfToken.getToken().equals(actualToken)) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Invalid CSRF token found for "
						+ UrlUtils.buildFullRequestUrl(request));
			}
			if (missingToken) {
				this.accessDeniedHandler.handle(request, response,
						new MissingCsrfTokenException(actualToken));
			}
			else {
				this.accessDeniedHandler.handle(request, response,
						new InvalidCsrfTokenException(csrfToken, actualToken));
			}
			return;
		}

		filterChain.doFilter(request, response);
	}

	public static void skipRequest(HttpServletRequest request) {
		request.setAttribute(SHOULD_NOT_FILTER, TRUE);
	}

	/**
	 * Specifies a {@link RequestMatcher} that is used to determine if CSRF protection
	 * should be applied. If the {@link RequestMatcher} returns true for a given request,
	 * then CSRF protection is applied.
	 *
	 * <p>
	 * The default is to apply CSRF protection for any HTTP method other than GET, HEAD,
	 * TRACE, OPTIONS.
	 * </p>
	 *
	 * @param requireCsrfProtectionMatcher the {@link RequestMatcher} used to determine if
	 * CSRF protection should be applied.
	 */
	public void setRequireCsrfProtectionMatcher(
			RequestMatcher requireCsrfProtectionMatcher) {
		Assert.notNull(requireCsrfProtectionMatcher,
				"requireCsrfProtectionMatcher cannot be null");
		this.requireCsrfProtectionMatcher = requireCsrfProtectionMatcher;
	}

	/**
	 * Specifies a {@link AccessDeniedHandler} that should be used when CSRF protection
	 * fails.
	 *
	 * <p>
	 * The default is to use AccessDeniedHandlerImpl with no arguments.
	 * </p>
	 *
	 * @param accessDeniedHandler the {@link AccessDeniedHandler} to use
	 */
	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		Assert.notNull(accessDeniedHandler, "accessDeniedHandler cannot be null");
		this.accessDeniedHandler = accessDeniedHandler;
	}

	private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
		private final HashSet<String> allowedMethods = new HashSet<>(
				Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.springframework.security.web.util.matcher.RequestMatcher#matches(javax.
		 * servlet.http.HttpServletRequest)
		 */
		@Override
		public boolean matches(HttpServletRequest request) {
			return !this.allowedMethods.contains(request.getMethod());
		}
	}
}

```

### 3.3 加载Token-1-LazyCsrfTokenRepository的loadToken

```java
package org.springframework.security.web.csrf;


public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		return this.delegate.loadToken(request);
	}
......
}
```

### 3.4 加载Token-2-HttpSessionCsrfTokenRepository的loadToken

```java
package org.springframework.security.web.csrf;


public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
......
	public CsrfToken loadToken(HttpServletRequest request) {
    	//首先获取一个Session,这是第一次访问，此为null
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		return (CsrfToken) session.getAttribute(this.sessionAttributeName);
	}
......
}
```

回到CsrfFilter，继续。

### 3.5  生成Token-1-LazyCsrfTokenRepository的generateToken

```java
package org.springframework.security.web.csrf;

public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		return wrap(request, this.delegate.generateToken(request));
	}
......
}
```

### 3.6 生成Token-2-HttpSessionCsrfTokenRepository的generateToken

```java
package org.springframework.security.web.csrf;


public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
	private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

	private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

	private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class
			.getName().concat(".CSRF_TOKEN");

	private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

	private String headerName = DEFAULT_CSRF_HEADER_NAME;

	private String sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;

	public CsrfToken generateToken(HttpServletRequest request) {
        //通过源码可以发现，此this.headerName为X-CSRF-TOKEN，此parameterName为_csrf，
        //继续查看createNewToken方法。可以发现是通过UUID生成了随机的Token
		return new DefaultCsrfToken(this.headerName, this.parameterName,
				createNewToken());
	}
    
	private String createNewToken() {
		return UUID.randomUUID().toString();
	}
......
}
```

回到LazyCsrfTokenRepository的源码中。

### 3.7 生成Token-2-LazyCsrfTokenRepository的wrap

```java
package org.springframework.security.web.csrf;

public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		return wrap(request, this.delegate.generateToken(request));
	}
    
	private CsrfToken wrap(HttpServletRequest request, CsrfToken token) {
		HttpServletResponse response = getResponse(request);
		return new SaveOnAccessCsrfToken(this.delegate, request, response, token);
	}
......
}
```

回到CsrfFilter的源码中。

### 3.8 保存Token-1-LazyCsrfTokenRepository的saveToken

```java
package org.springframework.security.web.csrf;

public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	public void saveToken(CsrfToken token, HttpServletRequest request,
			HttpServletResponse response) {
    	//由于此处的Token已经不是空，则直接跳过执行。
		if (token == null) {
			this.delegate.saveToken(token, request, response);
		}
	}
......    
}
```

### 3.9-CSRF检查-AndRequestMatcher的matches

```java
package org.springframework.security.web.util.matcher;


public final class AndRequestMatcher implements RequestMatcher {
    
	private final List<RequestMatcher> requestMatchers;
......
	public boolean matches(HttpServletRequest request) {
		for (RequestMatcher matcher : requestMatchers) {
			if (logger.isDebugEnabled()) {
				logger.debug("Trying to match using " + matcher);
			}
            //通过查询在allowedMethods = {HashSet@10931}  size = 4
            // 0 = "TRACE"
            // 1 = "HEAD"
            // 2 = "GET"
            // 3 = "OPTIONS"
            //这4个值直接跳过，由于当前是一个GET请求，便不满足，返回false.
			if (!matcher.matches(request)) {
				logger.debug("Did not match");
				return false;
			}
		}
		logger.debug("All requestMatchers returned true");
		return true;
	}
......
}
```

回到CsrfFilter

### 3.10-页面获取Token-LazyCsrfTokenRepository的getToken

```java
package org.springframework.security.web.csrf;


public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	private static final class SaveOnAccessCsrfToken implements CsrfToken {
		public String getToken() {
			saveTokenIfNecessary();
			return this.delegate.getToken();
		}
        
		private void saveTokenIfNecessary() {
			if (this.tokenRepository == null) {
				return;
			}

			synchronized (this) {
				if (this.tokenRepository != null) {
                     //执行Token的保存操作。
					this.tokenRepository.saveToken(this.delegate, this.request,
							this.response);
					this.tokenRepository = null;
					this.request = null;
					this.response = null;
				}
			}
		}
	}
......
}
```

### 3.11 页面获取Token-2-HttpSessionCsrfTokenRepository的saveToken

```java
package org.springframework.security.web.csrf;


public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
......
	public void saveToken(CsrfToken token, HttpServletRequest request,
			HttpServletResponse response) {
		if (token == null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(this.sessionAttributeName);
			}
		}
    	//在之前已经创建了Session,则直接将当前的Token保存至Session中，至此页面就存在具体的csrf的值了。
		else {
			HttpSession session = request.getSession();
			session.setAttribute(this.sessionAttributeName, token);
		}
	}
......
}
```

当页面加载完成后，则输入用户名和密码，发起登录请求。



### 3.12 登录的CSRF处理-1-AbstractAuthenticationProcessingFilter的doFilter

```java
package org.springframework.security.web.authentication;


public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
......
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
            //执行认证
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
            //session的策略处理。
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}
......
}
```



### 3.13 登录的CSRF处理-2-CompositeSessionAuthenticationStrategy的onAuthentication

```java
package org.springframework.security.web.authentication.session;


public class CompositeSessionAuthenticationStrategy
		implements SessionAuthenticationStrategy {
......
	public void onAuthentication(Authentication authentication,
			HttpServletRequest request, HttpServletResponse response)
					throws SessionAuthenticationException {
    	//result = {ArrayList@12215}  size = 4
		// 0 = {ConcurrentSessionControlAuthenticationStrategy@12220} 
		//  1 = {ChangeSessionIdAuthenticationStrategy@12221} 
		//  2 = {RegisterSessionAuthenticationStrategy@12222} 
		//  3 = {CsrfAuthenticationStrategy@12223} 跟踪这个。
		for (SessionAuthenticationStrategy delegate : this.delegateStrategies) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Delegating to " + delegate);
			}
			delegate.onAuthentication(authentication, request, response);
		}
	}
......
    
}
```

### 3.14  登录的CSRF处理-3-CsrfAuthenticationStrategy的onAuthentication

```java
package org.springframework.security.web.csrf;

public final class CsrfAuthenticationStrategy implements SessionAuthenticationStrategy {
......
	public void onAuthentication(Authentication authentication,
			HttpServletRequest request, HttpServletResponse response)
					throws SessionAuthenticationException {
    	//从session中获取Token，当前肯定存在，不为null
		boolean containsToken = this.csrfTokenRepository.loadToken(request) != null;
		if (containsToken) {
            //将null保存至session中，即清空了csrf的值。继续跟踪
			this.csrfTokenRepository.saveToken(null, request, response);

			CsrfToken newToken = this.csrfTokenRepository.generateToken(request);
			this.csrfTokenRepository.saveToken(newToken, request, response);

			request.setAttribute(CsrfToken.class.getName(), newToken);
			request.setAttribute(newToken.getParameterName(), newToken);
		}
	}
......
}
```

### 3.15 登录的CSRF处理-4-LazyCsrfTokenRepository的saveToken

```java
package org.springframework.security.web.csrf;

public final class LazyCsrfTokenRepository implements CsrfTokenRepository {
......
	public void saveToken(CsrfToken token, HttpServletRequest request,
			HttpServletResponse response) {
    	//仅在为null的情况下执行保存操作。
		if (token == null) {
			this.delegate.saveToken(token, request, response);
		}
	}
......
}
```



### 3.16 登录的CSRF处理-5-HttpSessionCsrfTokenRepository的saveToken

```java
package org.springframework.security.web.csrf;


public final class HttpSessionCsrfTokenRepository implements CsrfTokenRepository {
......
	public void saveToken(CsrfToken token, HttpServletRequest request,
			HttpServletResponse response) {
    	//满足此条件，当前为空，进行会话的移除操作。
		if (token == null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(this.sessionAttributeName);
			}
		}
		else {
			HttpSession session = request.getSession();
			session.setAttribute(this.sessionAttributeName, token);
		}
	}
......
}
```





## 4. 记住我的流程分析

### 4.1 用户登录时的记住密码功能。

在AbstractAuthenticationProcessingFilter的用户登录成功后。便会调用successfulAuthentication

```java
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
 ......

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}
		//认证成功后的方法调用。
		successfulAuthentication(request, response, chain, authResult);
	}
......
}
```





### 4.3 记住我登录-认证AbstractAuthenticationProcessingFilter的successfulAuthentication方法

```java
package org.springframework.security.web.authentication;


public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
......
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}
		//将认证的用户信息放入至SecurityContext中
		SecurityContextHolder.getContext().setAuthentication(authResult);
		//检查是不是记住我的
		rememberMeServices.loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
		}
		//登录成功的处理
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}
......
}
```



### 4.4 记住我登录-调用记住我的功能在登录的处理

```java
package org.springframework.security.web.authentication.rememberme;

public abstract class AbstractRememberMeServices implements RememberMeServices,
		InitializingBean, LogoutHandler {
	public static final String DEFAULT_PARAMETER = "remember-me";
	private String parameter = DEFAULT_PARAMETER;
	private boolean alwaysRemember;
......
	public final void loginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {
		//检查当前是否打开记住我的功能
		if (!rememberMeRequested(request, parameter)) {
			logger.debug("Remember-me login not requested.");
			return;
		}
		//在打开后记住我的功能后的逻辑处理。
		onLoginSuccess(request, response, successfulAuthentication);
	}
            
	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		if (alwaysRemember) {
			return true;
		}
		//从请求参数中获取remember-me这个参数。
		String paramValue = request.getParameter(parameter);
		
        //如果值为true,on,yes,1都表示打开。
		if (paramValue != null) {
			if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
					|| paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Did not send remember-me cookie (principal did not set parameter '"
					+ parameter + "')");
		}

		return false;
	}
......
}
```

### 4.5 记住我登录-PersistentTokenBasedRememberMeServices的onLoginSuccess方法

```java
package org.springframework.security.web.authentication.rememberme;


public class PersistentTokenBasedRememberMeServices extends AbstractRememberMeServices {
......
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();

		logger.debug("Creating new persistent login for user " + username);

		PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(
				username, generateSeriesData(), generateTokenData(), new Date());
		try {
            //进行TOKEN的保存操作，将调用JdbcTokenRepositoryImpl进行数据库存储。将在库里插入记录。
			tokenRepository.createNewToken(persistentToken);
            //添加cookie
			addCookie(persistentToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to save persistent token ", e);
		}
	}
	private void addCookie(PersistentRememberMeToken token, HttpServletRequest request,
			HttpServletResponse response) {
        //保存cookie,有效期为两周
		setCookie(new String[] { token.getSeries(), token.getTokenValue() },
				getTokenValiditySeconds(), request, response);
	}
......
}
```



### 4.6 记住我登录-AbstractRememberMeServices的setCookie

```java
package org.springframework.security.web.authentication.rememberme;


public abstract class AbstractRememberMeServices implements RememberMeServices,
		InitializingBean, LogoutHandler {
	public static final String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY = "remember-me";
	private String cookieName = SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;
            
	public static final int TWO_WEEKS_S = 1209600;
	private int tokenValiditySeconds = TWO_WEEKS_S;

	protected int getTokenValiditySeconds() {
		return tokenValiditySeconds;
	}

	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request,
			HttpServletResponse response) {
		String cookieValue = encodeCookie(tokens);
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath(getCookiePath(request));
		if (cookieDomain != null) {
			cookie.setDomain(cookieDomain);
		}
		if (maxAge < 1) {
			cookie.setVersion(1);
		}

		if (useSecureCookie == null) {
			cookie.setSecure(request.isSecure());
		}
		else {
			cookie.setSecure(useSecureCookie);
		}

		cookie.setHttpOnly(true);

		response.addCookie(cookie);
	}
	protected String encodeCookie(String[] cookieTokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cookieTokens.length; i++) {
			try
			{
				sb.append(URLEncoder.encode(cookieTokens[i], StandardCharsets.UTF_8.toString()));
			}
			catch (UnsupportedEncodingException e)
			{
				logger.error(e.getMessage(), e);
			}

			if (i < cookieTokens.length - 1) {
				sb.append(DELIMITER);
			}
		}

		String value = sb.toString();

		sb = new StringBuilder(new String(Base64.getEncoder().encode(value.getBytes())));

		while (sb.charAt(sb.length() - 1) == '=') {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}
......
}


```

至此，在登录时，就将记住我的相关信息保存到了数据库表persistent_logins和浏览器的COOKIE中

数据库中存储的记录



![image-20230617202943273](.\img\image-20230617202943273.png)

再来看看浏览器中存储的信息。

![image-20230617203506481](.\img\image-20230617203506481.png)

可以看出此信息与库中的不一致，这是因为经过了编码处理了，首先进行了，URL编码，再进行Base64编码。再在要还原，进行解码处理，先进行Base解码，再进行URL解码。

原始字符

```html
UTZIVjJ2N1ZHaDIyRERoTkJVV2tOZyUzRCUzRDpzTnlNQW42VWh2eFFuSnVFUTNrcG9nJTNEJTNE
```

经过Base64解码后可得

```html
Q6HV2v7VGh22DDhNBUWkNg%3D%3D:sNyMAn6UhvxQnJuEQ3kpog%3D%3D
```

再进行URL解码

```html
Q6HV2v7VGh22DDhNBUWkNg==:sNyMAn6UhvxQnJuEQ3kpog==
```

这样我们就发现与库中的记录一致了。

登录流程中的记住我功能结束。下面开始查看RememberMeAuthenticationFilter的处理。



### 4.7 记住我的处理-2-RememberMeAuthenticationFilter

```java
package org.springframework.security.web.authentication.rememberme;


public class RememberMeAuthenticationFilter extends GenericFilterBean implements
		ApplicationEventPublisherAware {
......
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //检查是否是记住我登录，如果是则完成自动登录，查看《4.8-自动登录》
			Authentication rememberMeAuth = rememberMeServices.autoLogin(request,
					response);

			if (rememberMeAuth != null) {
				// Attempt authenticaton via AuthenticationManager
				try {
                    //调用authenticationManager再次认证。
					rememberMeAuth = authenticationManager.authenticate(rememberMeAuth);

					// Store to SecurityContextHolder
                    //将认证信息重新放入至SecurityContext中
					SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);

					onSuccessfulAuthentication(request, response, rememberMeAuth);

					if (logger.isDebugEnabled()) {
						logger.debug("SecurityContextHolder populated with remember-me token: '"
								+ SecurityContextHolder.getContext().getAuthentication()
								+ "'");
					}

					// Fire event
					if (this.eventPublisher != null) {
						eventPublisher
								.publishEvent(new InteractiveAuthenticationSuccessEvent(
										SecurityContextHolder.getContext()
												.getAuthentication(), this.getClass()));
					}

					if (successHandler != null) {
						successHandler.onAuthenticationSuccess(request, response,
								rememberMeAuth);

						return;
					}

				}
				catch (AuthenticationException authenticationException) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"SecurityContextHolder not populated with remember-me token, as "
										+ "AuthenticationManager rejected Authentication returned by RememberMeServices: '"
										+ rememberMeAuth
										+ "'; invalidating remember-me token",
								authenticationException);
					}

					rememberMeServices.loginFail(request, response);

					onUnsuccessfulAuthentication(request, response,
							authenticationException);
				}
			}

			chain.doFilter(request, response);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("SecurityContextHolder not populated with remember-me token, as it already contained: '"
						+ SecurityContextHolder.getContext().getAuthentication() + "'");
			}

			chain.doFilter(request, response);
		}
	}
......
}
```

### 4.8 记住我的处理-2-AbstractRememberMeServices的autoLogin

```java
package org.springframework.security.web.authentication.rememberme;


public abstract class AbstractRememberMeServices implements RememberMeServices,
		InitializingBean, LogoutHandler {
	public static final String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY = "remember-me";
	private String cookieName = SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;
......
	public final Authentication autoLogin(HttpServletRequest request,
			HttpServletResponse response) {
    	//1,获取rememberMeCookie的信息。
		String rememberMeCookie = extractRememberMeCookie(request);

		if (rememberMeCookie == null) {
			return null;
		}

		logger.debug("Remember-me cookie detected");

		if (rememberMeCookie.length() == 0) {
			logger.debug("Cookie was empty");
			cancelCookie(request, response);
			return null;
		}

		UserDetails user = null;

		try {
            //进行Cookie的解码处理。
			String[] cookieTokens = decodeCookie(rememberMeCookie);
            //根据cookie完成自动登录。查看《4.9-记住我处理-processAutoLoginCookie》
			user = processAutoLoginCookie(cookieTokens, request, response);
            //用户状态检查
			userDetailsChecker.check(user);

			logger.debug("Remember-me cookie accepted");
			//创建认证成功的RememberMeAuthenticationToken并将认证状态设置为true
			return createSuccessfulAuthentication(request, user);
		}
		catch (CookieTheftException cte) {
			cancelCookie(request, response);
			throw cte;
		}
		catch (UsernameNotFoundException noUser) {
			logger.debug("Remember-me login was valid but corresponding user not found.",
					noUser);
		}
		catch (InvalidCookieException invalidCookie) {
			logger.debug("Invalid remember-me cookie: " + invalidCookie.getMessage());
		}
		catch (AccountStatusException statusInvalid) {
			logger.debug("Invalid UserDetails: " + statusInvalid.getMessage());
		}
		catch (RememberMeAuthenticationException e) {
			logger.debug(e.getMessage());
		}

		cancelCookie(request, response);
		return null;
	} 
   
	protected String extractRememberMeCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if ((cookies == null) || (cookies.length == 0)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}
	protected String[] decodeCookie(String cookieValue) throws InvalidCookieException {
		for (int j = 0; j < cookieValue.length() % 4; j++) {
			cookieValue = cookieValue + "=";
		}

		try {
			Base64.getDecoder().decode(cookieValue.getBytes());
		}
		catch (IllegalArgumentException e) {
			throw new InvalidCookieException(
					"Cookie token was not Base64 encoded; value was '" + cookieValue
							+ "'");
		}

		String cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));

		String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText,
				DELIMITER);

		for (int i = 0; i < tokens.length; i++)
		{
			try
			{
				tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8.toString());
			}
			catch (UnsupportedEncodingException e)
			{
				logger.error(e.getMessage(), e);
			}
		}

		return tokens;
	}
            
	protected Authentication createSuccessfulAuthentication(HttpServletRequest request,
			UserDetails user) {
		RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(key, user,
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		return auth;
	}

    
......
}
```

### 4.9-记住我处理-PersistentTokenBasedRememberMeServices-processAutoLoginCookie

```java
package org.springframework.security.web.authentication.rememberme;


public class PersistentTokenBasedRememberMeServices extends AbstractRememberMeServices {
......
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {

		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2
					+ " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}

		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];
		//从数据库中查询Token的信息
		PersistentRememberMeToken token = tokenRepository
				.getTokenForSeries(presentedSeries);
		//进行判空处理
		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}
		//进行对比值是否一致
		// We have a match for this user/series combination
		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete all logins for this user and throw
			// an exception to warn them.
			tokenRepository.removeUserTokens(token.getUsername());

			throw new CookieTheftException(
					messages.getMessage(
							"PersistentTokenBasedRememberMeServices.cookieStolen",
							"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
		}
		
    	//有效期检查
		if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System
				.currentTimeMillis()) {
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		// Token also matches, so login is valid. Update the token value, keeping the
		// *same* series number.
		if (logger.isDebugEnabled()) {
			logger.debug("Refreshing persistent login token for user '"
					+ token.getUsername() + "', series '" + token.getSeries() + "'");
		}
		
    	//创建一个新的Token。
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(
				token.getUsername(), token.getSeries(), generateTokenData(), new Date());

		try {
            //保存到数据库中
			tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(),
					newToken.getDate());
            //将新的token返回到cookie中。
			addCookie(newToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException(
					"Autologin failed due to data access problem");
		}
		//再次根据用户名调用UserDetailsService的自定义实现
		return getUserDetailsService().loadUserByUsername(token.getUsername());
	} 

......
}
```



## 5. 授权流程分析

FilterSecurityInterceptor是用来处理整个用户的授权流程的，也是距离用户API最后一个非常重要的过滤器链。



存在三种授权模式：

1. **AffiffiffirmativeBased** 基于肯定的逻辑：一票通过权
2. **ConsensusBased** 基于共识的逻辑：赞成票多于反对票则表示通过，反对票多于赞成票则抛出AccessDeniedException
3. **UnanimousBased**基于一致的逻辑：一票否决权



### 5.1 FilterSecurityInterceptor过滤器-认证通过

此过滤器，专门针对权限的校验，需要用户先登录，然后再进行鉴权处理。

那同样的调试代码，还是先登录，然后再点击一个请求到后端进行权限检查。



```java
package org.springframework.security.web.access.intercept;


public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements
		Filter {
	// ~ Static fields/initializers
	// =====================================================================================

	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

	// ~ Instance fields
	// ================================================================================================

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private boolean observeOncePerRequest = true;
    
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
    
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	
	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		if ((fi.getRequest() != null)
				&& (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
				&& observeOncePerRequest) {
			// filter already applied to this request and user wants us to observe
			// once-per-request handling, so don't re-do security checking
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}
		else {
			// first time this request being called, so perform security checking
			if (fi.getRequest() != null && observeOncePerRequest) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}
			
            //前置调用- 查看《5.2-前置调用》
			InterceptorStatusToken token = super.beforeInvocation(fi);

			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			}
			finally {
				super.finallyInvocation(token);
			}

			super.afterInvocation(token, null);
		}
	}

}

```



### 5.2-前置调用-beforeInvocation

```java
package org.springframework.security.access.intercept;

public abstract class AbstractSecurityInterceptor implements InitializingBean,
		ApplicationEventPublisherAware, MessageSourceAware {
......
	protected InterceptorStatusToken beforeInvocation(Object object) {
		Assert.notNull(object, "Object was null");
		final boolean debug = logger.isDebugEnabled();

		if (!getSecureObjectClass().isAssignableFrom(object.getClass())) {
			throw new IllegalArgumentException(
					"Security invocation attempted for object "
							+ object.getClass().getName()
							+ " but AbstractSecurityInterceptor only configured to support secure objects of type: "
							+ getSecureObjectClass());
		}
		
    	//查询所有的权限信息，即在系统启动时，
    	//this.obtainSecurityMetadataSource()返回的是在SecurityConfiguration中编写的加载所有权限
      	// permissionService.list()，然后保存到请求权限中
    	// 继续查看-《5.3-getAttributes方法》拿到权限信息。
		Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
				.getAttributes(object);

		if (attributes == null || attributes.isEmpty()) {
			if (rejectPublicInvocations) {
				throw new IllegalArgumentException(
						"Secure object invocation "
								+ object
								+ " was denied as public invocations are not allowed via this interceptor. "
								+ "This indicates a configuration error because the "
								+ "rejectPublicInvocations property is set to 'true'");
			}

			if (debug) {
				logger.debug("Public object - authentication not attempted");
			}

			publishEvent(new PublicInvocationEvent(object));

			return null; // no further work post-invocation
		}

		if (debug) {
			logger.debug("Secure object: " + object + "; Attributes: " + attributes);
		}

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			credentialsNotFound(messages.getMessage(
					"AbstractSecurityInterceptor.authenticationNotFound",
					"An Authentication object was not found in the SecurityContext"),
					object, attributes);
		}
		//获取当前用户的认证信息，查看《5.4-获取用户认证信息-authenticateIfRequired》
		Authentication authenticated = authenticateIfRequired();

		// Attempt authorization
		try {
            //调用决策管理器,查看《5.5-决策管理器-decide》默认使用AffirmativeBased，一票通过
			this.accessDecisionManager.decide(authenticated, object, attributes);
		}
		catch (AccessDeniedException accessDeniedException) {
			publishEvent(new AuthorizationFailureEvent(object, attributes, authenticated,
					accessDeniedException));

			throw accessDeniedException;
		}

		if (debug) {
			logger.debug("Authorization successful");
		}

		if (publishAuthorizationSuccess) {
			publishEvent(new AuthorizedEvent(object, attributes, authenticated));
		}

		// Attempt to run as a different user
		Authentication runAs = this.runAsManager.buildRunAs(authenticated, object,
				attributes);

		if (runAs == null) {
			if (debug) {
				logger.debug("RunAsManager did not change Authentication object");
			}

			// no further work post-invocation
			return new InterceptorStatusToken(SecurityContextHolder.getContext(), false,
					attributes, object);
		}
		else {
			if (debug) {
				logger.debug("Switching to RunAs Authentication: " + runAs);
			}

			SecurityContext origCtx = SecurityContextHolder.getContext();
			SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
			SecurityContextHolder.getContext().setAuthentication(runAs);

			// need to revert to token.Authenticated post-invocation
			return new InterceptorStatusToken(origCtx, true, attributes, object);
		}
	}

......
}
```

### 5.3-getAttributes方法

```java
package org.springframework.security.web.access.intercept;


public class DefaultFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
......
	public Collection<ConfigAttribute> getAttributes(Object object) {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
    	//根据当前请求的路径，获取当前路径所需的权限信息。
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}    

......
}
```

### 5.4-获取用户认证信息-authenticateIfRequired

```java
public abstract class AbstractSecurityInterceptor implements InitializingBean,
		ApplicationEventPublisherAware, MessageSourceAware {
......
	private Authentication authenticateIfRequired() {
    	//拿到用户认证的信息
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		//当前用户已经认证，则直接返回。
		if (authentication.isAuthenticated() && !alwaysReauthenticate) {
			if (logger.isDebugEnabled()) {
				logger.debug("Previously Authenticated: " + authentication);
			}

			return authentication;
		}

		authentication = authenticationManager.authenticate(authentication);

		// We don't authenticated.setAuthentication(true), because each provider should do
		// that
		if (logger.isDebugEnabled()) {
			logger.debug("Successfully Authenticated: " + authentication);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}    

......
}
```

![image-20230618155953013](.\img\image-20230618155953013.png)





### 5.5-决策管理器-decide

```java
package org.springframework.security.access.vote;

public class AffirmativeBased extends AbstractAccessDecisionManager {

	public AffirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
		super(decisionVoters);
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * This concrete implementation simply polls all configured
	 * {@link AccessDecisionVoter}s and grants access if any
	 * <code>AccessDecisionVoter</code> voted affirmatively. Denies access only if there
	 * was a deny vote AND no affirmative votes.
	 * <p>
	 * If every <code>AccessDecisionVoter</code> abstained from voting, the decision will
	 * be based on the {@link #isAllowIfAllAbstainDecisions()} property (defaults to
	 * false).
	 * </p>
	 *
	 * @param authentication the caller invoking the method
	 * @param object the secured object
	 * @param configAttributes the configuration attributes associated with the method
	 * being invoked
	 *
	 * @throws AccessDeniedException if access is denied
	 */
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
		int deny = 0;
		//目前，在Spring-security就只留下了一个WebExpressionVoter。
		for (AccessDecisionVoter voter : getDecisionVoters()) {
            //针对权限的表达式进行解析处理。
            //返回三种情况。0：弃权，-1：不通过，1：通过
			int result = voter.vote(authentication, object, configAttributes);

			if (logger.isDebugEnabled()) {
				logger.debug("Voter: " + voter + ", returned: " + result);
			}

			switch (result) {
            //通过，直接通过。
			case AccessDecisionVoter.ACCESS_GRANTED:
				return;
			//当决策不通过时，则计数++
			case AccessDecisionVoter.ACCESS_DENIED:
				deny++;

				break;

			default:
				break;
			}
		}
		
        //如果发现决策不通过的计数不为0，则响应异常
		if (deny > 0) {
			throw new AccessDeniedException(messages.getMessage(
					"AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}

		// To get this far, every AccessDecisionVoter abstained
		checkAllowIfAllAbstainDecisions();
	}
}

```





### 5.6- FilterSecurityInterceptor过滤器-认证不通过

```java
package org.springframework.security.web.access.intercept;


public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements
		Filter {
	// ~ Static fields/initializers
	// =====================================================================================

	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

	// ~ Instance fields
	// ================================================================================================

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private boolean observeOncePerRequest = true;
    
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
    
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	
	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		if ((fi.getRequest() != null)
				&& (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
				&& observeOncePerRequest) {
			// filter already applied to this request and user wants us to observe
			// once-per-request handling, so don't re-do security checking
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}
		else {
			// first time this request being called, so perform security checking
			if (fi.getRequest() != null && observeOncePerRequest) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}
			
            //前置调用- 查看《5.2-前置调用》,其他逻辑一致，会抛出一个AccessDeniedException的异常
            //紧接着，就会到达AbstractSecurityInterceptor的异常，会继续向外抛出
            //再就来到了ExceptionTranslationFilter处理--《5.7-ExceptionTranslationFilter的异常处理》
			InterceptorStatusToken token = super.beforeInvocation(fi);

			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			}
			finally {
				super.finallyInvocation(token);
			}

			super.afterInvocation(token, null);
		}
	}

}

```



### 5.7 ExceptionTranslationFilter的异常处理

```java
package org.springframework.security.web.access;

public class ExceptionTranslationFilter extends GenericFilterBean {
......
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try {
			chain.doFilter(request, response);

			logger.debug("Chain processed normally");
		}
		catch (IOException ex) {
			throw ex;
		}
		catch (Exception ex) {
			// Try to extract a SpringSecurityException from the stacktrace
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            //获取异常信息
			RuntimeException ase = (AuthenticationException) throwableAnalyzer
					.getFirstThrowableOfType(AuthenticationException.class, causeChain);
			if (ase == null) {
				ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(
						AccessDeniedException.class, causeChain);
			}
			
            //当前是一个未授权的异常，则进入这里处理
			if (ase != null) {
				if (response.isCommitted()) {
					throw new ServletException("Unable to handle the Spring Security Exception because the response is already committed.", ex);
				}
                //异常处理。
				handleSpringSecurityException(request, response, chain, ase);
			}
			else {
				// Rethrow ServletExceptions and RuntimeExceptions as-is
				if (ex instanceof ServletException) {
					throw (ServletException) ex;
				}
				else if (ex instanceof RuntimeException) {
					throw (RuntimeException) ex;
				}

				// Wrap other Exceptions. This shouldn't actually happen
				// as we've already covered all the possibilities for doFilter
				throw new RuntimeException(ex);
			}
		}
	}
	private void handleSpringSecurityException(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, RuntimeException exception)
			throws IOException, ServletException {
		if (exception instanceof AuthenticationException) {
			logger.debug(
					"Authentication exception occurred; redirecting to authentication entry point",
					exception);

			sendStartAuthentication(request, response, chain,
					(AuthenticationException) exception);
		}
        //当前一个未授权的异常，则进入这里处理
		else if (exception instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //当前已经登录非匿名用户。
			if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
				logger.debug(
						"Access is denied (user is " + (authenticationTrustResolver.isAnonymous(authentication) ? "anonymous" : "not fully authenticated") + "); redirecting to authentication entry point",
						exception);

				sendStartAuthentication(
						request,
						response,
						chain,
						new InsufficientAuthenticationException(
							messages.getMessage(
								"ExceptionTranslationFilter.insufficientAuthentication",
								"Full authentication is required to access this resource")));
			}
            //进入这里处理。
			else {
				logger.debug(
						"Access is denied (user is not anonymous); delegating to AccessDeniedHandler",
						exception);
				//此时的accessDeniedHandler是调用的自定义的权限不足的处理器，
				accessDeniedHandler.handle(request, response,
						(AccessDeniedException) exception);
			}
		}
	}
......
}
```

至此权限不足的异常流程处理完毕。
