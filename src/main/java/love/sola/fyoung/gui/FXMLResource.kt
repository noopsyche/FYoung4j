/*
 * This file is part of FYoung4j.
 *
 * FYoung4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FYoung4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FYoung4j.  If not, see <http://www.gnu.org/licenses/>.
 */

package love.sola.fyoung.gui

import java.net.URL
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.defaultType

/**
 * @author Sola
 */
object FXMLResource {

    lateinit var edit_config: URL
    lateinit var guilog: URL
    lateinit var tip_dialog: URL

    init {
        for (memberProperty in FXMLResource::class.declaredMemberProperties) {
            if (memberProperty.returnType != URL::class.defaultType) {
                continue
            }
            (memberProperty as KMutableProperty1<FXMLResource, URL>)
                    .set(this, SystemTrayLauncher::class.java.getResource("/assets/fxml/${memberProperty.name}.fxml"))
        }
    }

}
