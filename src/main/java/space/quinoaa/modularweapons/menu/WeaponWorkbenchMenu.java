package space.quinoaa.modularweapons.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import space.quinoaa.modularweapons.block.entity.WeaponWorkbenchEntity;
import space.quinoaa.modularweapons.init.MWMenus;
import space.quinoaa.modularweapons.item.BaseFirearm;

public class WeaponWorkbenchMenu extends AbstractContainerMenu {
    public final Slot weaponSlot;


    public int weaponCenterX = 80, weaponCenterY = 70;

    public WeaponWorkbenchMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, (Container) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public WeaponWorkbenchMenu(int id, Inventory inventory, Container weaponWorkbench) {
        super(MWMenus.WEAPON_WORKBENCH.get(), id);

        weaponSlot = new Slot(weaponWorkbench, 0, 8, 108);
        addSlot(weaponSlot);

        int i = 37;
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
        }

    }



    public static void open(Player pPlayer, BlockPos pos) {
        if(!(pPlayer instanceof ServerPlayer srvPlr)) return;
        if(!(pPlayer.level().getBlockEntity(pos) instanceof WeaponWorkbenchEntity entity)) return;

        NetworkHooks.openScreen(srvPlr, new SimpleMenuProvider((id, inv, plr)->{
            return new WeaponWorkbenchMenu(id, inv, entity);
        }, Component.translatable("modularweapons.workbench.title")), buf->{
            buf.writeBlockPos(pos);
        });
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void clickWorkbenchSlot(BaseFirearm.Slot<?> slot) {
        var stack = weaponSlot.getItem();
        if(!(stack.getItem() instanceof BaseFirearm arm)) return;

        var current = arm.getPart(stack, slot);
        var carried = getCarried();
        if(arm.trySetPart(stack, slot, !carried.isEmpty() ? carried.getItem() : null)){
            setCarried(current != null ? new ItemStack(current) : ItemStack.EMPTY);
        }
    }

    public @Nullable BaseFirearm.Slot<?> getWeaponSlot(int index) {
        var stack = weaponSlot.getItem();
        if(!(stack.getItem() instanceof BaseFirearm arm) || index >= arm.getSlots().size() || index < 0) return null;
        return arm.getSlots().get(index);
    }


}
