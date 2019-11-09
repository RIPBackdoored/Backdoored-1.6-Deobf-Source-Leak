package f.b.a;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import net.minecraft.block.properties.*;

public class j extends b
{
    public IBlockState iBlockState;
    public IBlockAccess iBlockAccess;
    public BlockPos blockPos;
    public CallbackInfoReturnable<AxisAlignedBB> gi;
    public PropertyDirection propertyDirection;
    
    public j(final IBlockState v, final IBlockAccess v, final BlockPos v, final PropertyDirection v, final CallbackInfoReturnable<AxisAlignedBB> v) {
        super();
        this.iBlockState = v;
        this.iBlockAccess = v;
        this.blockPos = v;
        this.propertyDirection = v;
        this.gi = v;
    }
}
