package objectifysample;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectifyConfig {

  @Bean
  public FilterRegistrationBean<ObjectifyFilter> objectifyFilterRegistration() {
    final FilterRegistrationBean<ObjectifyFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new ObjectifyFilter());
    registration.addUrlPatterns("/*");
    registration.setOrder(1);
    return registration;
  }

  @Bean
  public ServletListenerRegistrationBean<ObjectifyListener> listenerRegistrationBean() {
    ServletListenerRegistrationBean<ObjectifyListener> bean =
        new ServletListenerRegistrationBean<>();
    bean.setListener(new ObjectifyListener());
    return bean;
  }

  @WebListener
  public class ObjectifyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      // case: gradle bootRun
//      ObjectifyService.init(new ObjectifyFactory(
//          DatastoreOptions.newBuilder()
//              .setHost("http://localhost:8484")
//              .setProjectId("my-project")
//              .build()
//              .getService()
//      ));
      // case: gradle appengineRun
      ObjectifyService.init(new ObjectifyFactory(
          DatastoreOptions.newBuilder().setHost("http://localhost:8484")
              .setProjectId("my-project")
              .build().getService(),
          new AppEngineMemcacheClientService()
      ));
      // case: memcache
//      ObjectifyService.init(new ObjectifyFactory(new AppEngineMemcacheClientService()));
      ObjectifyService.register(Item.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
  }


}
