package space.quinoaa.modularweapons.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import space.quinoaa.modularweapons.init.MWBlockEntities;

public class WeaponWorkbenchEntity extends BlockEntity implements Container {
    private ItemStack weapon = ItemStack.EMPTY;



    public WeaponWorkbenchEntity(BlockPos pPos, BlockState pBlockState) {
        super(MWBlockEntities.WEAPON_WORKBENCH.get(), pPos, pBlockState);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        weapon = ItemStack.EMPTY;
        if(pTag.contains("content", CompoundTag.TAG_COMPOUND)) weapon = ItemStack.of(pTag.getCompound("content"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("content", weapon.save(new CompoundTag()));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return getItem(0).serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        setItem(0, ItemStack.of(tag));
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return weapon.isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return weapon;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        var weapon = this.weapon.copyWithCount(pAmount);
        this.weapon.shrink(pAmount);
        return weapon;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        var weapon = this.weapon;
        this.weapon = ItemStack.EMPTY;
        return weapon;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        weapon = pStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        weapon = ItemStack.EMPTY;
    }
}
