package io.github.teamgalacticraft.galacticraft.entity;

import io.github.teamgalacticraft.galacticraft.Constants;
import io.github.teamgalacticraft.galacticraft.blocks.machines.coalgenerator.CoalGeneratorBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author <a href="https://github.com/teamgalacticraft">TeamGalacticraft</a>
 */
public class GalacticraftBlockEntities {

    public static final BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_BLOCK_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY, new Identifier(Constants.MOD_ID, Constants.Blocks.COAL_GENERATOR_BLOCK), BlockEntityType.Builder.create(CoalGeneratorBlockEntity::new).build(null));
}
