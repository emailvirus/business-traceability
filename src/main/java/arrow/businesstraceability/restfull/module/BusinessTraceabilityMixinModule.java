package arrow.businesstraceability.restfull.module;

import com.fasterxml.jackson.databind.module.SimpleModule;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.cache.entity.Daily_Report_View;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_card_tag;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_cnumber;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.San_com_live_client;
import arrow.businesstraceability.persistence.entity.San_com_live_owner;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.businesstraceability.persistence.entity.San_com_mdomain;
import arrow.businesstraceability.persistence.entity.San_com_site;
import arrow.businesstraceability.persistence.entity.San_com_udomain;
import arrow.businesstraceability.persistence.entity.San_com_whole_card;
import arrow.businesstraceability.persistence.entity.San_idmap_card;
import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.entity.San_idmap_person;
import arrow.businesstraceability.restfull.mixin.AddresspointMstMixin;
import arrow.businesstraceability.restfull.mixin.CompanyMstMixin;
import arrow.businesstraceability.restfull.mixin.CompanyMstSuggestMixin;
import arrow.businesstraceability.restfull.mixin.CustomerInfoViewMixin;
import arrow.businesstraceability.restfull.mixin.DailyReportMixin;
import arrow.businesstraceability.restfull.mixin.EmployeeMstMixin;
import arrow.businesstraceability.restfull.mixin.SanCardDataMixin;
import arrow.businesstraceability.restfull.mixin.SanCardTagMixin;
import arrow.businesstraceability.restfull.mixin.SanComBranchMixin;
import arrow.businesstraceability.restfull.mixin.SanComCnumberMixin;
import arrow.businesstraceability.restfull.mixin.SanComInfoMixin;
import arrow.businesstraceability.restfull.mixin.SanComLiveClientMixin;
import arrow.businesstraceability.restfull.mixin.SanComLiveOwnerMixin;
import arrow.businesstraceability.restfull.mixin.SanComLiveStatMixin;
import arrow.businesstraceability.restfull.mixin.SanComMDomainMixin;
import arrow.businesstraceability.restfull.mixin.SanComSiteMixin;
import arrow.businesstraceability.restfull.mixin.SanComUDomainMixin;
import arrow.businesstraceability.restfull.mixin.SanComWholeCardMixin;
import arrow.businesstraceability.restfull.mixin.SanIdmapCardMixin;
import arrow.businesstraceability.restfull.mixin.SanIdmapCompanyMixin;
import arrow.businesstraceability.restfull.mixin.SanIdmapPersonMixin;


public class BusinessTraceabilityMixinModule extends SimpleModule {
    public BusinessTraceabilityMixinModule() {
        super("BusinessTraceabilityMixinModule");
    }

    @Override
    public void setupModule(final SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(Company_mst.class, CompanyMstMixin.class);
        context.setMixInAnnotations(Customer_info_view.class, CustomerInfoViewMixin.class);
        context.setMixInAnnotations(Company_mst_suggest.class, CompanyMstSuggestMixin.class);
        // context.setMixInAnnotations(Company_cluster.class, CompanyClusterMixin.class);
        context.setMixInAnnotations(Daily_Report_View.class, DailyReportMixin.class);
        context.setMixInAnnotations(San_card_data.class, SanCardDataMixin.class);
        context.setMixInAnnotations(San_com_branch.class, SanComBranchMixin.class);
        context.setMixInAnnotations(San_com_info.class, SanComInfoMixin.class);
        context.setMixInAnnotations(San_com_site.class, SanComSiteMixin.class);
        context.setMixInAnnotations(San_com_cnumber.class, SanComCnumberMixin.class);
        context.setMixInAnnotations(San_com_mdomain.class, SanComMDomainMixin.class);
        context.setMixInAnnotations(San_com_udomain.class, SanComUDomainMixin.class);
        context.setMixInAnnotations(San_com_live_stat.class, SanComLiveStatMixin.class);
        context.setMixInAnnotations(Employee_mst.class, EmployeeMstMixin.class);
        context.setMixInAnnotations(Addresspoint_mst.class, AddresspointMstMixin.class);
        context.setMixInAnnotations(San_idmap_card.class, SanIdmapCardMixin.class);
        context.setMixInAnnotations(San_idmap_company.class, SanIdmapCompanyMixin.class);
        context.setMixInAnnotations(San_idmap_person.class, SanIdmapPersonMixin.class);
        context.setMixInAnnotations(San_com_whole_card.class, SanComWholeCardMixin.class);
        context.setMixInAnnotations(San_com_live_owner.class, SanComLiveOwnerMixin.class);
        context.setMixInAnnotations(San_com_live_client.class, SanComLiveClientMixin.class);
        context.setMixInAnnotations(San_card_tag.class, SanCardTagMixin.class);
    }
}
