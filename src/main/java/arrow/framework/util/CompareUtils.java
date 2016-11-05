package arrow.framework.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_cnumber;
import arrow.businesstraceability.persistence.entity.San_com_live_client;
import arrow.businesstraceability.persistence.entity.San_com_site;
import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.entity.San_idmap_person;
import arrow.framework.faces.message.MessageCode;
import arrow.framework.util.i18n.Messages;

/**
 * The Class CompareUtils.
 */
public class CompareUtils {

    /**
     * Compare list data and csv.
     *
     * @param list the list
     * @param csvUrl the csv url
     * @param fieldId the field id
     * @param listExcludeField the list exclude field
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public static List<String> compareListDataAndCsv(final List<? extends Abstract_entity> list, final String csvUrl,
        final String fieldId, final List<String> listExcludeField) throws JsonProcessingException {
        List<String> errorMessage = new ArrayList<String>();

        List<? extends Abstract_entity> listCsvData;
        try {
            listCsvData = CsvUtils.readFiles(csvUrl, list.get(0).getClass());
        } catch (IOException e1) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, csvUrl));
            return errorMessage;
        }

        if (CollectionUtils.isEmpty(listCsvData) || (list.size() != listCsvData.size())) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_001_compare_total_record, list.get(0).getClass()
                .getSimpleName(), String.valueOf(list.size()), String.valueOf(listCsvData.size())));
            return errorMessage;
        }

        for (Abstract_entity entity : list) {
            try {
                Field field = entity.getEntityClass().getSuperclass().getDeclaredField(fieldId);
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        field.setAccessible(true);
                        return null;
                    }
                });
                String srcIdValue = field.get(entity).toString();

                Stream<? extends Abstract_entity> stream = listCsvData.stream().filter(p -> {
                    try {
                        String csvValue = field.get(p).toString();
                        return srcIdValue.equals(csvValue);
                    } catch (final Exception e) {
                        return false;
                    }
                });
                List<? extends Abstract_entity> listFilter = stream.collect(Collectors.toList());

                if (CollectionUtils.isEmpty(listFilter)) {
                    errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                        JsonUtils.getJson(entity)));
                    continue;
                }

                if (!EqualsBuilder.reflectionEquals(entity, listFilter.get(0), listExcludeField)) {
                    errorMessage.add(Messages.get(MessageCode.sansan_verify_002_compare_data_of_each_record, list
                        .get(0).getClass().getSimpleName(), JsonUtils.getJson(entity),
                        JsonUtils.getJson(listFilter.get(0))));
                    continue;
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                    JsonUtils.getJson(entity)));
                continue;
            }
        }

        return errorMessage;
    }

    /**
     * Compare san data and csv.
     *
     * @param listData the list data
     * @param entityClazz the entity clazz
     * @param mapClazz the map clazz
     * @param pathMapCsv the path map csv
     * @param pathDataCsv the path data csv
     * @param mapField the map field
     * @param listFieldNameCompare the list field name compare
     * @param listExcludeField the list exclude field
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public static List<String> compareSanDataAndCsv(final List<? extends Abstract_entity> listData,
        final List<String> listIdMapComp, final Class<? extends Abstract_entity> entityClazz,
        final Class<? extends Abstract_entity> mapClazz, final String pathMapCsv, final String pathDataCsv,
        final String mapField, final List<String> listFieldNameCompare, final List<String> listExcludeField)
        throws JsonProcessingException {
        List<String> errorMessage = new ArrayList<String>();

        List<? extends Abstract_entity> listMapData;
        try {
            listMapData = CsvUtils.readFiles(pathMapCsv, mapClazz);
        } catch (IOException e1) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathMapCsv));
            return errorMessage;
        }

        List<? extends Abstract_entity> listCsvData;
        try {
            listCsvData = CsvUtils.readFiles(pathDataCsv, entityClazz);
        } catch (IOException e1) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathDataCsv));
            return errorMessage;
        }

        if (CollectionUtils.isEmpty(listMapData) || (listData.size() != listCsvData.size())) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_001_compare_total_record,
                entityClazz.getSimpleName(), String.valueOf(listData.size()), String.valueOf(listCsvData.size())));
            return errorMessage;
        }

        for (int i = 0; i < listData.size(); i++) {
            try {
                Field idMapField = listMapData.get(0).getEntityClass().getSuperclass().getDeclaredField(mapField);
                List<Field> listFieldCompare = new ArrayList<Field>();
                for (String fieldName : listFieldNameCompare) {
                    Field field = listCsvData.get(0).getEntityClass().getSuperclass().getDeclaredField(fieldName);
                    listFieldCompare.add(field);
                }
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        idMapField.setAccessible(true);
                        for (Field field : listFieldCompare) {
                            field.setAccessible(true);
                        }
                        return null;
                    }
                });
                String idSanCompany = listIdMapComp.get(i);
                Abstract_entity entity = listData.get(i);

                Stream<? extends Abstract_entity> streamMapData = listMapData.stream().filter(p -> {
                    try {
                        String idSanCompanyMap = idMapField.get(p).toString();
                        return idSanCompany.equals(idSanCompanyMap);
                    } catch (final Exception e) {
                        return false;
                    }

                });
                List<? extends Abstract_entity> listFilterMapComp = streamMapData.collect(Collectors.toList());

                if (CollectionUtils.isEmpty(listFilterMapComp)) {
                    errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                        JsonUtils.getJson(entity)));
                    continue;
                }

                Stream<? extends Abstract_entity> streamCsvData =
                    listCsvData.stream().filter(
                        p -> {
                            boolean isMatched = true;
                            for (Field field : listFieldCompare) {
                                try {
                                    Field mappedField =
                                        listFilterMapComp.get(0).getEntityClass().getSuperclass()
                                            .getDeclaredField(field.getName());
                                    mappedField.setAccessible(true);
                                    isMatched =
                                        mappedField.get(listFilterMapComp.get(0)).toString()
                                            .equals(field.get(p).toString());
                                    if (!isMatched) {
                                        return isMatched;
                                    }
                                } catch (Exception ex) {
                                    try {
                                        isMatched = field.get(entity).toString().equals(field.get(p).toString());
                                        if (!isMatched) {
                                            return isMatched;
                                        }
                                    } catch (final Exception e) {
                                        return false;
                                    }
                                }
                            }
                            return isMatched;
                        });
                List<? extends Abstract_entity> listFilterEntity = streamCsvData.collect(Collectors.toList());

                if (CollectionUtils.isEmpty(listFilterEntity)) {
                    errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                        JsonUtils.getJson(entity)));
                    continue;
                }

                if (!EqualsBuilder.reflectionEquals(entity, listFilterEntity.get(0), listExcludeField)) {
                    errorMessage.add(Messages.get(MessageCode.sansan_verify_002_compare_data_of_each_record,
                        entityClazz.getSimpleName(), JsonUtils.getJson(entity),
                        JsonUtils.getJson(listFilterEntity.get(0))));
                    continue;
                }

            } catch (final NoSuchFieldException | SecurityException e) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                    JsonUtils.getJson(listData.get(i))));
                continue;
            }
        }

        return errorMessage;
    }

    /**
     * Compare san com live client.
     *
     * @param list the list
     * @param listIdSanCompany the list id san company
     * @param listIdSanPerson the list id san person
     * @param pathToClientCsv the path to client csv
     * @param pathToMapCompCsv the path to map comp csv
     * @param pathToMapPersonCsv the path to map person csv
     * @param listExcludeField the list exclude field
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public static List<String> compareSanComLiveClient(final List<San_com_live_client> list,
        final List<String> listIdSanCompany, final List<String> listIdSanPerson, final String pathToClientCsv,
        final String pathToMapCompCsv, final String pathToMapPersonCsv, final List<String> listExcludeField)
        throws JsonProcessingException {
        List<String> errorMessage = new ArrayList<String>();

        List<San_com_live_client> listCsvClient;
        try {
            listCsvClient = CsvUtils.readFiles(pathToClientCsv, San_com_live_client.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToClientCsv));
            return errorMessage;
        }

        if (CollectionUtils.isEmpty(listCsvClient) || (list.size() != listCsvClient.size())) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_001_compare_total_record, list.get(0).getClass()
                .getSimpleName(), String.valueOf(list.size()), String.valueOf(listCsvClient.size())));
            return errorMessage;
        }

        List<San_idmap_company> listCsvMapComp;
        try {
            listCsvMapComp = CsvUtils.readFiles(pathToMapCompCsv, San_idmap_company.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToMapCompCsv));
            return errorMessage;
        }

        List<San_idmap_person> listCsvMapPerson;
        try {
            listCsvMapPerson = CsvUtils.readFiles(pathToMapPersonCsv, San_idmap_person.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToMapPersonCsv));
            return errorMessage;
        }

        for (int i = 0; i < list.size(); i++) {
            String idSanCompany = listIdSanCompany.get(i);
            String idSanPerson = listIdSanPerson.get(i);
            Stream<San_idmap_company> streamCsvMapComp = listCsvMapComp.stream().filter(p -> {
                return idSanCompany.equals(p.getId_san_company());
            });
            List<San_idmap_company> listFilterIdMapCompany = streamCsvMapComp.collect(Collectors.toList());

            Stream<San_idmap_person> streamCsvMapPerson = listCsvMapPerson.stream().filter(p -> {
                return idSanPerson.equals(p.getId_san_person());
            });
            List<San_idmap_person> listFilterIdMapPerson = streamCsvMapPerson.collect(Collectors.toList());

            int idCompanyCsv = listFilterIdMapCompany.get(0).getId_company();
            int idPersonCsv = listFilterIdMapPerson.get(0).getId_person();
            San_com_live_client client = list.get(i);

            Stream<San_com_live_client> streamCsvClient = listCsvClient.stream().filter(p -> {
                return (p.getId_company() == idCompanyCsv) && (p.getId_person() == idPersonCsv);
            });
            List<San_com_live_client> listFilterClient = streamCsvClient.collect(Collectors.toList());

            if (CollectionUtils.isEmpty(listFilterClient)) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                    JsonUtils.getJson(client)));
                continue;
            }

            if (!EqualsBuilder.reflectionEquals(client, listFilterClient.get(0), listExcludeField)) {
                errorMessage
                    .add(Messages.get(MessageCode.sansan_verify_002_compare_data_of_each_record, client.getClass()
                        .getSimpleName(), JsonUtils.getJson(client), JsonUtils.getJson(listFilterClient.get(0))));
                continue;
            }
        }

        return errorMessage;
    }

    /**
     * Compare san com site.
     *
     * @param list the list
     * @param listMapIdComp the list map id comp
     * @param pathToIdMapComp the path to id map comp
     * @param pathToSanComBranch the path to san com branch
     * @param pathToSanComSite the path to san com site
     * @param listExcludeField the list exclude field
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public static List<String> compareSanComSite(final List<San_com_site> list, final List<String> listMapIdComp,
        final String pathToIdMapComp, final String pathToSanComBranch, final String pathToSanComSite,
        final List<String> listExcludeField) throws JsonProcessingException {
        List<String> errorMessage = new ArrayList<String>();

        List<San_com_site> listSiteCsv;
        try {
            listSiteCsv = CsvUtils.readFiles(pathToSanComSite, San_com_site.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToSanComSite));
            return errorMessage;
        }

        if (CollectionUtils.isEmpty(listSiteCsv) || (list.size() != listSiteCsv.size())) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_001_compare_total_record,
                San_com_site.class.getSimpleName(), String.valueOf(list.size()), String.valueOf(listSiteCsv.size())));
            return errorMessage;
        }

        List<San_idmap_company> listIdMapCompCsv;
        try {
            listIdMapCompCsv = CsvUtils.readFiles(pathToIdMapComp, San_idmap_company.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToIdMapComp));
            return errorMessage;
        }

        List<San_com_branch> listBranchCsv;
        try {
            listBranchCsv = CsvUtils.readFiles(pathToSanComBranch, San_com_branch.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToSanComBranch));
            return errorMessage;
        }

        for (int i = 0; i < list.size(); i++) {
            San_com_site site = list.get(i);
            String idSanComp = listMapIdComp.get(i);
            Stream<San_idmap_company> streamIdMapCompCsv = listIdMapCompCsv.stream().filter(p -> {
                return idSanComp.equals(p.getId_san_company());
            });
            List<San_idmap_company> listFilterMapComp = streamIdMapCompCsv.collect(Collectors.toList());

            Stream<San_com_branch> streamBranchCsv =
                listBranchCsv.stream().filter(
                    p -> {
                        return (listFilterMapComp.get(0).getId_company() == p.getId_company())
                            && (site.getSan_com_branch() != null ? site.getSan_com_branch().getName_branch()
                                .equals(p.getName_branch()) : false);
                    });
            List<San_com_branch> listFilterBranch = streamBranchCsv.collect(Collectors.toList());

            Stream<San_com_site> streamSiteCsv =
                listSiteCsv.stream().filter(
                    p -> {
                        return (listFilterMapComp.get(0).getId_company() == p.getId_company())
                            && (p.getId_branch() == null ? (CollectionUtils.isEmpty(listFilterBranch) ? true : false)
                                : (CollectionUtils.isEmpty(listFilterBranch) ? false : (listFilterBranch.get(0)
                                    .getId_branch() == p.getId_branch())))
                            && StringUtils.equalsIgnoreCase(site.getAddr_all(), p.getAddr_all())
                            && StringUtils.equalsIgnoreCase(site.getAddr_bldg(), p.getAddr_bldg())
                            && StringUtils.equalsIgnoreCase(site.getAddr_block(), p.getAddr_block())
                            && StringUtils.equalsIgnoreCase(site.getAddr_city(), p.getAddr_city())
                            && StringUtils.equalsIgnoreCase(site.getAddr_pref(), p.getAddr_pref())
                            && StringUtils.equalsIgnoreCase(site.getCode_zip(), p.getCode_zip());
                    });
            List<San_com_site> listFilterSanComSite = streamSiteCsv.collect(Collectors.toList());
            if (CollectionUtils.isEmpty(listFilterSanComSite)) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                    JsonUtils.getJson(site)));
                continue;
            }

            if (!EqualsBuilder.reflectionEquals(site, listFilterSanComSite.get(0), listExcludeField)) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_002_compare_data_of_each_record, site
                    .getClass().getSimpleName(), JsonUtils.getJson(site),
                    JsonUtils.getJson(listFilterSanComSite.get(0))));
                continue;
            }
        }
        return errorMessage;
    }

    /**
     * Compare san com c number.
     *
     * @param list the list
     * @param listMapIdComp the list map id comp
     * @param pathToIdMapComp the path to id map comp
     * @param pathToSanComBranch the path to san com branch
     * @param pathToSanComSite the path to san com site
     * @param pathToSanComCNumber the path to san com c number
     * @param listExcludeField the list exclude field
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public static List<String> compareSanComCNumber(final List<San_com_cnumber> list, final List<String> listMapIdComp,
        final String pathToIdMapComp, final String pathToSanComBranch, final String pathToSanComSite,
        final String pathToSanComCNumber, final List<String> listExcludeField) throws JsonProcessingException {
        List<String> errorMessage = new ArrayList<String>();

        List<San_com_cnumber> listCNumberCsv;
        try {
            listCNumberCsv = CsvUtils.readFiles(pathToSanComCNumber, San_com_cnumber.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToSanComCNumber));
            return errorMessage;
        }

        if (CollectionUtils.isEmpty(listCNumberCsv) || (list.size() != listCNumberCsv.size())) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_001_compare_total_record,
                San_com_cnumber.class.getSimpleName(), String.valueOf(list.size()),
                String.valueOf(listCNumberCsv.size())));
            return errorMessage;
        }

        List<San_com_site> listSiteCsv;
        try {
            listSiteCsv = CsvUtils.readFiles(pathToSanComSite, San_com_site.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToSanComSite));
            return errorMessage;
        }

        List<San_idmap_company> listIdMapCompCsv;
        try {
            listIdMapCompCsv = CsvUtils.readFiles(pathToIdMapComp, San_idmap_company.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToIdMapComp));
            return errorMessage;
        }

        List<San_com_branch> listBranchCsv;
        try {
            listBranchCsv = CsvUtils.readFiles(pathToSanComBranch, San_com_branch.class);
        } catch (final IOException e) {
            errorMessage.add(Messages.get(MessageCode.sansan_verify_012_error_when_read_file_csv, pathToSanComBranch));
            return errorMessage;
        }

        for (int i = 0; i < list.size(); i++) {
            San_com_cnumber cnumber = list.get(i);
            San_com_site site = cnumber.getSan_com_site();
            String idSanComp = listMapIdComp.get(i);
            Stream<San_idmap_company> streamIdMapCompCsv = listIdMapCompCsv.stream().filter(p -> {
                return idSanComp.equals(p.getId_san_company());
            });
            List<San_idmap_company> listFilterMapComp = streamIdMapCompCsv.collect(Collectors.toList());

            Stream<San_com_branch> streamBranchCsv =
                listBranchCsv.stream().filter(
                    p -> {
                        return (listFilterMapComp.get(0).getId_company() == p.getId_company())
                            && (site != null ? site.getSan_com_branch().getName_branch().equals(p.getName_branch())
                                : false);
                    });
            List<San_com_branch> listFilterBranch = streamBranchCsv.collect(Collectors.toList());

            Stream<San_com_site> streamSiteCsv =
                listSiteCsv.stream().filter(
                    p -> {
                        if (site == null) {
                            return false;
                        }
                        return (listFilterMapComp.get(0).getId_company() == p.getId_company())
                            && (p.getId_branch() == null ? (CollectionUtils.isEmpty(listFilterBranch) ? true : false)
                                : (CollectionUtils.isEmpty(listFilterBranch) ? false : (listFilterBranch.get(0)
                                    .getId_branch() == p.getId_branch())))
                            && StringUtils.equalsIgnoreCase(site.getAddr_all(), p.getAddr_all())
                            && StringUtils.equalsIgnoreCase(site.getAddr_bldg(), p.getAddr_bldg())
                            && StringUtils.equalsIgnoreCase(site.getAddr_block(), p.getAddr_block())
                            && StringUtils.equalsIgnoreCase(site.getAddr_city(), p.getAddr_city())
                            && StringUtils.equalsIgnoreCase(site.getAddr_pref(), p.getAddr_pref())
                            && StringUtils.equalsIgnoreCase(site.getCode_zip(), p.getCode_zip());
                    });
            List<San_com_site> listFilterSanComSite = streamSiteCsv.collect(Collectors.toList());

            Stream<San_com_cnumber> streamCNumberCsv =
                listCNumberCsv.stream().filter(
                    p -> {
                        return (listFilterMapComp.get(0).getId_company() == p.getId_company())
                            && (cnumber.getId_site() == null ? (p.getId_site() == null ? true : false) : (p
                                .getId_site() == null ? false : listFilterSanComSite.get(0).getId_site() == p
                                .getId_site())) && StringUtils.equalsIgnoreCase(cnumber.getTel11(), p.getTel11())
                            && StringUtils.equalsIgnoreCase(cnumber.getFax(), p.getFax());
                    });
            List<San_com_cnumber> listFilterCNumber = streamCNumberCsv.collect(Collectors.toList());

            if (CollectionUtils.isEmpty(listFilterCNumber)) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_003_compare_not_found_data,
                    JsonUtils.getJson(cnumber)));
                continue;
            }

            if (!EqualsBuilder.reflectionEquals(cnumber, listFilterCNumber.get(0), listExcludeField)) {
                errorMessage.add(Messages.get(MessageCode.sansan_verify_002_compare_data_of_each_record, cnumber
                    .getClass().getSimpleName(), JsonUtils.getJson(cnumber),
                    JsonUtils.getJson(listFilterCNumber.get(0))));
                continue;
            }
        }
        return errorMessage;
    }
}
