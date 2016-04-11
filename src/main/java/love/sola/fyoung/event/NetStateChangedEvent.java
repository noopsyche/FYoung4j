package love.sola.fyoung.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import love.sola.fyoung.NetState;

/**
 * ***********************************************
 * Created by Sola on 2016/4/10.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@AllArgsConstructor
@Getter
public class NetStateChangedEvent extends NetworkEvent {

	private final NetState previous;
	private final NetState now;

}
