package f.b.o.g.player;

import f.b.o.g.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000t.*;
import java.util.function.*;
import f.b.a.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import java.io.*;
import f.b.q.c.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import java.util.*;

@c$b(name = "Chunk Logger", description = "Log chunks that contain a specified ammount of chest", category = f.b.o.c.c.PLAYER)
public class ChunkLogger extends c
{
    private File xn;
    
    public ChunkLogger() {
        super();
        this.xn = new File("Backdoored/ChunkLogs.txt");
    }
    
    public void bv() {
        this.a(false);
    }
    
    public void bh() {
        final List<TileEntity> v = (List<TileEntity>)ChunkLogger.mc.world.loadedTileEntityList;
        v.stream().filter((Predicate<? super Object>)\u0000f::a);
    }
    
    @SubscribeEvent
    public void a(final v v) {
        if (v.packet instanceof SPacketChunkData) {
            final SPacketChunkData sPacketChunkData = (SPacketChunkData)v.packet;
        }
    }
    
    @SubscribeEvent
    public void a(final ChunkEvent.Load v) {
        if (!this.bu()) {
            return;
        }
        for (final ChunkPos v2 : ChunkLogger.mc.world.getPersistentChunks().keys()) {
            final Chunk v3 = ChunkLogger.mc.world.getChunk(v2.x, v2.z);
            final Collection<TileEntity> v4 = v3.getTileEntityMap().values();
            System.out.println(v3.x * 16 + " " + v3.z * 16);
            System.out.println(v3.getTileEntityMap().size());
            System.out.println(v3.getTileEntityMap().values().size());
            if (v3.getTileEntityMap().size() < 1) {
                return;
            }
            int v5 = 0;
            System.out.println("tiles: " + v3.getTileEntityMap().size());
            for (final TileEntity v6 : v4) {
                System.out.println(v6 instanceof TileEntityChest);
                System.out.println(v6.getPos());
                if (v6 instanceof TileEntityChest) {
                    ++v5;
                }
            }
            int v7 = 0;
            for (final TileEntity v8 : v4) {
                if (v8 instanceof TileEntityBed) {
                    ++v7;
                }
            }
            boolean v9 = false;
            for (final TileEntity v10 : v4) {
                if (v10 instanceof TileEntityEndPortal) {
                    v9 = true;
                    break;
                }
            }
            System.out.printf("\nChunk Loaded %d %d %s", v5, v7, String.valueOf(v9));
            if (v5 <= 0 && v7 <= 0 && !v9) {
                continue;
            }
            final long v11 = System.currentTimeMillis();
            final Date v12 = new Date(v11);
            String v13 = "Singleplayer";
            if (ChunkLogger.mc.getCurrentServerData() != null) {
                v13 = ChunkLogger.mc.getCurrentServerData().serverIP;
            }
            final String v14 = String.format("(%s) %s %s: %d chests, %d beds, %d end portals", v12, v13, "(" + v3.x * 16 + ", " + v3.z * 16 + ")", v5, v7, v9 ? 1 : 0);
            try {
                final FileWriter v15 = new FileWriter(this.xn, true);
                final BufferedWriter v16 = new BufferedWriter(v15);
                v16.write(v14 + "\n");
                v16.close();
                v15.close();
                System.out.println("Found Chunk " + v14);
                o.bn("Found Chunk " + v14);
            }
            catch (Exception v17) {
                System.out.println(v14);
                v17.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ boolean a(final TileEntity v) {
        return ChunkLogger.mc.player.getDistance((double)v.getPos().getX(), ChunkLogger.mc.player.posY, (double)v.getPos().getZ()) < 500.0;
    }
}
