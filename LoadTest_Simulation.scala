import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest_Simulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://demo.testfire.net")
    .inferHtmlResources()

  val scn = scenario("Demo Load Test")
    .exec(http("Home Page").get("/"))
    .pause(2)
    .exec(http("Login Page").get("/login.jsp"))
    .pause(2)
    .exec(
      http("Do Login")
        .post("/login.jsp")
        .formParam("uid", "jsmith")
        .formParam("passw", "demo123")
    )
    .pause(2)
    .exec(http("Account Summary").get("/bank/main.jsp"))
    .pause(2)
    .exec(http("Logout").get("/logout.jsp"))

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpProtocol)
}