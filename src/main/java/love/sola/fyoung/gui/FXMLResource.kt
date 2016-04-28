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
