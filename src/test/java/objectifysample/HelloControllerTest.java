package objectifysample;

import static org.junit.Assert.assertEquals;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloControllerTest {

  private static LocalDatastoreHelper helper = LocalDatastoreHelper.create(1.0);;

  private Closeable session;

  @BeforeClass
  public static void beforeClass() throws Exception {
    ObjectifyService.init(new ObjectifyFactory(helper.getOptions().getService()));
    ObjectifyService.register(Item.class);
    helper.start();
  }

  @AfterClass
  public static void afterClass() throws Exception {
    helper.stop();
  }

  @Before
  public void before() throws Exception {
    helper.reset();
    this.session = ObjectifyService.begin();
  }

  @After
  public void after() throws Exception {
    this.session.close();
  }

  @Test
  public void testPut() throws Exception {
    HelloController c = new HelloController();
    String s = c.put();

    assertEquals("001", ObjectifyService.ofy().cache(true).load().type(Item.class).id("001").now().getId());
  }

}
