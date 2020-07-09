/**
  * Copyright (c) 2019 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package busymachines.pureharm.phantom

/**
  * Example use case:
  * {{{
  * package object api {
  *   object SpecificString extends PhantomType[String]
  *   type SpecificString = SpecificString.Type
  * }
  * }}}
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 02 Apr 2019
  */
trait PhantomType[T] {
  import shapeless.tag
  import tag.@@

  final type Tag  = this.type
  final type Type = T @@ Tag

  /**
    * Override if you want to do pure transformations on your value
    * before tagging.
    */
  @inline def apply(v: T): Type = tag[Tag][T](v)

  /**
    * alias for [[apply]]
    */
  @inline final def spook(v: T): Type = apply(v)

  @inline final def despook(t: Type): T = t

  /**
    * Override for a custom spook instance
    */
  implicit def spookInstance: Spook[T, Type] = defaultSpook

  private[this] lazy val defaultSpook = new Spook[T, Type] {
    override def spook(v:   T):    Type = PhantomType.this.spook(v)
    override def despook(t: Type): T    = PhantomType.this.despook(t)
  }

}

/**
  * Use this typeclass to talk generically about SafePhantomType. For instance, the way
  * we define generic circe encoders/decoders is the following (taken from pureharm-json-circe):
  * {{{
  *       implicit final def phatomTypeEncoder[Underlying, Phantom](implicit
  *       spook:   Spook[Underlying, Phantom],
  *       encoder: Encoder[Underlying],
  *     ): Encoder[Phantom] = encoder.contramap(spook.despook)
  *
  *     implicit final def phatomTypeDecoder[Underlying, Phantom](implicit
  *       spook:   Spook[Underlying, Phantom],
  *       decoder: Decoder[Underlying],
  *     ): Decoder[Phantom] = decoder.map(spook.spook)
  * }}}
  *
  * @tparam T
  *  the underlying type
  * @tparam PT
  *  the final tagged type, "phantom" type.
  */
trait Spook[T, PT] {
  def spook(a: T): PT

  def despook(t: PT): T
}
