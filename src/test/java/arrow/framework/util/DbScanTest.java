package arrow.framework.util;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.debug.DBScan;
import arrow.businesstraceability.debug.ListCluster;
import arrow.businesstraceability.debug.Point;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;

public class DbScanTest {
    private static final BaseLogger log = BaseLoggerProducer.getLogger("DBScan");

    // @Test
    public void test() throws ClassNotFoundException, SQLException, UnknownHostException {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        connection =
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/business_traceability", "postgres", "x");

        Statement stmt = null;
        stmt = connection.createStatement();
        String query = "SELECT * FROM Company_mst where com_delete_flg = false ORDER BY com_company_code ";
        ResultSet rs = stmt.executeQuery(query);
        List<Point> listData = new ArrayList<>();
        while (rs.next()) {
            String comName = rs.getString("com_company_name");
            String comCode = rs.getString("com_company_code");
            String comCustomerCode = rs.getString("com_customer_code");
            String comUrl = rs.getString("com_url");
            String comNameFu = rs.getString("com_company_furigana");
            Company_mst_suggest cluster = new Company_mst_suggest();
            cluster.setCom_company_code(comCode);
            cluster.setCom_company_furigana(comNameFu);
            cluster.setCom_company_name(comName);
            cluster.setCom_customer_code(comCustomerCode);
            cluster.setCom_url(comUrl);

            Point point = new Point(cluster);

            listData.add(point);
        }
        DBScan dbScan = new DBScan();
        List<ListCluster> listCluster = dbScan.dbScan(listData, 2, 2, 20);


        StringBuilder output = new StringBuilder();
        output.append("=================== Result ==============\n");
        output.append(String.format("Number of Cluster: %d", listCluster.size()));
        output.append("=========================================\n");
        int countPoint = 0;
        for (ListCluster listCluster2 : listCluster) {
            countPoint += listCluster2.getListPoint().size();
            output.append(listCluster2.printOut());
            DbScanTest.log.debug(listCluster2.printOut());
        }

        DbScanTest.log.debug("=================== Result ==============\n");
        DbScanTest.log.debugf("Number of Cluster: %d \n", listCluster.size());
        DbScanTest.log.debugf("Total Point in cluster: %d \n", countPoint);
        DbScanTest.log.debugf("Number of Noise: %d \n", dbScan.getListNoise().size());
        DbScanTest.log.debugf("Total Point checked vs Total Point out: %d vs %d \n", listData.size(),
            countPoint + dbScan.getListNoise().size());
        DbScanTest.log.debug("=========================================\n");

    }
}
