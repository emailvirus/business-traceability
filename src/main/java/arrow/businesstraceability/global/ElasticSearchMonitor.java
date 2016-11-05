package arrow.businesstraceability.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;

import arrow.businesstraceability.auth.event.DataChange;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.framework.logging.BaseLogger;

/**
 * The Class ElasticSearchMonitor.
 */
@ApplicationScoped
public class ElasticSearchMonitor implements Serializable {

    /** The log. */
    @Inject
    protected BaseLogger log;

    /** The list data change info. */
    private final LinkedList<DataChangeInfo> listDataChangeInfo = new LinkedList<>();

    /** The Constant BULK_SIZE. */
    private static final int BULK_SIZE = 10;

    /** The is running. */
    private static boolean isRunning = false;

    /** The es service. */
    @Inject
    private ElasticSearchService esService;

    /**
     * Observer logout event.
     *
     * @param dataChangeInfo the data change info
     */
    public void observerLogoutEvent(@Observes @DataChange final DataChangeInfo dataChangeInfo) {
        this.log.debug("Thread Id: " + Thread.currentThread().getId() + " checking...");
        synchronized (this) {
            this.listDataChangeInfo.addLast(dataChangeInfo);
            this.log.debug("Thread Id: " + Thread.currentThread().getId() + " lock...");
            this.log.debug("Events size: " + this.listDataChangeInfo.size());
        }

        this.log.debug("Thread Id: " + Thread.currentThread().getId() + " unlock...");

        if (ElasticSearchMonitor.isRunning) {
            this.log.debug("Thread Id: " + Thread.currentThread().getId() + " quit because of other thread running...");
            return;
        }
        this.log.debug("Thread Id: " + Thread.currentThread().getId() + " start running...");
        ElasticSearchMonitor.isRunning = true;
        while (!this.listDataChangeInfo.isEmpty()) {
            List<DataChangeInfo> listWillHandle = this.getBulk(this.listDataChangeInfo);
            if (!this.process(listWillHandle)) {
                this.log.debug("Thread Id: " + Thread.currentThread().getId() + " Finish...");
                ElasticSearchMonitor.isRunning = false;
                return;
            }
        }
        this.log.debug("Thread Id: " + Thread.currentThread().getId() + " Finish...");
        ElasticSearchMonitor.isRunning = false;
    }

    /**
     * Gets the bulk.
     *
     * @param listDataChangeInfo the list data change info
     * @return the bulk
     */
    private List<DataChangeInfo> getBulk(final LinkedList<DataChangeInfo> listDataChangeInfo) {
        List<DataChangeInfo> listWillHandle = new ArrayList<DataChangeInfo>();
        if (CollectionUtils.isNotEmpty(listDataChangeInfo)) {
            int realBulkSize = Math.min(ElasticSearchMonitor.BULK_SIZE, listDataChangeInfo.size());
            for (int i = 0; i < realBulkSize; i++) {
                listWillHandle.add(this.listDataChangeInfo.removeFirst());

            }
        }
        return listWillHandle;
    }

    /**
     * Process.
     *
     * @param listWillHandle the list will handle
     * @return true, if successful
     */
    private boolean process(final List<DataChangeInfo> listWillHandle) {
        if (CollectionUtils.isNotEmpty(listWillHandle)) {
            int realBulkSize = listWillHandle.size();
            ServiceResult<List<DataChangeInfo>> result = this.esService.bulkProcess(listWillHandle);

            if (result.isSuccessful()) {
                Date lastSuccessTime = listWillHandle.get(realBulkSize - 1).getTimeStamp();
                this.storeToFile(lastSuccessTime);
                return true;
            } else {
                for (int i = realBulkSize - 1; i >= 0; i--) {
                    this.listDataChangeInfo.addFirst(result.getData().get(i));
                }
            }
        }
        return false;
    }

    /**
     * Store to file.
     *
     * @param lastSuccessTime the last success time
     */
    private void storeToFile(final Date lastSuccessTime) {
        // TODO veryfy lai cai cho nay ghi vao file log

    }
}
