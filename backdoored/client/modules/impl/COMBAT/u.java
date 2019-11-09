package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import f.b.q.*;
import net.minecraft.entity.item.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000x.*;
import java.util.function.*;
import net.minecraftforge.client.event.*;
import f.b.q.i.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@c$b(name = "Auto Crystal", description = "Auto Place Crystals, Modified Kami paste", category = f.b.o.c.c.COMBAT)
public class u extends c
{
    private f.b.f.c ko;
    private f.b.f.c kt;
    private f.b.f.c kw;
    private f.b.f.c ku;
    private f.b.f.c kh;
    private f.b.f.c kv;
    private f.b.f.c kd;
    private f.b.f.c ky;
    private f.b.f.c kj;
    private f.b.f.c pa;
    private BlockPos blockPos;
    private Entity entity;
    private long pr;
    
    public u() {
        super();
        this.ko = new f.b.f.c("Place Per Tick", this, 1, 0, 6);
        this.kt = new f.b.f.c("Place", this, true);
        this.kw = new f.b.f.c("Break", this, true);
        this.ku = new f.b.f.c("Switch", this, true);
        this.kh = new f.b.f.c("No Gapple Switch", this, true);
        this.kv = new f.b.f.c("Dont cancel mining", this, true);
        this.kd = new f.b.f.c("Place Range", this, 4.0, 0.0, 10.0);
        this.ky = new f.b.f.c("Break Range", this, 4.0, 0.0, 10.0);
        this.kj = new f.b.f.c("Raytrace Place Range", this, 3.0, 0.0, 10.0);
        this.pa = new f.b.f.c("Min Damage", this, 4.0, 0.0, 20.0);
        this.pr = -1L;
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        if (this.kw.cq()) {
            this.gq();
        }
        if (this.kt.cq()) {
            for (int v = 0; v < this.ko.cp(); ++v) {
                this.gf();
            }
        }
    }
    
