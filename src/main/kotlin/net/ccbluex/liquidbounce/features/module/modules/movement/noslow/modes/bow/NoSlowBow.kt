/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2015 - 2024 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.noslow.modes.bow

import net.ccbluex.liquidbounce.config.types.Choice
import net.ccbluex.liquidbounce.config.types.NoneChoice
import net.ccbluex.liquidbounce.config.types.ToggleableConfigurable
import net.ccbluex.liquidbounce.features.module.modules.movement.noslow.ModuleNoSlow
import net.ccbluex.liquidbounce.features.module.modules.movement.noslow.modes.shared.NoSlowNoBlockInteract
import net.ccbluex.liquidbounce.features.module.modules.movement.noslow.modes.shared.NoSlowSharedGrim2360
import net.ccbluex.liquidbounce.features.module.modules.movement.noslow.modes.shared.NoSlowSharedGrim2364MC18
import net.ccbluex.liquidbounce.features.module.modules.movement.noslow.modes.shared.NoSlowSharedInvalidHand
import net.ccbluex.liquidbounce.utils.client.inGame
import net.minecraft.util.UseAction

internal object NoSlowBow : ToggleableConfigurable(ModuleNoSlow, "Bow", true) {

    val forwardMultiplier by float("Forward", 1f, 0.2f..1f)
    val sidewaysMultiplier by float("Sideways", 1f, 0.2f..1f)

    val modes = choices<Choice>(this, "Choice", { it.choices[0] }) {
        arrayOf(
            NoneChoice(it),
            NoSlowSharedGrim2360(it),
            NoSlowSharedGrim2364MC18(it),
            NoSlowSharedInvalidHand(it),
        )
    }

    override fun handleEvents(): Boolean {
        if (!super.handleEvents() || !inGame) {
            return false
        }

        // Check if we are using a block item
        return player.isUsingItem && player.activeItem.useAction in arrayOf(
            UseAction.BOW,
            UseAction.CROSSBOW,
            UseAction.SPEAR
        )
    }

    @Suppress("unused")
    private val noBlockInteract = tree(NoSlowNoBlockInteract(this) { action ->
        action == UseAction.BOW || action == UseAction.CROSSBOW || action == UseAction.SPEAR
    })

}
