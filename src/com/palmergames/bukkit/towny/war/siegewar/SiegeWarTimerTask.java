package com.palmergames.bukkit.towny.war.siegewar;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.*;
import com.palmergames.bukkit.towny.tasks.TownyTimerTask;
import com.palmergames.bukkit.towny.utils.SiegeWarUtil;

import java.util.ArrayList;

import static com.palmergames.bukkit.towny.utils.SiegeWarUtil.ONE_HOUR_IN_MILLIS;
import static com.palmergames.bukkit.towny.utils.SiegeWarUtil.ONE_MINUTE_IN_MILLIS;

public class SiegeWarTimerTask extends TownyTimerTask {

	private static boolean timeForUpkeep;
	private static long nextTimeForUpkeep;
	private static boolean timeToSaveSiegeToDB;
	private static long nextTimeToSavePointsToDB;

	static
	{
		timeToSaveSiegeToDB = false;
		nextTimeToSavePointsToDB = System.currentTimeMillis() + ONE_MINUTE_IN_MILLIS;
		timeForUpkeep = false;
		nextTimeForUpkeep = System.currentTimeMillis() + ONE_HOUR_IN_MILLIS;
	}

	public SiegeWarTimerTask(Towny plugin) {
		super(plugin);
	}

	@Override
	public void run() {
		if (TownySettings.getWarSiegeEnabled()) {

			evaluateSiegeZones();

			evaluateSieges();

			if (System.currentTimeMillis() > nextTimeForUpkeep) {
				nextTimeForUpkeep = System.currentTimeMillis() + ONE_HOUR_IN_MILLIS;
				doSiegeUpkeep();
			}

			if (System.currentTimeMillis() > nextTimeToSavePointsToDB) {
				nextTimeToSavePointsToDB = System.currentTimeMillis() + ONE_MINUTE_IN_MILLIS;
				saveActiveSiegeZonesToDB();
			}
		}
	}

	private void doSiegeUpkeep() {
		//Cycle through all sieges
		TownyMessaging.sendMsg("Now evaluating siege upkeep");

		for (Siege siege : new ArrayList<>(SiegeWarUtil.getAllSieges())) {
			if (TownySettings.isUsingEconomy())
				SiegeWarUtil.applySiegeUpkeepCost(siege);
		}
	}


	private void saveActiveSiegeZonesToDB() {
		for(SiegeZone siegeZone: TownyUniverse.getDataSource().getSiegeZones()) {
			if(siegeZone.isActive()) {
				TownyUniverse.getDataSource().saveSiegeZone(siegeZone);
			}
		}
	}

	//Cycle through all siege zones
	private void evaluateSiegeZones() {
		for(SiegeZone siegeZone: TownyUniverse.getDataSource().getSiegeZones()) {
				SiegeWarUtil.evaluateSiegeZone(siegeZone);
		}
	}

	//Cycle through all sieges
	private void evaluateSieges() {
		for(Siege siege: SiegeWarUtil.getAllSieges()) {
			SiegeWarUtil.evaluateSiege(siege);
		}
	}
}