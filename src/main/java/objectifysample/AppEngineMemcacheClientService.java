package objectifysample;

import com.google.appengine.api.memcache.MemcacheService.CasValues;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.cache.IdentifiableValue;
import com.googlecode.objectify.cache.MemcacheService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppEngineMemcacheClientService implements MemcacheService {

  com.google.appengine.api.memcache.MemcacheService appengineMemcache;

  public AppEngineMemcacheClientService() {
    this.appengineMemcache = MemcacheServiceFactory.getMemcacheService("objectify");
  }

  @Override
  public Object get(String key) {
    return this.appengineMemcache.get(key);
  }

  @Override
  public Map<String, IdentifiableValue> getIdentifiables(Collection<String> keys) {
    Map<String, IdentifiableValue> rtn = new HashMap<>();
    this.appengineMemcache.getIdentifiables(keys).forEach((k, v) -> {
          final IdentifiableValue iv = new AppEngineIdentifiableValue(new CasValues(v, null));
          rtn.put(k, iv);
        }
    );
    return rtn;
  }

  @Override
  public Map<String, Object> getAll(Collection<String> keys) {
    return this.appengineMemcache.getAll(keys);
  }

  @Override
  public void put(String key, Object thing) {
    this.appengineMemcache.put(key, thing);
  }

  @Override
  public void putAll(Map<String, Object> values) {
    this.appengineMemcache.putAll(values);
  }

  @Override
  public Set<String> putIfUntouched(Map<String, CasPut> values) {
    Map<String, CasValues> param = new HashMap<>();
    values.forEach((k, v) -> {
      param.put(k,
          new CasValues(((AppEngineIdentifiableValue) v.getIv()).getCasValues().getOldValue(),
              v.getNextToStore())
      );
    });
    return this.appengineMemcache.putIfUntouched(param);
  }

  @Override
  public void deleteAll(Collection<String> keys) {
    this.appengineMemcache.deleteAll(keys);
  }

  public class AppEngineIdentifiableValue implements IdentifiableValue {
    private CasValues casValues;
    public AppEngineIdentifiableValue(CasValues casValues){
      this.casValues = casValues;
    }

    @Override
    public Object getValue() {
      return this.casValues.getNewValue();
    }

    @Override
    public IdentifiableValue withValue(final Object value) {
      return new AppEngineIdentifiableValue(new CasValues(casValues.getOldValue(), casValues.getNewValue()));
    }

    public CasValues getCasValues() {
      return casValues;
    }
  }

}
