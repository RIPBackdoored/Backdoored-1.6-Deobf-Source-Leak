package f.b.c;

import f.b.q.c.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.q.i.*;
import net.minecraft.util.math.*;
import net.minecraftforge.event.entity.player.*;
import f.b.q.*;
import java.util.*;

public class b extends x
{
    private static int p;
    private static final int m = 0;
    private static final int s = 1;
    private static final int e = 2;
    private static final int x = 3;
    private static Map<String, ArrayList<Vec3d>> z;
    private static ArrayList<Vec3d> o;
    private String t;
    private ArrayList<Vec3d> w;
    private int u;
    private int h;
    
    public b() {
        super(new String[] { "build", "builder", "br" });
        this.w = new ArrayList<Vec3d>();
        this.u = 0;
        this.h = 6;
    }
    
    @Override
    public String o() {
        return "-builder/br <mode> [arg]";
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length == 1) {
            if (v[0].equalsIgnoreCase("stop")) {
                if (this.w.size() > 0) {
                    b.z.put(this.t, this.w);
                }
                this.w.clear();
                b.p = 0;
                return true;
            }
            if (v[0].equalsIgnoreCase("list")) {
                final StringBuilder v2 = new StringBuilder("Available configs: ");
                for (final String v3 : b.z.keySet()) {
                    v2.append(v3);
                    v2.append(" ");
                }
                f.b.q.c.o.i(v2.toString(), "red");
                return true;
            }
            if (b.z.containsKey(v[0])) {
                b.o = b.z.get(v[0]);
                b.p = 2;
                return true;
            }
        }
        if (v.length == 2) {
            if (v[0].equalsIgnoreCase("record")) {
                this.t = v[1];
                this.w.clear();
                b.p = 1;
                return true;
            }
            if (v[0].equalsIgnoreCase("loop")) {
                if (b.z.containsKey(v[1])) {
                    b.o = b.z.get(v[1]);
                    b.p = 3;
                    return true;
                }
                f.b.q.c.o.i("Config not found! Use 'br list' to see all configs", "red");
                return false;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent v) {
        if (b.p == 0) {
            this.u = 0;
            return;
        }
        if (b.p == 1) {
            this.t();
        }
        else if (b.p == 2) {
            this.w();
        }
        else if (b.p == 3) {
            this.u();
        }
    }
    
    private void t() {
        f.b.q.i.j.a(0.0f, 255.0f, 0.0f, 1.0f);
        for (final Vec3d v : this.w) {
            final BlockPos v2 = new BlockPos(this.mc.player.getPositionVector().add(v).x, this.mc.player.getPositionVector().add(v).y, this.mc.player.getPositionVector().add(v).z);
            final AxisAlignedBB v3 = f.b.q.i.j.g(v2);
            f.b.q.i.j.g(v3);
        }
        f.b.q.i.j.nv();
    }
    
    @SubscribeEvent
    public void a(final PlayerInteractEvent.RightClickBlock v) {
        if (b.p == 1) {
            final BlockPos v2 = this.mc.objectMouseOver.getBlockPos().offset(this.mc.objectMouseOver.sideHit);
            this.w.add(new Vec3d(v2.getX() - this.mc.player.getPositionVector().x, v2.getY() - this.mc.player.getPositionVector().y, v2.getZ() - this.mc.player.getPositionVector().z));
            f.b.q.c.o.bn("added block" + this.w.get(this.w.size() - 1).toString());
        }
    }
    
    private void w() {
        if (this.u % this.h != 0) {
            ++this.u;
            return;
        }
        final int v = this.mc.player.inventory.currentItem;
        final int v2 = n.nj();
        if (v2 == -1) {
            b.p = 0;
            f.b.q.c.o.i("Blocks were not found in your hotbar!", "red");
            return;
        }
        this.mc.player.inventory.currentItem = v2;
        for (final BlockPos v3 : this.h()) {
            if (this.mc.world.getBlockState(v3).getMaterial().isReplaceable()) {
                n.c(v3);
                f.b.q.c.o.bn("place");
                ++this.u;
                return;
            }
        }
        this.mc.player.inventory.currentItem = v;
        b.p = 0;
    }
    
    private void u() {
        if (this.u % this.h != 0) {
            ++this.u;
            return;
        }
        final int v = this.mc.player.inventory.currentItem;
        final int v2 = n.nj();
        if (v2 == -1) {
            b.p = 0;
            f.b.q.c.o.i("Blocks were not found in your hotbar!", "red");
            return;
        }
        this.mc.player.inventory.currentItem = v2;
        for (final BlockPos v3 : this.h()) {
            if (this.mc.world.getBlockState(v3).getMaterial().isReplaceable()) {
                n.c(v3);
                f.b.q.c.o.bn("place");
                ++this.u;
                return;
            }
        }
        this.mc.player.inventory.currentItem = v;
    }
    
    private ArrayList<BlockPos> h() {
        final ArrayList<BlockPos> v = new ArrayList<BlockPos>();
        for (final Vec3d v2 : b.o) {
            v.add(new BlockPos(this.mc.player.getPositionVector().add(v2)));
        }
        return v;
    }
    
    static {
        b.p = 0;
        b.z = new HashMap<String, ArrayList<Vec3d>>();
        b.o = new ArrayList<Vec3d>();
    }
}
