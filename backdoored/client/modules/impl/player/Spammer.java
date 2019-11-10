package f.b.o.g.player;

import f.b.o.g.*;
import java.io.*;
import javax.swing.*;
import java.nio.file.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.event.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;

@c$b(name = "Spammer", description = "Spam the chat", category = f.b.o.c.c.PLAYER)
public class Spammer extends c
{
    private static final char[] zl;
    private static final File zi;
    private String zf;
    private f.b.f.c zq;
    private f.b.f.c zk;
    private Timer zp;
    private Timer zm;
    private Timer zs;
    
    public Spammer() {
        super();
        this.zq = new f.b.f.c("Mode", this, "File", new String[] { "File", "Spam" });
        this.zk = new f.b.f.c("Delay", this, 2, 1, 60);
        try {
            Spammer.zi.createNewFile();
        }
        catch (Exception v) {
            v.printStackTrace();
        }
    }
    
    public void bv() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        f/b/o/g/player/Spammer.zk:Lf/b/f/c;
        //     4: ifnonnull       25
        //     7: aload_0         /* v */
        //     8: new             Lf/b/f/c;
        //    11: dup            
        //    12: ldc             "Delay"
        //    14: aload_0         /* v */
        //    15: iconst_2       
        //    16: iconst_1       
        //    17: bipush          60
        //    19: invokespecial   f/b/f/c.<init>:(Ljava/lang/String;Lf/b/o/g/c;III)V
        //    22: putfield        f/b/o/g/player/Spammer.zk:Lf/b/f/c;
        //    25: aload_0         /* v */
        //    26: getfield        f/b/o/g/player/Spammer.zq:Lf/b/f/c;
        //    29: invokevirtual   f/b/f/c.ci:()Ljava/lang/String;
        //    32: astore_1       
        //    33: iconst_m1      
        //    34: istore_2       
        //    35: aload_1        
        //    36: invokevirtual   java/lang/String.hashCode:()I
        //    39: lookupswitch {
        //          2189724: 72
        //          2583401: 86
        //          67081517: 100
        //          default: 111
        //        }
        //    72: aload_1        
        //    73: ldc             "File"
        //    75: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    78: ifeq            111
        //    81: iconst_0       
        //    82: istore_2       
        //    83: goto            111
        //    86: aload_1        
        //    87: ldc             "Spam"
        //    89: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    92: ifeq            111
        //    95: iconst_1       
        //    96: istore_2       
        //    97: goto            111
        //   100: aload_1        
        //   101: ldc             "Empty"
        //   103: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   106: ifeq            111
        //   109: iconst_2       
        //   110: istore_2       
        //   111: iload_2        
        //   112: tableswitch {
        //                0: 140
        //                1: 230
        //                2: 268
        //          default: 268
        //        }
        //   140: new             Ljava/lang/String;
        //   143: dup            
        //   144: getstatic       f/b/o/g/player/Spammer.zi:Ljava/io/File;
        //   147: invokevirtual   java/io/File.getCanonicalPath:()Ljava/lang/String;
        //   150: iconst_0       
        //   151: anewarray       Ljava/lang/String;
        //   154: invokestatic    java/nio/file/Paths.get:(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;
        //   157: invokestatic    java/nio/file/Files.readAllBytes:(Ljava/nio/file/Path;)[B
        //   160: invokespecial   java/lang/String.<init>:([B)V
        //   163: ldc             "\n"
        //   165: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   168: astore_3        /* v */
        //   169: aload_3         /* v */
        //   170: arraylength    
        //   171: istore          v
        //   173: iload           v
        //   175: ifeq            187
        //   178: aload_3         /* v */
        //   179: iconst_0       
        //   180: aaload         
        //   181: invokevirtual   java/lang/String.isEmpty:()Z
        //   184: ifeq            187
        //   187: aload_0         /* v */
        //   188: new             Ljavax/swing/Timer;
        //   191: dup            
        //   192: aload_0         /* v */
        //   193: getfield        f/b/o/g/player/Spammer.zk:Lf/b/f/c;
        //   196: invokevirtual   f/b/f/c.cp:()I
        //   199: sipush          1000
        //   202: imul           
        //   203: new             Lf/b/o/g/t/v$z;
        //   206: dup            
        //   207: aload_0         /* v */
        //   208: iload           v
        //   210: aload_3         /* v */
        //   211: invokespecial   f/b/o/g/t/v$z.<init>:(Lf/b/o/g/t/v;I[Ljava/lang/String;)V
        //   214: invokespecial   javax/swing/Timer.<init>:(ILjava/awt/event/ActionListener;)V
        //   217: putfield        f/b/o/g/player/Spammer.zp:Ljavax/swing/Timer;
        //   220: aload_0         /* v */
        //   221: getfield        f/b/o/g/player/Spammer.zp:Ljavax/swing/Timer;
        //   224: invokevirtual   javax/swing/Timer.start:()V
        //   227: goto            295
        //   230: aload_0         /* v */
        //   231: new             Ljavax/swing/Timer;
        //   234: dup            
        //   235: aload_0         /* v */
        //   236: getfield        f/b/o/g/player/Spammer.zk:Lf/b/f/c;
        //   239: invokevirtual   f/b/f/c.cp:()I
        //   242: sipush          1000
        //   245: imul           
        //   246: aload_0         /* v */
        //   247: invokedynamic   BootstrapMethod #0, actionPerformed:(Lf/b/o/g/t/v;)Ljava/awt/event/ActionListener;
        //   252: invokespecial   javax/swing/Timer.<init>:(ILjava/awt/event/ActionListener;)V
        //   255: putfield        f/b/o/g/player/Spammer.zm:Ljavax/swing/Timer;
        //   258: aload_0         /* v */
        //   259: getfield        f/b/o/g/player/Spammer.zm:Ljavax/swing/Timer;
        //   262: invokevirtual   javax/swing/Timer.start:()V
        //   265: goto            295
        //   268: aload_0         /* v */
        //   269: new             Ljavax/swing/Timer;
        //   272: dup            
        //   273: aload_0         /* v */
        //   274: getfield        f/b/o/g/player/Spammer.zk:Lf/b/f/c;
        //   277: invokevirtual   f/b/f/c.cp:()I
        //   280: sipush          1000
        //   283: imul           
        //   284: invokedynamic   BootstrapMethod #1, actionPerformed:()Ljava/awt/event/ActionListener;
        //   289: invokespecial   javax/swing/Timer.<init>:(ILjava/awt/event/ActionListener;)V
        //   292: putfield        f/b/o/g/player/Spammer.zs:Ljavax/swing/Timer;
        //   295: goto            335
        //   298: astore_1        /* v */
        //   299: new             Ljava/lang/StringBuilder;
        //   302: dup            
        //   303: invokespecial   java/lang/StringBuilder.<init>:()V
        //   306: ldc             "Disabled spammer due to error: "
        //   308: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   311: aload_1         /* v */
        //   312: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //   315: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   318: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   321: ldc             "red"
        //   323: invokestatic    f/b/q/c/o.i:(Ljava/lang/String;Ljava/lang/String;)V
        //   326: aload_1         /* v */
        //   327: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   330: aload_0         /* v */
        //   331: iconst_0       
        //   332: invokevirtual   f/b/o/g/player/Spammer.a:(Z)V
        //   335: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  25     295    298    335    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void bh() {
        if (this.bu()) {
            if (this.zf == null) {
                this.zf = this.zq.ci();
                return;
            }
            if (!this.zf.equals(this.zq.ci())) {
                this.bd();
                this.bv();
            }
            this.zf = this.zq.ci();
        }
    }
    
    public void bd() {
        for (final Timer v : new Timer[] { this.zp, this.zm, this.zs }) {
            try {
                v.stop();
            }
            catch (Exception ex) {}
        }
    }
    
    private String gd() {
        final StringBuilder v = new StringBuilder();
        for (int v2 = 0; v2 < 256; ++v2) {
            char v3;
            for (v3 = ' '; v3 == ' ' || !ChatAllowedCharacters.isAllowedCharacter(v3); v3 = Spammer.zl[new Random().nextInt(Spammer.zl.length)]) {}
            v.append(v3);
        }
        return v.toString();
    }
    
    private static /* synthetic */ void b(final ActionEvent v) {
        Spammer.mc.player.sendChatMessage(StringUtils.repeat(Spammer.zl[195], 256));
    }
    
    private /* synthetic */ void g(final ActionEvent v) {
        Spammer.mc.player.sendChatMessage(this.gd());
    }
    
    static /* synthetic */ Minecraft gy() {
        return Spammer.mc;
    }
    
    static {
        zl = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u00e2\u0152\u201a\u00c3\u2021\u00c3¼\u00c3©\u00c3¢\u00c3¤\u00c3 \u00c3¥\u00c3§\u00c3ª\u00c3«\u00c3¨\u00c3¯\u00c3®\u00c3¬\u00c3\u201e\u00c3\u2026\u00c3\u2030\u00c3¦\u00c3\u2020\u00c3´\u00c3¶\u00c3²\u00c3»\u00c3¹\u00c3¿\u00c3\u2013\u00c3\u0153\u00c3¸\u00c2£\u00c3\u02dc\u00c3\u2014\u00c6\u2019\u00c3¡\u00c3\u00ad\u00c3³\u00c3º\u00c3±\u00c3\u2018\u00c2ª\u00c2º".toCharArray();
        zi = new File("Backdoored/spammer.txt");
    }
}
