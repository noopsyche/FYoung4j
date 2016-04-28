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

package love.sola.fyoung.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LoggerOutputStream extends ByteArrayOutputStream {

    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level level;

    public LoggerOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    public void flush() throws IOException {
        synchronized(this) {
            super.flush();
            String record = this.toString();
            super.reset();
            if(record.length() > 0 && !record.equals(this.separator)) {
                this.logger.log(this.level, record);
            }
        }
    }
}
