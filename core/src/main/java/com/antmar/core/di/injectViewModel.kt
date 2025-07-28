package com.antmar.core.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> (() -> VM).injectViewModel(): VM {
    val creator = this
    val factory = remember(creator) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val instance = creator()
                if (!modelClass.isInstance(instance)) {
                    throw IllegalArgumentException("Expected ${modelClass.canonicalName}, got ${instance::class.java.canonicalName}")
                }
                @Suppress("UNCHECKED_CAST")
                return instance as T
            }
        }
    }
    return viewModel(factory = factory)
}

@Composable
inline fun <reified VM : ViewModel> KIViewModel(noinline creator : () -> VM) : VM {
    val factory = remember (creator) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val instance = creator()
                if (!modelClass.isInstance(instance)){
                    throw IllegalArgumentException("Expected ${modelClass.canonicalName}, got ${instance::class.java.canonicalName}")
                }
                @Suppress("UNCHECKED_CAST")
                return instance as T
            }
        }
    }
    return viewModel(factory = factory)
}