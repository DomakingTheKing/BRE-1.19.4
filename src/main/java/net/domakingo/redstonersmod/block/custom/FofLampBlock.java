package net.domakingo.redstonersmod.block.custom;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class FofLampBlock extends Block {
    public static final BooleanProperty LIT;
    public static final IntegerProperty VALUE = IntegerProperty.create("value", 1, 16);

    public FofLampBlock(BlockBehaviour.Properties p_55657_) {
        super(p_55657_);
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LIT, false).setValue(VALUE, 1));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_55659_) {
        return (BlockState)this.defaultBlockState().setValue(LIT, p_55659_.getLevel().hasNeighborSignal(p_55659_.getClickedPos()));
    }

    public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
        if (!p_55667_.isClientSide) {
            boolean flag = (Boolean)p_55666_.getValue(LIT);
            if (flag != p_55667_.hasNeighborSignal(p_55668_)) {
                if (flag) {
                    p_55667_.scheduleTick(p_55668_, this, 4);
                } else {
                    p_55667_.setBlock(p_55668_, (BlockState)p_55666_.cycle(LIT), 2);
                }
            }
        }

    }

    public void tick(BlockState p_221937_, ServerLevel p_221938_, BlockPos p_221939_, RandomSource p_221940_) {
        if ((Boolean)p_221937_.getValue(LIT) && !p_221938_.hasNeighborSignal(p_221939_)) {
            p_221938_.setBlock(p_221939_, (BlockState)p_221937_.cycle(LIT), 2);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(new Property[]{LIT, VALUE});
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            int currentValue = state.getValue(VALUE);
            int newValue = currentValue == 16 ? 1 : currentValue + 1;
            level.setBlock(pos, state.setValue(VALUE, newValue), 2);
        }
        return InteractionResult.SUCCESS;
    }

    static {
        LIT = RedstoneTorchBlock.LIT;
    }
}
