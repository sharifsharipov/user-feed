package com.example.userfeed.fake

import com.example.userfeed.core.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : DispatcherProvider {
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
}
