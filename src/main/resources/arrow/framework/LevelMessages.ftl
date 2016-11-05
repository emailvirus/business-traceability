package ${targetPackage};

import arrow.framework.faces.message.Message;

public class ${level}Message extends Message {
  private final Severity severity = Severity.<#if level!='Warn'>${level?upper_case}<#else>WARNING</#if>;

  public ${level}Message(final String messageCode, final String summaryKey, final Object... params)
  {
    super(messageCode, summaryKey, summaryKey + Message.DETAIL_SUFFIX, params);
  }

  @Override
  public Severity getSeverity()
  {
    return this.severity;
  }
  
<#list messages as msg>
  public static final Message ${msg.getter?upper_case}(Object... params)
  {
    return Notification.create${level}Message(MessageCode.${msg.level?upper_case}_${msg.code?upper_case}, "${msg.key}", params);
  }
  
</#list>
}