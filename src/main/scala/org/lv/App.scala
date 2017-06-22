package org.lv

import org.lv.server.Server

object App {
  def main(args: Array[String]): Unit = {
    Server.boostrap(8000)
  }
}
