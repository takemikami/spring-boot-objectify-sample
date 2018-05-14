package objectifysample;

import com.googlecode.objectify.ObjectifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/put")
  public String put() {
    ObjectifyService.ofy().save().entity(new Item("001", "name", "desc"));

    return "put";
  }

  @RequestMapping("/get")
  public String get() {
    Item e = ObjectifyService.ofy().cache(true).load().type(Item.class).id("001").now();

    return String.format("%s: %s - %s", e.getId(), e.getName(), e.getDescription());
  }


}
