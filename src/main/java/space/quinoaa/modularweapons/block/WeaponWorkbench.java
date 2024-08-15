package space.quinoaa.modularweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import space.quinoaa.modularweapons.block.entity.WeaponWorkbenchEntity;
import space.quinoaa.modularweapons.init.MWItems;
import space.quinoaa.modularweapons.menu.WeaponWorkbenchMenu;

import java.util.ArrayList;
import java.util.List;

public class WeaponWorkbench extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty IS_MAIN = BooleanProperty.create("is_main");

    public WeaponWorkbench(Properties pProperties) {
        super(pProperties);

        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(IS_MAIN, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(IS_MAIN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(IS_MAIN) ? new WeaponWorkbenchEntity(pPos, pState) : null;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        if(!pState.getValue(IS_MAIN)) return new ArrayList<>();

        List<ItemStack> items = new ArrayList<>();

        if(pParams.getParameter(LootContextParams.BLOCK_ENTITY) instanceof WeaponWorkbenchEntity e){
            var item = e.getItem(0);
            if(!item.isEmpty()) items.add(item.copy());
        }
        items.add(new ItemStack(MWItems.WEAPON_WORKBENCH.get()));
        return items;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        if(!pState.getValue(IS_MAIN)) return RenderShape.INVISIBLE;
        return RenderShape.MODEL;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        super.neighborChanged(pState, pLevel, pPos, pNeighborBlock, pNeighborPos, pMovedByPiston);

        boolean isMain = pState.getValue(IS_MAIN);
        var dir = pState.getValue(FACING);
        Direction face = isMain ? dir.getClockWise() : dir.getCounterClockWise();

        var otherPos = pPos.relative(face);
        if(pLevel.getBlockState(otherPos) != pState.setValue(IS_MAIN, !isMain)) pLevel.destroyBlock(pPos, true);
    }

    

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if(!pState.getValue(IS_MAIN)) return;

        BlockPos second = pPos.relative(pState.getValue(FACING).getClockWise());
        pLevel.setBlock(second, pState.setValue(IS_MAIN, false), 3);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide) return InteractionResult.SUCCESS;
        BlockPos main = pPos;
        if(!pState.getValue(IS_MAIN)){
            main = pPos.relative(pState.getValue(FACING).getCounterClockWise());
        }

        WeaponWorkbenchMenu.open(pPlayer, main);

        return InteractionResult.CONSUME;
    }
}
