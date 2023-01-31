package de.horstblocks.homes.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

open class KMenu(
    rows: Int,
    title: Component,
    val player: Player
) : InventoryHolder {
    private val inventory: Inventory = Bukkit.createInventory(this, rows * 9, title)
    private val items: MutableMap<Int, KItem> = mutableMapOf()

    fun item(slot: Int, item: KItem): KItem {
        inventory.setItem(slot, item.itemStack)
        items[slot] = item
        return item
    }

    fun open() {
        player.openInventory(inventory)
    }

    fun click(event: InventoryClickEvent) {
        items[event.slot]?.click(this, event)
    }

    fun close() {
        player.closeInventory()
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    companion object {
        val MARKER_ITEM = KItem(Material.BLACK_STAINED_GLASS_PANE, Component.text(" "), listOf()).editMeta {
            it.displayName(Component.text(" "))
        }
    }

    open class KItem(
        type: Material,
        name: Component?,
        lore: List<Component>?,
        amount: Int = 1
    ) {
        val itemStack: ItemStack = ItemStack(type, amount)
        private var clickHandler: (KMenu, InventoryClickEvent) -> Unit = { _, _ -> }

        init {
            itemStack.editMeta { meta ->
                name?.let { meta.displayName(it) }
                lore?.let { meta.lore(it) }
            }
        }

        fun onClick(handler: (KMenu, InventoryClickEvent) -> Unit) {
            clickHandler = handler
        }

        fun click(menu: KMenu, event: InventoryClickEvent) {
            clickHandler(menu, event)
        }

        fun editItem(handler: (ItemStack) -> Unit): KItem {
            handler(itemStack)
            return this
        }

        fun editMeta(handler: (ItemMeta) -> Unit): KItem {
            itemStack.editMeta(handler)
            return this
        }
    }
}