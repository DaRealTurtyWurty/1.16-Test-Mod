package com.turtywurty.examplemod.common.tiles;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.core.init.TileEntityInit;
import com.turtywurty.examplemod.core.util.threading.NotifyingThread;
import com.turtywurty.examplemod.core.util.threading.ThreadCompleteListener;

import de.sfuhrm.radiobrowser4j.FieldName;
import de.sfuhrm.radiobrowser4j.ListParameter;
import de.sfuhrm.radiobrowser4j.RadioBrowser;
import de.sfuhrm.radiobrowser4j.SearchMode;
import de.sfuhrm.radiobrowser4j.Station;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class RadioTileEntity extends TileEntity implements ITickableTileEntity, ThreadCompleteListener {

	public final Set<Station> radioStations = new HashSet<>();
	public final Set<String> countries = new HashSet<>(), languages = new HashSet<>();

	public final Map<String, Station> countryStationMap = new HashMap<>(), languageStationMap = new HashMap<>();
	private final RadioBrowser radioBrowser = new RadioBrowser(5000, "Radio Mod Agent/1.0");

	public String currentCountry, currentLanguage;

	public RadioTileEntity() {
		super(TileEntityInit.RADIO.get());
	}

	@Override
	public void tick() {

	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (this.world.isRemote()) {
			this.grabCountriesAndLangs();
			this.grabStations();
		}
	}

	public void grabStations() {
		NotifyingThread stationGrabber = new NotifyingThread("StationGrabber") {
			@Override
			public void doRun() {
				radioBrowser.listStations(ListParameter.create().order(FieldName.NAME)).forEach(radioStations::add);
			}
		};

		stationGrabber.addListener(this);
		stationGrabber.start();
	}

	@Override
	public void notifyOfThreadComplete(Thread thread) {
		if (thread.getName().equalsIgnoreCase("StationGrabber")) {
			thread.interrupt();
			ExampleMod.LOGGER.info(this.radioStations);
		}
	}

	public void grabCountriesAndLangs() {
		Thread countryGrabber = new Thread(() -> {
			radioBrowser.listCountries().entrySet().stream().sorted(Comparator.comparingInt(Entry::getValue))
					.forEach(entry -> countries.add(entry.getKey()));
			radioBrowser.listLanguages().entrySet().stream().sorted(Comparator.comparingInt(Entry::getValue))
					.forEach(entry -> languages.add(entry.getKey()));
		}, "LangCountryGrabber");

		countryGrabber.start();
	}

	public void grabCountryStations(String country) {
		radioBrowser.listStationsBy(SearchMode.BYCOUNTRY, country, ListParameter.create().order(FieldName.NAME))
				.forEach(station -> countryStationMap.put(country, station));
	}

	public void grabLanguageStations(String language) {
		radioBrowser.listStationsBy(SearchMode.BYLANGUAGE, language, ListParameter.create().order(FieldName.NAME))
				.forEach(station -> languageStationMap.put(language, station));
	}
}
