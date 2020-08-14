package busymachines.pureharm.time
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.testkit._
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

  test("LocalDate - toLocalDateTime") {
    for {
      now <- LocalDate.now[IO]
      ldt <- LocalDate.toLocalDateTime[IO](now)
      ld <- LocalDateTime.toLocalDate[IO](ldt)
      value = now.show
      seen = ld.show
    } yield assert(value == seen)
  }
}
