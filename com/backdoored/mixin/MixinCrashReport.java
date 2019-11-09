package com.backdoored.mixin;

import net.minecraft.crash.*;
import org.spongepowered.asm.mixin.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.*;
import javax.swing.border.*;
import \u0000f.\u0000b.\u0000g.*;
import com.google.common.base.*;
import java.awt.*;
import javax.swing.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ CrashReport.class })
public class MixinCrashReport
{
    @Final
    @Shadow
    private Throwable field_71511_b;
    
    public MixinCrashReport() {
        super();
    }
    
    @Redirect(method = { "getCompleteReport" }, at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    public String interceptReport(final StringBuilder stringBuilder) {
        try {
            return \u0000m.a(stringBuilder);
        }
        catch (Throwable t) {
            return stringBuilder.toString();
        }
    }
    
    @Inject(method = { "saveToFile" }, at = { @At("RETURN") })
    private void showDialog(final File toFile, final CallbackInfoReturnable<Boolean> cir) {
        if (\u0000p.cf.func_71372_G()) {
            \u0000p.cf.func_71352_k();
        }
        final Frame frame = new Frame();
        frame.setAlwaysOnTop(true);
        frame.setState(1);
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        final String fullReport = ((CrashReport)this).func_71502_e();
        String hasteBinUrl;
        try {
            hasteBinUrl = \u0000x.a("https://paste.dimdev.org", "mccrash", fullReport);
        }
        catch (Exception e) {
            hasteBinUrl = e.getMessage();
        }
        final JTextArea comp = new JTextArea("Uploaded crash report: " + hasteBinUrl + "\n" + Throwables.getStackTraceAsString(this.field_71511_b));
        comp.setEditable(false);
        final JScrollPane scroll = new JScrollPane(comp, 22, 32);
        panel.add(scroll);
        StackTraceElement trace;
        if (this.field_71511_b.getStackTrace().length > 0) {
            trace = this.field_71511_b.getStackTrace()[0];
        }
        else {
            trace = new StackTraceElement("", "", "", -1);
        }
        JOptionPane.showMessageDialog(frame, panel, "ERROR encountered at " + trace.toString(), 0);
        frame.requestFocus();
    }
}
