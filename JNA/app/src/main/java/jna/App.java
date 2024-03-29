/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.ptr.IntByReference;

public class App {
    public interface V01dLib extends Library {
        void say_hi();

        int get_sum(int a, int b);

        int get_array_sum(long size, int[] array);
    }

    public interface Kernel32I extends Library {
        // FREQUENCY is expressed in hertz and ranges from 37 to 32767
        // DURATION is expressed in milliseconds
        boolean Beep(int FREQUENCY, int DURATION);

        void Sleep(int DURATION);
    }

    public static void main(String[] args) {
        // Custom library
        V01dLib lib = Native.load("v01dlib", V01dLib.class);
        lib.say_hi();
        System.out.println(lib.get_sum(1, 3));
        System.out.println(lib.get_array_sum(4, new int[] { 1, 2, 3, 1 }));

        // Get computer name
        char[] cn = new char[256];
        boolean success = Kernel32.INSTANCE.GetComputerName(cn, new IntByReference(256));

        if (success) {
            for (char c : cn) {
                if ((int) c != 0) {
                    System.out.print(c);
                }
            }

            System.out.print('\n');
        }

        // Beep
        Kernel32I lib2 = (Kernel32I) Native.load("kernel32", Kernel32I.class);
        lib2.Beep(698, 500);
        lib2.Sleep(500);
        lib2.Beep(698, 500);

        // Get platform type
        boolean isWin = Platform.isWindows();
        boolean isLin = Platform.isLinux();
        boolean isMac = Platform.isMac();

        System.out.println("Is windows : " + isWin);
        System.out.println("Is linux : " + isLin);
        System.out.println("Is mac : " + isMac);
    }
}
