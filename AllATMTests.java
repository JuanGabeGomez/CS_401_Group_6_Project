import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	TestMainFunctions.class, TestSecondaryFunctions.class
})
public class AllATMTests {
}