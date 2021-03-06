package io.github.teamgalacticraft.galacticraft.blocks.machines.coalgenerator;

import alexiil.mc.lib.attributes.item.impl.PartialInventoryFixedWrapper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

/**
 * @author <a href="https://github.com/teamgalacticraft">TeamGalacticraft</a>
 */
public class CoalGeneratorContainer extends Container {

    private ItemStack itemStack;
    private Inventory inventory;

    private BlockPos blockPos;
    private CoalGeneratorBlockEntity generator;
    private PlayerEntity playerEntity;


    @Override
    public ItemStack transferSlot(PlayerEntity playerEntity, int slotId) {

        ItemStack itemStack = null;
        Slot slot = this.slotList.get(slotId);

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (slotId < this.generator.inventory.getSlotCount()) {

                if (!this.insertItem(itemStack1, this.inventory.getInvSize(), this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.insertItem(itemStack1, 0, this.inventory.getInvSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack1.getAmount() == 0) {
                slot.setStack(ItemStack.EMPTY);
            }
            else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    public CoalGeneratorContainer(int syncId, BlockPos blockPos, PlayerEntity playerEntity) {
        super(null, syncId);
        this.blockPos = blockPos;
        this.playerEntity = playerEntity;

        BlockEntity blockEntity = playerEntity.world.getBlockEntity(blockPos);

        if (!(blockEntity instanceof CoalGeneratorBlockEntity)) {
            // TODO: Move this logic somewhere else to just not open this at all.
            throw new IllegalStateException("Found " + blockEntity + " instead of a coal generator!");
        }
        this.generator = (CoalGeneratorBlockEntity) blockEntity;
        this.inventory = new PartialInventoryFixedWrapper(generator.inventory) {
            @Override
            public void markDirty() {
                generator.markDirty();
            }

            @Override
            public boolean canPlayerUseInv(PlayerEntity player) {
                return CoalGeneratorContainer.this.canUse(player);
            }
        };
        // Coal Generator fuel slot
        this.addSlot(new CoalGeneratorFuelSlot(this, this.inventory, 0, 8, 53));

        // Player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerEntity.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Hotbar slots
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerEntity.inventory, i, 8 + i * 18, 142));
        }

    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return true;
    }

    public class CoalGeneratorFuelSlot extends Slot {
        private final CoalGeneratorContainer container;

        public CoalGeneratorFuelSlot(CoalGeneratorContainer container, Inventory inventory, int int_1, int int_2, int int_3) {
            super(inventory, int_1, int_2, int_3);
            this.container = container;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL || stack.getItem() == Items.COAL_BLOCK;
        }
    }
}
