package org.apache.flink.cep.scala

import org.apache.flink.cep.functions.InjectionPatternFunction
import org.apache.flink.cep.{EventComparator, CEP1 => JCEP}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.scala.DataStream

/**
  * Utility method to transform a [[DataStream]] into a [[PatternStream]] to do CEP.
  */

object CEP1 {
  /**
    * Transforms a [[DataStream]] into a [[PatternStream]] in the Scala API.
    * See [[JCEP}]]for a more detailed description how the underlying
    * Java API works.
    *
    * @param input   DataStream containing the input events
    * @param pattern Pattern specification which shall be detected
    * @tparam T Type of the input events
    * @return Resulting pattern stream
    */
  def pattern[T](input: DataStream[T], pattern: Pattern[T, _ <: T]): PatternStream[T] = {
    wrapPatternStream(JCEP.pattern(input.javaStream, pattern.wrappedPattern))
  }

  /**
    * Transforms a [[DataStream]] into a [[PatternStream]] in the Scala API.
    * See [[JCEP}]]for a more detailed description how the underlying
    * Java API works.
    *
    * @param input      DataStream containing the input events
    * @param pattern    Pattern specification which shall be detected
    * @param comparator Comparator to sort events with equal timestamps
    * @tparam T Type of the input events
    * @return Resulting pattern stream
    */
  def pattern[T](
                  input: DataStream[T],
                  pattern: Pattern[T, _ <: T],
                  comparator: EventComparator[T]): PatternStream[T] = {
    wrapPatternStream(JCEP.pattern(input.javaStream, pattern.wrappedPattern, comparator))
  }

  def injectionPattern[T](
    input: DataStream[T],
    injectionPatternFunction: InjectionPatternFunction[T]): PatternStream[T]= {
    wrapPatternStream(JCEP.injectionPattern(input.javaStream,injectionPatternFunction))
  }
}
