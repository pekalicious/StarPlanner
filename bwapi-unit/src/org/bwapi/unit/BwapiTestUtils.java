package org.bwapi.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import org.bwapi.bridge.BridgeRuntimeException;
import org.bwapi.unit.model.BroodwarButton;
import org.bwapi.unit.model.BroodwarGameType;
import org.bwapi.unit.model.BroodwarMap;
import org.bwapi.unit.model.BroodwarPlayer;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LRECT;
import org.xvolks.jnative.util.User32;

/**
 * Utilities for handling Starcraft and the Chaos Launcher
 * 
 * @author Chad Retz
 */
final class BwapiTestUtils {
    
    static final Robot ROBOT;
    
    static final float DELAY_MULTIPLIER = 1F;
    
    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    static void delay(int ms) throws Exception {
        Thread.sleep((int) (ms * DELAY_MULTIPLIER));
    }
    
    static void waitForButton(HWND wnd, BroodwarButton button, int timeout) throws Exception {
        LRECT wndRect = new LRECT();
        int currCount = 0;
        do {
            assertTrue("Can't get window rect", User32.GetWindowRect(wnd, wndRect));
            if (ROBOT.getPixelColor(wndRect.getLeft() + button.getX(), 
                    wndRect.getTop() + button.getY()).getRGB() == button.getRgb()) {
                delay(700);
                return;
            }
            currCount += 100;
            delay(100);
        } while (currCount < timeout);
        throw new BridgeRuntimeException("Timeout reached!");
    }
    
    static void waitForAndClickButton(HWND wnd, BroodwarButton button) throws Exception {
        waitForButton(wnd, button, 2000);
        relativeClick(wnd, button.getX(), button.getY(), InputEvent.BUTTON1_MASK);
    }
    
    static void relativeMove(HWND wnd, int x, int y) throws Exception {
        LRECT wndRect = new LRECT();
        assertTrue("Can't get window rect", User32.GetWindowRect(wnd, wndRect));
        int clickX = wndRect.getLeft() + x;
        int clickY = wndRect.getTop() + y;
        //move
        ROBOT.mouseMove(clickX, clickY);
    }
    
    static void relativeClick(HWND wnd, int x, int y, int buttonMask) throws Exception {
        relativeMove(wnd, x, y);
        //click
        ROBOT.mousePress(buttonMask);
        ROBOT.mouseRelease(buttonMask);
    }
    
    static HWND loadChaosLauncher(String chaosLauncherFolder, 
            AtomicReference<Process> process) throws Exception {
        //first, find chaos launcher exe
        File chaosLauncher = new File(chaosLauncherFolder +
                File.separator + "Chaoslauncher.exe");
        assertTrue("Cannot find Chaoslauncher.exe", chaosLauncher.exists());
        //now fire it up...
        ProcessBuilder builder = new ProcessBuilder(chaosLauncher.getAbsolutePath());
        builder.directory(new File(chaosLauncherFolder));
        process.set(builder.start());
        //process.set(Runtime.getRuntime().exec("cmd.exe /C " + chaosLauncher.getAbsolutePath()));
        //let's wait 200 ms
        delay(200);
        //find the damned window
        HWND chaosWnd = User32.FindWindow("TChaoslauncherForm", "Chaoslauncher");
        assertNotNull("Unable to locate launched Chaoslauncher process", chaosWnd);
        //make me the active guy here...
        User32.SetActiveWindow(chaosWnd);
        return chaosWnd;
    }

    static HWND loadStarcraft(String chaosLauncherFolder, 
            AtomicReference<Process> chaosProcess) throws Exception {
        //load chaos launcher
        HWND chaosWnd = loadChaosLauncher(chaosLauncherFolder, chaosProcess);
        delay(300);
        //click "Start"
        relativeClick(chaosWnd, 20, 360, InputEvent.BUTTON1_MASK);
        //wait
        delay(700);
        HWND scWnd = getStarcraftWindow();
        assertNotNull("Unable to locate StarCraft window", scWnd);
        //wait another second
        delay(1000);
        //activate it
        User32.SetActiveWindow(scWnd);
        return scWnd;
    }
    