    private void gf() {
        this.blockPos = null;
        this.entity = null;
        final boolean v = !this.kh.cq() || u.mc.player.getActiveItemStack().getItem() != Items.GOLDEN_APPLE;
        final boolean v2 = !this.kv.cq() || u.mc.player.getActiveItemStack().getItem() != Items.DIAMOND_PICKAXE;
        if (!v || !v2) {
            return;
        }
        final List<BlockPos> v3 = this.gk();
        final List<EntityPlayer> v4 = new ArrayList<EntityPlayer>();
        for (final EntityPlayer v5 : u.mc.world.playerEntities) {
            if (!f.b.q.x.c.g(v5)) {
                v4.add(v5);
            }
        }
        double v6 = 0.1;
        double v7 = 1000.0;
        BlockPos v8 = null;
        for (final EntityPlayer v9 : v4) {
            if (!v9.getUniqueID().equals(u.mc.player.getUniqueID())) {
                if (v9.isDead) {
                    continue;
                }
                for (final BlockPos v10 : v3) {
                    if (v9.getDistanceSq(v10) >= 169.0) {
                        continue;
                    }
                    final double v11 = a(v10.getX() + 0.5, v10.getY() + 1, v10.getZ() + 0.5, (Entity)v9) / 10.0f;
                    final double v12 = a(v10.getX() + 0.5, v10.getY() + 1, v10.getZ() + 0.5, (Entity)u.mc.player) / 10.0f;
                    if (v11 < this.pa.ck()) {
                        continue;
                    }
                    boolean v13 = true;
                    final RayTraceResult v14 = u.mc.world.rayTraceBlocks(new Vec3d(u.mc.player.posX, u.mc.player.posY + u.mc.player.getEyeHeight(), u.mc.player.posZ), new Vec3d(v10.getX() + 0.5, v10.getY() - 0.5, v10.getZ() + 0.5));
                    v13 = ((v14 != null && v14.typeOfHit == RayTraceResult.Type.BLOCK) || u.mc.player.getDistance((double)v10.getX(), (double)v10.getY(), (double)v10.getZ()) <= this.kj.ck());
                    final boolean v15 = u.mc.player.getDistance((double)v10.getX(), (double)v10.getY(), (double)v10.getZ()) <= this.kd.ck();
                    if (!v15 || !v13) {
                        continue;
                    }
                    if (v11 > v6) {
                        v6 = v11;
                        v7 = v12;
                        v8 = v10;
                        this.entity = (Entity)v9;
                        this.blockPos = v10;
                    }
                    else {
                        if (v11 != v6 || v12 >= v7) {
                            continue;
                        }
                        v6 = v11;
                        v7 = v12;
                        v8 = v10;
                        this.entity = (Entity)v9;
                        this.blockPos = v10;
                    }
                }
            }
        }
        if (v6 < this.pa.ck() || v8 == null) {
            return;
        }
        final boolean v16 = u.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || u.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        if (!v16 && !this.ku.cq()) {
            return;
        }
        if (v16) {
            final RayTraceResult v17 = u.mc.world.rayTraceBlocks(new Vec3d(u.mc.player.posX, u.mc.player.posY + u.mc.player.getEyeHeight(), u.mc.player.posZ), new Vec3d(v8.getX() + 0.5, v8.getY() - 0.5, v8.getZ() + 0.5));
            EnumFacing v18;
            if (v17 == null || v17.sideHit == null) {
                v18 = EnumFacing.UP;
            }
            else {
                v18 = v17.sideHit;
            }
            EnumHand v19 = EnumHand.MAIN_HAND;
            if (u.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                v19 = EnumHand.OFF_HAND;
            }
            final Vec3d v20 = new Vec3d((Vec3i)v8).add(0.5, 0.5, 0.5).add(new Vec3d(v18.getDirectionVec()).scale(0.5));
            u.mc.playerController.processRightClickBlock(u.mc.player, u.mc.world, v8, v18, v20, v19);
            u.mc.player.swingArm(v19);
            return;
        }
        final int v21 = n.b(Items.END_CRYSTAL);
        if (v21 >= 0) {
            u.mc.player.inventory.currentItem = v21;
        }
    }
    
