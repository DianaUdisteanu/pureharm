package busymachines.pureharm.time

import busymachines.pureharm.testkit.PureharmTest
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.time.implicits._

class OffsetDateTimeTest extends PureharmTest{

  test("OffsetDateTime - show") {
    val value = "2020-08-11T10:15:00Z"
    for {
      odt <- OffsetDateTime.parse[IO](value)
      seen = odt.show
    } yield assert(value == seen)
  }

  test("OffsetDateTime - show now"){
    for {
      now <- OffsetDateTime.now[IO]
      value = now.show
      odt <- OffsetDateTime.parse[IO](value)
      seen = odt.show
    } yield assert(value == seen)
  }

  /*test("OffsetDateTime - toLocalDateTime"){
    for{
      now <- OffsetDateTime.now[IO]
      ldt <- OffsetDateTime.toLocalDateTime[IO](now)
      seen = ldt.show
      localDateTime <- LocalDateTime.now[IO]
      value = localDateTime.show
    }yield assert(value == seen)
  }*/

  test("OffsetDateTime - toLocalDate"){
    for{
      now <- OffsetDateTime.now[IO]
      ldt <- OffsetDateTime.toLocalDate[IO](now)
      seen = ldt.show
      localDate <- LocalDate.now[IO]
     value = localDate.show
    }yield assert(value == seen)
  }

 /* test("OffsetDateTime - toLocalTime"){
    for{
      now <- OffsetDateTime.now[IO]
      ldt <- OffsetDateTime.toLocalTime[IO](now)
      seen = ldt.show
      localTime <- LocalTime.now[IO]
      value = localTime.show
    }yield assert(value == seen)
  }*/
}
