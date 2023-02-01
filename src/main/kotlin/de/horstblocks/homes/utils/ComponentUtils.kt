package de.horstblocks.homes.utils

import net.kyori.adventure.text.Component

operator fun Component.plus(t: Component): Component {
    return this.append(t)
}
