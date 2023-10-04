package org.example;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public class Test {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.load("user32", User32.class);
        int GetWindowThreadProcessId(WinDef.HWND hWnd, PointerByReference pref);
        WinDef.HWND GetForegroundWindow();
        int GetWindowTextW(WinDef.HWND hWnd, char[] lpString, int nMaxCount);
    }

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32) Native.load("kernel32", Kernel32.class);
        WinNT.HANDLE OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    public static void main(String[] args) {
        while (true) {
            char[] buffer = new char[1024 * 2];
            WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
            PointerByReference pointer = new PointerByReference();
            User32.INSTANCE.GetWindowThreadProcessId(hwnd, pointer);
            Pointer process = Kernel32.INSTANCE.OpenProcess(0x0010, false, pointer.getValue()).getPointer();



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
