package ru.javawebinar.topjava.service;


import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.TimeRules;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
abstract public class AbstractServiceTest  {

//    private static final Logger log = LoggerFactory.getLogger("result");
//
//    private static StringBuilder results = new StringBuilder();

    @ClassRule
    public static ExternalResource summary = TimeRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimeRules.STOPWATCH;
//        @Override
//        protected void finished(long nanos, Description description) {
//            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
//            results.append(result).append('\n');
//            log.info(result + " ms\n");
//        }
//    };
    public ExpectedException exception = ExpectedException.none();

    static {
        SLF4JBridgeHandler.install();
    }

//    @AfterClass
//    public static void printResult(){
//        log.info("\n-------------------------------------------------------------------------------------------------------" +
//                "\nTest                                                                                       Duration, ms" +
//                "\n-------------------------------------------------------------------------------------------------------\n" +
//                results +
//                "-------------------------------------------------------------------------------------------------------\n");
//        results.setLength(0);
//    }
}
