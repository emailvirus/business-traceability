package arrow.framework.faces.message;

import arrow.framework.faces.message.Message;

public class InfoMessage extends Message {
private final Severity severity = Severity.INFO;

  public InfoMessage(final String messageCode, final String summaryKey, final Object... params)
  {
    super(messageCode, summaryKey, summaryKey + Message.DETAIL_SUFFIX, params);
  }

  @Override
  public Severity getSeverity()
  {
    return this.severity;
  }
  
  public static Message common_001_save_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.common_001_save_successfully, MessageCode.common_001_save_successfully, params);
  }
  public static Message common_003_submission_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.common_003_submission_successfully, MessageCode.common_003_submission_successfully, params);
  }
  public static Message comp_001_save_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.comp_001_save_successfully, MessageCode.comp_001_save_successfully, params);
  }
  public static Message comp_002_delete_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.comp_002_delete_successfully, MessageCode.comp_002_delete_successfully, params);
  }
  public static Message csv_001_upload_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.csv_001_upload_successfully, MessageCode.csv_001_upload_successfully, params);
  }
  public static Message daily_001_save_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.daily_001_save_successfully, MessageCode.daily_001_save_successfully, params);
  }
  public static Message daily_002_delete_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.daily_002_delete_successfully, MessageCode.daily_002_delete_successfully, params);
  }
  public static Message daily_018_the_selected_month_is_in_reviewing_you_cant_modify_daily_report_in_this_month(Object... params) {
    return Notification.createInfoMessage(MessageCode.daily_018_the_selected_month_is_in_reviewing_you_cant_modify_daily_report_in_this_month, MessageCode.daily_018_the_selected_month_is_in_reviewing_you_cant_modify_daily_report_in_this_month, params);
  }
  public static Message daily_019_disabled_delete_daily_report_when_monthly_report_disabled(Object... params) {
    return Notification.createInfoMessage(MessageCode.daily_019_disabled_delete_daily_report_when_monthly_report_disabled, MessageCode.daily_019_disabled_delete_daily_report_when_monthly_report_disabled, params);
  }
  public static Message emp_001_update_profile_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.emp_001_update_profile_successfully, MessageCode.emp_001_update_profile_successfully, params);
  }
  public static Message emp_002_change_password_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.emp_002_change_password_successfully, MessageCode.emp_002_change_password_successfully, params);
  }
  public static Message emp_003_save_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.emp_003_save_successfully, MessageCode.emp_003_save_successfully, params);
  }
  public static Message emp_004_change_status_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.emp_004_change_status_successfully, MessageCode.emp_004_change_status_successfully, params);
  }
  public static Message monthlyreport_003_rejected(Object... params) {
    return Notification.createInfoMessage(MessageCode.monthlyreport_003_rejected, MessageCode.monthlyreport_003_rejected, params);
  }
  public static Message monthlyreport_004_approved(Object... params) {
    return Notification.createInfoMessage(MessageCode.monthlyreport_004_approved, MessageCode.monthlyreport_004_approved, params);
  }
  public static Message monthlyreport_007_reopened(Object... params) {
    return Notification.createInfoMessage(MessageCode.monthlyreport_007_reopened, MessageCode.monthlyreport_007_reopened, params);
  }
  public static Message notification_001_mark_as_read_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.notification_001_mark_as_read_successfully, MessageCode.notification_001_mark_as_read_successfully, params);
  }
  public static Message notification_002_save_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.notification_002_save_successfully, MessageCode.notification_002_save_successfully, params);
  }
  public static Message proc3_010_upload_report_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc3_010_upload_report_successfully, MessageCode.proc3_010_upload_report_successfully, params);
  }
  public static Message proc3_020_discard_successful(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc3_020_discard_successful, MessageCode.proc3_020_discard_successful, params);
  }
  public static Message proc4_001_save_financial_data_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc4_001_save_financial_data_successfully, MessageCode.proc4_001_save_financial_data_successfully, params);
  }
  public static Message proc4_003_delete_financial_data_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc4_003_delete_financial_data_successfully, MessageCode.proc4_003_delete_financial_data_successfully, params);
  }
  public static Message proc5_001_delete_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc5_001_delete_successfully, MessageCode.proc5_001_delete_successfully, params);
  }
  public static Message proc5_002_edit_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc5_002_edit_successfully, MessageCode.proc5_002_edit_successfully, params);
  }
  public static Message proc5_003_duplicated_item(Object... params) {
    return Notification.createInfoMessage(MessageCode.proc5_003_duplicated_item, MessageCode.proc5_003_duplicated_item, params);
  }
  public static Message resetpass_001_have_sent_success(Object... params) {
    return Notification.createInfoMessage(MessageCode.resetpass_001_have_sent_success, MessageCode.resetpass_001_have_sent_success, params);
  }
  public static Message resetpass_002_change_password_successfully(Object... params) {
    return Notification.createInfoMessage(MessageCode.resetpass_002_change_password_successfully, MessageCode.resetpass_002_change_password_successfully, params);
  }
  public static Message sansan_verify_004_compare_same_data(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_004_compare_same_data, MessageCode.sansan_verify_004_compare_same_data, params);
  }
  public static Message sansan_verify_005_actual_data_and_expected_data_is_same(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_005_actual_data_and_expected_data_is_same, MessageCode.sansan_verify_005_actual_data_and_expected_data_is_same, params);
  }
  public static Message sansan_verify_007_process_import_successful(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_007_process_import_successful, MessageCode.sansan_verify_007_process_import_successful, params);
  }
  public static Message sansan_verify_009_process_call_api_successful(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_009_process_call_api_successful, MessageCode.sansan_verify_009_process_call_api_successful, params);
  }
  public static Message sansan_verify_013_no_result(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_013_no_result, MessageCode.sansan_verify_013_no_result, params);
  }
  public static Message sansan_verify_014_clean_test_successful(Object... params) {
    return Notification.createInfoMessage(MessageCode.sansan_verify_014_clean_test_successful, MessageCode.sansan_verify_014_clean_test_successful, params);
  }
  public static Message sys_001_change_password_first_time_login(Object... params) {
    return Notification.createInfoMessage(MessageCode.sys_001_change_password_first_time_login, MessageCode.sys_001_change_password_first_time_login, params);
  }
}