package com.vauldex.inventory_management

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<InventoryManagementApplication>().with(TestcontainersConfiguration::class).run(*args)
}
