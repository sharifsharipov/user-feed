package com.example.userfeed.domain.usecase

import com.example.userfeed.fake.FakeData
import com.example.userfeed.fake.FakeUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetUsersUseCaseTest {

    private val repository = FakeUserRepository()
    private val useCase = GetUsersUseCase(repository)

    @Test
    fun returnsEmptyListWhenNoUsers() = runTest {
        val result = useCase().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun returnsCachedUsersFromDb() = runTest {
        repository.emitUsers(FakeData.users)

        val result = useCase().first()

        assertEquals(3, result.size)
        assertEquals("John Doe", result.first().name)
    }

    @Test
    fun reactsToNewDataEmissions() = runTest {
        repository.emitUsers(FakeData.users.take(1))
        val first = useCase().first()
        assertEquals(1, first.size)

        repository.emitUsers(FakeData.users)
        val second = useCase().first()
        assertEquals(3, second.size)
    }
}
