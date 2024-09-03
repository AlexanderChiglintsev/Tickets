import kotlinx.coroutines.*

fun main() {
    println("Количество киосков:")
    val servicePoints = getCount()
    println("Количество билетов в одном киоске:")
    val tickets = getCount()
    println("=================================")
    println("Киоски: $servicePoints, билеты в киоске: $tickets")
    println("=================================\n")

    println("Продажа билетов открыта!\n")

    val ticketShops: MutableList<TicketShop> = mutableListOf()
    repeat(servicePoints) {
        ticketShops.add(TicketShop(tickets))
    }

    runBlocking {
        for (shop in ticketShops) {
            launch {
                startSelling(shop)
            }
        }

        launch {
            checkRemainingTickets(ticketShops)
        }
    }
}

private suspend fun startSelling(shop: TicketShop) {
    withContext(Dispatchers.Default) {
        var remainingTickets: Int
        do {
            delay(600)
            remainingTickets = shop.buyTicket()
        } while (remainingTickets > 0)
    }
}

private suspend fun checkRemainingTickets(ticketShops: MutableList<TicketShop>) {
    withContext(Dispatchers.Default) {
        do {
            println("<------ Информация по оставшимся билетам ------>")

            var allRemainingTickets = 0
            for (i in 0 until ticketShops.size) {
                val currentTicketCount = ticketShops[i].getTicketCount()
                println("Киоск ${i + 1} - осталось билетов: $currentTicketCount")
                allRemainingTickets += currentTicketCount
            }
            println("Всего осталось билетов: $allRemainingTickets\n")

            if (allRemainingTickets == 0) println("Все билеты распроданы!")
            delay(1000)
        } while (allRemainingTickets > 0)
    }
}

private fun getCount(): Int {
    do {
        val input = readln()
        try {
            val count = input.toInt()
            if (count > 0) {
                return count
            }

            println("Число должно быть больше нуля! Введите целое положительное число:")
        } catch (e: NumberFormatException) {
            println("Некорректный ввод! Введите целое положительное число:")
        }
    } while (true)
}