package com.github.nebtrx

import java.io.IOException

import com.github.nebtrx.App.AppEnv
import scalaz.zio.console._
import scalaz.zio.internal.{Platform, PlatformLive}
import scalaz.zio.{Runtime, ZIO}
import com.github.nebtrx.persistence.Persistence

object App {
  type AppEnv = Console with Persistence
}

trait AppRuntime extends App with Runtime[AppEnv] {

  val Platform: Platform       = PlatformLive.Default
  val Environment: AppEnv      = new Console.Live with Persistence.Live
}

object Main extends AppRuntime {

  def program: ZIO[AppEnv, Nothing, Int] =
    myAppLogic.either.map(_.fold(_ => 1, _ => 0))

  val myAppLogic: ZIO[AppEnv, IOException, Unit] =
    for {
      _ <- putStrLn("Hello! What is your name?")
      n <- getStrLn
      numbers <- ZIO.accessM[AppEnv, Nothing, List[Int]](_.persistence.getAll)
      _ <- putStrLn(s"Hello, ${n}, good to meet you! This are the numbers: ${numbers}")
    } yield ()

  unsafeRun(program)
}
