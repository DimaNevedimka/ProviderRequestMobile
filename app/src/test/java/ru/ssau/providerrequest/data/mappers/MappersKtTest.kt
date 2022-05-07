package ru.ssau.providerrequest.data.mappers

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.ssau.providerrequest.data.entity.Home
import ru.ssau.providerrequest.data.entity.Tariff

class MappersKtTest {

    @Test
    fun testMapper() {
        val home = Home(
            address = "address",
            comment = "comment",
            coords = "53.215029, 50.172643",
            created_at = "2022-04-20T16:21:56.000000Z",
            email_manager = "79212345678",
            id = 1,
            phone_manager = "manager@roundcloud.ru",
            updated_at = "2022-04-20T16:21:56.000000Z"
        )
        val answer = home.toDto()
        val expected = Home(
            address = "address",
            comment = "comment",
            coords = "53.215029, 50.172643",
            created_at = "2022-04-20T16:21:56.000000Z",
            email_manager = "79212345678",
            id = 1,
            phone_manager = "manager@roundcloud.ru",
            updated_at = "2022-04-20 16:21")
        assertEquals(expected, answer)
    }

    @Test
    fun testFinder() {
        val tariff = Tariff(
            name = "fff",
            desc = "eee",
            archive = "fff",
            created_at = "2022-04-20T16:21:56.000000Z",
            id = 1,
            updated_at = "2022-04-20 16:21"
        )
        val expected = Tariff(
            name = "fff",
            archive = "fff",
            desc = "eee",
            created_at = "2022-04-20T16:21:56.000000Z",
            id = 1,
            updated_at = "2022-04-20 16:21")
        assertEquals(expected, tariff)
    }
}