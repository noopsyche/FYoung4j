package love.sola.fyoung.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ***********************************************
 * Created by Sola on 2016/4/11.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Getter
@AllArgsConstructor
public class LoginStateChangedEvent extends Event {

	boolean loggedIn;

}
