// package arrow.businesstraceability.data.repository;
//
// import java.io.File;
// import java.util.List;
// import javax.inject.Inject;
//
// import org.jboss.arquillian.container.test.api.Deployment;
// import org.jboss.arquillian.junit.Arquillian;
// import org.jboss.arquillian.persistence.CreateSchema;
// import org.jboss.arquillian.persistence.UsingDataSet;
// import org.jboss.shrinkwrap.api.ShrinkWrap;
// import org.jboss.shrinkwrap.api.spec.WebArchive;
// import org.jboss.shrinkwrap.resolver.api.maven.Maven;
// import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
// import org.junit.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
//
// import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
// import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
// import arrow.framework.util.AssertUtils;
// import arrow.framework.util.cdi.CDIUtils;
// import arrow.framework.util.cdi.Instance;
//
/// **
// * Created by michael on 25/05/2016.
// */
// @CreateSchema({ "truncate.sql" })
// @RunWith(Arquillian.class)
// public class OtherTestcaseTestIT {
//
// @Deployment
// public static WebArchive createDeployment() {
// WebArchive war = ShrinkWrap.create(WebArchive.class)
// .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
// .addAsResource("test-apache-deltaspike.properties", "apache-deltaspike.properties")
// .addAsWebInfResource("test-ds.xml").addPackages(true, "arrow.framework.persistence")
// .addPackages(true, "arrow.framework.util").addPackages(true, "arrow.framework.logging")
// .addClass(arrow.framework.bootstrap.Resources.class).addClass(CDIUtils.class).addClass(Instance.class)
// .addClass(AssertUtils.class).addAsWebInfResource("beans.xml", "beans.xml")
// .addPackages(true, "arrow.businesstraceability.persistence");
//
// // Add libraries
// PomEquippedResolveStage resolver = Maven.resolver().loadPomFromFile("pom.xml");
// File[] files = resolver.importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();
// war.addAsLibraries(files);
//
// // System.out.println(war.toString(true));
// return war;
// }
//
// @Inject
// private Addresspoint_mstRepository repo;
//
// @Test
// @UsingDataSet("AddresspointRepositoryIT.xls")
// public void testGetDistinctAddressPointOfSalesPersonBaseOnCustomerCode() throws Exception {
// String companyCode = "4000000649";
// List<Addresspoint_mst> listAll = this.repo.getDistinctAddressPointOfSalesPersonBaseOnCustomerCode(companyCode);
// Assert.assertNotNull(listAll);
// Assert.assertEquals(1, listAll.size());
// }
// }
