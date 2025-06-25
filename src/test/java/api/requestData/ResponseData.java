package api.requestData;

import lombok.Getter;

@Getter
public class ResponseData {
  private String message;
  private String _id;
  public String getMessage() {
    return message;
  }

  public String get_id() {
    return _id;
  }



}
