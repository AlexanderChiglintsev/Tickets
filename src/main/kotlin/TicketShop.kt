import kotlinx.atomicfu.atomic

class TicketShop(tickets: Int) {
    private val ticketsCount = atomic(tickets)

    fun buyTicket(): Int {
        if (ticketsCount.value > 0) {
            return ticketsCount.decrementAndGet()
        }

        return 0
    }

    fun getTicketCount(): Int {
        return ticketsCount.value
    }
}