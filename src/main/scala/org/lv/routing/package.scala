package org.lv

package object routing {
  trait BuildingLogInfo {
    val buildingId: Int
    val badgeId: Int
  }
  case class CheckinInput(buildingId: Int, badgeId: Int) extends BuildingLogInfo

  case class CheckoutInput(buildingId: Int, badgeId: Int) extends BuildingLogInfo
}
