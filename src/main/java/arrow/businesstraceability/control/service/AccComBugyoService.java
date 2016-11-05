package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo12;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo14;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo16;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo90;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo91;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo12Repository;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo14Repository;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo16Repository;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo90Repository;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo91Repository;

public class AccComBugyoService extends AbstractService {

    @Inject
    private Acc_com_bugyo91Repository accComBugyo91Repo;

    @Inject
    private Acc_com_bugyo16Repository accComBugyo16Repo;

    @Inject
    private Acc_com_bugyo90Repository accComBugyo90Repo;

    @Inject
    private Acc_com_bugyo14Repository accComBugyo14Repo;

    @Inject
    private Acc_com_bugyo12Repository accComBugyo12Repo;

    public List<Acc_com_bugyo91> getAllAccComBugyo91() {
        return this.accComBugyo91Repo.findAll();
    }

    public List<Acc_com_bugyo16> getAllAccComBugyo16() {
        return this.accComBugyo16Repo.findAll();
    }

    public List<Acc_com_bugyo90> getAllAccComBugyo90() {
        return this.accComBugyo90Repo.findAll();
    }

    public List<Acc_com_bugyo14> getAllAccComBugyo14() {
        return this.accComBugyo14Repo.findAll();
    }

    public List<Acc_com_bugyo12> getAllAccComBugyo12() {
        return this.accComBugyo12Repo.findAll();
    }

}