    static HWND getStarcraftWindow() throws Exception {
        return User32.FindWindow("SWarClass", "Brood War");
    }
    
    static void killStarcraft() throws Exception {
        //kill starcraft.exe
        //I hope they have tskill
        Runtime.getRuntime().exec("tskill StarCraft");
    }
    
    static String getMapDirectory(String starcraftFolder) {
        return starcraftFolder + "/Maps/Broodwar/()UnitTestMaps";
    }
    
    static void createMapFileDirectory(File path) {
        if (!path.exists()) {
            path.mkdir();
        }
    }

    static void deleteMapsInDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".scx") ||
                        files[i].getName().endsWith(".scm")) {
                    files[i].delete();
                }
            }
        }
    }
    
    static void selectMap(HWND scWnd, String starcraftFolder,
            BroodwarMap map) throws Exception {
        assertTrue("Map name must end with .scx or .scm", 
                map.getName().endsWith(".scx") || map.getName().endsWith(".scm"));
        //delete my old dirs files if there
        File dir = new File(getMapDirectory(starcraftFolder));
        deleteMapsInDirectory(dir);
        //copy this map
        InputStream stream = map.open();
        OutputStream writer = null;
        try {
            writer = new FileOutputStream(dir.getAbsolutePath() + "/" + map.getName());
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead); 
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
        //now scroll to the top...
        int fileCount = new File(starcraftFolder + "/Maps/Broodwar").list().length;
        for (int i = 0; i < fileCount; i++) {
            relativeClick(scWnd, 345, 182, InputEvent.BUTTON1_MASK);
        }
        //now double click the first one (my dir)
        relativeClick(scWnd, 90, 169, InputEvent.BUTTON1_MASK);
        delay(50);
        relativeClick(scWnd, 90, 169, InputEvent.BUTTON1_MASK);
        delay(100);
        //now single click the second file (the newly created map)
        relativeClick(scWnd, 90, 189, InputEvent.BUTTON1_MASK);
        delay(100);
    }
    
    static void selectGameType(HWND scWnd, BroodwarGameType type) throws Exception {
        //move mouse, drag, move, release
        relativeMove(scWnd, 345, 300);
        delay(100);
        ROBOT.mousePress(InputEvent.BUTTON1_MASK);
        delay(100);
        relativeMove(scWnd, 345, type.getY());
        delay(100);
        ROBOT.mouseRelease(InputEvent.BUTTON1_MASK);
        delay(100);
    }
    
    static void setupPlayers(HWND scWnd, BroodwarPlayer[] players) throws Exception {
        if (players == null || players.length == 0) {
            //no players...do nothin
            return;
        }
        assertTrue("Must have at least two players", players.length > 1);
        int playerX = 176;
        int yInit = 354;
        int raceX = 336;
        int yDiff = 19;
        for (int i = 0; i < 8; i++) {
            if (i >= players.length) {
                //close it up
                relativeMove(scWnd, playerX, yInit + yDiff * i);
                //drop it down
                ROBOT.mousePress(InputEvent.BUTTON1_MASK);
                delay(100);
                //move to the right place
                if (i != 7) {
                    relativeMove(scWnd, playerX, yInit + yDiff * i + 17);
                } else {
                    relativeMove(scWnd, playerX, yInit + yDiff * i - 37);
                }
                delay(100);
                //release
                ROBOT.mouseRelease(InputEvent.BUTTON1_MASK);
                delay(100);
            } else {
                //set the race
                relativeMove(scWnd, raceX, yInit + yDiff * i);
                //drop it down
                ROBOT.mousePress(InputEvent.BUTTON1_MASK);
                delay(100);
                //move to the right place
                if (i < 5) {
                    relativeMove(scWnd, raceX, yInit + yDiff * i + 
                            17 * (players[i].getRace().ordinal() + 1));
                } else {
                    relativeMove(scWnd, raceX, yInit + yDiff * i - 
                            17 * (3 - players[i].getRace().ordinal() + 1));
                }
                delay(100);
                //release
                ROBOT.mouseRelease(InputEvent.BUTTON1_MASK);
                delay(100);
            }
        }
    }
    
    private BwapiTestUtils() {
        
    }
}
