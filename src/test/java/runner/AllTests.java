package runner;

import api.tests.users.DeleteUserTest;
import api.tests.users.GetUsersTest;
import api.tests.users.PostUserTest;
import api.tests.users.PutUserTest;
//import e2e.tests.LoginTest;
//import e2e.tests.ProductTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    DeleteUserTest.class,
    GetUsersTest.class,
    PostUserTest.class,
    PutUserTest.class,
//    LoginTest.class,
//    ProductTest.class
})
public class AllTests {

}
