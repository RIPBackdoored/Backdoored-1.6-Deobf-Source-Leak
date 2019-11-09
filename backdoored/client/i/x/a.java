package f.b.i.x;

import f.b.o.g.*;
import f.b.f.*;
import f.b.*;
import org.lwjgl.input.*;
import f.b.q.*;
import java.util.*;
import f.b.i.c.*;

public class a extends j
{
    public a() {
        super();
    }
    
    @Override
    public void bm() {
        for (final c v : u.so) {
            o v2 = null;
            for (final f.b.f.c v3 : Objects.requireNonNull(b.a(v))) {
                final o v4 = v3.dp;
                final o v5 = v.lm;
                if (v3.cw()) {
                    if (Keyboard.getEventKeyState() && !v4.cp && Keyboard.getKeyName(Keyboard.getEventKey()).equals(v3.ci()) && !v3.dk && p.mc.inGameHasFocus) {
                        if (!Keyboard.getKeyName(Keyboard.getEventKey()).equals("NONE")) {
                            for (final c v6 : u.so) {
                                if (v5.co.equals(v6.lm.co)) {
                                    v6.a(!v6.bu());
                                }
                            }
                            v3.dk = true;
                        }
                    }
                    else if (!Keyboard.getEventKeyState()) {
                        v3.dk = false;
                    }
                    if (v4.cp) {
                        v4.co = "Bind: ...";
                        if (Keyboard.getEventKeyState()) {
                            v3.g(Keyboard.getKeyName(Keyboard.getEventKey()));
                            v4.co = "Bind: " + v3.ci();
                            v4.cp = false;
                            v5.cp = false;
                            v3.dk = true;
                        }
                        if (v4.cm) {
                            v3.g("NONE");
                            v4.co = "Bind: " + v3.ci();
                            v4.cp = false;
                        }
                    }
                    else {
                        v4.co = "Bind: " + v3.ci();
                    }
                    if (v4.cm) {
                        v4.cm = false;
                    }
                }
                if (v3.ce()) {
                    if (v4.cp) {
                        int v7 = 0;
                        while (v7 < v3.cf().length) {
                            if (v3.cf()[v7].equals(v3.ci())) {
                                if (v7 == 0) {
                                    v3.g(v3.cf()[v3.cf().length - 1]);
                                    break;
                                }
                                v3.g(v3.cf()[v7 - 1]);
                                break;
                            }
                            else {
                                ++v7;
                            }
                        }
                        v4.cp = false;
                    }
                    v4.co = v3.rh() + ": " + v3.ci();
                }
                if (v3.cx()) {
                    if (v4.cp) {
                        v3.g(!v3.cq());
                        v4.cp = false;
                    }
                    v4.co = v3.rh() + ": " + v3.ci();
                }
                if (v3.cz()) {
                    if (v4.cp) {
                        v3.dk = true;
                        v4.cp = false;
                    }
                    if (!Mouse.isButtonDown(0)) {
                        v3.dk = false;
                    }
                    if (v3.dk && f.b.i.b.nn >= v4.cs && f.b.i.b.nn <= v4.cs + v4.cx) {
                        v3.g(h.a((f.b.i.b.nn - v4.cs) / (double)v4.cx * v3.cs() - v3.cm() + v3.cm(), 2));
                    }
                    v4.co = v3.rh() + ": " + v3.ci();
                }
                if (v3.co()) {
                    if (v4.cp) {
                        v3.dk = true;
                        v4.cp = false;
                    }
                    if (!Mouse.isButtonDown(0)) {
                        v3.dk = false;
                    }
                    if (v3.dk && f.b.i.b.nn >= v4.cs && f.b.i.b.nn <= v4.cs + v4.cx) {
                        v3.g((int)h.a((f.b.i.b.nn - v4.cs) / (double)v4.cx * v3.cs() - v3.cm() + v3.cm(), 0));
                    }
                    v4.co = v3.rh() + ": " + v3.ci();
                }
                v4.cs = v5.cs + v5.cx;
                if (v2 != null) {
                    v4.ce = v2.ce + v2.cz;
                }
                else {
                    v4.ce = v5.ce;
                }
                v2 = v4;
            }
        }
    }
}
