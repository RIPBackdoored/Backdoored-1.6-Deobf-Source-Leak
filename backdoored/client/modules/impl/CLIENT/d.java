package f.b.o.g.a;

import f.b.o.g.*;
import java.util.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;
import net.minecraft.client.renderer.*;

@c$b(name = "Chunk Anim", description = "Animate chunk loading", category = f.b.o.c.c.CLIENT)
public class d extends c
{
    private f.b.f.c fi;
    private WeakHashMap<RenderChunk, d$z> ff;
    
    public d() {
        super();
        this.fi = new f.b.f.c("Duration", this, 2, 0, 10);
        this.ff = new WeakHashMap<RenderChunk, d$z>();
    }
    
    @SubscribeEvent
    public void a(final x$c v) {
        if (!this.bu()) {
            return;
        }
        if (d.mc.player != null) {
            BlockPos v2 = d.mc.player.getPosition();
            BlockPos v3 = new BlockPos(v.bd, v.by, v.bj);
            v2 = v2.add(0, -v2.getY(), 0);
            v3 = v3.add(8, -v3.getY(), 8);
            EnumFacing v4 = null;
            final Vec3i v5 = (Vec3i)v2.subtract((Vec3i)v3);
            v4 = this.a(v5, Math.abs(v5.getX()), Math.abs(v5.getZ()));
            final d$z v6 = new d$z(-1L, v4);
            this.ff.put(v.renderChunk, v6);
        }
    }
    
    @SubscribeEvent
    public void a(final x$o v) {
        if (!this.bu()) {
            return;
        }
        if (this.ff.containsKey(v.renderChunk)) {
            final d$z v2 = this.ff.get(v.renderChunk);
            long v3 = v2.fc;
            if (v3 == -1L) {
                v3 = System.currentTimeMillis();
                v2.fc = v3;
                BlockPos v4 = d.mc.player.getPosition();
                BlockPos v5 = v.renderChunk.getPosition();
                v4 = v4.add(0, -v4.getY(), 0);
                v5 = v5.add(8, -v5.getY(), 8);
                final Vec3i v6 = (Vec3i)v4.subtract((Vec3i)v5);
                final int v7 = Math.abs(v6.getX());
                final int v8 = Math.abs(v6.getZ());
                v2.enumFacing = this.a(v6, v7, v8);
            }
            final long v9 = System.currentTimeMillis() - v3;
            final int v10 = this.fi.cp();
            if (v9 < v10) {
                if (v2.enumFacing != null) {
                    final Vec3i v11 = v2.enumFacing.getDirectionVec();
                    final double v12 = -(200.0 - 200.0 / v10 * v9);
                    GlStateManager.translate(v11.getX() * v12, 0.0, v11.getZ() * v12);
                }
            }
            else {
                this.ff.remove(v.renderChunk);
            }
        }
    }
    
    private EnumFacing a(final Vec3i v, final int v, final int v) {
        if (v > v) {
            if (v.getX() > 0) {
                return EnumFacing.EAST;
            }
            return EnumFacing.WEST;
        }
        else {
            if (v.getZ() > 0) {
                return EnumFacing.SOUTH;
            }
            return EnumFacing.NORTH;
        }
    }
}
