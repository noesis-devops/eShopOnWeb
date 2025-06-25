package commons;

import commons.Exceptions.ContextException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The Context class represents a simple key-value storage for contextual information. It allows
 * storing and retrieving information based on a case-insensitive key.
 */
public class Context extends RuntimeException {

  /**
   * Internal map to store key-value pairs.
   */
  private final Map<String, Object> context = new HashMap<>();

  /**
   * Sets the value associated with the specified key. The key is stored in a case-insensitive
   * manner.
   *
   * @param key   The key to set the associated value.
   * @param value The value to be associated with the key.
   */
  public void set(String key, Object value) {
    try {
      var list = (List<?>) value;
      if (list instanceof ArrayList<?>) {
        this.context.put(key.toUpperCase(), value);
      } else {
        this.context.put(key.toUpperCase(), new ArrayList<>(list));
      }
    } catch (Exception _) {
      this.context.put(key.toUpperCase(), value);
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The value associated with the specified key as String.
   */
  public String getString(String key) {
    try {
      return (String) this.context.get(key.toUpperCase());
    } catch (Exception _) {
      throw new ContextException(key + " is not a string");
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The value associated with the specified key as int.
   */
  public int getInt(String key) {
    try {
      return (Integer) this.context.get(key.toUpperCase());
    } catch (Exception _) {
      throw new ContextException(key + " is not a integer");
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The value associated with the specified key as double.
   */
  public double getDouble(String key) {
    try {
      return (Double) this.context.get(key.toUpperCase());
    } catch (Exception _) {
      throw new ContextException(key + " is not a double");
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The boolean value associated with the specified key or {@code false} if the key is not
   * present.
   * @throws ContextException If the value associated with the key is not a boolean.
   */
  public boolean getBoolean(String key) {
    try {
      var value = (Boolean) this.context.get(key.toUpperCase());
      return Objects.nonNull(value) ? value : false;
    } catch (Exception _) {
      throw new ContextException(key + " is not a boolean");
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The value associated with the specified key as double.
   */
  public <T> T getObject(String key, Class<T> type) {
    try {
      if (Objects.isNull(this.context.get(key.toUpperCase()))) {
        set(key, type.getDeclaredConstructor().newInstance());
      }
      return type.cast(this.context.get(key.toUpperCase()));
    } catch (IllegalAccessException | NoSuchMethodException e) {
      throw new ContextException(type.getName() + " do not have a default constructor");
    } catch (Exception e) {
      throw new ContextException(key + " is not a " + type.getName());
    }
  }

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key The key to retrieve the associated value.
   * @return The value associated with the specified key as object.
   */
  public <T> ArrayList<T> getList(String key, Class<T> type) {
    try {
      if (Objects.isNull(this.context.get(key.toUpperCase()))) {
        set(key, new ArrayList<T>());
      }
      var list = (ArrayList<?>) this.context.get(key.toUpperCase());
      if (list.stream().allMatch(type::isInstance)) {
        @SuppressWarnings("unchecked")
        var converted = (ArrayList<T>) list;
        return converted;
      }
      throw new ContextException(key + " is not a " + type.getSimpleName() + " list");
    } catch (ContextException e) {
      throw e;
    } catch (Exception _) {
      throw new ContextException(key + " is not a list");
    }
  }


}
