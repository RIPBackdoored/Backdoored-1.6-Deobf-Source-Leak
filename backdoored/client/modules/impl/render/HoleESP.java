package f.b.o.g.render;

import f.b.o.g.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraftforge.client.event.*;
import f.b.q.i.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Hole ESP", description = "See holes to camp in during pvp", category = f.b.o.c.c.RENDER)
public class HoleESP extends c
{
    private f.b.f.c ov;
    private f.b.f.c od;
    private static f.b.f.c oy;
    private static f.b.f.c oj;
    private ArrayList<BlockPos> ta;
    
    public HoleESP() {
        super();
        this.ov = new f.b.f.c("Hole Radius", this, 10, 1, 50);
        this.od = new f.b.f.c("Hole height 3", this, false);
        HoleESP.oy = new f.b.f.c("Whitelist", this, "Bedrock and Obby", new String[] { "All", "Only Bedrock", "Bedrock and Obby" });
        final f.b.f.c v = new f.b.f.c("Max Y", this, 125, 0, 125);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        this.ta = new ArrayList<BlockPos>();
        final Iterable<BlockPos> v = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleESP.mc.player.getPosition().add(-this.ov.cp(), -this.ov.cp(), -this.ov.cp()), HoleESP.mc.player.getPosition().add(this.ov.cp(), this.ov.cp(), this.ov.cp()));
        for (final BlockPos v2 : v) {
            if (a(v2, this.od.cq())) {
                this.ta.add(v2);
            }
        }
    }
    
    public static boolean b(final BlockPos v) {
        return a(v, false);
    }
    
    public static boolean a(final BlockPos v, final boolean v) {
        if (v.getY() > HoleESP.oj.cp()) {
            return false;
        }
        final IBlockState[] v2 = { HoleESP.mc.world.getBlockState(v), HoleESP.mc.world.getBlockState(v.add(0, 1, 0)), HoleESP.mc.world.getBlockState(v.add(0, 2, 0)), HoleESP.mc.world.getBlockState(v.add(0, -1, 0)), HoleESP.mc.world.getBlockState(v.add(1, 0, 0)), HoleESP.mc.world.getBlockState(v.add(0, 0, 1)), HoleESP.mc.world.getBlockState(v.add(-1, 0, 0)), HoleESP.mc.world.getBlockState(v.add(0, 0, -1)) };
        final boolean v3 = !v2[0].getMaterial().blocksMovement() && !v2[1].getMaterial().blocksMovement() && (!v2[2].getMaterial().blocksMovement() || !v) && v2[3].getMaterial().isSolid() && v2[4].getMaterial().isSolid() && v2[5].getMaterial().isSolid() && v2[6].getMaterial().isSolid() && v2[7].getMaterial().isSolid();
        if (HoleESP.oy.ci().equals("All")) {
            return v3;
        }
        final boolean v4 = v2[3].getBlock().equals(Blocks.BEDROCK) && v2[4].getBlock().equals(Blocks.BEDROCK) && v2[5].getBlock().equals(Blocks.BEDROCK) && v2[6].getBlock().equals(Blocks.BEDROCK) && v2[7].getBlock().equals(Blocks.BEDROCK);
        return v4 || (!HoleESP.oy.ci().equals("Only Bedrock") && (v2[3].getBlock().equals(Blocks.BEDROCK) || v2[3].getBlock().equals(Blocks.OBSIDIAN)) && (v2[4].getBlock().equals(Blocks.BEDROCK) || v2[4].getBlock().equals(Blocks.OBSIDIAN)) && (v2[5].getBlock().equals(Blocks.BEDROCK) || v2[5].getBlock().equals(Blocks.OBSIDIAN)) && (v2[6].getBlock().equals(Blocks.BEDROCK) || v2[6].getBlock().equals(Blocks.OBSIDIAN)) && (v2[7].getBlock().equals(Blocks.BEDROCK) || v2[7].getBlock().equals(Blocks.OBSIDIAN)));
    }
    
    @SubscribeEvent
    public void a(final RenderWorldLastEvent v) {
        if (!this.bu()) {
            return;
        }
        j.a(255.0f, 0.0f, 255.0f, 1.0f);
        if (this.ta != null) {
            for (final BlockPos v2 : this.ta) {
                final AxisAlignedBB v3 = j.g(v2);
                j.g(v3);
            }
        }
        j.nv();
    }
}
