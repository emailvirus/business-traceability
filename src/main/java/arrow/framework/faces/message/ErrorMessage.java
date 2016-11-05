package arrow.framework.faces.message;

import arrow.framework.faces.message.Message;

public class ErrorMessage extends Message {
private final Severity severity = Severity.ERROR;

  public ErrorMessage(final String messageCode, final String summaryKey, final Object... params)
  {
    super(messageCode, summaryKey, summaryKey + Message.DETAIL_SUFFIX, params);
  }

  @Override
  public Severity getSeverity()
  {
    return this.severity;
  }
  
  public static Message apd_detail_001_supervisor_existed(Object... params) {
    return Notification.createErrorMessage(MessageCode.apd_detail_001_supervisor_existed, MessageCode.apd_detail_001_supervisor_existed, params);
  }
  public static Message apd_detail_002_no_employee_select(Object... params) {
    return Notification.createErrorMessage(MessageCode.apd_detail_002_no_employee_select, MessageCode.apd_detail_002_no_employee_select, params);
  }
  public static Message auth_001_invalid_user_or_password(Object... params) {
    return Notification.createErrorMessage(MessageCode.auth_001_invalid_user_or_password, MessageCode.auth_001_invalid_user_or_password, params);
  }
  public static Message auth_002_employee_has_retired(Object... params) {
    return Notification.createErrorMessage(MessageCode.auth_002_employee_has_retired, MessageCode.auth_002_employee_has_retired, params);
  }
  public static Message common_002_save_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.common_002_save_unsuccessfully, MessageCode.common_002_save_unsuccessfully, params);
  }
  public static Message common_004_submission_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.common_004_submission_unsuccessfully, MessageCode.common_004_submission_unsuccessfully, params);
  }
  public static Message comp_001_save_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.comp_001_save_unsuccessfully, MessageCode.comp_001_save_unsuccessfully, params);
  }
  public static Message comp_002_delete_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.comp_002_delete_unsuccessfully, MessageCode.comp_002_delete_unsuccessfully, params);
  }
  public static Message comp_003_cannot_generate_company_code(Object... params) {
    return Notification.createErrorMessage(MessageCode.comp_003_cannot_generate_company_code, MessageCode.comp_003_cannot_generate_company_code, params);
  }
  public static Message comp_004_costomer_code_greater_four_characters(Object... params) {
    return Notification.createErrorMessage(MessageCode.comp_004_costomer_code_greater_four_characters, MessageCode.comp_004_costomer_code_greater_four_characters, params);
  }
  public static Message comp_005_customer_code_duplicated(Object... params) {
    return Notification.createErrorMessage(MessageCode.comp_005_customer_code_duplicated, MessageCode.comp_005_customer_code_duplicated, params);
  }
  public static Message csv_002_upload_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.csv_002_upload_unsuccessfully, MessageCode.csv_002_upload_unsuccessfully, params);
  }
  public static Message daily_001_save_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_001_save_unsuccessfully, MessageCode.daily_001_save_unsuccessfully, params);
  }
  public static Message daily_002_delete_unsucessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_002_delete_unsucessfully, MessageCode.daily_002_delete_unsucessfully, params);
  }
  public static Message daily_003_you_have_to_select_company(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_003_you_have_to_select_company, MessageCode.daily_003_you_have_to_select_company, params);
  }
  public static Message daily_004_the_start_job_time_cannot_greater_end_job_time(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_004_the_start_job_time_cannot_greater_end_job_time, MessageCode.daily_004_the_start_job_time_cannot_greater_end_job_time, params);
  }
  public static Message daily_005_you_must_select_atleast_1_item(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_005_you_must_select_atleast_1_item, MessageCode.daily_005_you_must_select_atleast_1_item, params);
  }
  public static Message daily_006_content_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_006_content_is_required, MessageCode.daily_006_content_is_required, params);
  }
  public static Message daily_007_period_is_overlapped_with_existing_data(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_007_period_is_overlapped_with_existing_data, MessageCode.daily_007_period_is_overlapped_with_existing_data, params);
  }
  public static Message daily_008_daily_report_not_found(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_008_daily_report_not_found, MessageCode.daily_008_daily_report_not_found, params);
  }
  public static Message daily_009_report_does_not_exist_please_add_new_report(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_009_report_does_not_exist_please_add_new_report, MessageCode.daily_009_report_does_not_exist_please_add_new_report, params);
  }
  public static Message daily_010_report_does_not_exist_to_delete(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_010_report_does_not_exist_to_delete, MessageCode.daily_010_report_does_not_exist_to_delete, params);
  }
  public static Message daily_011_the_start_time_reminder_cannot_greater_end_time_reminder(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_011_the_start_time_reminder_cannot_greater_end_time_reminder, MessageCode.daily_011_the_start_time_reminder_cannot_greater_end_time_reminder, params);
  }
  public static Message daily_012_cannot_search_without_any_condition(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_012_cannot_search_without_any_condition, MessageCode.daily_012_cannot_search_without_any_condition, params);
  }
  public static Message daily_013_start_time_and_end_time_are_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_013_start_time_and_end_time_are_required, MessageCode.daily_013_start_time_and_end_time_are_required, params);
  }
  public static Message daily_014_working_time_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_014_working_time_is_required, MessageCode.daily_014_working_time_is_required, params);
  }
  public static Message daily_015_more_than_2000_records_were_found(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_015_more_than_2000_records_were_found, MessageCode.daily_015_more_than_2000_records_were_found, params);
  }
  public static Message daily_016_purpose_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_016_purpose_is_required, MessageCode.daily_016_purpose_is_required, params);
  }
  public static Message daily_017_business_type_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_017_business_type_is_required, MessageCode.daily_017_business_type_is_required, params);
  }
  public static Message daily_020_noNodeAvailable(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_020_noNodeAvailable, MessageCode.daily_020_noNodeAvailable, params);
  }
  public static Message daily_021_can_not_get_suggestion(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_021_can_not_get_suggestion, MessageCode.daily_021_can_not_get_suggestion, params);
  }
  public static Message daily_022_branch_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.daily_022_branch_is_required, MessageCode.daily_022_branch_is_required, params);
  }
  public static Message emp_001_update_profile_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_001_update_profile_unsuccessfully, MessageCode.emp_001_update_profile_unsuccessfully, params);
  }
  public static Message emp_002_change_password_unsucessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_002_change_password_unsucessfully, MessageCode.emp_002_change_password_unsucessfully, params);
  }
  public static Message emp_003_password_is_not_correct(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_003_password_is_not_correct, MessageCode.emp_003_password_is_not_correct, params);
  }
  public static Message emp_004_password_length_is_violated(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_004_password_length_is_violated, MessageCode.emp_004_password_length_is_violated, params);
  }
  public static Message emp_005_password_must_be_alphanumberic(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_005_password_must_be_alphanumberic, MessageCode.emp_005_password_must_be_alphanumberic, params);
  }
  public static Message emp_006_password_confirm_not_matched(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_006_password_confirm_not_matched, MessageCode.emp_006_password_confirm_not_matched, params);
  }
  public static Message emp_007_user_does_not_exist(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_007_user_does_not_exist, MessageCode.emp_007_user_does_not_exist, params);
  }
  public static Message emp_008_employeeId_duplicated(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_008_employeeId_duplicated, MessageCode.emp_008_employeeId_duplicated, params);
  }
  public static Message emp_010_please_select_one_to_edit(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_010_please_select_one_to_edit, MessageCode.emp_010_please_select_one_to_edit, params);
  }
  public static Message emp_011_please_select_to_change_status(Object... params) {
    return Notification.createErrorMessage(MessageCode.emp_011_please_select_to_change_status, MessageCode.emp_011_please_select_to_change_status, params);
  }
  public static Message maintenance_accounting_001_id_company_using_by_other_customer(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_001_id_company_using_by_other_customer, MessageCode.maintenance_accounting_001_id_company_using_by_other_customer, params);
  }
  public static Message maintenance_accounting_002_name_company_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_002_name_company_is_required, MessageCode.maintenance_accounting_002_name_company_is_required, params);
  }
  public static Message maintenance_accounting_003_name_furigana_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_003_name_furigana_is_required, MessageCode.maintenance_accounting_003_name_furigana_is_required, params);
  }
  public static Message maintenance_accounting_004_client_status_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_004_client_status_is_required, MessageCode.maintenance_accounting_004_client_status_is_required, params);
  }
  public static Message maintenance_accounting_005_client_code_prohibition_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_005_client_code_prohibition_is_required, MessageCode.maintenance_accounting_005_client_code_prohibition_is_required, params);
  }
  public static Message maintenance_accounting_006_update_with_code_prohibition(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_006_update_with_code_prohibition, MessageCode.maintenance_accounting_006_update_with_code_prohibition, params);
  }
  public static Message maintenance_accounting_007_year_of_starting_business_not_valid(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_007_year_of_starting_business_not_valid, MessageCode.maintenance_accounting_007_year_of_starting_business_not_valid, params);
  }
  public static Message maintenance_accounting_008_year_of_official_establishment_not_valid(Object... params) {
    return Notification.createErrorMessage(MessageCode.maintenance_accounting_008_year_of_official_establishment_not_valid, MessageCode.maintenance_accounting_008_year_of_official_establishment_not_valid, params);
  }
  public static Message monthlyreport_001_no_permission_to_view_report(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_001_no_permission_to_view_report, MessageCode.monthlyreport_001_no_permission_to_view_report, params);
  }
  public static Message monthlyreport_002_working_day_invalid(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_002_working_day_invalid, MessageCode.monthlyreport_002_working_day_invalid, params);
  }
  public static Message monthlyreport_005_report_no_existed(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_005_report_no_existed, MessageCode.monthlyreport_005_report_no_existed, params);
  }
  public static Message monthlyreport_006_comment_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_006_comment_is_required, MessageCode.monthlyreport_006_comment_is_required, params);
  }
  public static Message monthlyreport_008_reject_failed_by_other_person_rejected(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_008_reject_failed_by_other_person_rejected, MessageCode.monthlyreport_008_reject_failed_by_other_person_rejected, params);
  }
  public static Message monthlyreport_009_reopen_failed_by_other_person_reopened(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_009_reopen_failed_by_other_person_reopened, MessageCode.monthlyreport_009_reopen_failed_by_other_person_reopened, params);
  }
  public static Message monthlyreport_010_approve_failed_by_other_person_approved(Object... params) {
    return Notification.createErrorMessage(MessageCode.monthlyreport_010_approve_failed_by_other_person_approved, MessageCode.monthlyreport_010_approve_failed_by_other_person_approved, params);
  }
  public static Message notification_001_mark_as_read_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.notification_001_mark_as_read_unsuccessfully, MessageCode.notification_001_mark_as_read_unsuccessfully, params);
  }
  public static Message notification_002_employee_exited(Object... params) {
    return Notification.createErrorMessage(MessageCode.notification_002_employee_exited, MessageCode.notification_002_employee_exited, params);
  }
  public static Message notification_003_save_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.notification_003_save_unsuccessfully, MessageCode.notification_003_save_unsuccessfully, params);
  }
  public static Message proc3_001_only_document_file_is_support(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_001_only_document_file_is_support, MessageCode.proc3_001_only_document_file_is_support, params);
  }
  public static Message proc3_002_document_file_size_should_be_less_than_100_MB(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_002_document_file_size_should_be_less_than_100_MB, MessageCode.proc3_002_document_file_size_should_be_less_than_100_MB, params);
  }
  public static Message proc3_003_the_maximum_number_of_files_has_been_exceeded(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_003_the_maximum_number_of_files_has_been_exceeded, MessageCode.proc3_003_the_maximum_number_of_files_has_been_exceeded, params);
  }
  public static Message proc3_004_rating_number_and_scores_rank_cant_null_at_the_same_time(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_004_rating_number_and_scores_rank_cant_null_at_the_same_time, MessageCode.proc3_004_rating_number_and_scores_rank_cant_null_at_the_same_time, params);
  }
  public static Message proc3_005_need_select_registration_facility(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_005_need_select_registration_facility, MessageCode.proc3_005_need_select_registration_facility, params);
  }
  public static Message proc3_006_main_industry_code_invalid(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_006_main_industry_code_invalid, MessageCode.proc3_006_main_industry_code_invalid, params);
  }
  public static Message proc3_007_industry_code_invalid(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_007_industry_code_invalid, MessageCode.proc3_007_industry_code_invalid, params);
  }
  public static Message proc3_008_founded_year_must_be_greater_than_or_equal_founding_year(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_008_founded_year_must_be_greater_than_or_equal_founding_year, MessageCode.proc3_008_founded_year_must_be_greater_than_or_equal_founding_year, params);
  }
  public static Message proc3_009_upload_report_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_009_upload_report_unsuccessfully, MessageCode.proc3_009_upload_report_unsuccessfully, params);
  }
  public static Message proc3_011_you_have_a_accounting_data_not_approve(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_011_you_have_a_accounting_data_not_approve, MessageCode.proc3_011_you_have_a_accounting_data_not_approve, params);
  }
  public static Message proc3_012_request_user_account_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_012_request_user_account_is_required, MessageCode.proc3_012_request_user_account_is_required, params);
  }
  public static Message proc3_013_branch_of_request_user_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_013_branch_of_request_user_is_required, MessageCode.proc3_013_branch_of_request_user_is_required, params);
  }
  public static Message proc3_014_index_accounting_system_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_014_index_accounting_system_is_required, MessageCode.proc3_014_index_accounting_system_is_required, params);
  }
  public static Message proc3_015_information_source_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_015_information_source_is_required, MessageCode.proc3_015_information_source_is_required, params);
  }
  public static Message proc3_016_survey_date_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_016_survey_date_is_required, MessageCode.proc3_016_survey_date_is_required, params);
  }
  public static Message proc3_017_please_enter_a_valid_fax(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_017_please_enter_a_valid_fax, MessageCode.proc3_017_please_enter_a_valid_fax, params);
  }
  public static Message proc3_018_cannot_create_a_report_duplicate_ac_request_date_survey_source_info(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_018_cannot_create_a_report_duplicate_ac_request_date_survey_source_info, MessageCode.proc3_018_cannot_create_a_report_duplicate_ac_request_date_survey_source_info, params);
  }
  public static Message proc3_019_cannot_create_new_credit_because_selected_request_user_account(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc3_019_cannot_create_new_credit_because_selected_request_user_account, MessageCode.proc3_019_cannot_create_new_credit_because_selected_request_user_account, params);
  }
  public static Message proc4_002_save_financial_data_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc4_002_save_financial_data_unsuccessfully, MessageCode.proc4_002_save_financial_data_unsuccessfully, params);
  }
  public static Message proc4_004_delete_financial_data_unsuccessfully(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc4_004_delete_financial_data_unsuccessfully, MessageCode.proc4_004_delete_financial_data_unsuccessfully, params);
  }
  public static Message proc4_005_can_not_add_new_financial_data(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc4_005_can_not_add_new_financial_data, MessageCode.proc4_005_can_not_add_new_financial_data, params);
  }
  public static Message proc5_001_can_not_edit_out_of_date(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc5_001_can_not_edit_out_of_date, MessageCode.proc5_001_can_not_edit_out_of_date, params);
  }
  public static Message proc5_002_can_not_delete_out_of_date(Object... params) {
    return Notification.createErrorMessage(MessageCode.proc5_002_can_not_delete_out_of_date, MessageCode.proc5_002_can_not_delete_out_of_date, params);
  }
  public static Message recover_password_001_have_not_send(Object... params) {
    return Notification.createErrorMessage(MessageCode.recover_password_001_have_not_send, MessageCode.recover_password_001_have_not_send, params);
  }
  public static Message resetpass_001_have_not_send(Object... params) {
    return Notification.createErrorMessage(MessageCode.resetpass_001_have_not_send, MessageCode.resetpass_001_have_not_send, params);
  }
  public static Message resetpass_002_sessionid_expired(Object... params) {
    return Notification.createErrorMessage(MessageCode.resetpass_002_sessionid_expired, MessageCode.resetpass_002_sessionid_expired, params);
  }
  public static Message resetpass_003_change_pass_not_success(Object... params) {
    return Notification.createErrorMessage(MessageCode.resetpass_003_change_pass_not_success, MessageCode.resetpass_003_change_pass_not_success, params);
  }
  public static Message resetpass_004_password_confirm_not_matched(Object... params) {
    return Notification.createErrorMessage(MessageCode.resetpass_004_password_confirm_not_matched, MessageCode.resetpass_004_password_confirm_not_matched, params);
  }
  public static Message resetpass_005_employeeId_or_email_not_correct(Object... params) {
    return Notification.createErrorMessage(MessageCode.resetpass_005_employeeId_or_email_not_correct, MessageCode.resetpass_005_employeeId_or_email_not_correct, params);
  }
  public static Message sansan_verify_001_compare_total_record(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_001_compare_total_record, MessageCode.sansan_verify_001_compare_total_record, params);
  }
  public static Message sansan_verify_002_compare_data_of_each_record(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_002_compare_data_of_each_record, MessageCode.sansan_verify_002_compare_data_of_each_record, params);
  }
  public static Message sansan_verify_003_compare_not_found_data(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_003_compare_not_found_data, MessageCode.sansan_verify_003_compare_not_found_data, params);
  }
  public static Message sansan_verify_006_actual_data_and_expected_data_is_not_same(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_006_actual_data_and_expected_data_is_not_same, MessageCode.sansan_verify_006_actual_data_and_expected_data_is_not_same, params);
  }
  public static Message sansan_verify_008_process_import_unsuccessful(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_008_process_import_unsuccessful, MessageCode.sansan_verify_008_process_import_unsuccessful, params);
  }
  public static Message sansan_verify_010_process_call_api_unsuccessful(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_010_process_call_api_unsuccessful, MessageCode.sansan_verify_010_process_call_api_unsuccessful, params);
  }
  public static Message sansan_verify_011_an_error_has_occurred(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_011_an_error_has_occurred, MessageCode.sansan_verify_011_an_error_has_occurred, params);
  }
  public static Message sansan_verify_012_error_when_read_file_csv(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_012_error_when_read_file_csv, MessageCode.sansan_verify_012_error_when_read_file_csv, params);
  }
  public static Message sansan_verify_015_clean_test_unsuccessful(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_015_clean_test_unsuccessful, MessageCode.sansan_verify_015_clean_test_unsuccessful, params);
  }
  public static Message sansan_verify_016_process_call_api_unsuccessful_other_import_running(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_016_process_call_api_unsuccessful_other_import_running, MessageCode.sansan_verify_016_process_call_api_unsuccessful_other_import_running, params);
  }
  public static Message sansan_verify_017_process_call_api_date_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_017_process_call_api_date_required, MessageCode.sansan_verify_017_process_call_api_date_required, params);
  }
  public static Message sansan_verify_018_process_call_api_starttime_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_018_process_call_api_starttime_required, MessageCode.sansan_verify_018_process_call_api_starttime_required, params);
  }
  public static Message sansan_verify_019_process_call_api_endtime_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.sansan_verify_019_process_call_api_endtime_required, MessageCode.sansan_verify_019_process_call_api_endtime_required, params);
  }
  public static Message sum_001_date_from_must_smaller_date_to(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_001_date_from_must_smaller_date_to, MessageCode.sum_001_date_from_must_smaller_date_to, params);
  }
  public static Message sum_002_company_must_require(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_002_company_must_require, MessageCode.sum_002_company_must_require, params);
  }
  public static Message sum_003_start_date_must_be_before_end_date(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_003_start_date_must_be_before_end_date, MessageCode.sum_003_start_date_must_be_before_end_date, params);
  }
  public static Message sum_004_start_date_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_004_start_date_is_required, MessageCode.sum_004_start_date_is_required, params);
  }
  public static Message sum_005_end_date_is_required(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_005_end_date_is_required, MessageCode.sum_005_end_date_is_required, params);
  }
  public static Message sum_006_need_select_employee_for_report(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_006_need_select_employee_for_report, MessageCode.sum_006_need_select_employee_for_report, params);
  }
  public static Message sum_007_export_periodic_report_failed(Object... params) {
    return Notification.createErrorMessage(MessageCode.sum_007_export_periodic_report_failed, MessageCode.sum_007_export_periodic_report_failed, params);
  }
  public static Message sys_001_date_from_must_smaller_date_to(Object... params) {
    return Notification.createErrorMessage(MessageCode.sys_001_date_from_must_smaller_date_to, MessageCode.sys_001_date_from_must_smaller_date_to, params);
  }
}