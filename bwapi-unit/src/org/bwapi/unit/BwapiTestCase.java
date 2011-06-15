package org.bwapi.unit;

import static org.junit.Assert.assertNotNull;

import java.awt.event.InputEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bwapi.bridge.BridgeException;
import org.bwapi.unit.model.BroodwarButton;
import org.bwapi.unit.server.BwapiBridgeBotServerCallback;
import org.bwapi.unit.server.BwapiBridgeBotServerImpl;
import org.xvolks.jnative.misc.basicStructures.HWND;

/**
 * Base test case for JUnit 4 testing
 * 
 * @author Chad Retz
 */
public class BwapiTestCase {
    
    /**
     * Start a Starcraft game with the given settings
     * 
     * @param testInformation
     * @param chaosProcess
     * @throws Exception
     */
    private static void startStarcraft(BwapiTestInformation testInformation,
            AtomicReference<Process> chaosProcess) throws Exception {
        assertNotNull("testInformation must be set in base class", testInformation);
        //put the map folder there if not there
        BwapiTestUtils.createMapFileDirectory(new File(BwapiTestUtils.
                getMapDirectory(testInformation.getStarcraftFolder())));
        //load up SC
        HWND scWnd = BwapiTestUtils.loadStarcraft(testInformation.
                getChaosLauncherFolder(), chaosProcess);
        //single player
        BwapiTestUtils.waitForAndClickButton(scWnd, BroodwarButton.SINGLE_PLAYER);
        //select expansion
        BwapiTestUtils.waitForAndClickButton(scWnd, BroodwarButton.EXPANSION_PACK);
        //select default ID
        BwapiTestUtils.waitForAndClickButton(scWnd, BroodwarButton.ID_OK);
        //play custom
        BwapiTestUtils.waitForAndClickButton(scWnd, BroodwarButton.PLAY_CUSTOM);
        //select the map after waiting
        BwapiTestUtils.delay(1000);
        BwapiTestUtils.selectMap(scWnd, testInformation.getStarcraftFolder(), 
                testInformation.getMap());
        //game type
        BwapiTestUtils.selectGameType(scWnd, testInformation.getGameType());
        //players
        BwapiTestUtils.setupPlayers(scWnd, testInformation.getPlayers());
        //click OK
        BwapiTestUtils.relativeClick(scWnd, BroodwarButton.ID_OK.getX(),
                BroodwarButton.ID_OK.getY(), InputEvent.BUTTON1_MASK);
        //click start (wait just a sec...)
        BwapiTestUtils.delay(1000);
        BwapiTestUtils.relativeClick(scWnd, BroodwarButton.ID_OK.getX(),
                BroodwarButton.ID_OK.getY(), InputEvent.BUTTON1_MASK);
    }
    
    /**
     * Stop Starcraft
     * 
     * @param chaosProcess
     * @throws Exception
     */
    private static void stopStarcraft(AtomicReference<Process> chaosProcess) throws Exception {
        //grab SC and close
        //HWND scWnd = BwapiTestUtils.getStarcraftWindow();
        //if (scWnd != null) {
        //    BwapiTestUtils.relativeClick(scWnd, 322, 249, InputEvent.BUTTON1_MASK);
            //wait a little
        //    BwapiTestUtils.delay(500);
            //just kill the damned process
            BwapiTestUtils.killStarcraft();
        //}
        //kill chaos
        if (chaosProcess.get() != null) {
            chaosProcess.get().destroy();
        }
    }
    
    /**
     * Execute bot with the given information
     * 
     * @param testInformation
     * @throws Exception
     */
    protected void execute(BwapiTestInformation testInformation) throws Exception {
        final AtomicBoolean completed = new AtomicBoolean(false);
        final AtomicReference<Throwable> throwable = new AtomicReference<Throwable>(null);
        //start rmi server
        BwapiBridgeBotServerImpl.startServer(testInformation.getBridgeBotClass(),
                new BwapiBridgeBotServerCallback() {
                    @Override
                    public void onEnd() {
                        completed.set(true);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        throwable.set(error);
                    }
        }, testInformation.isConsideredOverWhenUnitsGone());
        AtomicReference<Process> chaosProcess = new AtomicReference<Process>(null);
        try {
            //start up starcraft
            startStarcraft(testInformation, chaosProcess);
            while (!completed.get() && throwable.get() == null) {
                //check every second
                BwapiTestUtils.delay(1000);
                //TODO deadlock or timeout?
            }
            if (throwable.get() != null) {
                throw new BridgeException("Failure during execution", throwable.get());
            }
        } finally {
            //stop
            try {
                stopStarcraft(chaosProcess);
            } catch (Exception e) {
                System.out.println("Error closing starcraft: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
