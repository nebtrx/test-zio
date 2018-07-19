package com.github.nebtrx

import scalaz.zio.{App, IO, Void}
import scalaz.zio.console._
import scala.concurrent.duration._

import java.io.IOException

object Main extends App {

  def run(args: List[String]): IO[Void, ExitStatus] =
    myAppLogic.attempt[Void]
      .map(_.fold(_ => 1, _ => 0))
      .map(exitCode => ExitStatus.ExitWhenDone(exitCode, 2.seconds))

  def myAppLogic: IO[IOException, Unit] =
    for {
      _ <- putStrLn("Hello! What is your name?")
      n <- getStrLn
      _ <- putStrLn("Hello, " + n + ", good to meet you!")
    } yield ()
}
