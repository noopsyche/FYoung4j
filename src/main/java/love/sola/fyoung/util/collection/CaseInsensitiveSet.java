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