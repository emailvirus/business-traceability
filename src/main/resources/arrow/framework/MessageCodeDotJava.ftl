package ${targetPackage};

public class MessageCode
{
<#list messages as msg >
  public static final String ${msg.level?upper_case}_${msg.code?upper_case} = "${msg.level?upper_case}_${msg.code?upper_case}";
</#list>
}