//package com.kbalazsworks.stackjudge_notification.common.factories.DateFactory
//
//import com.kbalazsworks.stackjudge_notification.common.factories.DateFactory
//import io.quarkus.test.junit.QuarkusTest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import java.util.*
//
//@QuarkusTest
//class DateFactory_CreateTest {
//    @Test
//    fun noArgumentCall_returnsDateInstance() {
//        // Arrange
//        // Assert
//        val date = DateFactory().create()
//
//        // Act
//        assertThat(date).isInstanceOf(Date().javaClass)
//    }
//
//    @Test
//    fun parameterizedCall_returnsDateInstanceWithCreatedDate() {
//        // Arrange
//        val testedUnitTimestamp = 1641089045L // Sun Jan 02 2022 02:04:05 GMT+0000
//        val expectedUnitTimestamp = 1641089045L // Sun Jan 02 2022 02:04:05 GMT+0000
//
//        // Assert
//        val date = DateFactory().create(testedUnitTimestamp)
//
//        // Act
//        assertThat(date.time).isEqualTo(expectedUnitTimestamp)
//    }
//}