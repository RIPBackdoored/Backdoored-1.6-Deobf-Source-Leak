package f.b.q.x;

import java.util.*;
import java.io.*;
import net.minecraft.entity.player.*;
import f.b.*;

public class c
{
    private static final String yx;
    private static ArrayList<String> yz;
    
    public c() {
        super();
    }
    
    public static boolean nn() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnonnull       16
        //     6: new             Lf/b/q/x/c$b;
        //     9: dup            
        //    10: invokespecial   f/b/q/x/c$b.<init>:()V
        //    13: putstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //    16: new             Ljava/io/PrintWriter;
        //    19: dup            
        //    20: getstatic       f/b/q/x/c.yx:Ljava/lang/String;
        //    23: invokespecial   java/io/PrintWriter.<init>:(Ljava/lang/String;)V
        //    26: astore_0        /* v */
        //    27: aload_0         /* v */
        //    28: ldc             ""
        //    30: invokevirtual   java/io/PrintWriter.print:(Ljava/lang/String;)V
        //    33: aload_0         /* v */
        //    34: invokevirtual   java/io/PrintWriter.close:()V
        //    37: new             Ljava/io/BufferedWriter;
        //    40: dup            
        //    41: new             Ljava/io/FileWriter;
        //    44: dup            
        //    45: getstatic       f/b/q/x/c.yx:Ljava/lang/String;
        //    48: invokespecial   java/io/FileWriter.<init>:(Ljava/lang/String;)V
        //    51: invokespecial   java/io/BufferedWriter.<init>:(Ljava/io/Writer;)V
        //    54: astore_1        /* v */
        //    55: getstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //    58: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    61: astore_2       
        //    62: aload_2        
        //    63: invokeinterface java/util/Iterator.hasNext:()Z
        //    68: ifeq            93
        //    71: aload_2        
        //    72: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    77: checkcast       Ljava/lang/String;
        //    80: astore_3        /* v */
        //    81: aload_1         /* v */
        //    82: aload_3         /* v */
        //    83: invokevirtual   java/io/BufferedWriter.write:(Ljava/lang/String;)V
        //    86: aload_1         /* v */
        //    87: invokevirtual   java/io/BufferedWriter.newLine:()V
        //    90: goto            62
        //    93: aload_1         /* v */
        //    94: invokevirtual   java/io/BufferedWriter.flush:()V
        //    97: aload_1         /* v */
        //    98: invokevirtual   java/io/BufferedWriter.close:()V
        //   101: iconst_1       
        //   102: ireturn        
        //   103: astore_0        /* v */
        //   104: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   107: new             Ljava/lang/StringBuilder;
        //   110: dup            
        //   111: invokespecial   java/lang/StringBuilder.<init>:()V
        //   114: ldc             "Could not write friends.txt: "
        //   116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   119: aload_0         /* v */
        //   120: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //   123: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   126: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   129: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   132: aload_0         /* v */
        //   133: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   136: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   139: getstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //   142: invokevirtual   java/io/PrintStream.println:(Ljava/lang/Object;)V
        //   145: iconst_0       
        //   146: ireturn        
        //    StackMapTable: 00 04 10 FE 00 2D 07 00 1F 07 00 2D 07 00 3B FA 00 1E FF 00 09 00 00 00 01 07 00 18
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  16     102    103    147    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean nl() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: putstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //    10: new             Ljava/io/BufferedReader;
        //    13: dup            
        //    14: new             Ljava/io/FileReader;
        //    17: dup            
        //    18: getstatic       f/b/q/x/c.yx:Ljava/lang/String;
        //    21: invokespecial   java/io/FileReader.<init>:(Ljava/lang/String;)V
        //    24: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //    27: astore_0        /* v */
        //    28: aload_0         /* v */
        //    29: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    32: dup            
        //    33: astore_1        /* v */
        //    34: ifnull          48
        //    37: getstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //    40: aload_1         /* v */
        //    41: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    44: pop            
        //    45: goto            28
        //    48: aload_0         /* v */
        //    49: invokevirtual   java/io/BufferedReader.close:()V
        //    52: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    55: new             Ljava/lang/StringBuilder;
        //    58: dup            
        //    59: invokespecial   java/lang/StringBuilder.<init>:()V
        //    62: ldc             "Successfully read friends: "
        //    64: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    67: getstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //    70: invokevirtual   java/util/ArrayList.toString:()Ljava/lang/String;
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    79: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    82: iconst_1       
        //    83: ireturn        
        //    84: astore_0        /* v */
        //    85: new             Ljava/io/File;
        //    88: dup            
        //    89: getstatic       f/b/q/x/c.yx:Ljava/lang/String;
        //    92: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    95: astore_1        /* v */
        //    96: aload_1         /* v */
        //    97: invokevirtual   java/io/File.createNewFile:()Z
        //   100: pop            
        //   101: new             Lf/b/q/x/c$m;
        //   104: dup            
        //   105: invokespecial   f/b/q/x/c$m.<init>:()V
        //   108: putstatic       f/b/q/x/c.yz:Ljava/util/ArrayList;
        //   111: iconst_1       
        //   112: ireturn        
        //   113: astore_0        /* v */
        //   114: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   117: new             Ljava/lang/StringBuilder;
        //   120: dup            
        //   121: invokespecial   java/lang/StringBuilder.<init>:()V
        //   124: ldc             "Could not read friends: "
        //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: aload_0         /* v */
        //   130: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   139: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   142: aload_0         /* v */
        //   143: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   146: iconst_0       
        //   147: ireturn        
        //    StackMapTable: 00 04 FC 00 1C 07 00 76 FC 00 13 07 00 44 FF 00 23 00 00 00 01 07 00 73 5C 07 00 18
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                           
        //  -----  -----  -----  -----  -------------------------------
        //  0      83     84     113    Ljava/io/FileNotFoundException;
        //  0      83     113    148    Ljava/lang/Exception;
        //  84     112    113    148    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static ArrayList<String> ni() {
        return c.yz;
    }
    
    public static void bq(final String v) {
        if (c.yz == null) {
            return;
        }
        c.yz.add(v);
    }
    
    public static void bk(final String v) {
        if (c.yz == null) {
            return;
        }
        c.yz.remove(v);
    }
    
    public static boolean bp(final String v) {
        return c.yz != null && c.yz.contains(v);
    }
    
    public static boolean g(final EntityPlayer v) {
        return c.yz != null && (p.mc.player.getUniqueID().equals(v.getUniqueID()) || bp(v.getName()));
    }
    
    static {
        yx = f.b.t.c.n(42);
    }
}
