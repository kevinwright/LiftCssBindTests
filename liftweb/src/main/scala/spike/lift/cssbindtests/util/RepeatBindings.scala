package spike.lift.cssbindtests
package util

import xml.{Elem, NodeSeq}

object RepeatBindings {
  type NsFunc = NodeSeq => NodeSeq

  def contentOf(ns: NodeSeq): NodeSeq = ns match {
    case e: Elem => e.child
    case x => x
  }

  def repeat(nsf:Seq[NsFunc]): NsFunc =
    (xml:NodeSeq) => nsf flatMap (_ apply xml)

  def repeatContents(nsf:Seq[NsFunc]): NsFunc =
    (xml:NodeSeq) =>  nsf flatMap (_ apply contentOf(xml))

  def repeatFor[T](coll:Seq[T])(func:T => NsFunc) = {
    repeat(coll map func)
  }

  def repeatContentsFor[T](coll:Seq[T])(func:T => NsFunc) = {
    repeatContents(coll map func)
  }

}