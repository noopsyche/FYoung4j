package love.sola.fyoung.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import love.sola.fyoung.NetState;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@AllArgsConstructor
@Getter
public class NetStateChangedEvent extends NetworkEvent {

	private final NetState previous;
	private final NetState now;

}
