import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class TestLifeCycle {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @BeforeSuite
    public void setUpSuite() {
        log.info( " THREAD ID : {} | Entering Setup {} function!", Thread.currentThread().getId(),"Suite");
    }

    @BeforeTest
    public void setUpTest() {
        log.info( " THREAD ID : {} | Entering Setup {} function!", Thread.currentThread().getId(),"Test");
    }

    @BeforeClass
    public void setUpClass() {
        log.info( " THREAD ID : {} | Entering Setup {} function!", Thread.currentThread().getId(),"Class");
    }

    @BeforeMethod
    public void setUpMethod() {
        log.info( " THREAD ID : {} | Entering Setup {} function!", Thread.currentThread().getId(),"Method");
    }

    @Test
    public void test1() {
        log.info( " THREAD ID : {} | Entering {} function!",Thread.currentThread().getId(),"test1");
    }

    @Test
    public void test2() {
        log.info( " THREAD ID : {} | Entering {} function!",Thread.currentThread().getId(),"test2");
    }

    @AfterMethod
    public void tearDownMethod() {
        log.info( " THREAD ID : {} | Entering After {} function!", Thread.currentThread().getId(),"Method");
    }

    @AfterClass
    public void tearDownClass() {
        log.info( " THREAD ID : {} | Entering After {} function!", Thread.currentThread().getId(),"Class");
    }

    @AfterTest
    public void tearDownTest() {
        log.info( " THREAD ID : {} | Entering After {} function!", Thread.currentThread().getId(),"Test");
    }

    @AfterSuite
    public void tearDownSuite() {
        log.info( " THREAD ID : {} | Entering After {} function!", Thread.currentThread().getId(),"Suite");
    }


}
