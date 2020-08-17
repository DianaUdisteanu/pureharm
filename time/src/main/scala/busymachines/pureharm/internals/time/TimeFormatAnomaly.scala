package busymachines.pureharm.internals.time
import busymachines.pureharm.anomaly._

final case class TimeFormatAnomaly(formater : String) extends ConflictAnomaly(
  s"""
     |
     |🙈 The format introduced ${formater} is not correct.
     |🙈 Please look carefully and change it!
     |
     |""".stripMargin
) {

  override val id: AnomalyID = TimeFormatAnomaly.TimeFormatID
}

object TimeFormatAnomaly {
  case object TimeFormatID extends AnomalyID { override val name: String = "time anomaly" }
}
