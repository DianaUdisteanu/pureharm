package busymachines.pureharm.time
import busymachines.pureharm.testkit._
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.time.implicits._

class LocalDateTest extends PureharmTest {

  test("LocalDate - show") {
    val value = "2020-08-10"
    for {
      ld <- LocalDate.parse[IO](value)
      seen = ld.show
    } yield assert(value == seen)
  }

  test("LocalDate - show now"){
    for {
      now <- LocalDate.now[IO]
      value = now.show
      lt <- LocalDate.parse[IO](value)
      seen = lt.show
    } yield assert(value == seen)
  }

  /*test("LocalDate - toLocalDateTime"){
    for{
      nw <- LocalDate.now[IO]
      lt = LocalDate.toLocalDateTime(nw)
      seen = lt.show
      ldt <- LocalDateTime.now[IO]
      see = ldt.show
    }yield  assert(see == seen)
  }*/
}
