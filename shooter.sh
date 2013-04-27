DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

JAR_CLASSPATH="$ANDROID_HOME/tools/lib/ddmlib.jar"
JAR_CLASSPATH="$JAR_CLASSPATH:$ANDROID_HOME/tools/lib/x86_64/swt.jar"
JAR_CLASSPATH="$JAR_CLASSPATH:$DIR/target/ScreenShooter-1.0.0-SNAPSHOT.jar"
export JAR_CLASSPATH

echo $JAR_CLASSPATH

java -classpath $JAR_CLASSPATH io.appium.android.screenshooter.ScreenShooter $1 $2
