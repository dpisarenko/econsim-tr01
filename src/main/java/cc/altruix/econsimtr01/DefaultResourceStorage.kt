/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.altruix.econsimtr01

import com.google.common.util.concurrent.AtomicDouble
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class DefaultResourceStorage(val id:String) : IResourceStorage {
    val amountsByResource = HashMap<String, AtomicDouble>()
    val infiniteResources = HashSet<String>()

    override fun put(res: String, amt:Double) {
        var storedAmount = amountsByResource.get(res)
        if (storedAmount == null) {
            storedAmount = AtomicDouble(0.0)
            amountsByResource.put(res, storedAmount)
        }
        storedAmount.getAndAdd(amt)
    }

    override fun amount(res: String): Double {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            return mapAmt.get()
        }
        return 0.0
    }

    override fun remove(res: String, amt: Double) {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            mapAmt.getAndAdd(-amt)
        }
    }

    override fun setInfinite(res: String) {
        infiniteResources.add(res)
    }

    override fun isInfinite(res:String):Boolean = infiniteResources.contains(res)

    override fun id(): String = id
}