    private void gq() {
        final EntityEnderCrystal v = (EntityEnderCrystal)u.mc.world.loadedEntityList.stream().filter(\u0000u::b).map(\u0000u::a).min(Comparator.comparing((Function<? super T, ? extends Comparable>)\u0000u::a)).orElse(null);
        if (v != null) {
            final double v2 = u.mc.player.getDistance((Entity)v);
            boolean v3 = true;
            final RayTraceResult v4 = u.mc.world.rayTraceBlocks(new Vec3d(u.mc.player.posX, u.mc.player.posY + u.mc.player.getEyeHeight(), u.mc.player.posZ), new Vec3d(v.posX + 0.5, v.posY - 0.5, v.posZ + 0.5));
            if (v4 != null && v4.typeOfHit == RayTraceResult.Type.BLOCK) {
                v3 = (v2 <= this.kj.ck());
            }
            final boolean v5 = v2 <= this.kd.ck();
            if (v5 && v3 && System.nanoTime() / 1000000L - this.pr >= 250L) {
                u.mc.playerController.attackEntity((EntityPlayer)u.mc.player, (Entity)v);
                u.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.pr = System.nanoTime() / 1000000L;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final RenderWorldLastEvent v) {
        j.a(255.0f, 255.0f, 255.0f, 1.0f);
        if (this.blockPos != null) {
            final AxisAlignedBB v2 = j.g(this.blockPos);
            j.g(v2);
        }
        if (this.entity != null) {
            j.g(this.entity.getEntityBoundingBox());
        }
        j.nv();
    }
    
    public void bm() {
    }
    
    public void bd() {
        this.blockPos = null;
        this.entity = null;
    }
    
    private List<BlockPos> gk() {
        final NonNullList<BlockPos> v = (NonNullList<BlockPos>)NonNullList.create();
        v.addAll((Collection)this.a(new BlockPos(Math.floor(u.mc.player.posX), Math.floor(u.mc.player.posY), Math.floor(u.mc.player.posZ)), (float)this.kd.ck(), (int)Math.round(this.kd.ck()), false, true, 0).stream().filter((Predicate<? super Object>)this::a).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)v;
    }
    
    private List<BlockPos> a(final BlockPos v, final float v, final int v, final boolean v, final boolean v, final int v) {
        final List<BlockPos> v2 = new ArrayList<BlockPos>();
        final int v3 = v.getX();
        final int v4 = v.getY();
        final int v5 = v.getZ();
        for (int v6 = v3 - (int)v; v6 <= v3 + v; ++v6) {
            for (int v7 = v5 - (int)v; v7 <= v5 + v; ++v7) {
                for (int v8 = v ? (v4 - (int)v) : v4; v8 < (v ? (v4 + v) : ((float)(v4 + v))); ++v8) {
                    final double v9 = (v3 - v6) * (v3 - v6) + (v5 - v7) * (v5 - v7) + (v ? ((v4 - v8) * (v4 - v8)) : 0);
                    if (v9 < v * v && (!v || v9 >= (v - 1.0f) * (v - 1.0f))) {
                        final BlockPos v10 = new BlockPos(v6, v8 + v, v7);
                        v2.add(v10);
                    }
                }
            }
        }
        return v2;
    }
    
    private boolean a(final BlockPos v) {
        final BlockPos v2 = v.add(0, 1, 0);
        final BlockPos v3 = v.add(0, 2, 0);
        return (u.mc.world.getBlockState(v).getBlock() == Blocks.BEDROCK || u.mc.world.getBlockState(v).getBlock() == Blocks.OBSIDIAN) && u.mc.world.getBlockState(v2).getBlock() == Blocks.AIR && u.mc.world.getBlockState(v3).getBlock() == Blocks.AIR && u.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(v2)).isEmpty();
    }
    
    private static float a(final double v, final double v, final double v, final Entity v) {
        final float v2 = 12.0f;
        final double v3 = v.getDistance(v, v, v) / 12.0;
        final Vec3d v4 = new Vec3d(v, v, v);
        final double v5 = v.world.getBlockDensity(v4, v.getEntityBoundingBox());
        final double v6 = (1.0 - v3) * v5;
        final float v7 = (float)(int)((v6 * v6 + v6) / 2.0 * 7.0 * 12.0 + 1.0);
        double v8 = 1.0;
        if (v instanceof EntityLivingBase) {
            v8 = a((EntityLivingBase)v, a(v7), new Explosion((World)u.mc.world, (Entity)null, v, v, v, 6.0f, false, true));
        }
        return (float)v8;
    }
    
    private static float a(final EntityLivingBase v, float v, final Explosion v) {
        if (v instanceof EntityPlayer) {
            final EntityPlayer v2 = (EntityPlayer)v;
            v = CombatRules.getDamageAfterAbsorb(v, (float)v2.getTotalArmorValue(), (float)v2.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return v;
        }
        v = CombatRules.getDamageAfterAbsorb(v, (float)v.getTotalArmorValue(), (float)v.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return v;
    }
    
    private static float a(final float v) {
        final int v2 = u.mc.world.getDifficulty().getId();
        return v * ((v2 == 0) ? 0.0f : ((v2 == 2) ? 1.0f : ((v2 == 1) ? 0.5f : 1.5f)));
    }
    
    private static /* synthetic */ Float a(final EntityEnderCrystal v) {
        return u.mc.player.getDistance((Entity)v);
    }
    
    private static /* synthetic */ EntityEnderCrystal a(final Entity v) {
        return (EntityEnderCrystal)v;
    }
    
    private static /* synthetic */ boolean b(final Entity v) {
        return v instanceof EntityEnderCrystal;
    }
}
