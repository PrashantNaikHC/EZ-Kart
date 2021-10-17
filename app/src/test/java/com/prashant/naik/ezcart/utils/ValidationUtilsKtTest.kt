package com.prashant.naik.ezcart.utils


import com.prashant.naik.ezcart.ui.login.LoginState
import junit.framework.TestCase.*
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun `Invalid email address returns false`() {
        assertFalse(validateInputIsEmail(null, "prashantsn23gmail.com"))
        assertFalse(validateInputIsEmail(null, "prashantsn23@gmailcom"))
    }

    @Test
    fun `Valid email address returns true`() {
        assertTrue(validateInputIsEmail(null, "prashantsn23@gmail.com"))
    }

    @Test
    fun `Valid mobile number returns true`() {
        assertTrue(validateInputIsMobile(null, "1234567890"))
    }

    @Test
    fun `Invalid mobile number returns true`() {
        assertFalse(validateInputIsMobile(null, "12345678"))
        assertFalse(validateInputIsMobile(null, "12345678901"))
    }

    @Test
    fun `Empty input field returns false`() {
        assertFalse(validateInputField(null, ""))
    }

    @Test
    fun `Non Empty input field returns true`() {
        assertTrue(validateInputField(null, "aa"))
    }

    @Test
    fun `Empty Feedback input text returns false`() {
        assertFalse(validateFeedbackField(null, ""))
    }


    @Test
    fun `Feedback input text exceeding 500 characters returns false`() {
        assertFalse(
            validateFeedbackField(
                null,
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901"
            )
        )
    }

    @Test
    fun `Password without a capital letter returns false`() {
        assertFalse(validateSignUpPassword(null, "asdfq1234!"))
    }

    @Test
    fun `Password without numbers returns false`() {
        assertFalse(validateSignUpPassword(null, "ASDFq!qwer"))
    }


    @Test
    fun `Password without special character returns false`() {
        assertFalse(validateSignUpPassword(null, "ASDFq44qwer"))
    }

    @Test
    fun `Password with length less than 8 returns false`() {
        assertFalse(validateSignUpPassword(null, "ASq12!"))
    }

    @Test
    fun `Valid Password returns true`() {
        assertTrue(validateSignUpPassword(null, "ASDFq1234!"))
    }

    @Test
    fun validateSignInSuccess() {
        assertFalse(validateSignInSuccess(null, LoginState.USER_NOT_PRESENT))
        assertFalse(validateSignInSuccess(null, LoginState.INCORRECT_PASSWORD))
        assertTrue(validateSignInSuccess(null, LoginState.SUCCESS))
    }
}