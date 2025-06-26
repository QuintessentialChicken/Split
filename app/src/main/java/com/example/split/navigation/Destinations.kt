package com.example.split.navigation

interface Destinations {
    val title: String
    val route: String
}

object Home : Destinations {
    override val route: String
        get() = "home_route"
    override val title: String
        get() = "Home"
}