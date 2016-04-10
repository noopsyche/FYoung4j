package love.sola.fyoung.util.collection;

import gnu.trove.set.hash.TCustomHashSet;

import java.util.Collection;

public class CaseInsensitiveSet extends TCustomHashSet<String> {

	public CaseInsensitiveSet() {
		super(CaseInsensitiveHashingStrategy.INSTANCE);
	}

	public CaseInsensitiveSet(Collection<? extends String> collection) {
		super(CaseInsensitiveHashingStrategy.INSTANCE, collection);
	}
}