package com.antmar.core.navigation

interface Navigator {
    fun navigate (route : NavRoutes)
    fun popBackStack()
}