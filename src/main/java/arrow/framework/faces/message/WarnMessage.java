package arrow.framework.faces.message;

import arrow.framework.faces.message.Message;

public class WarnMessage extends Message {
private final Severity severity = Severity.WARNING;

  public WarnMessage(final String messageCode, final String summaryKey, final Object... params)
  {
    super(messageCode, summaryKey, summaryKey + Message.DETAIL_SUFFIX, params);
  }

  @Override
  public Severity getSeverity()
  {
    return this.severity;
  }
  
  public static Message emp_009_cannot_send_mail(Object... params) {
    return Notification.createWarnMessage(MessageCode.emp_009_cannot_send_mail, MessageCode.emp_009_cannot_send_mail, params);
  }
}