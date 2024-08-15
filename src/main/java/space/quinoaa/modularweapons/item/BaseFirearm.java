package space.quinoaa.modularweapons.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public abstract class BaseFirearm extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private List<Slot<?>> slots = new ArrayList<>();

    public BaseFirearm(Properties pProperties) {
        super(pProperties.stacksTo(1));

        initSlots();
        slots = List.copyOf(slots);
    }

    protected abstract void initSlots();

    public abstract void attack(Player plr, ItemStack stack, IntConsumer cooldown);

    public List<Slot<?>> getSlots(){
        return slots;
    }

    @SuppressWarnings("all")
    public <T extends BaseFirearmPart> @Nullable T getPart(ItemStack weapon, Slot<T> slot){
        if(slot != slots.get(slot.index)) throw new IllegalArgumentException("Slot is not from this item");
        var parts = weapon.getOrCreateTagElement("parts");

        String key = slot.index + "";
        if(!parts.contains(key, CompoundTag.TAG_STRING)) return null;

        var location = ResourceLocation.read(parts.getString(key)).result();
        if(location.isEmpty()) return null;

        var item = ForgeRegistries.ITEMS.getValue(location.get());

        return slot.isValid(item) ? (T) item : null;
    }

    @SuppressWarnings("all")
    public boolean trySetPart(ItemStack weapon, Slot<?> slot, Item part){
        if(slot != slots.get(slot.index)) throw new IllegalArgumentException("Slot is not from this item");

        if(part != null && !slot.isValid(part)) return false;
        var parts = weapon.getOrCreateTagElement("parts");

        String key = slot.index + "";
        if(part != null){
            parts.putString(key, ForgeRegistries.ITEMS.getKey(part).toString());
        }else{
            parts.remove(key);
        }
        return true;
    }

    public @Nullable ItemStack getAmmo(Player player){
        var inv = player.getInventory();

        var size = inv.getContainerSize();
        for (int i = 0; i < size; i++) {
            var item = inv.getItem(i);
            if(item.getItem() instanceof BaseAmmo){
                return item;
            }
        }
        return null;
    }


    protected <T extends BaseFirearmPart> Slot<T> createSlot(int x, int y, Class<T> type, Vec3 offset){
        var slot = new Slot<>(x, y, type, slots.size(), offset);
        slots.add(slot);
        return slot;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }

        });
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public abstract float getZoom(ItemStack stack);


    public record Slot<T extends BaseFirearmPart>(int x, int y, Class<T> type, int index, Vec3 offset) {
        public boolean isValid(ItemStack pStack) {
            return isValid(pStack.getItem());
        }

        public boolean isValid(Item item) {
            return type.isInstance(item);
        }
    }
}
