package com.example.split.navigation

interface Destinations {
    val title: String
    val route: String
}

object Groups : Destinations {
    override val route: String
        get() = "groups_route"
    override val title: String
        get() = "Groups"
}

object Friends : Destinations {
    override val route: String
        get() = "friends_route"
    override val title: String
        get() = "Friends"
}

object Account : Destinations {
    override val route: String
        get() = "account_route"
    override val title: String
        get() = "Account"
}

object Expenses : Destinations {
    override val route: String
        get() = "expenses_route"
    override val title: String
        get() = "Expenses"
}