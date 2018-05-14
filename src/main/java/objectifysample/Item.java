package objectifysample;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Cache(expirationSeconds=600)
@Entity
public class Item {

  public Item() {
  }

  public Item(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  @Id
  private String id;

  public String getId() {return id;}
  public void setId(String id) {this.id = id;}

  private String name;

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  private String description;

  public String getDescription() {return description;}
  public void setDescription(String description) {this.description = description;}

}
