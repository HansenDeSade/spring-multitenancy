package de.evelopment.multitenancy.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.PrintWriter;
import java.io.StringWriter;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "failure")
public class ErrorMessage {

  @XmlElement
  private String message;

  @XmlElement
  private String type;

  @XmlElement
  private String stacktrace;

  @XmlElement
  private String path;

  public ErrorMessage() {
  }

  public ErrorMessage(final String message, final Throwable t) {
    this(message, t, true);
  }

  public ErrorMessage(final String message, final Throwable t, boolean withStracktrace) {
    this.message = message;
    if (t != null) {
      type = t.getClass().getName();
      if (withStracktrace) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();
        stacktrace = sw.toString();
      }
    }
  }

  public String getMessage() {
    return message;
  }

  public String getType() {
    return type;
  }

  public String getStacktrace() {
    return stacktrace;
  }

  public void setPathByRequest(HttpServletRequest req) {
    this.path = req.getRequestURI() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }


}