package io.appium.android.adb;

import io.appium.android.exceptions.AdbException;
import io.appium.android.screenshooter.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

public class AdbHelper {
  private AndroidDebugBridge adb = null;

  public AdbHelper() {
    AndroidDebugBridge.init(false);

    adb = AndroidDebugBridge
        .createBridge(getAdbLocation(), true /* forceNewBridge */);
    waitForDeviceList();
    Logger.info("Done waiting for device list...");

    if (adb.isConnected() == false) {
      adb.restart();
      waitForDeviceList();
    }
  }

  public void close() {
    AndroidDebugBridge.terminate();
  }

  public String getAdbLocation() {
    String adbLocation = System.getProperty("com.android.uiautomator.bindir"); //$NON-NLS-1$
    if (adbLocation != null && adbLocation.length() != 0) {
      adbLocation += File.separator + "adb"; //$NON-NLS-1$
      Logger.info("ADB location: " + adbLocation);
    } else {
      final String value = System.getenv("ANDROID_HOME");
      if (value != null) {
        adbLocation = new File(value, "platform-tools/adb").getAbsolutePath(); //$NON-NLS-1$
        Logger.info("ADB location: " + adbLocation);
      } else {
        adbLocation = "adb";
      }
    }
    return adbLocation;
  }

  public Device getDevice(final String serial) throws AdbException {
    for (final IDevice d : getDevices()) {
      if (serial.equalsIgnoreCase(d.getSerialNumber())) {
        return new Device(d);
      }
    }
    return null;
  }

  public List<IDevice> getDevices() {
    return Arrays.asList(adb.getDevices());
  }

  private void waitForDeviceList() {
    int count = 0;
    while (adb.hasInitialDeviceList() == false) {
      try {
        Thread.sleep(100);
        count++;
      } catch (final InterruptedException e) {
        // pass
      }

      // let's not wait > 10 sec.
      if (count > 100) {
        System.err.println("Timeout getting device list!");
        return;
      }
    }
  }
}
