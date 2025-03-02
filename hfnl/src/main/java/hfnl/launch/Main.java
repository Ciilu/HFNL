/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * */

package hfnl.launch;

import hfnl.launch.utils.FileLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        System.getProperties().putIfAbsent("fabric.development", "true");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
        File logPath = new File("./logs/");

        if (!logPath.exists()) {
            logPath.mkdirs();
        }

        File logFile = new File("logs/" + time + ".log");


        try (FileLogger logger = new FileLogger(System.out, logFile)) {
            System.setErr(logger);
            System.setOut(logger);
            new Launcher().run(args);
        }
    }
}




