package com.github.nebtrx.persistence

import scalaz.zio.UIO

trait Persistence {
  val persistence: Persistence.Service[Any]
}

object Persistence extends Serializable {
  trait Service[R] {
    def getAll : UIO[List[Int]]
  }

  trait Live extends Persistence {
    val persistence: Persistence.Service[Any] = new Persistence.Service[Any] {
      def getAll: UIO[List[Int]] = UIO.apply(List(1, 3, 4))
    }
  }

  object Live extends Live
}
