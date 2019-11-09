package f.b.a;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class ba extends b
{
    public final IBlockState iBlockState;
    public final IBlockAccess iBlockAccess;
    public final BlockPos blockPos;
    public final EnumFacing enumFacing;
    
    public ba(final IBlockState v, final IBlockAccess v, final BlockPos v, final EnumFacing v) {
        super();
        this.iBlockState = v;
        this.iBlockAccess = v;
        this.blockPos = v;
        this.enumFacing = v;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
