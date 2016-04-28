package love.sola.fyoung.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Getter
@AllArgsConstructor
public class LoginStateChangedEvent extends Event {

	boolean loggedIn;

}
