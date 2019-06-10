package busymachines.pureharm

import busymachines.pureharm.Anomaly.Parameters

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 10 Jun 2019
  *
  */
abstract class NotImplementedCatastrophe(
  override val message: String,
  causedBy:             Option[Throwable] = None,
) extends Catastrophe(message, causedBy) with Product with Serializable {
  override def id: AnomalyID = NotImplementedCatastropheID
}

private[pureharm] case object NotImplementedCatastropheID extends AnomalyID with Product with Serializable {
  override val name: String = "NI_0"
}

object NotImplementedCatastrophe extends CatastropheConstructors[NotImplementedCatastrophe] {
  private[pureharm] val NotImplementedCatastropheMsg: String = "Not Implemented Catastrophe"

  override def apply(causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(message = causedBy.getMessage, causedBy = Option(causedBy))

  override def apply(id: AnomalyID, message: String, causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, message = message, causedBy = Option(causedBy))

  override def apply(id: AnomalyID, parameters: Parameters, causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, parameters = parameters, causedBy = Option(causedBy))

  override def apply(message: String, parameters: Parameters, causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(message = message, parameters = parameters, causedBy = Option(causedBy))

  override def apply(
    id:         AnomalyID,
    message:    String,
    parameters: Parameters,
    causedBy:   Throwable,
  ): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, message = message, parameters = parameters, causedBy = Option(causedBy))

  override def apply(a: Anomaly, causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(
      id         = a.id,
      message    = a.message,
      parameters = a.parameters,
      causedBy   = Option(causedBy),
    )

  override def apply(id: AnomalyID): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id)

  override def apply(message: String): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(message = message)

  override def apply(parameters: Parameters): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(parameters = parameters)

  override def apply(id: AnomalyID, message: String): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, message = message)

  override def apply(id: AnomalyID, parameters: Parameters): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, parameters = parameters)

  override def apply(message: String, parameters: Parameters): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(message = message, parameters = parameters)

  override def apply(id: AnomalyID, message: String, parameters: Parameters): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(id = id, message = message, parameters = parameters)

  override def apply(a: Anomaly): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(
      id         = a.id,
      message    = a.message,
      parameters = a.parameters,
      causedBy   = Option(a),
    )

  override def apply(message: String, causedBy: Throwable): NotImplementedCatastrophe =
    NotImplementedCatastropheImpl(message = message, causedBy = Option(causedBy))
}

final private[pureharm] case class NotImplementedCatastropheImpl(
  override val id:         AnomalyID          = NotImplementedCatastropheID,
  override val message:    String             = NotImplementedCatastrophe.NotImplementedCatastropheMsg,
  override val parameters: Anomaly.Parameters = Anomaly.Parameters.empty,
  override val causedBy:   Option[Throwable]  = None,
) extends NotImplementedCatastrophe(message, causedBy = causedBy)
