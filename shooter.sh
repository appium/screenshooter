DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

JAR_CLASSPATH="$ANDROID_HOME/tools/lib/ddmlib.jar"
JAR_CLASSPATH="$JAR_CLASSPATH:$ANDROID_HOME/tools/lib/x86_64/swt.jar"
JAR_CLASSPATH="$JAR_CLASSPATH:$DIR/bin/ScreenShooter.jar"
export JAR_CLASSPATH

java -classpath $JAR_CLASSPATH io.appium.android.screenshooter.ScreenShooter
